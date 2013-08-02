package jp.ac.osaka_u.ist.sdl.prevol.db.registerer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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

}
