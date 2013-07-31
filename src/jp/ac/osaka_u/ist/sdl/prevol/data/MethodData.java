package jp.ac.osaka_u.ist.sdl.prevol.data;

/**
 * メソッドを表すクラス
 * 
 * @author k-hotta
 * 
 */
public class MethodData {

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

}
