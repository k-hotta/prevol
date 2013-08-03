package jp.ac.osaka_u.ist.sdl.prevol.data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * @author k-hotta
 * 
 */
public class VectorPairData extends AbstractElement {

	/**
	 * ID管理用カウンタ
	 */
	private static final AtomicLong count = new AtomicLong(0);

	/**
	 * 変更前リビジョンのID
	 */
	private final long beforeRevisionId;

	/**
	 * 変更後リビジョンのiD
	 */
	private final long afterRevisionId;

	/**
	 * 変更前メソッドのID
	 */
	private final long beforeMethodId;

	/**
	 * 変更後メソッドのID
	 */
	private final long afterMethodId;

	/**
	 * 変更前ベクトルのID
	 */
	private final long beforeVectorId;

	/**
	 * 変更後ベクトルのID
	 */
	private final long afterVectorId;

	/**
	 * インスタンス復元用コンストラクタ
	 * 
	 * @param id
	 * @param beforeRevisionId
	 * @param afterRevisionId
	 * @param beforeMethodId
	 * @param afterMethodId
	 * @param beforeVectorId
	 * @param afterVectorId
	 */
	public VectorPairData(long id, final long beforeRevisionId,
			final long afterRevisionId, final long beforeMethodId,
			final long afterMethodId, final long beforeVectorId,
			final long afterVectorId) {
		super(id);
		this.beforeRevisionId = beforeRevisionId;
		this.afterRevisionId = afterRevisionId;
		this.beforeMethodId = beforeMethodId;
		this.afterMethodId = afterMethodId;
		this.beforeVectorId = beforeVectorId;
		this.afterVectorId = afterVectorId;
	}

	/**
	 * インスタンス生成用コンストラクタ
	 * 
	 * @param beforeRevisionId
	 * @param afterRevisionId
	 * @param beforeMethodId
	 * @param afterMethodId
	 * @param beforeVectorId
	 * @param afterVectorId
	 */
	public VectorPairData(final long beforeRevisionId,
			final long afterRevisionId, final long beforeMethodId,
			final long afterMethodId, final long beforeVectorId,
			final long afterVectorId) {
		this(count.getAndIncrement(), beforeRevisionId, afterRevisionId,
				beforeMethodId, afterMethodId, beforeVectorId, afterVectorId);
	}

	/*
	 * ゲッタ群
	 */
	
	public long getBeforeRevisionId() {
		return beforeRevisionId;
	}

	public long getAfterRevisionId() {
		return afterRevisionId;
	}

	public long getBeforeMethodId() {
		return beforeMethodId;
	}

	public long getAfterMethodId() {
		return afterMethodId;
	}

	public long getBeforeVectorId() {
		return beforeVectorId;
	}

	public long getAfterVectorId() {
		return afterVectorId;
	}
	
}
