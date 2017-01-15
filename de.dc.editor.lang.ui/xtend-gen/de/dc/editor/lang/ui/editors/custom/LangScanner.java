package de.dc.editor.lang.ui.editors.custom;

import de.dc.editor.lang.model.Color;
import de.dc.editor.lang.model.Key;
import de.dc.editor.lang.model.KeywordGroup;
import de.dc.editor.lang.model.LanguageDefinition;
import de.dc.editor.lang.ui.file.LangFile;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class LangScanner extends RuleBasedScanner {
  public LangScanner() {
    try {
      final LangFile file = new LangFile();
      final LanguageDefinition model = file.load("C:\\Development\\eclipse-4.7-dsl\\workspace\\de.dc.editor.lang.resource\\java.model");
      final ArrayList<IRule> rules = new ArrayList<IRule>();
      EList<KeywordGroup> _keywordGroups = model.getKeywordGroups();
      final Consumer<KeywordGroup> _function = (KeywordGroup group) -> {
        final Color color = group.getColor();
        final WordRule rule = new WordRule(new IWordDetector() {
          @Override
          public boolean isWordPart(final char c) {
            return Character.isJavaIdentifierStart(c);
          }
          
          @Override
          public boolean isWordStart(final char c) {
            return Character.isJavaIdentifierPart(c);
          }
        });
        EList<Key> _keyList = group.getKeyList();
        final Consumer<Key> _function_1 = (Key key) -> {
          String _value = key.getValue();
          Display _current = Display.getCurrent();
          int _r = color.getR();
          int _g = color.getG();
          int _b = color.getB();
          org.eclipse.swt.graphics.Color _color = new org.eclipse.swt.graphics.Color(_current, _r, _g, _b);
          TextAttribute _textAttribute = new TextAttribute(_color, null, SWT.BOLD);
          Token _token = new Token(_textAttribute);
          rule.addWord(_value, _token);
        };
        _keyList.forEach(_function_1);
        rules.add(rule);
      };
      _keywordGroups.forEach(_function);
      this.setRules(((IRule[])Conversions.unwrapArray(rules, IRule.class)));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
