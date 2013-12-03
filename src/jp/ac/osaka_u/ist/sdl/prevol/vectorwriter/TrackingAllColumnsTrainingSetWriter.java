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
import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinter;

/**
 * メソッドのトレースをして，期間内に指定回数以上修正されたものについての AllColumnsTrainingSet を出力するクラス
 * 
 * @author k-hotta
 * 
 */
public class TrackingAllColumnsTrainingSetWriter extends
		AllColumnsTrainingSetWriter {

	public TrackingAllColumnsTrainingSetWriter(VectorWriterSettings settings) {
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

		int maxChanged = 0;
		for (final VectorGenealogy genealogy : genealogies) {
			final int changed = genealogy.getNumberOfChanged(startRevisionId,
					endRevisionId);
			if (changed > maxChanged) {
				maxChanged = changed;
			}
		}

		final Map<Long, VectorData> vectorsMap = retrieveStartAndEndVectors(
				genealogies, maxChanged, startRevisionId, endRevisionId);

		final List<Integer> ignoreList = getIgnoreColumnsList(false,
				vectorsMap.values());

		for (int i = 1; i <= maxChanged; i++) {
			process(genealogies, i, vectorsMap, ignoreList, startRevisionId);
		}
	}

	private void process(final Set<VectorGenealogy> genealogies,
			final int changeCount, final Map<Long, VectorData> vectorsMap,
			final List<Integer> ignoreList, final long startRevisionId)
			throws Exception {
		final String outputFilePath = getOutputFileName(
				settings.getOutputFilePath(), changeCount);

		final Set<VectorPairData> placeboVectorPairs = new TreeSet<VectorPairData>();
		for (final VectorGenealogy genealogy : genealogies) {
			final long beforeVectorId = genealogy
					.getStartVectorAfterSpecifiedRevision(startRevisionId);
			final long afterVectorId = genealogy
					.getVectorAfterSpecifiedRevision(startRevisionId,
							changeCount);
			if (beforeVectorId != -1 && afterVectorId != -1) {
				placeboVectorPairs.add(new VectorPairData(genealogy
						.getStartRevisionId(), genealogy.getEndRevisionId(),
						-1, -1, beforeVectorId, afterVectorId));
			}
		}

		MessagePrinter.stronglyPrintln("processing " + changeCount
				+ " changed vector genealogies ...");
		writeElements(placeboVectorPairs, vectorsMap, ignoreList,
				outputFilePath);
	}

	private final String getOutputFileName(final String originalPath,
			final int changeCount) {
		final int suffixLength = settings.getOutputFileFormat()
				.getSuffixLength();
		final String exceptSuffix = originalPath.substring(0,
				originalPath.length() - suffixLength);
		return exceptSuffix + "-" + changeCount
				+ settings.getOutputFileFormat().getSuffix();
	}
}
