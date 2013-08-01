package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

/**
 * リポジトリを解析してメソッド情報をDBに保存する機能のメインクラス
 * 
 * @author k-hotta
 * 
 */
public class MethodAnalyzer {

	public static void main(String[] args) {
		try {
			// 引数を解析
			final MethodAnalyzerSettings settings = MethodAnalyzerSettings.parseArgs(args);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
