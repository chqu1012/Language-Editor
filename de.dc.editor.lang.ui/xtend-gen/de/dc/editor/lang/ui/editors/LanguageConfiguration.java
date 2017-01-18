package de.dc.editor.lang.ui.editors;

import de.dc.editor.lang.ui.editors.LanguageCompletionProcessor;
import de.dc.editor.lang.ui.editors.LanguageScanner;
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
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class LanguageConfiguration extends TextSourceViewerConfiguration {
  private LanguageScanner scanner;
  
  private String fileExtension;
  
  public LanguageConfiguration(final String fileExtension) {
    this.fileExtension = fileExtension;
  }
  
  @Override
  public String[] getConfiguredContentTypes(final ISourceViewer sourceViewer) {
    return new String[] { IDocument.DEFAULT_CONTENT_TYPE };
  }
  
  public LanguageScanner getScanner() {
    LanguageScanner _xblockexpression = null;
    {
      if ((this.scanner == null)) {
        LanguageScanner _languageScanner = new LanguageScanner(this.fileExtension);
        this.scanner = _languageScanner;
      }
      _xblockexpression = this.scanner;
    }
    return _xblockexpression;
  }
  
  @Override
  public IPresentationReconciler getPresentationReconciler(final ISourceViewer sourceViewer) {
    PresentationReconciler _presentationReconciler = new PresentationReconciler();
    final Procedure1<PresentationReconciler> _function = (PresentationReconciler it) -> {
      LanguageScanner _scanner = this.getScanner();
      final DefaultDamagerRepairer dr = new DefaultDamagerRepairer(_scanner);
      it.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
      it.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
    };
    return ObjectExtensions.<PresentationReconciler>operator_doubleArrow(_presentationReconciler, _function);
  }
  
  @Override
  public IContentAssistant getContentAssistant(final ISourceViewer sourceViewer) {
    ContentAssistant _contentAssistant = new ContentAssistant();
    final Procedure1<ContentAssistant> _function = (ContentAssistant it) -> {
      String _configuredDocumentPartitioning = this.getConfiguredDocumentPartitioning(sourceViewer);
      it.setDocumentPartitioning(_configuredDocumentPartitioning);
      LanguageCompletionProcessor _languageCompletionProcessor = new LanguageCompletionProcessor(this.fileExtension);
      it.setContentAssistProcessor(_languageCompletionProcessor, IDocument.DEFAULT_CONTENT_TYPE);
      IInformationControlCreator _informationControlCreator = this.getInformationControlCreator(sourceViewer);
      it.setInformationControlCreator(_informationControlCreator);
      it.enableAutoActivation(true);
      it.setAutoActivationDelay(500);
    };
    return ObjectExtensions.<ContentAssistant>operator_doubleArrow(_contentAssistant, _function);
  }
  
  @Override
  public IInformationControlCreator getInformationControlCreator(final ISourceViewer sourceViewer) {
    final IInformationControlCreator _function = (Shell parent) -> {
      return new DefaultInformationControl(parent);
    };
    return _function;
  }
}
