package de.dc.editor.lang.ui.editors.custom;

import de.dc.editor.lang.model.Content;
import de.dc.editor.lang.model.ContentProposal;
import de.dc.editor.lang.model.Function;
import de.dc.editor.lang.model.LanguageDefinition;
import de.dc.editor.lang.model.Token;
import de.dc.editor.lang.ui.file.LangFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jface.text.templates.DocumentTemplateContext;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
class RecipeCompletionProcessor implements IContentAssistProcessor {
  private static String CONTEXT_ID = "bitbake_variables";
  
  private TemplateContextType fContextType = new TemplateContextType(RecipeCompletionProcessor.CONTEXT_ID, "Common BitBake Variables");
  
  private TemplateContextType fKeywordContextType = new TemplateContextType("bitbake_keywords", "BitBake Keywords");
  
  private TemplateContextType fFunctionContextType = new TemplateContextType("bitbake_functions", "BitBake Functions");
  
  RecipeCompletionProcessor() {
  }
  
  @Override
  public ICompletionProposal[] computeCompletionProposals(final ITextViewer viewer, final int offset) {
    ICompletionProposal[] _xblockexpression = null;
    {
      IDocument document = viewer.getDocument();
      Region region = new Region(offset, 0);
      DocumentTemplateContext templateContext = new DocumentTemplateContext(this.fContextType, document, offset, 0);
      DocumentTemplateContext keywordContext = new DocumentTemplateContext(this.fKeywordContextType, document, offset, 0);
      DocumentTemplateContext functionContext = new DocumentTemplateContext(this.fFunctionContextType, document, offset, 0);
      ArrayList<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
      this.getVariableTemplateProposals(templateContext, region, proposals);
      this.getKeywordTemplateProposals(keywordContext, region, proposals);
      this.getAddTaskTemplateProposals(templateContext, region, proposals);
      this.getFunctionTemplateProposals(functionContext, region, proposals);
      _xblockexpression = proposals.<ICompletionProposal>toArray(new ICompletionProposal[] {});
    }
    return _xblockexpression;
  }
  
  @Override
  public IContextInformation[] computeContextInformation(final ITextViewer viewer, final int offset) {
    return null;
  }
  
