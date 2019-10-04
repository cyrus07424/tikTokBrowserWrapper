package jp.cyrus.tikTokBrowserWrapper.utils;

import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import jp.cyrus.tikTokBrowserWrapper.TikTokBrowserWrapper;
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
	 * @param tikTokBrowserWrapper
	 * @param url
	 * @param referer
	 * @param valueType
	 * @return
	 */
	public static <T> T getHttpResponse(TikTokBrowserWrapper tikTokBrowserWrapper, String url, String referer,
			Class<T> valueType) {
		try {
			LogHelper.debug("Get : " + url);
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("User-Agent", Configurations.HTTP_CLIENT_USER_AGENT);
			httpGet.setHeader("Referer", referer);
			httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
			httpGet.setHeader("Accept", "*/*");
			httpGet.setHeader("Accept-Language", "en-US,en;q=0.5");
			httpGet.setHeader("Accept-Encoding", "gzip");
			httpGet.setHeader("Pragma", "no-cache");
			httpGet.setHeader("Cache-Control", "no-cache");
			try (CloseableHttpClient client = getHttpClient(tikTokBrowserWrapper);
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
	 * @param tikTokBrowserWrapper
	 * @return
	 */
	private static CloseableHttpClient getHttpClient(TikTokBrowserWrapper tikTokBrowserWrapper) {
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
				.setDefaultCookieStore(getCookieStore(tikTokBrowserWrapper))
				.build();
	}

	/**
	 * Get CookieStore.
	 *
	 * @param tikTokBrowserWrapper
	 * @return
	 */
	private static BasicCookieStore getCookieStore(TikTokBrowserWrapper tikTokBrowserWrapper) {
		BasicCookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie phpSessIdCookie = new BasicClientCookie("PHPSESSID", tikTokBrowserWrapper.phpSessId);
		phpSessIdCookie.setDomain("tiktokapi.ga");
		phpSessIdCookie.setPath("/");
		phpSessIdCookie.setSecure(true);
		cookieStore.addCookie(phpSessIdCookie);
		BasicClientCookie sesCookie = new BasicClientCookie("ses", tikTokBrowserWrapper.ses);
		sesCookie.setDomain("tiktokapi.ga");
		sesCookie.setPath("/");
		sesCookie.setSecure(true);
		cookieStore.addCookie(sesCookie);
		return cookieStore;
	}
}