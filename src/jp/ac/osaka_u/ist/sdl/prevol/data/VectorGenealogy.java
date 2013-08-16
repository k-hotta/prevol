package jp.ac.osaka_u.ist.sdl.prevol.data;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
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
	private final SortedSet<Long> vectors;

	/**
	 * 変更されたリビジョンのID (変更後)
	 */
	private final SortedSet<Long> changedRevisions;

	public VectorGenealogy(final long id, final long startRevisionId,
			final long endRevisionId, final SortedSet<Long> vectors,
			final SortedSet<Long> changedRevisions) {
		super(id);
		this.startRevisionId = startRevisionId;
		this.endRevisionId = endRevisionId;
		this.vectors = vectors;
		this.changedRevisions = changedRevisions;
	}

	public VectorGenealogy(final long startRevisionId,
			final long endRevisionId, final long beforeVectorId,
			final long afterVectorId) {
		super(count.getAndIncrement());
		this.startRevisionId = startRevisionId;
		this.endRevisionId = endRevisionId;
		this.vectors = new TreeSet<Long>();
		vectors.add(beforeVectorId);
		vectors.add(afterVectorId);
		this.changedRevisions = new TreeSet<Long>();
		this.changedRevisions.add(endRevisionId);
	}

	public final long getStartRevisionId() {
		return startRevisionId;
	}

	public final long getEndRevisionId() {
		return endRevisionId;
	}

	public final SortedSet<Long> getVectors() {
		return Collections.unmodifiableSortedSet(vectors);
	}

	public final SortedSet<Long> getChangedRevisions() {
		return Collections.unmodifiableSortedSet(changedRevisions);
	}

	public void addVector(final long revisionId, final long vectorId) {
		this.vectors.add(vectorId);
		this.endRevisionId = revisionId;
		this.changedRevisions.add(revisionId);
	}

	public boolean containsAtLast(final long vectorId) {
		return this.vectors.last() == vectorId;
	}

	public final int getNumberOfChanged() {
		return this.changedRevisions.size();
	}

	public final int getNumberOfChanged(final long startRevisionId,
			final long endRevisionId) {
		int count = 0;
		for (final long changedRevision : this.changedRevisions) {
			if (startRevisionId < changedRevision
					&& changedRevision <= endRevisionId) {
				count++;
			}
		}
		return count;
	}

	public final long getStartVectorId() {
		return this.vectors.first();
	}

	public final long getEndVectorId() {
		return this.vectors.last();
	}

}
