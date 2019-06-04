package jp.cyrus.tikTokBrowserWrapper;

import jp.cyrus.tikTokBrowserWrapper.models.JsonPst;
import jp.cyrus.tikTokBrowserWrapper.models.JsonUsrInfo;
import jp.cyrus.tikTokBrowserWrapper.utils.HttpClientHelper;

/**
 * TikTokBrowserWrapper.
 *
 * @author cyrus
 */
public abstract class TikTokBrowserWrapper {

	/**
	 * Get JsonUsrInfo.
	 *
	 * @param userId
	 * @return
	 */
	public static JsonUsrInfo getJsonUsrInfo(long userId) {
		String url = String.format("https://tiktokapi.ga/php/jsonusrinfo.php?uid=%d", userId);
		String referer = getUserPostsUrl(userId);
		return HttpClientHelper.getHttpResponse(url, referer, JsonUsrInfo.class);
	}

	/**
	 * Get JsonPst.
	 *
	 * @param userId
	 * @return
	 */
	public static JsonPst getJsonPst(long userId) {
		return getJsonPst(userId, 0);
	}

	/**
	 * Get JsonPst.
	 *
	 * @param userId
	 * @param cursor
	 * @return
	 */
	public static JsonPst getJsonPst(long userId, long cursor) {
		String url = String.format("https://tiktokapi.ga/php/jsonpst.php?uid=%d&cursor=%d", userId, cursor);
		String referer = getUserPostsUrl(userId);
		return HttpClientHelper.getHttpResponse(url, referer, JsonPst.class);
	}

	/**
	 * Get JsonLikes.
	 *
	 * @param userId
	 * @param cursor
	 * @param mode
	 * @return
	 */
	public static JsonPst getJsonLikes(long userId, long cursor, int mode) {
		String url = String.format("https://tiktokapi.ga/php/jsonlikes.php?uid=%d&cursor=%d&mode=%d", userId, cursor,
				mode);
		String referer = getUserPostsUrl(userId);
		return HttpClientHelper.getHttpResponse(url, referer, JsonPst.class);
	}

	/**
	 * Get User posts URL.
	 *
	 * @param userId
	 * @return
	 */
	private static String getUserPostsUrl(Long userId) {
		return String.format("https://tiktokapi.ga/user_posts.php?user_id=%d", userId);
	}
}