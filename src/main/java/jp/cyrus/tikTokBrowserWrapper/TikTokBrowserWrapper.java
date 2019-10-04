package jp.cyrus.tikTokBrowserWrapper;

import jp.cyrus.tikTokBrowserWrapper.constants.Commons;
import jp.cyrus.tikTokBrowserWrapper.models.JsonHash;
import jp.cyrus.tikTokBrowserWrapper.models.JsonPst;
import jp.cyrus.tikTokBrowserWrapper.models.JsonUsr;
import jp.cyrus.tikTokBrowserWrapper.models.JsonUsrInfo;
import jp.cyrus.tikTokBrowserWrapper.utils.HttpClientHelper;

/**
 * TikTokBrowserWrapper.
 *
 * @author cyrus
 */
public class TikTokBrowserWrapper {

	/**
	 * PHPSESSID cookie value.
	 */
	public final String phpSessId;

	/**
	 * ses cookie value.
	 */
	public final String ses;

	/**
	 * Constructor.
	 *
	 * @param phpSessId
	 * @param ses
	 */
	public TikTokBrowserWrapper(String phpSessId, String ses) {
		this.phpSessId = phpSessId;
		this.ses = ses;
	}

	/**
	 * Get JsonUsr.
	 *
	 * @param q
	 * @param cursor
	 * @return
	 */
	public JsonUsr getJsonUsr(String q, long cursor) {
		String url = String.format("https://tiktokapi.ga/site/php/jsonusr.php?q=%s&cursor=%d&u=%s",
				q, cursor, ses);
		return HttpClientHelper.getHttpResponse(this, url, Commons.REFERER, JsonUsr.class);
	}

	/**
	 * Get JsonUsrInfo.
	 *
	 * @param userId
	 * @return
	 */
	public JsonUsrInfo getJsonUsrInfo(long userId) {
		String url = String.format("https://tiktokapi.ga/site/php/jsonusrinfo.php?uid=%d&u=%s",
				userId, ses);
		return HttpClientHelper.getHttpResponse(this, url, Commons.REFERER, JsonUsrInfo.class);
	}

	/**
	 * Get JsonPst.
	 *
	 * @param userId
	 * @return
	 */
	public JsonPst getJsonPst(long userId) {
		return getJsonPst(userId, 0);
	}

	/**
	 * Get JsonPst.
	 *
	 * @param userId
	 * @param cursor
	 * @return
	 */
	public JsonPst getJsonPst(long userId, long cursor) {
		String url = String.format("https://tiktokapi.ga/site/php/jsonpst.php?uid=%d&cursor=%d&u=%s",
				userId, cursor, ses);
		return HttpClientHelper.getHttpResponse(this, url, Commons.REFERER, JsonPst.class);
	}

	/**
	 * Get JsonLikes.
	 *
	 * @param userId
	 * @param cursor
	 * @return
	 */
	public JsonPst getJsonLikes(long userId, long cursor) {
		String url = String.format("https://tiktokapi.ga/site/php/jsonpstlikes.php?uid=%d&cursor=%d&u=%s",
				userId, cursor, ses);
		return HttpClientHelper.getHttpResponse(this, url, Commons.REFERER, JsonPst.class);
	}

	/**
	 * Get JsonHashPst.
	 *
	 * @param challengeId
	 * @param cursor
	 * @param mode
	 * @return
	 */
	public JsonPst getJsonHashPst(long challengeId, long cursor, int mode) {
		String url = String.format("https://tiktokapi.ga/site/php/jsonhashpst.php?cid=%d&cursor=%d&mode=%d&u=%s",
				challengeId, cursor, mode, ses);
		return HttpClientHelper.getHttpResponse(this, url, Commons.REFERER, JsonPst.class);
	}

	/**
	 * Get JsonHash.
	 *
	 * @param q
	 * @param cursor
	 * @return
	 */
	public JsonHash getJsonHash(String q, long cursor) {
		String url = String.format("https://tiktokapi.ga/site/php/jsonhash.php?q=%s&cursor=%d&u=%s",
				q, cursor, ses);
		return HttpClientHelper.getHttpResponse(this, url, Commons.REFERER, JsonHash.class);
	}
}