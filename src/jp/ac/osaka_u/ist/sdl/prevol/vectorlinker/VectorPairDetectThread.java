package jp.ac.osaka_u.ist.sdl.prevol.vectorlinker;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinter;

/**
 * ベクトルペアを特定するスレッド
 * 
 * @author k-hotta
 * 
 */
public class VectorPairDetectThread implements Runnable {

	/**
	 * インデックス
	 */
	private final AtomicInteger index;

	/**
	 * 分析対象リビジョンの配列
	 */
	private final RevisionData[] revisionsArray;

	/**
	 * リビジョンのペア
	 */
	private final ConcurrentMap<RevisionData, RevisionData> revisionPairs;

	public VectorPairDetectThread(final AtomicInteger index,
			final RevisionData[] revisionsArray,
			final ConcurrentMap<RevisionData, RevisionData> revisionPairs) {
		this.index = index;
		this.revisionsArray = revisionsArray;
		this.revisionPairs = revisionPairs;
	}

	@Override
	public void run() {
		while (true) {
			final int currentIndex = index.getAndIncrement();

			if (currentIndex >= revisionsArray.length) {
				break;
			}

			try {

				final RevisionData beforeRevision = revisionsArray[currentIndex];
				final RevisionData afterRevision = revisionPairs
						.get(beforeRevision);
				MessagePrinter.stronglyPrintln("\tnow processing revision "
						+ beforeRevision.getRevisionNum() + "\t- "
						+ afterRevision.getRevisionNum() + "\t["
						+ (currentIndex + 1) + "/" + revisionsArray.length
						+ "]");

				final VectorPairDetector detector = new VectorPairDetector(
						beforeRevision, afterRevision);
				detector.detectAndRegister();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
