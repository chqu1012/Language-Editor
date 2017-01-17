package de.dc.editor.lang.ui.editors;

import de.dc.editor.lang.model.Content;
import de.dc.editor.lang.model.ContentProposal;
import de.dc.editor.lang.model.Function;
import de.dc.editor.lang.model.Image;
import de.dc.editor.lang.model.LanguageDefinition;
import de.dc.editor.lang.model.Token;
import de.dc.editor.lang.ui.editors.ILanguageConstants;
import de.dc.editor.lang.ui.file.LangFile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.ECollections;
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
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class LanguageCompletionProcessor implements IContentAssistProcessor {
  private final static String CONTEXT_ID = "language_variables";
  
  private final TemplateContextType fContextType = new TemplateContextType(LanguageCompletionProcessor.CONTEXT_ID, "Common Language Variables");
  
  private final TemplateContextType fKeywordContextType = new TemplateContextType("language_keywords", "Language Keywords");
  
  private final TemplateContextType fFunctionContextType = new TemplateContextType("functions", "Language Functions");
  
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
  
  public Template generateVariableTemplate(final String name, final String description) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(name, "");
    _builder.append(" = \"${");
    String _lowerCase = name.toLowerCase();
    _builder.append(_lowerCase, "");
    _builder.append("}\"");
    return new Template(name, description, LanguageCompletionProcessor.CONTEXT_ID, _builder.toString(), false);
  }
  
  public void getAddTaskTemplateProposals(final TemplateContext templateContext, final Region region, final List<ICompletionProposal> p) {
    try {
      LangFile file = new LangFile();
      LanguageDefinition model = file.load(ILanguageConstants.MODEL_PATH);
      EList<ContentProposal> _contentProposals = model.getContentProposals();
      for (final ContentProposal prop : _contentProposals) {
        {
          EList<Content> _contents = prop.getContents();
          final Comparator<Content> _function = (Content o1, Content o2) -> {
            String _name = o1.getName();
            String _name_1 = o2.getName();
            return _name.compareTo(_name_1);
          };
          ECollections.<Content>sort(_contents, _function);
          IWorkbench _workbench = PlatformUI.getWorkbench();
          ISharedImages _sharedImages = _workbench.getSharedImages();
          Image _image = prop.getImage();
          String _name = _image.getName();
          org.eclipse.swt.graphics.Image img = _sharedImages.getImage(_name);
          EList<Content> _contents_1 = prop.getContents();
          final Function1<Content, Boolean> _function_1 = (Content it) -> {
            return Boolean.valueOf((it instanceof de.dc.editor.lang.model.Template));
          };
          Iterable<Content> _filter = IterableExtensions.<Content>filter(_contents_1, _function_1);
          for (final Content content : _filter) {
            {
              String name = content.getName();
              String descr = content.getDescription();
              String pattern = content.getPattern();
              Template _template = new Template(name, descr, LanguageCompletionProcessor.CONTEXT_ID, pattern, false);
              TemplateProposal _templateProposal = new TemplateProposal(_template, templateContext, region, img);
              p.add(_templateProposal);
            }
          }
        }
      }
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
  
  public void getFunctionTemplateProposals(final TemplateContext templateContext, final Region region, final List<ICompletionProposal> p) {
    try {
      LangFile file = new LangFile();
      LanguageDefinition model = file.load(ILanguageConstants.MODEL_PATH);
      EList<ContentProposal> _contentProposals = model.getContentProposals();
      for (final ContentProposal prop : _contentProposals) {
        {
          EList<Content> _contents = prop.getContents();
          final Comparator<Content> _function = (Content o1, Content o2) -> {
            String _name = o1.getName();
            String _name_1 = o2.getName();
            return _name.compareTo(_name_1);
          };
          ECollections.<Content>sort(_contents, _function);
          IWorkbench _workbench = PlatformUI.getWorkbench();
          ISharedImages _sharedImages = _workbench.getSharedImages();
          Image _image = prop.getImage();
          String _name = _image.getName();
          org.eclipse.swt.graphics.Image img = _sharedImages.getImage(_name);
          EList<Content> _contents_1 = prop.getContents();
          final Function1<Content, Boolean> _function_1 = (Content it) -> {
            return Boolean.valueOf((it instanceof Function));
          };
          Iterable<Content> _filter = IterableExtensions.<Content>filter(_contents_1, _function_1);
          for (final Content content : _filter) {
            {
              String name = content.getName();
              String descr = content.getDescription();
              String pattern = content.getPattern();
              Template _template = new Template(name, descr, LanguageCompletionProcessor.CONTEXT_ID, pattern, false);
              TemplateProposal _templateProposal = new TemplateProposal(_template, templateContext, region, img);
              p.add(_templateProposal);
            }
          }
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void getKeywordTemplateProposals(final TemplateContext templateContext, final Region region, final List<ICompletionProposal> p) {
    try {
      LangFile file = new LangFile();
      LanguageDefinition model = file.load(ILanguageConstants.MODEL_PATH);
      EList<ContentProposal> _contentProposals = model.getContentProposals();
      for (final ContentProposal prop : _contentProposals) {
        {
          EList<Content> _contents = prop.getContents();
          final Comparator<Content> _function = (Content o1, Content o2) -> {
            String _name = o1.getName();
            String _name_1 = o2.getName();
            return _name.compareTo(_name_1);
          };
          ECollections.<Content>sort(_contents, _function);
          IWorkbench _workbench = PlatformUI.getWorkbench();
          ISharedImages _sharedImages = _workbench.getSharedImages();
          Image _image = prop.getImage();
          String _name = _image.getName();
          org.eclipse.swt.graphics.Image img = _sharedImages.getImage(_name);
          EList<Content> _contents_1 = prop.getContents();
          final Function1<Content, Boolean> _function_1 = (Content it) -> {
            return Boolean.valueOf((it instanceof Token));
          };
          Iterable<Content> _filter = IterableExtensions.<Content>filter(_contents_1, _function_1);
          for (final Content content : _filter) {
            {
              String name = content.getName();
              String descr = content.getDescription();
              String pattern = content.getPattern();
              Template _template = new Template(name, descr, LanguageCompletionProcessor.CONTEXT_ID, pattern, false);
              TemplateProposal _templateProposal = new TemplateProposal(_template, templateContext, region, img);
              p.add(_templateProposal);
            }
          }
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void getVariableTemplateProposals(final TemplateContext templateContext, final Region region, final List<ICompletionProposal> p) {
    Map<String, String> n = new HashMap<String, String>();
    org.eclipse.swt.graphics.Image img = null;
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