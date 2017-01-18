package de.dc.editor.lang.ui.editors

import org.eclipse.jface.text.DefaultInformationControl
import org.eclipse.jface.text.IDocument
import org.eclipse.jface.text.contentassist.ContentAssistant
import org.eclipse.jface.text.presentation.PresentationReconciler
import org.eclipse.jface.text.rules.DefaultDamagerRepairer
import org.eclipse.jface.text.source.ISourceViewer
import org.eclipse.swt.widgets.Shell
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration

class LanguageConfiguration extends TextSourceViewerConfiguration {

	LanguageScanner scanner
	String fileExtension
	
	new(String fileExtension) {
		this.fileExtension=fileExtension
	}
	
	override getConfiguredContentTypes(ISourceViewer sourceViewer) {
		#[IDocument.DEFAULT_CONTENT_TYPE]
	}

	def LanguageScanner getScanner() {
		if (scanner === null) {
			scanner = new LanguageScanner(fileExtension)
		}
		scanner
	}

	override getPresentationReconciler(ISourceViewer sourceViewer) {
		new PresentationReconciler => [
			val dr = new DefaultDamagerRepairer(getScanner)
			setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE)
			setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE)
		]
	}

	override getContentAssistant(ISourceViewer sourceViewer) {
		new ContentAssistant => [
			setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer))
			setContentAssistProcessor(new LanguageCompletionProcessor(fileExtension), IDocument.DEFAULT_CONTENT_TYPE)
			informationControlCreator = getInformationControlCreator(sourceViewer)
			enableAutoActivation = true
			autoActivationDelay = 500
		]
	}

	override getInformationControlCreator(ISourceViewer sourceViewer) {
		[Shell parent|return new DefaultInformationControl(parent)]
	}
}
