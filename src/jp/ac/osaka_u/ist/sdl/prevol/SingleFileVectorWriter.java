package jp.ac.osaka_u.ist.sdl.prevol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.ASTCreator;

import org.eclipse.jdt.core.dom.CompilationUnit;

public class SingleFileVectorWriter {

	public static void main(String[] args) {
		try {
			// 分析対象ファイル
			final String path = args[0];

			// ASTを構築
			final String source = getFileContent(path);
			final CompilationUnit rootNode = ASTCreator.createAST(source);

			// ASTをパース
			SingleFileVectorDetector detector = new SingleFileVectorDetector(
					rootNode);
			rootNode.accept(detector);

			for (final SingleFileMethodData method : detector.getMethods()) {
				final Map<Integer, Integer> vectorMap = method.getVector()
						.getVector();

				boolean firstElement = true;
				for (final Map.Entry<Integer, Integer> element : vectorMap
						.entrySet()) {
					final int type = element.getKey();
					final int value = element.getValue();

					if (!firstElement) {
						System.out.print(",");
					} else {
						firstElement = false;
					}

					System.out.print("A" + type + ":" + value);
				}
				System.out.print("\t" + method.getStart());
				System.out.print("\t" + method.getEnd());
				System.out.print("\t" + method.getSignature());
				System.out.println();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getFileContent(final String path) throws Exception {
		final StringBuilder builder = new StringBuilder();
		final BufferedReader br = new BufferedReader(new FileReader(new File(
				path)));
		String line = null;
		while ((line = br.readLine()) != null) {
			builder.append(line + "\n");
		}
		br.close();
		return builder.toString();
	}

}
