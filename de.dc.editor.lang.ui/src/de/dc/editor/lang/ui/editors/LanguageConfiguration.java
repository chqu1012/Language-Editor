package de.dc.editor.lang.ui.editors;

import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

public class LanguageConfiguration extends TextSourceViewerConfiguration {

	private LanguageScanner scanner;

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE };
	}

	private LanguageScanner getScanner() {
		if (scanner == null) {
			scanner = new LanguageScanner();
		}
		return scanner;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getScanner());
		PresentationReconciler presentationReconciler = new PresentationReconciler();
		presentationReconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		presentationReconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		return presentationReconciler;
	}

	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant contentAssistant = new ContentAssistant();
		contentAssistant.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
		contentAssistant.setContentAssistProcessor(new LanguageCompletionProcessor(), IDocument.DEFAULT_CONTENT_TYPE);
		contentAssistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));
		contentAssistant.enableAutoActivation(true);
		contentAssistant.setAutoActivationDelay(500);
		return contentAssistant; 
	}
	@Override
	public  IInformationControlCreator getInformationControlCreator(ISourceViewer sourceViewer) {
		return new IInformationControlCreator(){
			@Override
			public IInformationControl createInformationControl(Shell parent) {
				return new DefaultInformationControl(parent);
			}
		};
	 }
}
