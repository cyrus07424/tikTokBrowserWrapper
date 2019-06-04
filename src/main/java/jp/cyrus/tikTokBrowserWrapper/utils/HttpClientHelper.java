package jp.cyrus.tikTokBrowserWrapper.utils;

import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import jp.cyrus.tikTokBrowserWrapper.constants.Configurations;

/**
 * Http client helper.
 *
 * @author cyrus
 */
public class HttpClientHelper {

	/**
	 * Retry interval millis.
	 */
	private static final int RETRY_INTERVAL_MILLI_SEC = 60 * 1000;

	/**
	 * Max retry count.
	 */
	private static final int MAX_RETRY_COUNT = 10;

	/**
	 * Get HTTP response.
	 *
	 * @param url
	 * @param referer
	 * @param valueType
	 * @return
	 */
	public static <T> T getHttpResponse(String url, String referer, Class<T> valueType) {
		try {
			LogHelper.debug("Get : " + url);
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("User-Agent", Configurations.HTTP_CLIENT_USER_AGENT);
			httpGet.setHeader("Referer", referer);
			httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
			httpGet.setHeader("Accept-Language", "ja,en-US;q=0.7,en;q=0.3");
			httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
			try (CloseableHttpClient client = getHttpClient();
					CloseableHttpResponse response = client.execute(httpGet)) {
				LogHelper.debug(response);
				LogHelper.debug("code = " + response.getStatusLine().getStatusCode());
				String responseString = EntityUtils.toString(response.getEntity());
				LogHelper.debug("response = " + responseString);
				return JsonHelper.getObjectMapper().readValue(responseString, valueType);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get HttpClient.<br>
	 * https://qiita.com/kgyamaryllis/items/531abacdc83f72239332.
	 *
	 * @return
	 */
	private static CloseableHttpClient getHttpClient() {
		return HttpClientBuilder.create()
				.setRetryHandler(new DefaultHttpRequestRetryHandler())
				.setServiceUnavailableRetryStrategy(new ServiceUnavailableRetryStrategy() {

					@Override
					public boolean retryRequest(
							final HttpResponse response, final int executionCount, final HttpContext context) {
						int statusCode = response.getStatusLine().getStatusCode();
						return Arrays.asList(HttpStatus.SC_SERVICE_UNAVAILABLE).contains(statusCode)
								&& executionCount < MAX_RETRY_COUNT;
					}

					@Override
					public long getRetryInterval() {
						return RETRY_INTERVAL_MILLI_SEC;
					}
				})
				.build();
	}
}