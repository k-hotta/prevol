package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn;

/**
 * An exception class to represent the repository is not initialized
 * 
 * @author k-hotta
 * 
 */
public class RepositoryNotInitializedException extends Exception {

	public RepositoryNotInitializedException(final String message) {
		super(message);
	}

}
