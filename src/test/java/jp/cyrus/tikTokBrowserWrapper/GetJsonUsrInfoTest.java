package jp.cyrus.tikTokBrowserWrapper;

import jp.cyrus.tikTokBrowserWrapper.models.JsonUsrInfo;

/**
 * Get JsonUsrInfo test.
 *
 * @author cyrus
 */
public class GetJsonUsrInfoTest {

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
		// Get JsonUsrInfo data
		JsonUsrInfo jsonUsrInfo = TikTokBrowserWrapper.getJsonUsrInfo(USER_ID);
		System.out.println(jsonUsrInfo);
	}
}