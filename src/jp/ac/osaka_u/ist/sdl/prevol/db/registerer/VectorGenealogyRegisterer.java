package jp.ac.osaka_u.ist.sdl.prevol.db.registerer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import jp.ac.osaka_u.ist.sdl.prevol.data.VectorGenealogy;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;

public class VectorGenealogyRegisterer extends
		AbstractElementRegisterer<VectorGenealogy> {

	public VectorGenealogyRegisterer(DBConnection connection) {
		super(connection);
	}

	@Override
	protected String createPreparedStatementQueue() {
		return "insert into VECTOR_GENEALOGY values (?,?,?,?,?,?)";
	}

	@Override
	protected void setAttributes(PreparedStatement pstmt,
			VectorGenealogy element) throws SQLException {
		int column = 0;
		pstmt.setLong(++column, element.getId());
		pstmt.setLong(++column, element.getStartRevisionId());
		pstmt.setLong(++column, element.getEndRevisionId());
		pstmt.setInt(++column, element.getNumberOfChanged());

		final StringBuilder vectorsBuilder = new StringBuilder();
		for (final long vector : element.getVectors()) {
			vectorsBuilder.append(vector + ",");
		}
		vectorsBuilder.deleteCharAt(vectorsBuilder.length() - 1);
		pstmt.setString(++column, vectorsBuilder.toString());

		final StringBuilder changedRevisionsBuilder = new StringBuilder();
		for (final long changedRevision : element.getChangedRevisions()) {
			changedRevisionsBuilder.append(changedRevision + ",");
		}
		changedRevisionsBuilder
				.deleteCharAt(changedRevisionsBuilder.length() - 1);
		pstmt.setString(++column, changedRevisionsBuilder.toString());
	}

}
