package jp.ac.osaka_u.ist.sdl.prevol.db.registerer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import jp.ac.osaka_u.ist.sdl.prevol.data.FileData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;

/**
 * ファイルを登録するためのクラス
 * 
 * @author k-hotta
 * 
 */
public class FileDataRegisterer extends AbstractElementRegisterer<FileData> {

	public FileDataRegisterer(DBConnection connection) {
		super(connection);
	}

	@Override
	protected String createPreparedStatementQueue() {
		return "insert into FILE values (?,?,?,?)";
	}

	@Override
	protected void setAttributes(PreparedStatement pstmt, FileData element)
			throws SQLException {
		int column = 0;
		pstmt.setLong(++column, element.getId());
		pstmt.setLong(++column, element.getStartRevisionId());
		pstmt.setLong(++column, element.getEndRevisionId());
		pstmt.setString(++column, element.getPath());
	}

	/**
	 * 引数で指定されたIDを持つファイルレコードすべてについて，endRevisionId を 指定されたリビジョンIDに設定する
	 * 
	 * @param previousRevId
	 * @param fileIds
	 * @throws SQLException
	 */
	public void updatePreviousRevisionFiles(final Collection<Long> fileIds,
			final long previousRevId) throws SQLException {
		PreparedStatement pstmt = connection
				.createPreparedStatement("update FILE set END_REVISION_ID = "
						+ ((Long) previousRevId).toString()
						+ " where FILE_ID = ?");

		int count = 0;

		for (final long fileId : fileIds) {
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
