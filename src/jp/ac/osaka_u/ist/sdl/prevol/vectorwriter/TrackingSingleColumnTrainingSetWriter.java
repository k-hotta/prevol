package jp.ac.osaka_u.ist.sdl.prevol.vectorwriter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorData;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorGenealogy;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorPairData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;

/**
 * メソッドのトレースをして，期間内に指定回数以上修正されたものについての SingleColumnTrainingSet を出力するクラス
 * 
 * @author k-hotta
 * 
 */
public class TrackingSingleColumnTrainingSetWriter extends
		SingleColumnTrainingSetWriter {

	public TrackingSingleColumnTrainingSetWriter(VectorWriterSettings settings) {
		super(settings);
	}

	@Override
	public void write() throws Exception {
		final RevisionData startRevision = DBConnection
				.getInstance()
				.getRevisionRetriever()
				.getOldestRevisionAfterSpecifiedRevision(
						settings.getStartRevision());
		final RevisionData endRevision = DBConnection
				.getInstance()
				.getRevisionRetriever()
				.getLatestRevisionBeforeSpecifiedRevision(
						settings.getEndRevision());

		final long startRevisionId = startRevision.getId();
		final long endRevisionId = endRevision.getId();

		final Set<VectorGenealogy> genealogies = retrieveGenealogies(
				startRevisionId, endRevisionId);
		final Map<Long, VectorData> vectorsMap = retrieveStartAndEndVectors(genealogies);

		final List<Integer> ignoreList = getIgnoreColumnsList(vectorsMap
				.values());

		final Set<VectorPairData> placeboVectorPairs = new TreeSet<VectorPairData>();
		for (final VectorGenealogy genealogy : genealogies) {
			placeboVectorPairs.add(new VectorPairData(genealogy
					.getStartRevisionId(), genealogy.getEndRevisionId(), -1,
					-1, genealogy.getStartVectorId(), genealogy
							.getEndVectorId()));
		}

		writeElements(placeboVectorPairs, vectorsMap, ignoreList);
	}
}
