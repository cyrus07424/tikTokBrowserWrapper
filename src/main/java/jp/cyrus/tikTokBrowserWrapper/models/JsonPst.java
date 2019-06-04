
package jp.cyrus.tikTokBrowserWrapper.models;

import java.util.List;

import jp.cyrus.tikTokBrowserWrapper.models.tikTok.Aweme;
import jp.cyrus.tikTokBrowserWrapper.models.tikTok.Extra;
import jp.cyrus.tikTokBrowserWrapper.models.tikTok.LogPb;

/**
 * JsonPst.
 *
 * @author cyrus
 */
public class JsonPst {

	public Long maxCursor;

	public Long hasMore;

	public List<Aweme> awemeList;

	public Extra extra;

	public LogPb logPb;

	public Long statusCode;

	public Long minCursor;
}