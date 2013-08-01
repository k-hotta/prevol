package jp.ac.osaka_u.ist.sdl.prevol.data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * リビジョンを表すクラス
 * 
 * @author k-hotta
 * 
 */
public class RevisionData {

	private static final AtomicLong count = new AtomicLong(0);

	/**
	 * ID
	 */
	private final long id;

	/**
	 * リビジョン番号
	 */
	private final long revisionNum;

	public RevisionData(final long revisionNum) {
		this.id = count.getAndIncrement();
		this.revisionNum = revisionNum;
	}

	public final long getId() {
		return this.id;
	}

	public final long getRevisionNum() {
		return this.revisionNum;
	}

}
