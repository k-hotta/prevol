package jp.ac.osaka_u.ist.sdl.prevol.vectorwriter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorData;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorGenealogy;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorPairData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;
import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinter;

/**
 * ベクトル情報を出力する出力器を表す抽象クラス
 * 
 * @author k-hotta
 * 
 */
public abstract class AbstractWriter {

	protected final VectorWriterSettings settings;

	public AbstractWriter(final VectorWriterSettings settings) {
		this.settings = settings;
	}

	/**
	 * 結果を出力する
	 * 
	 * @throws Exception
	 */
	public abstract void write() throws Exception;

	/**
	 * ベクトルペアを復元
	 * 
	 * @param settings
	 * @return
	 * @throws Exception
	 */
	protected Set<VectorPairData> retrieveVectorPairs() throws Exception {
		String query = settings.getQuery();
		if (settings.isDefaultQuery()) {
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

			query = "select * from VECTOR_LINK where BEFORE_REVISION_ID >= "
					+ startRevision.getId() + " and BEFORE_REVISION_ID <= "
					+ endRevision.getId();
		}

		// ベクトルペア情報を復元
		MessagePrinter.stronglyPrint("retrieving vector pairs");
		MessagePrinter.print(" with \"" + query + "\"");
		MessagePrinter.stronglyPrintln(" ... ");
		final Set<VectorPairData> vectorPairs = DBConnection.getInstance()
				.getVectorPairRetriever().retrieve(query);
		MessagePrinter.stronglyPrintln("\t" + vectorPairs.size()
				+ " pairs were retrieved");
		MessagePrinter.stronglyPrintln();

		return vectorPairs;
	}

	/**
	 * 指定されたベクトルペア集合に含まれるベクトルをすべてマップとして取得
	 * 
	 * @param vectorPairs
	 * @return
	 * @throws Exception
	 */
	protected Map<Long, VectorData> retrieveVectorsInSpecifiedVectorPairs(
			final Collection<VectorPairData> vectorPairs) throws Exception {
		// 復元したベクトルペアに含まれるベクトル情報を復元
		MessagePrinter.stronglyPrintln("retrieving vectors ... ");
		final Set<Long> vectorIdsToBeRetrieved = new TreeSet<Long>();

		for (final VectorPairData vectorPair : vectorPairs) {
			vectorIdsToBeRetrieved.add(vectorPair.getBeforeVectorId());
			vectorIdsToBeRetrieved.add(vectorPair.getAfterVectorId());
		}

		final Set<VectorData> vectors = DBConnection.getInstance()
				.getVectorRetriever().retrieveWithIds(vectorIdsToBeRetrieved);
		final Map<Long, VectorData> result = new TreeMap<Long, VectorData>();
		for (final VectorData vector : vectors) {
			result.put(vector.getId(), vector);
		}

		MessagePrinter.stronglyPrintln("\t" + result.size()
				+ " vectors were retrieved");
		MessagePrinter.stronglyPrintln();

		return result;
	}

	/**
	 * 全部0のカラムと，もともと設定されている無視対象カラムを併せた，無視対象カラムを返す
	 * 
	 * @param vectors
	 * @return
	 */
	protected final List<Integer> getIgnoreColumnsList(
			final boolean ignoreZeroColumns,
			final Collection<VectorData> vectors) {
		final List<Integer> ignoreList = new ArrayList<Integer>();
		ignoreList.addAll(settings.getIgnoreList());

		if (ignoreZeroColumns) {
			final Set<Integer> zeroColumns = VectorData.getNodeTypeIntegers();

			for (final VectorData vector : vectors) {
				final Set<Integer> nonzeroColumns = vector
						.getElementContainingColumns();
				zeroColumns.removeAll(nonzeroColumns);
			}

			ignoreList.addAll(zeroColumns);
		}

		return ignoreList;
	}

	protected final List<Integer> getIgnoreColumnsList(
			final Collection<VectorData> vectors) {
		return this.getIgnoreColumnsList(true, vectors);
	}

	/**
	 * ベクトルの系譜を復元する
	 * 
	 * @return
	 * @throws Exception
	 */
	protected final Set<VectorGenealogy> retrieveGenealogies() throws Exception {
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

		MessagePrinter.stronglyPrintln("retrieving vector pairs ... ");

		final Set<VectorGenealogy> allGenealogies = DBConnection.getInstance()
				.getVectorGenealogyRetriever().retrieveAll();

		MessagePrinter.stronglyPrintln("\t" + allGenealogies.size()
				+ " genealogies have been retrieved");
		MessagePrinter.stronglyPrintln();

		final int minimumChangeCount = settings.getMinimumChangeCount();

		final Set<VectorGenealogy> result = new TreeSet<VectorGenealogy>();

		MessagePrinter.stronglyPrintln("refining retrieved genealogies ... ");
		for (final VectorGenealogy genealogy : allGenealogies) {
			if (genealogy.getNumberOfChanged(startRevisionId, endRevisionId) >= minimumChangeCount) {
				result.add(genealogy);
			}
		}
		MessagePrinter.stronglyPrintln("\t" + result.size()
				+ " genealogies have remained");
		MessagePrinter.stronglyPrintln();

		return result;
	}

	protected final Map<Long, VectorData> retrieveStartAndEndVectors(
			final Collection<VectorGenealogy> genealogies) throws Exception {
		MessagePrinter
				.stronglyPrintln("retrieving start/end vectors for each genealogy ... ");
		final Set<Long> vectorIdsToBeRetrieved = new TreeSet<Long>();

		for (final VectorGenealogy genealogy : genealogies) {
			vectorIdsToBeRetrieved.add(genealogy.getStartVectorId());
			vectorIdsToBeRetrieved.add(genealogy.getEndVectorId());
		}

		final Set<VectorData> vectors = DBConnection.getInstance()
				.getVectorRetriever().retrieveWithIds(vectorIdsToBeRetrieved);
		final Map<Long, VectorData> result = new TreeMap<Long, VectorData>();
		for (final VectorData vector : vectors) {
			result.put(vector.getId(), vector);
		}

		MessagePrinter.stronglyPrintln("\t" + result.size()
				+ " vectors were retrieved");
		MessagePrinter.stronglyPrintln();

		return result;
	}

}
