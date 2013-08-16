package jp.ac.osaka_u.ist.sdl.prevol.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ベクトルの系譜を表すクラス
 * 
 * @author k-hotta
 * 
 */
public class VectorGenealogy extends AbstractElement {

	/**
	 * ID管理用カウンタ
	 */
	private static final AtomicLong count = new AtomicLong(0);

	/**
	 * 開始リビジョンのID
	 */
	private final long startRevisionId;

	/**
	 * 終了リビジョンのID
	 */
	private long endRevisionId;

	/**
	 * 系譜を構成するベクトルのIDのリスト
	 */
	private final List<Long> vectors;

	public VectorGenealogy(final long id, final long startRevisionId,
			final long endRevisionId, final List<Long> vectors) {
		super(id);
		this.startRevisionId = startRevisionId;
		this.endRevisionId = endRevisionId;
		this.vectors = vectors;
	}

	public VectorGenealogy(final long startRevisionId,
			final long endRevisionId, final long vectorId) {
		super(count.getAndIncrement());
		this.startRevisionId = startRevisionId;
		this.endRevisionId = endRevisionId;
		this.vectors = new ArrayList<Long>();
		vectors.add(vectorId);
	}

	public final long getStartRevisionId() {
		return startRevisionId;
	}

	public final long getEndRevisionId() {
		return endRevisionId;
	}

	public final List<Long> getVectors() {
		return Collections.unmodifiableList(vectors);
	}

	public void addVector(final long revisionId, final long vectorId) {
		this.vectors.add(vectorId);
		this.endRevisionId = revisionId;
	}

	public boolean containsAtLast(final long vectorId) {
		return this.vectors.get(vectors.size() - 1) == vectorId;
	}

}
