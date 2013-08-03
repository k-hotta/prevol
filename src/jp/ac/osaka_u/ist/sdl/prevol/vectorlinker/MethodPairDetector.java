package jp.ac.osaka_u.ist.sdl.prevol.vectorlinker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

import jp.ac.osaka_u.ist.sdl.prevol.data.MethodData;
import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.utils.StringSimilarityCalculator;
import jp.ac.osaka_u.ist.sdl.prevol.utils.Table;

/**
 * 連続する2つのリビジョン間でメソッドの対を特定するクラス <br>
 * 追加変更削除されたファイルに含まれるメソッドのみが対象
 * 
 * @author k-hotta
 * 
 */
public class MethodPairDetector {

	/**
	 * 変更前リビジョン
	 */
	private final RevisionData beforeRevision;

	/**
	 * 変更前リビジョンに存在するメソッドの集合
	 */
	private final Set<MethodData> beforeMethods;

	/**
	 * 変更後リビジョン
	 */
	private final RevisionData afterRevision;

	/**
	 * 変更後リビジョンに存在するメソッドの集合
	 */
	private final Set<MethodData> afterMethods;

	/**
	 * 類似度テーブル
	 */
	private final Table<Long, Long, Double> similarityTable;

	/**
	 * 前リビジョンメソッドから見た，後リビジョンメソッドの希望順リスト
	 */
	private final Map<MethodData, Queue<MethodData>> wishLists;

	/**
	 * コンストラクタ <br>
	 * メソッド対の特定に必要となるデータ (類似度テーブルとか) はここで作成する
	 * 
	 * @param beforeRevision
	 * @param beforeMethods
	 * @param afterRevision
	 * @param afterMethods
	 */
	public MethodPairDetector(final RevisionData beforeRevision,
			final Set<MethodData> beforeMethods,
			final RevisionData afterRevision, final Set<MethodData> afterMethods) {
		this.beforeRevision = beforeRevision;
		this.beforeMethods = beforeMethods;
		this.afterRevision = afterRevision;
		this.afterMethods = afterMethods;
		this.similarityTable = createSimilarityTable(beforeMethods,
				afterMethods);
		this.wishLists = detectWishLists(beforeMethods, afterMethods,
				similarityTable);
	}

	/**
	 * 類似度テーブルを作成する
	 * 
	 * @param beforeMethods
	 * @param afterMethods
	 * @return
	 */
	private Table<Long, Long, Double> createSimilarityTable(
			final Set<MethodData> beforeMethods,
			final Set<MethodData> afterMethods) {
		final Table<Long, Long, Double> result = new Table<Long, Long, Double>();

		for (final MethodData beforeMethod : beforeMethods) {
			for (final MethodData afterMethod : afterMethods) {
				result.changeValueAt(beforeMethod.getId(), afterMethod.getId(),
						StringSimilarityCalculator
								.calcLebenshteinDistanceBasedSimilarity(
										beforeMethod.getCrd(),
										afterMethod.getCrd()));
			}
		}

		return result;
	}

	/**
	 * 希望順リストを特定する
	 * 
	 * @param beforeMethods
	 * @param afterMethods
	 * @return
	 */
	private Map<MethodData, Queue<MethodData>> detectWishLists(
			final Set<MethodData> beforeMethods,
			final Set<MethodData> afterMethods,
			final Table<Long, Long, Double> similarityTable) {
		final Map<MethodData, Queue<MethodData>> result = new TreeMap<MethodData, Queue<MethodData>>();

		for (final MethodData beforeMethod : beforeMethods) {
			final Map<MethodData, Double> similarities = new TreeMap<MethodData, Double>();

			for (final MethodData afterMethod : afterMethods) {
				similarities.put(afterMethod, similarityTable.getValueAt(
						beforeMethod.getId(), afterMethod.getId()));
			}

			final Queue<MethodData> wishList = sortWithValues(similarities);
			result.put(beforeMethod, wishList);
		}

		return Collections.unmodifiableMap(result);
	}

	/**
	 * 引数で指定されたマップを value の値の降順にソートし，ソート結果を key のキューとして返す
	 * 
	 * @param target
	 * @return
	 */
	private Queue<MethodData> sortWithValues(
			final Map<MethodData, Double> target) {
		final Queue<MethodData> result = new LinkedList<MethodData>();
		final int targetSize = target.size();

		while (result.size() < targetSize) {
			double maxValue = -1.0;
			MethodData keyHasMaxValue = null;

			for (final Map.Entry<MethodData, Double> entry : target.entrySet()) {
				final MethodData key = entry.getKey();
				final double value = entry.getValue();

				if (result.contains(key)) {
					continue;
				}

				if (value > maxValue) {
					maxValue = value;
					keyHasMaxValue = key;
				}
			}

			result.add(keyHasMaxValue);
		}

		return result;
	}

