package jp.ac.osaka_u.ist.sdl.prevol.db.retriever;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedSet;

import jp.ac.osaka_u.ist.sdl.prevol.data.VectorPairData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;

/**
 * VectorPairData を復元するクラス
 * 
 * @author k-hotta
 * 
 */
public class VectorPairDataRetriever extends
		AbstractElementRetriever<VectorPairData> {

	public VectorPairDataRetriever(DBConnection connection) {
		super(connection);
	}

	@Override
	protected VectorPairData createElement(ResultSet rs) throws SQLException {
		int column = 0;

		final long id = rs.getLong(++column);
		final long beforeRevisionId = rs.getLong(++column);
		final long afterRevisionId = rs.getLong(++column);
		final long beforeMethodId = rs.getLong(++column);
		final long afterMethodId = rs.getLong(++column);
		final long beforeVectorId = rs.getLong(++column);
		final long afterVectorId = rs.getLong(++column);

		return new VectorPairData(id, beforeRevisionId, afterRevisionId,
				beforeMethodId, afterMethodId, beforeVectorId, afterVectorId);
	}

	@Override
	protected String getTableName() {
		return "VECTOR_LINK";
	}

	/**
	 * 引数で指定されたリビジョンへのコミットに存在するベクトルペアを取得する
	 * 
	 * @param revId
	 * @return
	 * @throws SQLException
	 */
	public SortedSet<VectorPairData> retrieveInSpecifiedRevision(
			final long revId) throws SQLException {
		final String query = "select * from " + getTableName()
				+ " where AFTER_REVISION_ID = " + revId;
		
		return retrieve(query);
	}
}
