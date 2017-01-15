package de.dc.editor.lang.ui.editors.custom;

import de.dc.editor.lang.ui.editors.custom.LangConfiguration;
import org.eclipse.ui.editors.text.TextEditor;

@SuppressWarnings("all")
public class LangEditor extends TextEditor {
  public LangEditor() {
    LangConfiguration _langConfiguration = new LangConfiguration();
    this.setSourceViewerConfiguration(_langConfiguration);
  }
}
