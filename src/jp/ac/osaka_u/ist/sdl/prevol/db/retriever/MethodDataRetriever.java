package jp.ac.osaka_u.ist.sdl.prevol.db.retriever;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.SortedSet;

import jp.ac.osaka_u.ist.sdl.prevol.data.MethodData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;

/**
 * メソッドを復元するクラス
 * 
 * @author k-hotta
 * 
 */
public class MethodDataRetriever extends AbstractElementRetriever<MethodData> {

	public MethodDataRetriever(DBConnection connection) {
		super(connection);
	}

	@Override
	protected MethodData createElement(ResultSet rs) throws SQLException {
		int column = 0;

		final long id = rs.getLong(++column);
		final long startRevisionId = rs.getLong(++column);
		final long endRevisionId = rs.getLong(++column);
		final long fileId = rs.getLong(++column);
		final String name = rs.getString(++column);
		final int startLine = rs.getInt(++column);
		final int endLine = rs.getInt(++column);
		final String crd = rs.getString(++column);
		final long vectorId = rs.getLong(++column);

		return new MethodData(id, startRevisionId, endRevisionId, fileId, name,
				startLine, endLine, vectorId, crd);
	}

	@Override
	protected String getTableName() {
		return "METHOD";
	}

	protected String getStartRevisionIdColumnName() {
		return "START_REVISION_ID";
	}

	protected String getEndRevisionIdColumnName() {
		return "END_REVISION_ID";
	}

	protected String getFileIdColumnName() {
		return "OWNER_FILE_ID";
	}

	/**
	 * 指定されたリビジョンに存在するメソッドをすべて取得
	 * 
	 * @param revId
	 * @return
	 * @throws SQLException
	 */
	public SortedSet<MethodData> retrieveInSpecifiedRevision(final long revId)
			throws SQLException {
		final String query = "select * from " + getTableName() + " where "
				+ getStartRevisionIdColumnName() + " <= " + revId + " AND "
				+ getEndRevisionIdColumnName() + " >=" + revId;

		return retrieve(query);
	}

	public SortedSet<MethodData> retrieveInSpecifiedFiles(
			final Collection<Long> fileIds) throws SQLException {
		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select * from ");
		queryBuilder.append(getTableName());
		queryBuilder.append(" where ");
		queryBuilder.append(getFileIdColumnName());
		queryBuilder.append(" in (");

		for (final long id : fileIds) {
			queryBuilder.append(((Long) id).toString());
			queryBuilder.append(",");
		}

		queryBuilder.deleteCharAt(queryBuilder.length() - 1);
		queryBuilder.append(")");

		return retrieve(queryBuilder.toString());
	}

	/**
	 * 引数で指定されたリビジョンを開始リビジョンとするメソッドをすべて取得
	 * 
	 * @param revisionId
	 * @return
	 */
	public SortedSet<MethodData> retrieveGeneratedInSpecifiedRevision(
			final long revisionId) throws SQLException {
		final String query = "select * from " + getTableName() + " where "
				+ getStartRevisionIdColumnName() + " = " + revisionId;

		return retrieve(query);
	}
	
	/**
	 * 引数で指定されたリビジョンを終了リビジョンとするメソッドをすべて取得
	 * 
	 * @param revisionId
	 * @return
	 */
	public SortedSet<MethodData> retrieveDeadInSpecifiedRevision(
			final long revisionId) throws SQLException {
		final String query = "select * from " + getTableName() + " where "
				+ getEndRevisionIdColumnName() + " = " + revisionId;

		return retrieve(query);
	}

}
