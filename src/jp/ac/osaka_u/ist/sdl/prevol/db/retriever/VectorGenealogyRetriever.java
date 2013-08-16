package jp.ac.osaka_u.ist.sdl.prevol.db.retriever;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;

import jp.ac.osaka_u.ist.sdl.prevol.data.VectorGenealogy;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;

public class VectorGenealogyRetriever extends
		AbstractElementRetriever<VectorGenealogy> {

	public VectorGenealogyRetriever(DBConnection connection) {
		super(connection);
	}

	@Override
	protected VectorGenealogy createElement(ResultSet rs) throws SQLException {
		int column = 0;

		final long id = rs.getLong(++column);
		final long startRevisionId = rs.getLong(++column);
		final long endRevisionId = rs.getLong(++column);
		@SuppressWarnings("unused")
		final int numberChanged = rs.getInt(++column);
		final String vectorsStr = rs.getString(++column);
		final String changedRevisionsStr = rs.getString(++column);

		return new VectorGenealogy(id, startRevisionId, endRevisionId,
				divideIntoList(vectorsStr), divideIntoList(changedRevisionsStr));
	}

	@Override
	protected String getTableName() {
		return "VECTOR_GENEALOGY";
	}

	@Override
	protected String getIdColumnName() {
		return "VECTOR_GENEALOGY_ID";
	}

	private SortedSet<Long> divideIntoList(final String str) {
		final SortedSet<Long> result = new TreeSet<Long>();
		final String[] splitStrs = str.split(",");
		for (final String splitStr : splitStrs) {
			result.add(Long.parseLong(splitStr));
		}
		return result;
	}

}
