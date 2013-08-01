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

	public RevisionData(final long revisionNum) {
		super(count.getAndIncrement());
		this.revisionNum = revisionNum;
	}

	public final long getRevisionNum() {
		return this.revisionNum;
	}

}
