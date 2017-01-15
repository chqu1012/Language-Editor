package de.dc.editor.lang.ui.editors.custom;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ContextInformationValidator;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.graphics.Point;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class LangCompletionProcessor implements IContentAssistProcessor {
  @Override
  public ICompletionProposal[] computeCompletionProposals(final ITextViewer viewer, final int documentOffset) {
    try {
      ICompletionProposal[] _xblockexpression = null;
      {
        IDocument doc = viewer.getDocument();
        Point selectedRange = viewer.getSelectedRange();
        ArrayList<CompletionProposal> propList = new ArrayList<CompletionProposal>();
        if ((selectedRange.y > 0)) {
          String text = doc.get(selectedRange.x, selectedRange.y);
          this.computeStyleProposals(text, selectedRange, propList);
        } else {
          String qualifier = this.getQualifier(doc, documentOffset);
          this.computeStructureProposals(qualifier, documentOffset, propList);
        }
        int _size = propList.size();
        ICompletionProposal[] proposals = new ICompletionProposal[_size];
        propList.<ICompletionProposal>toArray(proposals);
        _xblockexpression = proposals;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public String getQualifier(final IDocument doc, final int documentOffset_finalParam_) {
    try {
      int documentOffset = documentOffset_finalParam_;
      StringBuffer buf = new StringBuffer();
      while (true) {
        {
          char _char = doc.getChar(documentOffset = (documentOffset - 1));
          char c = ((char) _char);
          if (((c == Character.valueOf('>').charValue()) || Character.isWhitespace(c))) {
            return "";
          }
          Character _valueOf = Character.valueOf('}');
          char _charValue = _valueOf.charValue();
          boolean _tripleEquals = (c == _charValue);
          if (_tripleEquals) {
            return "";
          }
          Character _valueOf_1 = Character.valueOf(')');
          char _charValue_1 = _valueOf_1.charValue();
          boolean _tripleEquals_1 = (c == _charValue_1);
          if (_tripleEquals_1) {
            return "";
          }
          buf.append(c);
          Character _valueOf_2 = Character.valueOf('<');
          char _charValue_2 = _valueOf_2.charValue();
          boolean _tripleEquals_2 = (c == _charValue_2);
          if (_tripleEquals_2) {
            StringBuffer _reverse = buf.reverse();
            return _reverse.toString();
          }
          Character _valueOf_3 = Character.valueOf('{');
          char _charValue_3 = _valueOf_3.charValue();
          boolean _tripleEquals_3 = (c == _charValue_3);
          if (_tripleEquals_3) {
            StringBuffer _reverse_1 = buf.reverse();
            return _reverse_1.toString();
          }
          Character _valueOf_4 = Character.valueOf('(');
          char _charValue_4 = _valueOf_4.charValue();
          boolean _tripleEquals_4 = (c == _charValue_4);
          if (_tripleEquals_4) {
            StringBuffer _reverse_2 = buf.reverse();
            return _reverse_2.toString();
          }
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Accessors
  private List<String> STRUCTTAGS1 = new ArrayList<String>();
  
  @Accessors
  private List<String> STRUCTTAGS2 = new ArrayList<String>();
  
  public void computeStructureProposals(final String qualifier, final int documentOffset, final List<CompletionProposal> propList) {
    InputOutput.<String>println(qualifier);
    int qlen = qualifier.length();
    for (int i = 0; (i < ((Object[])Conversions.unwrapArray(this.STRUCTTAGS1, Object.class)).length); i++) {
      {
        String startTag = this.STRUCTTAGS1.get(i);
        boolean _startsWith = startTag.startsWith(qualifier);
        if (_startsWith) {
          String text = startTag;
          int cursor = startTag.length();
          CompletionProposal proposal = new CompletionProposal(text, (documentOffset - qlen), qlen, cursor);
          propList.add(proposal);
        }
      }
    }
  }
  
  private final static String[] STYLETAGS = { "b", "i", "code", "strong" };
  
  private final static String[] STYLELABELS = { "bold", "italic", "code", "strong" };
  
  private void computeStyleProposals(final String selectedText, final Point selectedRange, final List propList) {
    for (int i = 0; (i < LangCompletionProcessor.STYLETAGS.length); i++) {
      {
        String _xblockexpression = null;
        {
          final int _rdIndx_STYLETAGS = i;
          _xblockexpression = LangCompletionProcessor.STYLETAGS[_rdIndx_STYLETAGS];
        }
        String tag = _xblockexpression;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("<");
        _builder.append(tag, "");
        _builder.append(">");
        _builder.append(selectedText, "");
        _builder.append("</");
        _builder.append(tag, "");
        _builder.append(">");
        String replacement = _builder.toString();
        int _length = tag.length();
        int cursor = (_length + 2);
        StringConcatenation _builder_1 = new StringConcatenation();
        String _xblockexpression_1 = null;
        {
          final int _rdIndx_STYLELABELS = i;
          _xblockexpression_1 = LangCompletionProcessor.STYLELABELS[_rdIndx_STYLELABELS];
        }
        _builder_1.append(_xblockexpression_1, "");
        _builder_1.append(" Style");
        ContextInformation contextInfo = new ContextInformation(
          null, _builder_1.toString());
        String _xblockexpression_2 = null;
        {
          final int _rdIndx_STYLELABELS = i;
          _xblockexpression_2 = LangCompletionProcessor.STYLELABELS[_rdIndx_STYLELABELS];
        }
        CompletionProposal proposal = new CompletionProposal(replacement, selectedRange.x, selectedRange.y, cursor, null, _xblockexpression_2, contextInfo, replacement);
        propList.add(proposal);
      }
    }
  }
  
  @Override
  public IContextInformation[] computeContextInformation(final ITextViewer viewer, final int documentOffset) {
    Point selectedRange = viewer.getSelectedRange();
    if ((selectedRange.y > 0)) {
      int _length = LangCompletionProcessor.STYLELABELS.length;
      ContextInformation[] contextInfos = new ContextInformation[_length];
      for (int i = 0; (i < LangCompletionProcessor.STYLELABELS.length); i++) {
        {
          final ContextInformation[] _wrVal_contextInfos = contextInfos;
          final int _wrIndx_contextInfos = i;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("<");
          String _xblockexpression = null;
          {
            final int _rdIndx_STYLETAGS = i;
            _xblockexpression = LangCompletionProcessor.STYLETAGS[_rdIndx_STYLETAGS];
          }
          _builder.append(_xblockexpression, "");
          _builder.append(">");
          StringConcatenation _builder_1 = new StringConcatenation();
          String _xblockexpression_1 = null;
          {
            final int _rdIndx_STYLELABELS = i;
            _xblockexpression_1 = LangCompletionProcessor.STYLELABELS[_rdIndx_STYLELABELS];
          }
          _builder_1.append(_xblockexpression_1, "");
          _builder_1.append(" Style");
          ContextInformation _contextInformation = new ContextInformation(_builder.toString(), _builder_1.toString());
          _wrVal_contextInfos[_wrIndx_contextInfos] = _contextInformation;
        }
      }
      return contextInfos;
    }
    return new IContextInformation[0];
  }
  
  @Override
  public char[] getCompletionProposalAutoActivationCharacters() {
    return null;
  }
  
  @Override
  public char[] getContextInformationAutoActivationCharacters() {
    return null;
  }
  
  @Override
  public String getErrorMessage() {
    return null;
  }
  
  @Override
  public IContextInformationValidator getContextInformationValidator() {
    return new ContextInformationValidator(this);
  }
  
  @Pure
  public List<String> getSTRUCTTAGS1() {
    return this.STRUCTTAGS1;
  }
  
  public void setSTRUCTTAGS1(final List<String> STRUCTTAGS1) {
    this.STRUCTTAGS1 = STRUCTTAGS1;
  }
  
  @Pure
  public List<String> getSTRUCTTAGS2() {
    return this.STRUCTTAGS2;
  }
  
  public void setSTRUCTTAGS2(final List<String> STRUCTTAGS2) {
    this.STRUCTTAGS2 = STRUCTTAGS2;
  }
}
