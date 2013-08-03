package jp.ac.osaka_u.ist.sdl.prevol.vectorlinker;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;

/**
 * 連続する2つのリビジョンのペアを特定する
 * 
 * @author k-hotta
 * 
 */
public class RevisionPairDetector {

	public static Map<RevisionData, RevisionData> detectRevisionPairs(
			final List<RevisionData> revisions) {
		// 対象リビジョンが1つしかない場合は何もできない
		if (revisions.size() < 2) {
			return new TreeMap<RevisionData, RevisionData>();
		}

		final Map<RevisionData, RevisionData> result = new TreeMap<RevisionData, RevisionData>();
		RevisionData previousRevision = revisions.get(0);
		for (final RevisionData revision : revisions) {
			// 最初の要素は無視
			if (revision == previousRevision) {
				continue;
			}

			result.put(previousRevision, revision);
			previousRevision = revision;
		}
		
		return Collections.unmodifiableMap(result);
	}
}
