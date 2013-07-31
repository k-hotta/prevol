package jp.ac.osaka_u.ist.sdl.prevol.data;

import static jp.ac.osaka_u.ist.sdl.prevol.utils.Constants.LINE_SEPARATOR;

import java.util.concurrent.atomic.AtomicLong;

/**
 * メソッドを表すクラス
 * 
 * @author k-hotta
 * 
 */
public class MethodData {

	/**
	 * ID管理用カウンタ
	 */
	private static final AtomicLong count = new AtomicLong(0);

	/**
	 * id
	 */
	private final long id;

	/**
	 * リビジョン番号
	 */
	private final long revision;

	/**
	 * 属するファイルの名前
	 */
	private final String ownerFile;

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
	 * ベクトルデータ
	 */
	private final VectorData vectorData;

	/**
	 * CRD
	 */
	private final CRD crd;

	/**
	 * コンストラクタ
	 * 
	 * @param revision
	 * @param ownerFile
	 * @param name
	 * @param startLine
	 * @param endLine
	 * @param vectorData
	 */
	public MethodData(final long revision, final String ownerFile,
			final String name, final int startLine, final int endLine,
			final VectorData vectorData, final CRD crd) {
		this.id = count.getAndIncrement();
		this.revision = revision;
		this.ownerFile = ownerFile;
		this.name = name;
		this.startLine = startLine;
		this.endLine = endLine;
		this.vectorData = vectorData;
		this.crd = crd;
	}

	/*
	 * ゲッタ群
	 */

	public long getId() {
		return id;
	}

	public long getRevision() {
		return revision;
	}

	public String getOwnerFile() {
		return ownerFile;
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

	public VectorData getVectorData() {
		return vectorData;
	}

	public CRD getCrd() {
		return crd;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("rev." + revision + LINE_SEPARATOR);
		builder.append(ownerFile + " " + name + " " + startLine + "-" + endLine
				+ LINE_SEPARATOR);
		builder.append(LINE_SEPARATOR);
		builder.append("CRD" + LINE_SEPARATOR);
		builder.append(crd.toString());
		builder.append(LINE_SEPARATOR + LINE_SEPARATOR);
		builder.append("vector" + LINE_SEPARATOR);
		builder.append(vectorData.toString());
		return builder.toString();
	}
}
