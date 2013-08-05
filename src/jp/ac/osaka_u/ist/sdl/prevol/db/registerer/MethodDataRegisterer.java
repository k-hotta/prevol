package jp.ac.osaka_u.ist.sdl.prevol.db.registerer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import jp.ac.osaka_u.ist.sdl.prevol.data.MethodData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;

/**
 * MethodData を登録するクラス
 * 
 * @author k-hotta
 * 
 */
public class MethodDataRegisterer extends AbstractElementRegisterer<MethodData> {

	public MethodDataRegisterer(DBConnection connection) {
		super(connection);
	}

	@Override
	protected String createPreparedStatementQueue() {
		return "insert into METHOD values (?,?,?,?,?,?,?,?,?,?,?)";
	}

	@Override
	protected void setAttributes(PreparedStatement pstmt, MethodData element)
			throws SQLException {
		int column = 0;
		pstmt.setLong(++column, element.getId());
		pstmt.setLong(++column, element.getStartRevisionId());
		pstmt.setLong(++column, element.getEndRevisionId());
		pstmt.setLong(++column, element.getFileId());
		pstmt.setString(++column, element.getName());
		pstmt.setInt(++column, element.getStartLine());
		pstmt.setInt(++column, element.getEndLine());
		pstmt.setString(++column, element.getCrd().toString());
		pstmt.setLong(++column, element.getVectorId());
		pstmt.setInt(++column, element.getHash());
		pstmt.setString(++column, element.getParameters());
	}

	/**
	 * 引数で指定されたIDを持つメソッドレコードすべてについて，endRevisionId を 指定されたリビジョンIDに設定する
	 * 
	 * @param previousRevId
	 * @param methodIds
	 * @throws SQLException
	 */
	public void updatePreviousRevisionMethods(final Collection<Long> methodIds,
			final long previousRevId) throws SQLException {
		PreparedStatement pstmt = connection
				.createPreparedStatement("update METHOD set END_REVISION_ID = "
						+ ((Long) previousRevId).toString()
						+ " where METHOD_ID = ?");

		int count = 0;

		for (final long fileId : methodIds) {
			pstmt.setLong(1, fileId);
			pstmt.addBatch();
			if ((++count % maxBatchCount) == 0) {
				pstmt.executeBatch();
				pstmt.clearBatch();
			}
		}

		pstmt.executeBatch();
		connection.commit();

		pstmt.close();
	}

}
