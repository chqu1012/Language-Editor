package de.dc.editor.lang.ui.editors;

import de.dc.editor.lang.model.Color;
import de.dc.editor.lang.model.Key;
import de.dc.editor.lang.model.KeywordGroup;
import de.dc.editor.lang.model.LanguageDefinition;
import de.dc.editor.lang.ui.editors.ILanguageConstants;
import de.dc.editor.lang.ui.file.LangFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class LanguageScanner extends RuleBasedScanner {
  public LanguageScanner() {
    try {
      if (((ILanguageConstants.MODEL_PATH == null) || ILanguageConstants.MODEL_PATH.equals(""))) {
        Shell _shell = new Shell();
        FileDialog fd = new FileDialog(_shell, SWT.OPEN);
        String open = fd.open();
        if ((open != null)) {
          IPreferenceStore _preferenceStore = PlatformUI.getPreferenceStore();
          _preferenceStore.setValue(ILanguageConstants.LANGUAGE_PATH, open);
          this.init();
        } else {
          Shell _shell_1 = new Shell();
          MessageDialog.openError(_shell_1, "Language Definition Error", 
            "File cannot be found, please set up the language definition in the preferences.");
        }
      } else {
        this.init();
      }
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  private void init() throws FileNotFoundException, IOException {
    LangFile file = new LangFile();
    LanguageDefinition model = file.load(ILanguageConstants.MODEL_PATH);
    List<IRule> rules = new ArrayList<IRule>();
    EList<KeywordGroup> _keywordGroups = model.getKeywordGroups();
    for (final KeywordGroup group : _keywordGroups) {
      {
        Color color = group.getColor();
        WordRule rule = new WordRule(new IWordDetector() {
          @Override
          public boolean isWordStart(final char c) {
            return Character.isJavaIdentifierStart(c);
          }
          
          @Override
          public boolean isWordPart(final char c) {
            return Character.isJavaIdentifierStart(c);
          }
        });
        EList<Key> _keyList = group.getKeyList();
        for (final Key key : _keyList) {
          {
            String _value = key.getValue();
            Display _current = Display.getCurrent();
            int _r = color.getR();
            int _g = color.getG();
            int _b = color.getB();
            org.eclipse.swt.graphics.Color _color = new org.eclipse.swt.graphics.Color(_current, _r, _g, _b);
            TextAttribute _textAttribute = new TextAttribute(_color, 
              null, SWT.BOLD);
            Token _token = new Token(_textAttribute);
            rule.addWord(_value, _token);
            rules.add(rule);
          }
        }
      }
    }
    IRule[] _newArrayOfSize = new IRule[0];
    IRule[] _array = rules.<IRule>toArray(_newArrayOfSize);
    this.setRules(_array);
  }
}
