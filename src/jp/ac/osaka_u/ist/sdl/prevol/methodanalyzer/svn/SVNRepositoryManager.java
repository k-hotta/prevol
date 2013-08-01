package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import jp.ac.osaka_u.ist.sdl.prevol.setting.Language;

import org.tmatesoft.svn.core.ISVNDirEntryHandler;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCClient;

/**
 * SVNリポジトリを管理するためのクラス
 * 
 * @author k-hotta
 * 
 */
public class SVNRepositoryManager {

	/**
	 * リポジトリのURL
	 */
	private final SVNURL url;

	/**
	 * 管理対象リポジトリ
	 */
	private final SVNRepository repository;

	/**
	 * リポジトリURLの文字列表記
	 */
	private final String urlStr;

	/**
	 * リポジトリにアクセスする際のユーザ名
	 */
	private final String userName;

	/**
	 * リポジトリにアクセスする際のパスワード
	 */
	private final String passwd;

	/**
	 * 追加URL
	 */
	private final String additionalUrl;

	/**
	 * シングルトンオブジェクト
	 */
	private static SVNRepositoryManager SINGLETON = null;

	private SVNRepositoryManager(final SVNURL url,
			final SVNRepository repository, final String urlStr,
			final String userName, final String passwd,
			final String additionalUrl) {
		this.url = url;
		this.repository = repository;
		this.urlStr = urlStr;
		this.userName = userName;
		this.passwd = passwd;
		this.additionalUrl = additionalUrl;
	}

	/**
	 * リポジトリをセットアップする
	 * 
	 * @param urlRoot
	 * @param additionalUrl
	 * @param userName
	 * @param passwd
	 * @return
	 * @throws Exception
	 */
	public static SVNRepositoryManager setup(final String urlRoot,
			final String additionalUrl, final String userName,
			final String passwd) throws Exception {
		if (SINGLETON != null) {
			return SINGLETON;
		}

		final String urlStr = (additionalUrl == null) ? urlRoot : urlRoot
				+ additionalUrl;
		final SVNURL url = SVNURL.parseURIDecoded(urlStr);

		final RepositoryCreator creator = RepositoryCreator
				.getCorrespondingInstance(urlStr);

		final SVNRepository repository = creator.create(url, userName, passwd);

		SINGLETON = new SVNRepositoryManager(url, repository, urlStr, userName,
				passwd, additionalUrl);

		return SINGLETON;
	}

	/**
	 * ユーザ名，パスワードの指定なしでリポジトリをセットアップする
	 * 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static SVNRepositoryManager setup(final String urlStr)
			throws Exception {
		return setup(urlStr, null, null, null);
	}

	private static void validate() throws RepositoryNotInitializedException {
		if (SINGLETON == null) {
			throw new RepositoryNotInitializedException(
					"The repository is not initialized. Call SVNRepositoryManager.setup(urlstr, name, passwd)");
		}
	}

	public static SVNRepository getRepository()
			throws RepositoryNotInitializedException {
		validate();
		return SINGLETON.repository;
	}

	public static SVNURL getURL() throws RepositoryNotInitializedException {
		validate();
		return SINGLETON.url;
	}

	public static long getLatestRevision()
			throws RepositoryNotInitializedException, SVNException {
		validate();
		return SINGLETON.repository.getLatestRevision();
	}

	public static synchronized String getFileContents(final long revisionNum,
			final String path) throws RepositoryNotInitializedException,
			SVNException {
		validate();
		final StringBuilder builder = new StringBuilder();
		final String normalizedPath = (path == null) ? "" : path;
		final SVNURL target = SINGLETON.url.appendPath(normalizedPath, false);
		final SVNWCClient wcClient = SVNClientManager.newInstance(null,
				SINGLETON.userName, SINGLETON.passwd).getWCClient();
		wcClient.doGetFileContents(target, SVNRevision.create(revisionNum),
				SVNRevision.create(revisionNum), false, new OutputStream() {

					@Override
					public void write(int b) throws IOException {
						builder.append((char) b);
					}

				});

		return builder.toString();
	}

	public static synchronized List<String> getListOfSourceFiles(
			final long revisionNum, final Language lang)
			throws RepositoryNotInitializedException, SVNException {
		return getListOfSourceFiles(revisionNum, lang, null);
	}

	public static synchronized List<String> getListOfSourceFiles(
			final long revisionNum, final Language lang,
			final Collection<String> targets) throws SVNException,
			RepositoryNotInitializedException {
		validate();

		final SVNLogClient logClient = SVNClientManager.newInstance(null,
				SINGLETON.userName, SINGLETON.passwd).getLogClient();

		final List<String> result = new ArrayList<String>();
		logClient.doList(SINGLETON.url, SVNRevision.create(revisionNum),
				SVNRevision.create(revisionNum), true, SVNDepth.INFINITY,
				SVNDirEntry.DIRENT_ALL, new ISVNDirEntryHandler() {

					@Override
					public void handleDirEntry(SVNDirEntry dirEntry)
							throws SVNException {
						final String path = (SINGLETON.additionalUrl == null) ? dirEntry
								.getRelativePath() : SINGLETON.additionalUrl
								+ dirEntry.getRelativePath();

						if (lang.isTarget(path)) {
							if (targets == null) {
								result.add(dirEntry.getRelativePath());
							} else {
								for (final String target : targets) {
									if (path.contains(target)) {
										result.add(dirEntry.getRelativePath());
										break;
									}
								}
							}
						}
					}

				});

		return Collections.unmodifiableList(result);
	}

	public static String doDiff(final long beforeRevNum, final long afterRevNum)
			throws RepositoryNotInitializedException, SVNException {
		validate();

		final SVNDiffClient diffClient = SVNClientManager.newInstance(null,
				SINGLETON.userName, SINGLETON.passwd).getDiffClient();
		final StringBuilder diffText = new StringBuilder();
		diffClient.doDiff(SINGLETON.url, SVNRevision.create(beforeRevNum),
				SINGLETON.url, SVNRevision.create(afterRevNum),
				SVNDepth.INFINITY, true, new OutputStream() {
					@Override
					public void write(int arg0) throws IOException {
						diffText.append((char) arg0);
					}
				});

		return diffText.toString();
	}

}
