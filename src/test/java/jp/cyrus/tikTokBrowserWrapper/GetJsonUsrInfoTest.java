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
	private static final long USER_ID = 115974592602513408L;

	/**
	 * PHPSESSID.
	 */
	private static final String PHPSESSID = "CHANGEME";

	/**
	 * ses.
	 */
	private static final String SES = "CHANGEME";

	/**
	 * main.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// Get JsonUsrInfo data
		JsonUsrInfo jsonUsrInfo = new TikTokBrowserWrapper(PHPSESSID, SES).getJsonUsrInfo(USER_ID);
		System.out.println(jsonUsrInfo);
	}
}