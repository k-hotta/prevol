package jp.ac.osaka_u.ist.sdl.prevol.vectorwriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;

import jp.ac.osaka_u.ist.sdl.prevol.data.VectorData;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorPairData;
import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinter;

/**
 * TrainingSet を出力するクラス (各カラムごとにファイルに分割)
 * 
 * @author k-hotta
 * 
 */
public class SingleColumnTrainingSetWriter extends AbstractWriter {

	public SingleColumnTrainingSetWriter(VectorWriterSettings settings) {
		super(settings);
	}

	@Override
	public void write() throws Exception {
		// ベクトルペアを復元
		final Set<VectorPairData> vectorPairs = retrieveVectorPairs();

		// ベクトルを復元
		final Map<Long, VectorData> vectorsMap = retrieveVectorsInSpecifiedVectorPairs(vectorPairs);

		final List<Integer> ignoreList = getIgnoreColumnsList(vectorsMap
				.values());

		writeElements(vectorPairs, vectorsMap, ignoreList);
	}

	protected void writeElements(final Set<VectorPairData> vectorPairs,
			final Map<Long, VectorData> vectorsMap,
			final List<Integer> ignoreList) throws IOException {
		// 出力
		MessagePrinter.stronglyPrintln("printing the result ... ");
		
		final File specifiedFile = new File(settings.getOutputFilePath());
		final int suffixLength = (settings.getOutputFileFormat() == OutputFileFormat.CSV) ? 4
				: 5;
		final String specifiedFileName = specifiedFile.getName().substring(0,
				specifiedFile.getName().length() - suffixLength);
		final String outputDir = specifiedFile.getParentFile()
				.getAbsolutePath();
		final String suffix = (settings.getOutputFileFormat() == OutputFileFormat.CSV) ? ".csv"
				: ".arff";

		for (final int nodeType : VectorData.getNodeTypeIntegers()) {
			if (ignoreList.contains(nodeType)) {
				continue;
			}

			final String outputFileName = specifiedFileName + "-"
					+ ASTNode.nodeClassForType(nodeType).getSimpleName()
					+ suffix;
			final String outputFilePath = outputDir + File.separator
					+ outputFileName;
			final PrintWriter pw = new PrintWriter(new BufferedWriter(
					new FileWriter(new File(outputFilePath))));

			// ヘッダを出力
			if (settings.getOutputFileFormat() == OutputFileFormat.CSV) {
				pw.println(VectorData.getSingleColumnTrainingCsvHeader(
						ignoreList, ASTNode.nodeClassForType(nodeType)
								.getSimpleName()));
			} else {
				pw.println(VectorData.getSingleColumnTrainingArffHeader(
						ignoreList, settings.getRelationName()
								+ "-"
								+ ASTNode.nodeClassForType(nodeType)
										.getSimpleName(), ASTNode
								.nodeClassForType(nodeType).getSimpleName()));
			}

			// 各レコードを出力
			for (final VectorPairData vectorPair : vectorPairs) {
				final VectorData beforeVector = vectorsMap.get(vectorPair
						.getBeforeVectorId());
				final VectorData afterVector = vectorsMap.get(vectorPair
						.getAfterVectorId());

				final Map<Integer, Integer> afterRawVector = afterVector
						.getVector();
				final int afterValue = (afterRawVector.containsKey(nodeType)) ? afterRawVector
						.get(nodeType) : 0;

				pw.println(beforeVector.toCsvRecord(ignoreList) + ","
						+ afterValue);
			}

			pw.close();
		}

		MessagePrinter.stronglyPrintln("\tcomplete!!");
	}

}
