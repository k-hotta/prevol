package jp.ac.osaka_u.ist.sdl.prevol.evaluator;

/**
 * 予測精度の評価を行うためのクラス
 * 
 * @author k-hotta
 * 
 */
public class Evaluator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			final EvaluatorSettings settings = EvaluatorSettings.parseArgs(args);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
