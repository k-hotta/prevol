package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.setting.Language;

import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.io.SVNRepository;

/**
 * 解析対象となるリビジョンを特定するクラス
 * 
 * @author k-hotta
 * 
 */
public class TargetRevisionDetector {

	/**
	 * 解析対象リビジョンを特定
	 * 
	 * @param language
	 * @param startRevisionNum
	 * @param endRevisionNum
	 * @return
	 * @throws Exception
	 */
	public static List<RevisionData> detectTargetRevisions(
			final Language language, final long startRevisionNum,
			final long endRevisionNum) throws Exception {
		final SVNRepository repository = SVNRepositoryManager.getRepository();

		final long latestRevisionNum = repository.getLatestRevision();
		final long selectedEndRevisionNum = Math.min(endRevisionNum, latestRevisionNum);
		
		final SortedSet<Long> revisions = new TreeSet<Long>();	
		repository.log(null, startRevisionNum, selectedEndRevisionNum, true, false,
				new ISVNLogEntryHandler() {
					public void handleLogEntry(SVNLogEntry logEntry)
							throws SVNException {

						for (final Map.Entry<String, SVNLogEntryPath> entry : logEntry
								.getChangedPaths().entrySet()) {

							// 対象ソースファイルが変更されている場合
							if (language.isTarget(entry.getKey())) {
								final long revision = logEntry.getRevision();
								revisions.add(revision);
								break;
							}

							// ディレクトリの削除の可能性がある場合は，対象リビジョンに追加
							else if (('D' == entry.getValue().getType())
									|| ('R' == entry.getValue().getType())) {
								final long revision = logEntry.getRevision();
								revisions.add(revision);
								break;
							}
						}
					}
				});

		final List<RevisionData> revisionInstances = new ArrayList<RevisionData>();
		for (final long revision : revisions) {
			revisionInstances.add(new RevisionData(revision));
		}

		return Collections.unmodifiableList(revisionInstances);
	}

}
