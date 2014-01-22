package jp.ac.osaka_u.ist.sdl.prevol.vectorsourcereader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import jp.ac.osaka_u.ist.sdl.prevol.data.FileData;
import jp.ac.osaka_u.ist.sdl.prevol.data.MethodData;
import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn.SVNRepositoryManager;
import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinter;

public class VectorSourceReader {

	public static void main(String[] args) throws Exception {
		final String url = args[0];
		final String dbPath = args[1];
		final long id = Long.parseLong(args[2]);
		final String outputPath = args[4];

		// リポジトリとのコネクションを生成
		MessagePrinter.stronglyPrintln("initializing repository ... ");
		SVNRepositoryManager.setup(url, null, null, null);
		MessagePrinter.stronglyPrintln("\tOK");
		MessagePrinter.stronglyPrintln();

		// データベースとのコネクションを生成
		MessagePrinter.stronglyPrintln("initializing db ... ");
		DBConnection.createInstance(dbPath);
		MessagePrinter.stronglyPrintln("\tOK");
		MessagePrinter.stronglyPrintln();

		final Set<Long> ids = new HashSet<Long>();
		ids.add(id);
		final MethodData method = DBConnection.getInstance()
				.getMethodRetriever().retrieveWithIds(ids).first();

		ids.clear();
		ids.add(method.getFileId());
		final FileData file = DBConnection.getInstance().getFileRetriever()
				.retrieveWithIds(ids).first();

		ids.clear();
		ids.add(file.getStartRevisionId());
		final RevisionData revision = DBConnection.getInstance()
				.getRevisionRetriever().retrieveWithIds(ids).first();

		final long revisionNum = revision.getRevisionNum();
		final String filePath = file.getPath();
		final int start = method.getStartLine();
		final int end = method.getEndLine();

		final String content = SVNRepositoryManager.getFileContents(
				revisionNum, filePath);
		final BufferedReader br = new BufferedReader(new StringReader(content));

		String line;
		int index = 1;
		final StringBuilder builder = new StringBuilder();
		while ((line = br.readLine()) != null) {
			if (index > end) {
				break;
			}
			if (start <= index && index <= end) {
				builder.append(line + "\n");
			}
			index++;
		}

		br.close();

		System.out.println(builder.toString());

		if (DBConnection.getInstance() != null) {
			DBConnection.getInstance().close();
		}

		final PrintWriter pw = new PrintWriter(new BufferedWriter(
				new FileWriter(new File(outputPath))));
		
		pw.println(builder.toString());
		
		pw.close();
	}

}