  private Template generateVariableTemplate(final String name, final String description) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(name, "");
    _builder.append(" = \"${");
    String _lowerCase = name.toLowerCase();
    _builder.append(_lowerCase, "");
    _builder.append("}\"");
    return new Template(name, description, RecipeCompletionProcessor.CONTEXT_ID, _builder.toString(), false);
  }
  
  private void getAddTaskTemplateProposals(final TemplateContext templateContext, final Region region, final List<ICompletionProposal> p) {
    try {
      ArrayList<Content> keywords = new ArrayList<Content>();
      final Comparator<Content> _function = (Content o1, Content o2) -> {
        String _name = o1.getName();
        String _name_1 = o2.getName();
        return _name.compareTo(_name_1);
      };
      Collections.<Content>sort(keywords, _function);
      LangFile file = new LangFile();
      LanguageDefinition model = file.load("C:\\Development\\eclipse-4.7-dsl\\workspace\\de.dc.editor.lang.resource\\java.model");
      EList<ContentProposal> _contentProposals = model.getContentProposals();
      for (final ContentProposal prop : _contentProposals) {
        EList<Content> _contents = prop.getContents();
        final Function1<Content, Boolean> _function_1 = (Content it) -> {
          return Boolean.valueOf((it instanceof de.dc.editor.lang.model.Template));
        };
        Iterable<Content> _filter = IterableExtensions.<Content>filter(_contents, _function_1);
        for (final Content content : _filter) {
          keywords.add(content);
        }
      }
      final Consumer<Content> _function_2 = (Content key) -> {
        final String name = key.getName();
        final String descr = key.getDescription();
        final String pattern = key.getPattern();
        Template _template = new Template(name, descr, RecipeCompletionProcessor.CONTEXT_ID, pattern, false);
        TemplateProposal _templateProposal = new TemplateProposal(_template, templateContext, region, null);
        p.add(_templateProposal);
      };
      keywords.forEach(_function_2);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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
  public IContextInformationValidator getContextInformationValidator() {
    return null;
  }
  
  @Override
  public String getErrorMessage() {
    return null;
  }
  
  private void getFunctionTemplateProposals(final TemplateContext templateContext, final Region region, final List<ICompletionProposal> p) {
    try {
      IWorkbench _workbench = PlatformUI.getWorkbench();
      ISharedImages _sharedImages = _workbench.getSharedImages();
      Image img = _sharedImages.getImage(ISharedImages.IMG_OBJS_INFO_TSK);
      List<Content> keywords = new ArrayList<Content>();
      final Comparator<Content> _function = (Content o1, Content o2) -> {
        String _name = o1.getName();
        String _name_1 = o2.getName();
        return _name.compareTo(_name_1);
      };
      Collections.<Content>sort(keywords, _function);
      LangFile file = new LangFile();
      LanguageDefinition model = file.load("C:\\Development\\eclipse-4.7-dsl\\workspace\\de.dc.editor.lang.resource\\java.model");
      EList<ContentProposal> _contentProposals = model.getContentProposals();
      for (final ContentProposal prop : _contentProposals) {
        EList<Content> _contents = prop.getContents();
        final Function1<Content, Boolean> _function_1 = (Content it) -> {
          return Boolean.valueOf((it instanceof Function));
        };
        Iterable<Content> _filter = IterableExtensions.<Content>filter(_contents, _function_1);
        for (final Content content : _filter) {
          keywords.add(content);
        }
      }
      {
        int i = 0;
        final List<Content> _converted_keywords = (List<Content>)keywords;
        int _length = ((Object[])Conversions.unwrapArray(_converted_keywords, Object.class)).length;
        boolean _lessThan = (i < _length);
        boolean _while = _lessThan;
        while (_while) {
          {
            Content _get = keywords.get(i);
            final String name = _get.getName();
            Content _get_1 = keywords.get(i);
            final String description = _get_1.getDescription();
            Content _get_2 = keywords.get(i);
            final String pattern = _get_2.getPattern();
            Template _template = new Template(name, description, RecipeCompletionProcessor.CONTEXT_ID, pattern, false);
            TemplateProposal _templateProposal = new TemplateProposal(_template, templateContext, region, img);
            p.add(_templateProposal);
          }
          i = (i + 1);
          final List<Content> _converted_keywords_1 = (List<Content>)keywords;
          int _length_1 = ((Object[])Conversions.unwrapArray(_converted_keywords_1, Object.class)).length;
          boolean _lessThan_1 = (i < _length_1);
          _while = _lessThan_1;
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void getKeywordTemplateProposals(final TemplateContext templateContext, final Region region, final List<ICompletionProposal> proposals) {
    List<Content> keywords = new ArrayList<Content>();
    IWorkbench _workbench = PlatformUI.getWorkbench();
    ISharedImages _sharedImages = _workbench.getSharedImages();
    Image img = _sharedImages.getImage(ISharedImages.IMG_ELCL_STOP);
    final Comparator<Content> _function = (Content o1, Content o2) -> {
      String _name = o1.getName();
      String _name_1 = o2.getName();
      return _name.compareTo(_name_1);
    };
    Collections.<Content>sort(keywords, _function);
    try {
      LangFile file = new LangFile();
      LanguageDefinition model = file.load(
        "C:\\Development\\eclipse-4.7-dsl\\workspace\\de.dc.editor.lang.resource\\java.model");
      EList<ContentProposal> _contentProposals = model.getContentProposals();
      for (final ContentProposal prop : _contentProposals) {
        EList<Content> _contents = prop.getContents();
        final Function1<Content, Boolean> _function_1 = (Content it) -> {
          return Boolean.valueOf((it instanceof Token));
        };
        Iterable<Content> _filter = IterableExtensions.<Content>filter(_contents, _function_1);
        for (final Content content : _filter) {
          keywords.add(content);
        }
      }
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    {
      int i = 0;
      int _size = keywords.size();
      boolean _lessThan = (i < _size);
      boolean _while = _lessThan;
      while (_while) {
        {
          Content _get = keywords.get(i);
          String name = _get.getName();
          Content _get_1 = keywords.get(i);
          String description = _get_1.getDescription();
          StringConcatenation _builder = new StringConcatenation();
          _builder.append(name, "");
          _builder.append(" ");
          Template _template = new Template(name, description, RecipeCompletionProcessor.CONTEXT_ID, _builder.toString(), false);
          TemplateProposal _templateProposal = new TemplateProposal(_template, templateContext, region, img);
          proposals.add(_templateProposal);
        }
        i = (i + 1);
        int _size_1 = keywords.size();
        boolean _lessThan_1 = (i < _size_1);
        _while = _lessThan_1;
      }
    }
  }
  
  private void getVariableTemplateProposals(final TemplateContext templateContext, final Region region, final List<ICompletionProposal> p) {
    Map<String, String> n = new HashMap<String, String>();
    Image img = null;
    for (Iterator<String> i = n.keySet().iterator(); i.hasNext();) {
      {
        String _next = i.next();
        String name = ((String) _next);
        String _get = n.get(name);
        String description = ((String) _get);
        Template _generateVariableTemplate = this.generateVariableTemplate(name, description);
        TemplateProposal _templateProposal = new TemplateProposal(_generateVariableTemplate, templateContext, region, img);
        p.add(_templateProposal);
      }
    }
  }
}
