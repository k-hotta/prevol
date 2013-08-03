package jp.ac.osaka_u.ist.sdl.prevol.vectorlinker;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jp.ac.osaka_u.ist.sdl.prevol.data.MethodData;
import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorPairData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;

/**
 * ベクトルのペアを特定するクラス
 * 
 * @author k-hotta
 * 
 */
public class VectorPairDetector {

	private final RevisionData beforeRevision;

	private final RevisionData afterRevision;

	private final DBConnection connection;

	private final Set<MethodData> methodsDeadInBeforeRevision;

	private final Set<MethodData> methodsCreatedInAfterRevision;

	public VectorPairDetector(final RevisionData beforeRevision,
			final RevisionData afterRevision) throws SQLException {
		this.beforeRevision = beforeRevision;
		this.afterRevision = afterRevision;
		this.connection = DBConnection.getInstance();
		methodsDeadInBeforeRevision = connection.getMethodRetriever()
				.retrieveDeadInSpecifiedRevision(beforeRevision.getId());
		methodsCreatedInAfterRevision = connection.getMethodRetriever()
				.retrieveGeneratedInSpecifiedRevision(afterRevision.getId());
	}

	/**
	 * ベクトルペアを特定し，dbに登録
	 * 
	 * @throws SQLException
	 */
	public void detectAndRegister() throws SQLException {
		final MethodPairDetector methodPairDetector = new MethodPairDetector(
				beforeRevision, methodsDeadInBeforeRevision, afterRevision,
				methodsCreatedInAfterRevision);
		final Map<MethodData, MethodData> methodPairs = methodPairDetector
				.detectMethodPairs();

		final Set<VectorPairData> vectorPairs = new HashSet<VectorPairData>();
		for (final Map.Entry<MethodData, MethodData> entry : methodPairs
				.entrySet()) {
			final MethodData beforeMethod = entry.getKey();
			final MethodData afterMethod = entry.getValue();
			vectorPairs.add(new VectorPairData(beforeRevision.getId(),
					afterRevision.getId(), beforeMethod.getId(), afterMethod
							.getId(), beforeMethod.getVectorId(), afterMethod
							.getVectorId()));
		}

		connection.getVectorPairRegisterer().register(vectorPairs);
	}
}
