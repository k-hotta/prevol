package jp.ac.osaka_u.ist.sdl.prevol.data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * リビジョンを表すクラス
 * 
 * @author k-hotta
 * 
 */
public class RevisionData extends AbstractElement {

	/**
	 * ID管理用カウンタ
	 */
	private static final AtomicLong count = new AtomicLong(0);

	/**
	 * リビジョン番号
	 */
	private final long revisionNum;

	/**
	 * インスタンス復元用コンストラクタ
	 * 
	 * @param id
	 * @param revisionNum
	 */
	public RevisionData(final long id, final long revisionNum) {
		super(id);
		this.revisionNum = revisionNum;
	}

	/**
	 * 新規インスタンス生成用コンストラクタ
	 * 
	 * @param revisionNum
	 */
	public RevisionData(final long revisionNum) {
		this(count.getAndIncrement(), revisionNum);
	}

	public final long getRevisionNum() {
		return this.revisionNum;
	}

}
