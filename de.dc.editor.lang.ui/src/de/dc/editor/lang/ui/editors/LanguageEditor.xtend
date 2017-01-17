package de.dc.editor.lang.ui.editors

import org.eclipse.ui.editors.text.TextEditor

class LanguageEditor extends TextEditor{

	new() {
		setSourceViewerConfiguration(new LanguageConfiguration())
	}
}
