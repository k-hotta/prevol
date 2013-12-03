package jp.ac.osaka_u.ist.sdl.prevol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.ac.osaka_u.ist.sdl.prevol.data.VectorData;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.NodeTypeCounter;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeParameter;

public class SingleFileVectorDetector extends ASTVisitor {

	private final CompilationUnit root;

	private final List<SingleFileMethodData> methods;

	public SingleFileVectorDetector(final CompilationUnit root) {
		this.root = root;
		this.methods = new ArrayList<SingleFileMethodData>();
	}

	public final List<SingleFileMethodData> getMethods() {
		return Collections.unmodifiableList(methods);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		final NodeTypeCounter counter = new NodeTypeCounter();
		node.accept(counter);
		final VectorData vector = counter.getVectorData();

		final int start = root.getLineNumber(node.getStartPosition());
		final int end = root.getLineNumber(node.getStartPosition()
				+ node.getLength());

		final String signature = getSignature(node);

		this.methods.add(new SingleFileMethodData(start, end, vector,
				signature));

		return true;
	}

	private String getSignature(MethodDeclaration node) {
		final StringBuilder builder = new StringBuilder();

		for (final Object obj : node.modifiers()) {
			IExtendedModifier modifier = (IExtendedModifier) obj;
			builder.append(modifier.toString());
			builder.append(" ");
		}

		if (!node.typeParameters().isEmpty()) {
			builder.append("<");
			boolean isFirstTypeParameter = true;
			for (Object obj : node.typeParameters()) {
				if (!isFirstTypeParameter) {
					builder.append(",");
				} else {
					isFirstTypeParameter = false;
				}
				TypeParameter param = (TypeParameter) obj;
				builder.append(param.toString());
			}
			builder.append("> ");
		}

		if (!node.isConstructor()) {
			if (node.getReturnType2() != null) {
				builder.append(node.getReturnType2().toString());
			} else {
				builder.append("void");
			}
			builder.append(" ");
		}

		builder.append(node.getName().toString());

		boolean isFirstParameter = true;
		builder.append("(");
		for (Object obj : node.parameters()) {
			if (!isFirstParameter) {
				builder.append(",");
			} else {
				isFirstParameter = false;
			}
			builder.append(obj.toString());
		}

		builder.append(")");
		for (int i = 0; i < node.getExtraDimensions(); i++) {
			builder.append("[]");
		}

		if (!node.thrownExceptions().isEmpty()) {
			builder.append(" throws ");
			boolean isFirstException = true;
			for (Object obj : node.thrownExceptions()) {
				if (!isFirstException) {
					builder.append(", ");
				} else {
					isFirstException = false;
				}
				builder.append(obj.toString());
			}
		}

		return builder.toString();
	}

}
