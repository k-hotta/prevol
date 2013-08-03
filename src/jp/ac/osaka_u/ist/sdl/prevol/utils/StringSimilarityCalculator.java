package jp.ac.osaka_u.ist.sdl.prevol.utils;

/**
 * 文字列の類似度を計測するためのクラス
 * 
 * @author k-hotta
 * 
 */
public class StringSimilarityCalculator {

	public static int calcLevenshteinDistance(String str1, String str2) {
		final int len1 = str1.length();
		final int len2 = str2.length();
		final int[][] d = new int[len1 + 1][len2 + 1];

		for (int i = 0; i < len1 + 1; i++) {
			d[i][0] = i;
		}
		for (int j = 0; j < len2 + 1; j++) {
			d[0][j] = j;
		}

		for (int i = 1; i < len1 + 1; i++) {
			for (int j = 1; j < len2 + 1; j++) {
				int cost;
				if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
					cost = 0;
				} else {
					cost = 1;
				}
				d[i][j] = MathUtilities.min(d[i - 1][j] + 1, d[i][j - 1] + 1,
						d[i - 1][j - 1] + cost);
			}
		}
		return d[len1][len2];
	}

}
