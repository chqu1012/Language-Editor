package de.dc.editor.lang.ui.editors.custom;

import de.dc.editor.lang.model.Content;
import de.dc.editor.lang.model.ContentProposal;
import de.dc.editor.lang.model.LanguageDefinition;
import de.dc.editor.lang.model.Token;
import de.dc.editor.lang.ui.editors.custom.LangCompletionProcessor;
import de.dc.editor.lang.ui.editors.custom.LangScanner;
import de.dc.editor.lang.ui.editors.custom.RecipeCompletionProcessor;
import de.dc.editor.lang.ui.file.LangFile;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class LangConfiguration extends TextSourceViewerConfiguration {
  private LangScanner scanner;
  
  @Override
  public String[] getConfiguredContentTypes(final ISourceViewer sourceViewer) {
    return new String[] { IDocument.DEFAULT_CONTENT_TYPE };
  }
  
  public LangScanner getScanner() {
    LangScanner _xblockexpression = null;
    {
      if ((this.scanner == null)) {
        LangScanner _langScanner = new LangScanner();
        this.scanner = _langScanner;
      }
      _xblockexpression = this.scanner;
    }
    return _xblockexpression;
  }
  
  @Override
  public IPresentationReconciler getPresentationReconciler(final ISourceViewer sourceViewer) {
    PresentationReconciler _xblockexpression = null;
    {
      LangScanner _scanner = this.getScanner();
      final DefaultDamagerRepairer dr = new DefaultDamagerRepairer(_scanner);
      PresentationReconciler _presentationReconciler = new PresentationReconciler();
      final Procedure1<PresentationReconciler> _function = (PresentationReconciler it) -> {
        it.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        it.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
      };
      _xblockexpression = ObjectExtensions.<PresentationReconciler>operator_doubleArrow(_presentationReconciler, _function);
    }
    return _xblockexpression;
  }
  
  @Override
  public IContentAssistant getContentAssistant(final ISourceViewer sourceViewer) {
    try {
      ContentAssistant _xblockexpression = null;
      {
        final LangCompletionProcessor processor = new LangCompletionProcessor();
        final ArrayList<String> contents = new ArrayList<String>();
        final LangFile file = new LangFile();
        final LanguageDefinition model = file.load("C:\\Development\\eclipse-4.7-dsl\\workspace\\de.dc.editor.lang.resource\\java.model");
        EList<ContentProposal> _contentProposals = model.getContentProposals();
        final Consumer<ContentProposal> _function = (ContentProposal proposal) -> {
          EList<Content> _contents = proposal.getContents();
          final Function1<Content, String> _function_1 = (Content it) -> {
            return it.getName();
          };
          List<Content> _sortBy = IterableExtensions.<Content, String>sortBy(_contents, _function_1);
          final Function1<Content, Boolean> _function_2 = (Content it) -> {
            return Boolean.valueOf((it instanceof Token));
          };
          Iterable<Content> _filter = IterableExtensions.<Content>filter(_sortBy, _function_2);
          final Consumer<Content> _function_3 = (Content content) -> {
            String _name = content.getName();
            contents.add(_name);
          };
          _filter.forEach(_function_3);
        };
        _contentProposals.forEach(_function);
        processor.setSTRUCTTAGS1(contents);
        ContentAssistant _contentAssistant = new ContentAssistant();
        final Procedure1<ContentAssistant> _function_1 = (ContentAssistant it) -> {
          String _configuredDocumentPartitioning = this.getConfiguredDocumentPartitioning(sourceViewer);
          it.setDocumentPartitioning(_configuredDocumentPartitioning);
          RecipeCompletionProcessor _recipeCompletionProcessor = new RecipeCompletionProcessor();
          it.setContentAssistProcessor(_recipeCompletionProcessor, IDocument.DEFAULT_CONTENT_TYPE);
          IInformationControlCreator _informationControlCreator = this.getInformationControlCreator(sourceViewer);
          it.setInformationControlCreator(_informationControlCreator);
          it.enableAutoActivation(true);
          it.setAutoActivationDelay(500);
        };
        _xblockexpression = ObjectExtensions.<ContentAssistant>operator_doubleArrow(_contentAssistant, _function_1);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public IInformationControlCreator getInformationControlCreator(final ISourceViewer sourceViewer) {
    final IInformationControlCreator _function = (Shell parent) -> {
      return new DefaultInformationControl(parent);
    };
    return _function;
  }
}
