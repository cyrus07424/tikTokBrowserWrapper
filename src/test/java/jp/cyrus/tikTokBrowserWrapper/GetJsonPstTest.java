package jp.cyrus.tikTokBrowserWrapper;

import jp.cyrus.tikTokBrowserWrapper.models.JsonPst;
import jp.cyrus.tikTokBrowserWrapper.models.tikTok.Aweme;

/**
 * Get JsonPst test.
 *
 * @author cyrus
 */
public class GetJsonPstTest {

	/**
	 * User ID.
	 */
	private static final long USER_ID = 6685132210997167106L;

	/**
	 * main.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// Get JsonPst data
		JsonPst jsonPst = TikTokBrowserWrapper.getJsonPst(USER_ID);
		System.out.println(jsonPst);

		for (Aweme aweme : jsonPst.awemeList) {
			System.out.println(aweme);
		}
	}
}