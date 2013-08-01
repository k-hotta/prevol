package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import java.util.List;

import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn.SVNRepositoryManager;

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
			final MethodAnalyzerSettings settings = MethodAnalyzerSettings
					.parseArgs(args);

			// リポジトリを設定
			SVNRepositoryManager.setup(settings.getRepositoryPath(),
					settings.getAdditionalPath(), settings.getUserName(),
					settings.getPasswd());

			// 分析対象リビジョンを特定
			final List<RevisionData> targetRevisions = TargetRevisionDetector
					.detectTargetRevisions(settings.getLanguage(),
							settings.getStartRevision(),
							settings.getEndRevision());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
