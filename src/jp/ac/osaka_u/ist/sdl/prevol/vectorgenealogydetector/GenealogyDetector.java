package jp.ac.osaka_u.ist.sdl.prevol.vectorgenealogydetector;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorGenealogy;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorPairData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;
import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinter;

public class GenealogyDetector {

	public static void main(String[] args) {
		try {
			final GenealogyDetectorSettings settings = GenealogyDetectorSettings
					.parseArgs(args);

			initialize(settings);

			final Set<VectorPairData> vectorPairs = retrieveAllVectorPairs(settings);

			final Set<VectorGenealogy> vectorGenealogies = detect(vectorPairs);

			register(vectorGenealogies);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postprocess();
		}
	}

	/**
	 * 初期設定を行う
	 * 
	 * @param settings
	 * @throws Exception
	 */
	private static void initialize(final GenealogyDetectorSettings settings)
			throws Exception {
		// 出力レベルを設定
		MessagePrinter.setMode(settings.getPrintMode());

		MessagePrinter.stronglyPrintln("operations start");
		MessagePrinter.stronglyPrintln();

		// データベースとのコネクションを生成
		MessagePrinter.stronglyPrintln("initializing db ... ");
		DBConnection.createInstance(settings.getDbPath());
		MessagePrinter.stronglyPrintln("\tOK");
		MessagePrinter.stronglyPrintln();
	}

	private static Set<VectorPairData> retrieveAllVectorPairs(
			final GenealogyDetectorSettings settings) throws Exception {
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

		final String query = "select * from VECTOR_LINK where BEFORE_REVISION_ID >= "
				+ startRevision.getId()
				+ " and BEFORE_REVISION_ID <= "
				+ endRevision.getId();

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

	private static Set<VectorGenealogy> detect(
			final Set<VectorPairData> vectorPairs) {
		final SortedSet<VectorGenealogy> result = new TreeSet<VectorGenealogy>();

		MessagePrinter.stronglyPrintln("connecting vector links ... ");
		int count = 0;
		final int max = vectorPairs.size();

		for (final VectorPairData vectorPair : vectorPairs) {
			boolean partnerFound = false;
			for (final VectorGenealogy genealogy : result) {
				if (genealogy.containsAtLast(vectorPair.getBeforeVectorId())) {
					genealogy.addVector(vectorPair.getAfterRevisionId(),
							vectorPair.getAfterVectorId());
					partnerFound = true;
					break;
				}
			}

			if (!partnerFound) {
				final VectorGenealogy genealogy = new VectorGenealogy(
						vectorPair.getBeforeRevisionId(),
						vectorPair.getAfterRevisionId(),
						vectorPair.getBeforeVectorId(),
						vectorPair.getAfterVectorId());
				result.add(genealogy);
			}

			if (++count % 1000 == 0) {
				MessagePrinter.stronglyPrintln("\t" + count
						+ " vector links have been processed (out of " + max
						+ ")");
			}
		}
		MessagePrinter.stronglyPrintln();
		MessagePrinter.stronglyPrintln("\t" + result.size()
				+ " genealogies have been detected");
		MessagePrinter.stronglyPrintln();

		return Collections.unmodifiableSortedSet(result);
	}

	private static void register(final Set<VectorGenealogy> genealogies)
			throws Exception {
		MessagePrinter
				.stronglyPrintln("registering all the genealogies into db ...");
		DBConnection.getInstance().getVectorGenealogyRegisterer()
				.register(genealogies);
		MessagePrinter.stronglyPrintln("\tOK");
		MessagePrinter.stronglyPrintln();
	}

	/**
	 * 後処理を行う
	 */
	private static void postprocess() {
		if (DBConnection.getInstance() != null) {
			DBConnection.getInstance().close();
		}
	}

}
