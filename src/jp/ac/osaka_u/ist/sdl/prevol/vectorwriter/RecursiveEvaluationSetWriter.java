package jp.ac.osaka_u.ist.sdl.prevol.vectorwriter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import jp.ac.osaka_u.ist.sdl.prevol.data.NodeType;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorData;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorPairData;
import jp.ac.osaka_u.ist.sdl.prevol.evaluator.PredictedVectorReconstructor;

public class RecursiveEvaluationSetWriter extends
		SingleColumnEvaluationSetWriter {

	public RecursiveEvaluationSetWriter(VectorWriterSettings settings) {
		super(settings);
	}

	@Override
	public void write() throws Exception {
		final PredictedVectorReconstructor reconstructor = new PredictedVectorReconstructor(
				settings.getPredictedResultDir());
		final Map<Integer, SortedMap<NodeType, Integer>> reconstructedVectorsMap = reconstructor
				.reconstruct();

		final Map<Long, VectorData> vectorsMap = new TreeMap<Long, VectorData>();
		for (final Map.Entry<Integer, SortedMap<NodeType, Integer>> entry : reconstructedVectorsMap
				.entrySet()) {
			final long id = (long) entry.getKey();
			final Map<Integer, Integer> vectorMap = new TreeMap<Integer, Integer>();
			for (final Map.Entry<NodeType, Integer> element : entry.getValue()
					.entrySet()) {
				vectorMap.put(element.getKey().getJdtOrdinal(),
						element.getValue());
			}
			
			final VectorData vectorData = new VectorData(id, vectorMap);
			vectorsMap.put(id, vectorData);
		}

		final List<Integer> ignoreList = getIgnoreColumnsList(vectorsMap
				.values());

		final Set<VectorPairData> placeboVectorPairs = new TreeSet<VectorPairData>();
		for (final Map.Entry<Long, VectorData> vectorEntry : vectorsMap
				.entrySet()) {
			placeboVectorPairs.add(new VectorPairData(vectorEntry.getKey(), -1,
					-1, -1, -1, vectorEntry.getValue().getId(), -1));
		}
		
		writeElements(placeboVectorPairs, vectorsMap, ignoreList);
	}

}