	/**
	 * メソッド対を特定する <br>
	 * 安定結婚問題に対する Gale-Shapley Algorithm を参考にしている
	 * 
	 * @return
	 */
	public Map<MethodData, MethodData> detectMethodPairs() {
		// 特定されたメソッド対
		// ただし，キーが後リビジョンのメソッド，値が前リビジョンのメソッドになる
		final Map<MethodData, MethodData> reversedResult = new TreeMap<MethodData, MethodData>();

		// 相手が見つかっていない前リビジョンメソッドたち
		// とりあえずは全員
		final List<MethodData> unmarriedBeforeMethods = new ArrayList<MethodData>();
		unmarriedBeforeMethods.addAll(beforeMethods);

		// 見つかったペアの数が前リビジョンメソッド数と後リビジョンメソッド数のうち小さいほうに達するまで繰り返す
		while (reversedResult.size() < Math.min(beforeMethods.size(),
				afterMethods.size())) {

			// 前リビジョンメソッドたちにアタックさせる
			// 返り値は void だけど reversedResult と unmarriedBeforeMethods の中身は変化する
			processAllProposes(reversedResult, unmarriedBeforeMethods);

		}

		// 特定されたメソッド対のキーと値を入れ替えて返す
		return Collections.unmodifiableMap(getReversedMap(reversedResult));
	}

	/**
	 * 相手がいない前リビジョンメソッド全員にアタックさせる <br>
	 * 1回のメソッド呼び出しで，それぞれの前リビジョンメソッドがアタックする相手は1人だけ <br>
	 * 引数のマップとリストはどちらもこのメソッド呼び出しで中身に変化が生じる
	 * 
	 * @param reversedResult
	 *            見つかったペアを入れるマップ
	 * @param unmarriedBeforeMethods
	 *            相手がいない前リビジョンメソッドのリスト
	 */
	private void processAllProposes(
			final Map<MethodData, MethodData> reversedResult,
			final List<MethodData> unmarriedBeforeMethods) {
		// このループで相手が見つかった変更前メソッドの集合
		final Set<MethodData> marriedBeforeMethods = new HashSet<MethodData>();

		// このループでパートナーから振られた変更前メソッドの集合
		final Set<MethodData> dumpedBeforeMethods = new HashSet<MethodData>();

		/*
		 * 相手が見つかっていない前リビジョンメソッドが，後リビジョンメソッドにアタック
		 */
		for (final MethodData proposingMethod : unmarriedBeforeMethods) {

			// 一番類似度の高い相手にアタック
			// ダメだったら素直にあきらめるので poll
			final MethodData proposedMethod = wishLists.get(proposingMethod)
					.poll();

			// 振られ続けてアタックする相手がいない…
			if (proposedMethod == null) {
				continue;
			}

			/*
			 * アタックする相手となる後リビジョンのメソッドにすでにパートナーがいるかどうかで分岐
			 */

			/*
			 * いる
			 */
			if (reversedResult.containsKey(proposedMethod)) {
				// アタック相手の現在のパートナーよりも自分の方が似てれば略奪
				// でなければ振られる
				// まったく五分の場合も振られる

				// アタック中の相手の現在のパートナー
				final MethodData rivalMethod = reversedResult
						.get(proposedMethod);

				// それぞれの類似度
				final double similarityBetweenProposingMethod = similarityTable
						.getValueAt(proposingMethod.getId(),
								proposedMethod.getId());
				final double similarityBetweenCurrentPartner = similarityTable
						.getValueAt(rivalMethod.getId(), proposedMethod.getId());

				// 勝負
				if (similarityBetweenProposingMethod > similarityBetweenCurrentPartner) {
					// 勝利
					// よって奪う
					reversedResult.remove(proposedMethod);
					reversedResult.put(proposedMethod, proposingMethod);
					marriedBeforeMethods.add(proposingMethod);
					dumpedBeforeMethods.add(rivalMethod);
				} else {
					// 敗北
					// することなし
				}

			}

			/*
			 * いない
			 */
			else {
				// いない
				// とりあえずアタック成功
				// あとで振られるかもしれないけど
				reversedResult.put(proposedMethod, proposingMethod);
				marriedBeforeMethods.add(proposingMethod);
			}
		}

		// 相手がいないリストから，今回相手が見つかったメソッドたちを消去
		unmarriedBeforeMethods.removeAll(marriedBeforeMethods);

		// 相手がいないリストに，今回振られたメソッドたちを追加
		unmarriedBeforeMethods.addAll(dumpedBeforeMethods);
	}

	/**
	 * 引数で受け取ったマップのキーと値を逆にしたマップを生成する
	 * 
	 * @param target
	 * @return
	 */
	private Map<MethodData, MethodData> getReversedMap(
			final Map<MethodData, MethodData> target) {
		final Map<MethodData, MethodData> result = new TreeMap<MethodData, MethodData>();

		for (final Map.Entry<MethodData, MethodData> entry : target.entrySet()) {
			result.put(entry.getValue(), entry.getKey());
		}

		return result;
	}

}
