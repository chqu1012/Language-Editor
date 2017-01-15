package de.dc.editor.lang.ui.editors.custom

import de.dc.editor.lang.model.Token
import de.dc.editor.lang.ui.file.LangFile
import java.util.ArrayList
import org.eclipse.jface.text.DefaultInformationControl
import org.eclipse.jface.text.IDocument
import org.eclipse.jface.text.IInformationControlCreator
import org.eclipse.jface.text.contentassist.ContentAssistant
import org.eclipse.jface.text.contentassist.IContentAssistant
import org.eclipse.jface.text.presentation.IPresentationReconciler
import org.eclipse.jface.text.presentation.PresentationReconciler
import org.eclipse.jface.text.rules.DefaultDamagerRepairer
import org.eclipse.jface.text.source.ISourceViewer
import org.eclipse.swt.widgets.Shell
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration
import org.eclipse.jface.text.ITextHover

class LangConfiguration extends TextSourceViewerConfiguration {

	LangScanner scanner
	
	override String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		#[IDocument.DEFAULT_CONTENT_TYPE] 
	}
	def LangScanner getScanner() {
		if (scanner === null) {
			scanner=new LangScanner 
		}
		scanner 
	}
	
	override IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		val dr=new DefaultDamagerRepairer(getScanner) 
		new PresentationReconciler=>[
			setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE) 
			setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE) 
		] 
	}
	override IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		val processor=new LangCompletionProcessor 
		val contents = new ArrayList
		val file = new LangFile
		val model = file.load('C:\\Development\\eclipse-4.7-dsl\\workspace\\de.dc.editor.lang.resource\\java.model')
		model.contentProposals.forEach[proposal|
			proposal.contents.sortBy[it.name].filter[it instanceof Token].forEach[content|
				contents+=content.name
			]
		]
		processor.STRUCTTAGS1 = contents
		new ContentAssistant=>[
			documentPartitioning = getConfiguredDocumentPartitioning(sourceViewer)
			setContentAssistProcessor(new RecipeCompletionProcessor, IDocument.DEFAULT_CONTENT_TYPE)
			setInformationControlCreator(getInformationControlCreator(sourceViewer)) 
			enableAutoActivation=true 
			autoActivationDelay=500 
		] 
	}
	override IInformationControlCreator getInformationControlCreator(ISourceViewer sourceViewer) {
		[Shell parent|return new DefaultInformationControl(parent) ] 
	}
}
