package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn;

/**
 * SVNリポジトリを管理するためのクラス
 * 
 * @author k-hotta
 * 
 */
public class SVNRepositoryManager {

	/**
	 * リポジトリURLの文字列表記
	 */
	private final String urlStr;

	/**
	 * シングルトンオブジェクト
	 */
	private static SVNRepositoryManager SINGLETON = null;

	private SVNRepositoryManager(final String urlStr) {
		this.urlStr = urlStr;
	}

	public static SVNRepositoryManager setup(final String urlRoot,
			final String additionalUrl) throws Exception {
		if (SINGLETON != null) {
			return SINGLETON;
		}

		final String urlStr = (additionalUrl == null) ? urlRoot : urlRoot
				+ additionalUrl;

		final RepositoryCreator creator = RepositoryCreator
				.getCorrespondingInstance(urlStr);
		
		SINGLETON = new SVNRepositoryManager(urlStr);
		
		return SINGLETON;
	}

}
