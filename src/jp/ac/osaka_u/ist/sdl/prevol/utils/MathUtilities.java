package jp.ac.osaka_u.ist.sdl.prevol.utils;

/**
 * 計算関係のユーティリティクラス
 * 
 * @author k-hotta
 * 
 */
public class MathUtilities {

	/**
	 * 引数で与えられた数値の中で一番小さい値を返す
	 * 
	 * @param args
	 *            values
	 * @return the minimum value
	 */
	public static int min(int... args) {
		if (args.length == 0) {
			return -1;
		}

		int result = args[0];
		for (final int tmp : args) {
			if (tmp < result) {
				result = tmp;
			}
		}

		return result;
	}

}
