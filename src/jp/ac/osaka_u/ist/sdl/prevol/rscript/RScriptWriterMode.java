package jp.ac.osaka_u.ist.sdl.prevol.rscript;

public enum RScriptWriterMode {

	LM, GAM, SVM;
	
	public RScriptWriter getWriterInstance() {
		if (this == LM) {
			return new LMScriptWriter();
		} else if (this == GAM) {
			return new GAMScriptWriter();
		} else if (this == SVM) {
			return new SVMScriptWriter();
		} else {
			return null;
		}
	}
	
}
