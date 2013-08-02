package jp.ac.osaka_u.ist.sdl.prevol.data;

import static jp.ac.osaka_u.ist.sdl.prevol.utils.Constants.LINE_SEPARATOR;

import java.util.concurrent.atomic.AtomicLong;

/**
 * メソッドを表すクラス
 * 
 * @author k-hotta
 * 
 */
public class MethodData extends AbstractElement {

	/**
	 * ID管理用カウンタ
	 */
	private static final AtomicLong count = new AtomicLong(0);

	/**
	 * 生成されたリビジョンのID
	 */
	private final long startRevisionId;

	/**
	 * なくなったリビジョンのID
	 */
	private final long endRevisionId;

	/**
	 * 属するファイルのID
	 */
	private final long fileId;

	/**
	 * メソッド名
	 */
	private final String name;

	/**
	 * 開始行
	 */
	private final int startLine;

	/**
	 * 終了行
	 */
	private final int endLine;

	/**
	 * ベクトルデータのID
	 */
	private final long vectorId;

	/**
	 * CRD
	 */
	private final String crd;

	/**
	 * インスタンス復元用コンストラクタ
	 * 
	 * @param id
	 * @param startRevisionId
	 * @param endRevisionId
	 * @param fileId
	 * @param name
	 * @param startLine
	 * @param endLine
	 * @param vectorData
	 * @param crdstr
	 */
	public MethodData(final long id, final long startRevisionId,
			final long endRevisionId, final long fileId, final String name,
			final int startLine, final int endLine, final long vectorId,
			final String crdstr) {
		super(id);
		this.startRevisionId = startRevisionId;
		this.endRevisionId = endRevisionId;
		this.fileId = fileId;
		this.name = name;
		this.startLine = startLine;
		this.endLine = endLine;
		this.vectorId = vectorId;
		this.crd = crdstr;
	}

	/**
	 * インスタンス生成用コンストラクタ
	 * 
	 * @param startRevisionId
	 * @param fileId
	 * @param name
	 * @param startLine
	 * @param endLine
	 * @param vectorData
	 */
	public MethodData(final long startRevisionId, final long endRevisionId,
			final long fileId, final String name, final int startLine,
			final int endLine, final long vectorId, final CRD crd) {
		this(count.getAndIncrement(), startRevisionId, endRevisionId, fileId,
				name, startLine, endLine, vectorId, crd.toString());
	}

	/*
	 * ゲッタ群
	 */

	public long getStartRevisionId() {
		return startRevisionId;
	}

	public long getEndRevisionId() {
		return endRevisionId;
	}

	public long getFileId() {
		return fileId;
	}

	public String getName() {
		return name;
	}

	public int getStartLine() {
		return startLine;
	}

	public int getEndLine() {
		return endLine;
	}

	public long getVectorId() {
		return vectorId;
	}

	public String getCrd() {
		return crd;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("rev." + startRevisionId + LINE_SEPARATOR);
		builder.append(fileId + " " + name + " " + startLine + "-" + endLine
				+ LINE_SEPARATOR);
		builder.append(LINE_SEPARATOR);
		builder.append("CRD" + LINE_SEPARATOR);
		builder.append(crd);
		builder.append(LINE_SEPARATOR + LINE_SEPARATOR);
		builder.append("vector : " + vectorId + LINE_SEPARATOR);
		return builder.toString();
	}
}
