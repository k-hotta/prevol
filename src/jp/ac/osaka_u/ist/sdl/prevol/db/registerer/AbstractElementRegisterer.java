package jp.ac.osaka_u.ist.sdl.prevol.db.registerer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import jp.ac.osaka_u.ist.sdl.prevol.data.AbstractElement;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;
import jp.ac.osaka_u.ist.sdl.prevol.utils.Constants;

/**
 * データベースに要素を登録するための抽象クラス
 * 
 * @author k-hotta
 * 
 */
public abstract class AbstractElementRegisterer<T extends AbstractElement> {

	/**
	 * DBとの接続
	 */
	protected final DBConnection connection;

	/**
	 * バッチ処理するstatementの最大数
	 */
	protected final int maxBatchCount = Constants.MAX_BATCH_COUNT;

	public AbstractElementRegisterer(DBConnection connection) {
		this.connection = connection;
	}

	/**
	 * 要素を登録
	 * 
	 * @param elements
	 * @throws SQLException
	 */
	public void register(final Collection<T> elements) throws SQLException {
		final PreparedStatement pstmt = connection
				.createPreparedStatement(createPreparedStatementQueue());

		int count = 0;

		for (final T element : elements) {
			setAttributes(pstmt, element);
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

	/**
	 * PreparedStatementを生成するためのクエリを取得する
	 * 
	 * @return
	 */
	protected abstract String createPreparedStatementQueue();

	/**
	 * PreparedStatement に属性を設定する
	 * 
	 * @param pstmt
	 * @param element
	 * @throws SQLException
	 */
	protected abstract void setAttributes(PreparedStatement pstmt, T element)
			throws SQLException;

}
