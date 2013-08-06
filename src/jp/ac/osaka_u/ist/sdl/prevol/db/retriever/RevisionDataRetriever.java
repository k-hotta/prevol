package jp.ac.osaka_u.ist.sdl.prevol.db.retriever;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedSet;

import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;

/**
 * リビジョン情報を復元するクラス
 * 
 * @author k-hotta
 * 
 */
public class RevisionDataRetriever extends
		AbstractElementRetriever<RevisionData> {

	public RevisionDataRetriever(DBConnection connection) {
		super(connection);
	}

	@Override
	protected RevisionData createElement(ResultSet rs) throws SQLException {
		int column = 0;
		final long id = rs.getLong(++column);
		final long revisionNum = rs.getLong(++column);

		return new RevisionData(id, revisionNum);
	}

	@Override
	protected String getTableName() {
		return "REVISION";
	}

	@Override
	protected String getIdColumnName() {
		return "REVISION_ID";
	}

	protected String getRevisionNumColumnName() {
		return "REVISION_NUM";
	}

	/**
	 * 引数で指定された範囲のリビジョンをすべて取得する
	 * 
	 * @param startRevisionNum
	 * @param endRevisionNum
	 * @return
	 * @throws SQLException
	 */
	public SortedSet<RevisionData> getRevisionsInSpecifiedRange(
			final long startRevisionNum, final long endRevisionNum)
			throws SQLException {
		final String query = "select * from " + getTableName() + " where "
				+ getRevisionNumColumnName() + " >= " + startRevisionNum
				+ " and " + getRevisionNumColumnName() + " <= "
				+ endRevisionNum;
		return retrieve(query);
	}

	/**
	 * 指定された番号のリビジョンよりも前に存在するリビジョンの中で，最も新しいものを取得
	 * 
	 * @param revisionNum
	 * @return
	 * @throws SQLException
	 */
	public RevisionData getLatestRevisionBeforeSpecifiedRevision(
			final long revisionNum) throws SQLException {
		final SortedSet<RevisionData> allBeforeRevisions = retrieve("select * from "
				+ getTableName()
				+ " where "
				+ getRevisionNumColumnName()
				+ " <= " + revisionNum);

		long maxRevisionNum = -1;
		RevisionData result = null;
		for (final RevisionData revision : allBeforeRevisions) {
			if (revision.getRevisionNum() > maxRevisionNum) {
				maxRevisionNum = revision.getRevisionNum();
				result = revision;
			}
		}

		return result;
	}

	/**
	 * 指定された番号のリビジョンよりも後に存在するリビジョンの中で，最も古いものを取得
	 * 
	 * @param revisionNum
	 * @return
	 * @throws SQLException
	 */
	public RevisionData getOldestRevisionAfterSpecifiedRevision(
			final long revisionNum) throws SQLException {
		final SortedSet<RevisionData> allAfterRevisions = retrieve("select * from "
				+ getTableName()
				+ " where "
				+ getRevisionNumColumnName()
				+ " >= " + revisionNum);

		long minRevisionNum = Long.MAX_VALUE;
		RevisionData result = null;
		for (final RevisionData revision : allAfterRevisions) {
			if (revision.getRevisionNum() < minRevisionNum) {
				minRevisionNum = revision.getRevisionNum();
				result = revision;
			}
		}

		return result;
	}

}
