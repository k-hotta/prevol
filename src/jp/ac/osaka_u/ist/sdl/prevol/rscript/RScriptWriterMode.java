package jp.ac.osaka_u.ist.sdl.prevol.rscript;

public enum RScriptWriterMode {

	LM, GAM, SVM, LMP;
	
	public RScriptWriter getWriterInstance() {
		if (this == LM) {
			return new LMScriptWriter();
		} else if (this == GAM) {
			return new GAMScriptWriter();
		} else if (this == SVM) {
			return new SVMScriptWriter();
		} else if (this == LMP){
			return new LMPoisonScriptWriter();
		}
		else {
			return null;
		}
	}
	
}
