package de.dc.editor.lang.ui.editors;

import com.google.common.base.Objects;
import de.dc.editor.lang.model.Color;
import de.dc.editor.lang.model.Key;
import de.dc.editor.lang.model.KeywordGroup;
import de.dc.editor.lang.model.LanguageDefinition;
import de.dc.editor.lang.ui.editors.LanguageDefinitionProvider;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class LanguageScanner extends RuleBasedScanner {
  private String fileExtension;
  
  public LanguageScanner(final String fileExtension) {
    try {
      this.fileExtension = fileExtension;
      this.init();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void init() throws FileNotFoundException, IOException {
    final LanguageDefinition model = LanguageDefinitionProvider.instance.getDefinitionByExtension(this.fileExtension);
    boolean _notEquals = (!Objects.equal(model, null));
    if (_notEquals) {
      ArrayList<IRule> rules = new ArrayList<IRule>();
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
      boolean _isUseMultiQuotesHighlighting = model.isUseMultiQuotesHighlighting();
      if (_isUseMultiQuotesHighlighting) {
        Display _current = Display.getCurrent();
        org.eclipse.swt.graphics.Color _systemColor = _current.getSystemColor(SWT.COLOR_GREEN);
        TextAttribute _textAttribute = new TextAttribute(_systemColor);
        Token _token = new Token(_textAttribute);
        SingleLineRule _singleLineRule = new SingleLineRule("\"", "\"", _token, '\\');
        rules.add(_singleLineRule);
      }
      boolean _isUseSingleQuotesHighlighting = model.isUseSingleQuotesHighlighting();
      if (_isUseSingleQuotesHighlighting) {
        Display _current_1 = Display.getCurrent();
        org.eclipse.swt.graphics.Color _systemColor_1 = _current_1.getSystemColor(SWT.COLOR_GREEN);
        TextAttribute _textAttribute_1 = new TextAttribute(_systemColor_1);
        Token _token_1 = new Token(_textAttribute_1);
        SingleLineRule _singleLineRule_1 = new SingleLineRule("\'", "\'", _token_1, '\\');
        rules.add(_singleLineRule_1);
      }
      boolean _isUseMultiLineCommentHighlighting = model.isUseMultiLineCommentHighlighting();
      if (_isUseMultiLineCommentHighlighting) {
        Display _current_2 = Display.getCurrent();
        org.eclipse.swt.graphics.Color _systemColor_2 = _current_2.getSystemColor(SWT.COLOR_BLUE);
        TextAttribute _textAttribute_2 = new TextAttribute(_systemColor_2);
        Token _token_2 = new Token(_textAttribute_2);
        MultiLineRule _multiLineRule = new MultiLineRule("/*", "*/", _token_2);
        rules.add(_multiLineRule);
      }
      boolean _isUseSingleLineCommentHighlighting = model.isUseSingleLineCommentHighlighting();
      if (_isUseSingleLineCommentHighlighting) {
        Display _current_3 = Display.getCurrent();
        org.eclipse.swt.graphics.Color _systemColor_3 = _current_3.getSystemColor(SWT.COLOR_BLUE);
        TextAttribute _textAttribute_3 = new TextAttribute(_systemColor_3);
        Token _token_3 = new Token(_textAttribute_3);
        EndOfLineRule _endOfLineRule = new EndOfLineRule("//", _token_3);
        rules.add(_endOfLineRule);
      }
      IRule[] _newArrayOfSize = new IRule[0];
      IRule[] _array = rules.<IRule>toArray(_newArrayOfSize);
      this.setRules(_array);
    }
  }
}
