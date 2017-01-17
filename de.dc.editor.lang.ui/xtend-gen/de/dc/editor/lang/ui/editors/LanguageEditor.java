package de.dc.editor.lang.ui.editors;

import de.dc.editor.lang.ui.editors.LanguageConfiguration;
import org.eclipse.ui.editors.text.TextEditor;

@SuppressWarnings("all")
public class LanguageEditor extends TextEditor {
  public LanguageEditor() {
    LanguageConfiguration _languageConfiguration = new LanguageConfiguration();
    this.setSourceViewerConfiguration(_languageConfiguration);
  }
}
