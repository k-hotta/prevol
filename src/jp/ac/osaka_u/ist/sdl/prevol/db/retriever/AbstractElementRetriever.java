package jp.ac.osaka_u.ist.sdl.prevol.db.retriever;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import jp.ac.osaka_u.ist.sdl.prevol.data.AbstractElement;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;

/**
 * データベースから要素を取り出すための抽象クラス
 * 
 * @author k-hotta
 * 
 */
public abstract class AbstractElementRetriever<T extends AbstractElement> {

	/**
	 * DBとの接続
	 */
	protected final DBConnection connection;

	public AbstractElementRetriever(final DBConnection connection) {
		this.connection = connection;
	}

	/**
	 * 指定されたクエリを実行し，要素を取得する
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public SortedSet<T> retrieve(final String query) throws SQLException {
		final SortedSet<T> result = new TreeSet<T>();

		final Statement stmt = connection.createStatement();
		final ResultSet rs = stmt.executeQuery(query);

		while (rs.next()) {
			result.add(createElement(rs));
		}

		stmt.close();
		rs.close();

		return Collections.unmodifiableSortedSet(result);
	}

	/**
	 * 要素をすべて取得する
	 * 
	 * @return
	 * @throws SQLException
	 */
	public SortedSet<T> retrieveAll() throws SQLException {
		final String query = "select * from " + getTableName();
		return retrieve(query);
	}

	/**
	 * 指定されたIDの要素をすべて取得する
	 * 
	 * @param ids
	 * @return
	 */
	public SortedSet<T> retrieveWithIds(final Collection<Long> ids)
			throws SQLException {
		if (ids.isEmpty()) {
			return new TreeSet<T>();
		}
		final StringBuilder builder = new StringBuilder();
		builder.append("select * from " + getTableName() + " where "
				+ getIdColumnName() + " in (");

		for (final long id : ids) {
			builder.append(id + ",");
		}

		builder.deleteCharAt(builder.length() - 1);
		builder.append(")");

		return retrieve(builder.toString());
	}

	/**
	 * レコードから要素を復元する
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	protected abstract T createElement(ResultSet rs) throws SQLException;

	/**
	 * テーブルの名前を取得する
	 * 
	 * @return
	 */
	protected abstract String getTableName();

	/**
	 * IDを保持する列の名前を取得する
	 * 
	 * @return
	 */
	protected abstract String getIdColumnName();

}
