package jp.ac.osaka_u.ist.sdl.prevol.rscript;

public enum RScriptWriterMode {

	LM, GAM;
	
	public RScriptWriter getWriterInstance() {
		if (this == LM) {
			return new LMScriptWriter();
		} else if (this == GAM) {
			return new GAMScriptWriter();
		} else {
			return null;
		}
	}
	
}
