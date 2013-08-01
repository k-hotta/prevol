package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jp.ac.osaka_u.ist.sdl.prevol.setting.Language;

import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.io.SVNRepository;

/**
 * リビジョン間で修正されたファイルを特定するクラス
 * 
 * @author k-hotta
 * 
 */
public class ChangedFilesDetector {

	private final Language language;

	public ChangedFilesDetector(final Language language) {
		this.language = language;
	}

	public Map<String, Character> detectChangedFiles(final long revNum) throws SVNException,
			RepositoryNotInitializedException {
		final Map<String, Character> result = new HashMap<String, Character>();

		final SVNRepository repository = SVNRepositoryManager.getRepository();

		repository.log(null, revNum, revNum, true, false,
				new ISVNLogEntryHandler() {
					public void handleLogEntry(SVNLogEntry logEntry)
							throws SVNException {

						for (final Map.Entry<String, SVNLogEntryPath> entry : logEntry
								.getChangedPaths().entrySet()) {

							// in the case that source files are updated
							if (language.isTarget(entry.getKey())) {
								result.put(entry.getKey().substring(1), entry
										.getValue().getType());
								continue;
							}

							// in the case that directories are deleted
							else if (('D' == entry.getValue().getType())
									|| ('R' == entry.getValue().getType())) {
								result.put(entry.getKey().substring(1), entry
										.getValue().getType());
								continue;
							}
						}
					}
				});

		return Collections.unmodifiableMap(result);
	}
	
}
