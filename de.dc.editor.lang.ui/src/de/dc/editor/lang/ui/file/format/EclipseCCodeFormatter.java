package de.dc.editor.lang.ui.file.format;

import org.eclipse.cdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

public class EclipseCCodeFormatter {

	private CodeFormatter codeFormatter;

	public EclipseCCodeFormatter() {
		codeFormatter = org.eclipse.cdt.core.ToolFactory.createDefaultCodeFormatter(null);
	}

	public String format(String source) {

		// int kind, String source, int offset, int length, int
		// indentationLevel, String lineSeparator

		TextEdit edit = codeFormatter.format(0, source, 0, source.length(), 0, null);

		IDocument document = new Document(source);
		try {
			edit.apply(document);
		} catch (MalformedTreeException e) {
			;
		} catch (BadLocationException e) {
			;
		}
		String formattedSource = document.get();
		return formattedSource;
	}
}