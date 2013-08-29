package jp.ac.osaka_u.ist.sdl.prevol.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
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
	private final SortedMap<Long, Long> vectors;

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
		this.vectors = new TreeMap<Long, Long>();
		final List<Long> revisions = new ArrayList<Long>();
		revisions.add(startRevisionId);
		revisions.addAll(changedRevisions);
		int i = 0;
		for (final long vector : vectors) {
			this.vectors.put(revisions.get(i++), vector);
		}
		this.changedRevisions = changedRevisions;
	}

	public VectorGenealogy(final long startRevisionId,
			final long endRevisionId, final long beforeVectorId,
			final long afterVectorId) {
		super(count.getAndIncrement());
		this.startRevisionId = startRevisionId;
		this.endRevisionId = endRevisionId;
		this.vectors = new TreeMap<Long, Long>();
		vectors.put(startRevisionId, beforeVectorId);
		vectors.put(endRevisionId, afterVectorId);
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
		final SortedSet<Long> result = new TreeSet<Long>();
		result.addAll(vectors.values());
		return Collections.unmodifiableSortedSet(result);
	}

	public final long getVector(final int index) {
		return vectors.get(index);
	}
	
	public final long getVectorAfterSpecifiedRevision(final long revisionId, final long index) {
		boolean enableCount = false;
		int count = 0;
		
		for (final Map.Entry<Long, Long> entry : this.vectors.entrySet()) {
			if (entry.getKey() >= revisionId) {
				enableCount = true;
			}
			
			if (index == count) {
				return entry.getValue();
			}
			
			if (enableCount) {
				count++;
			}
		}
		
		return -1;
	}

	public final SortedSet<Long> getChangedRevisions() {
		return Collections.unmodifiableSortedSet(changedRevisions);
	}

	public void addVector(final long revisionId, final long vectorId) {
		this.vectors.put(revisionId, vectorId);
		this.endRevisionId = revisionId;
		this.changedRevisions.add(revisionId);
	}

	public boolean containsAtLast(final long vectorId) {
		return this.vectors.get(this.vectors.lastKey()) == vectorId;
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
		return this.vectors.get(this.vectors.firstKey());
	}

	public final long getStartVectorAfterSpecifiedRevision(final long revisionId) {
		for (final Map.Entry<Long, Long> entry : this.vectors.entrySet()) {
			// SortedMapなので，最初に条件を満たすものに出くわせばクリア
			if (entry.getKey() >= revisionId) {
				return entry.getValue();
			}
		}
		return -1;
	}

	public final long getEndVectorId() {
		return this.vectors.get(this.vectors.lastKey());
	}

}
