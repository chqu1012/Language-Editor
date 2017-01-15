package de.dc.editor.lang.ui.editors;

import org.eclipse.ui.editors.text.TextEditor;

public class LanguageEditor  extends TextEditor {
	
	public LanguageEditor() {
		setSourceViewerConfiguration(new LanguageConfiguration());
	}
}
