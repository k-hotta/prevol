package jp.ac.osaka_u.ist.sdl.prevol.rscript;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CSVReader {

	public static CSVData read(final String path) throws Exception {
		final BufferedReader br = new BufferedReader(new FileReader(new File(path)));

		final Map<Integer, Set<Integer>> columnValues = new TreeMap<Integer, Set<Integer>>();
		
		String line = br.readLine(); // 1行目はスルー
		while ((line = br.readLine()) != null) {
			final String[] split = line.split(",");
			int column = 1;
			for (final String fragmented : split) {
				final int value = Integer.parseInt(fragmented);
				if (columnValues.containsKey(column)) {
					columnValues.get(column).add(value);
				} else {
					final Set<Integer> newSet = new HashSet<Integer>();
					newSet.add(value);
					columnValues.put(column, newSet);
				}
				column++;
			}
		}
		
		final Map<Integer, Integer> result = new TreeMap<Integer, Integer>();
		for (final Map.Entry<Integer, Set<Integer>> entry : columnValues.entrySet()) {
			result.put(entry.getKey(), entry.getValue().size());
		}
		
		br.close();
		
		return new CSVData(result);
	}
	
}
