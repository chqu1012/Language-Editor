package de.dc.editor.lang.ui.editors

import org.eclipse.jface.text.DefaultInformationControl
import org.eclipse.jface.text.IDocument
import org.eclipse.jface.text.contentassist.ContentAssistant
import org.eclipse.jface.text.contentassist.IContentAssistant
import org.eclipse.jface.text.presentation.PresentationReconciler
import org.eclipse.jface.text.rules.DefaultDamagerRepairer
import org.eclipse.jface.text.source.ISourceViewer
import org.eclipse.swt.widgets.Shell
import org.eclipse.ui.PlatformUI
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration
import org.eclipse.ui.part.FileEditorInput

class LanguageConfiguration extends TextSourceViewerConfiguration {

	LanguageScanner scanner

	override getConfiguredContentTypes(ISourceViewer sourceViewer) {
		#[IDocument.DEFAULT_CONTENT_TYPE]
	}

	def private LanguageScanner getScanner() {
		if (scanner === null) {
			scanner = new LanguageScanner
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

	override IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		new ContentAssistant => [
			setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer))
			setContentAssistProcessor(new LanguageCompletionProcessor(), IDocument.DEFAULT_CONTENT_TYPE)
			informationControlCreator = getInformationControlCreator(sourceViewer)
			enableAutoActivation = true
			autoActivationDelay = 500
		]
	}

	override getInformationControlCreator(ISourceViewer sourceViewer) {
		[Shell parent|return new DefaultInformationControl(parent)]
	}
}
