package de.dc.editor.lang.ui.editors

import de.dc.editor.lang.model.Content
import de.dc.editor.lang.model.ContentProposal
import de.dc.editor.lang.model.Function
import de.dc.editor.lang.model.Token
import de.dc.editor.lang.ui.file.LangFile
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.Iterator
import java.util.List
import java.util.Map
import org.eclipse.emf.common.util.ECollections
import org.eclipse.jface.text.ITextViewer
import org.eclipse.jface.text.Region
import org.eclipse.jface.text.contentassist.ICompletionProposal
import org.eclipse.jface.text.contentassist.IContentAssistProcessor
import org.eclipse.jface.text.contentassist.IContextInformationValidator
import org.eclipse.jface.text.templates.DocumentTemplateContext
import org.eclipse.jface.text.templates.Template
import org.eclipse.jface.text.templates.TemplateContext
import org.eclipse.jface.text.templates.TemplateContextType
import org.eclipse.jface.text.templates.TemplateProposal
import org.eclipse.swt.graphics.Image
import org.eclipse.ui.PlatformUI

class LanguageCompletionProcessor implements IContentAssistProcessor {
	
	static final String CONTEXT_ID = "language_variables"
	final TemplateContextType fContextType = new TemplateContextType(CONTEXT_ID, "Common Language Variables")
	final TemplateContextType fKeywordContextType = new TemplateContextType("language_keywords", "Language Keywords")
	final TemplateContextType fFunctionContextType = new TemplateContextType("functions", "Language Functions")
	String fileExtension
	
	new(String fileExtension) {
		this.fileExtension=fileExtension
	}
	
	override computeCompletionProposals(ITextViewer viewer, int offset) {
		var document = viewer.document
		var region = new Region(offset, 0)
		var templateContext = new DocumentTemplateContext(fContextType, document, offset, 0)
		var keywordContext = new DocumentTemplateContext(fKeywordContextType, document, offset, 0)
		var functionContext = new DocumentTemplateContext(fFunctionContextType, document, offset, 0)
		var proposals = new ArrayList<ICompletionProposal>()
		
		getVariableTemplateProposals(templateContext, region, proposals)
		getKeywordTemplateProposals(keywordContext, region, proposals)
		getAddTaskTemplateProposals(templateContext, region, proposals)
		getFunctionTemplateProposals(functionContext, region, proposals)
		
		proposals.toArray(#[]) 
	}

	override computeContextInformation(ITextViewer viewer, int offset) { null}

	def generateVariableTemplate(String name, String description) {
		return new Template(name, description, CONTEXT_ID, '''«name» = "${«name.toLowerCase()»}"''', false)
	}

	def getAddTaskTemplateProposals(TemplateContext templateContext, Region region,
		List<ICompletionProposal> p) {
//		var file = new LangFile
//		var model = file.load(ILanguageConstants.MODEL_PATH)
		
		val model = LanguageDefinitionProvider.instance.getDefinitionByExtension(fileExtension);

		for (ContentProposal prop : model.getContentProposals()) {
			ECollections.sort(prop.contents, [ o1, o2| o1.name.compareTo(o2.name)])
			var img = PlatformUI.workbench.sharedImages.getImage(prop.image.getName)
			for (Content content : prop.contents.filter[it instanceof de.dc.editor.lang.model.Template]) {
				var String name = content.getName()
				var String descr = content.getDescription()
				var String pattern = content.getPattern()
				p+=new TemplateProposal(new Template(name, descr, CONTEXT_ID, pattern, false), templateContext,region, img)
			}
		}
	}

	override char[] getCompletionProposalAutoActivationCharacters() { null 	}
	override char[] getContextInformationAutoActivationCharacters() { null 	}
	override IContextInformationValidator getContextInformationValidator() { null 	}
	override String getErrorMessage() { null }

	def getFunctionTemplateProposals(TemplateContext templateContext, Region region,
		List<ICompletionProposal> p) {
//		var file = new LangFile
//		var model = file.load(ILanguageConstants.MODEL_PATH)

		val model = LanguageDefinitionProvider.instance.getDefinitionByExtension(fileExtension);

		for (ContentProposal prop : model.contentProposals) {
			ECollections.sort(prop.contents, [ o1, o2| o1.name.compareTo(o2.name)])
			var img = PlatformUI.workbench.sharedImages.getImage(prop.image.getName)
			for (Content content : prop.contents.filter[it instanceof Function]) {
				var name = content.name
				var descr = content.description
				var pattern = content.pattern
				p+=new TemplateProposal(new Template(name, descr, CONTEXT_ID, pattern, false), templateContext, region, img)
			}
		}
	}

	def getKeywordTemplateProposals(TemplateContext templateContext, Region region,
		List<ICompletionProposal> p){
//		var file = new LangFile
//		var model = file.load(ILanguageConstants.MODEL_PATH)

		val model = LanguageDefinitionProvider.instance.getDefinitionByExtension(fileExtension);

		for (ContentProposal prop : model.contentProposals) {
			ECollections.sort(prop.contents, [ o1, o2| o1.name.compareTo(o2.name)])
			var img = PlatformUI.workbench.sharedImages.getImage(prop.image.getName)
			for (Content content : prop.contents.filter[it instanceof Token]) {
				var name = content.name
				var descr = content.description
				var pattern = content.pattern
				p+=new TemplateProposal(new Template(name, descr, CONTEXT_ID, pattern, false), templateContext, region, img)
			}
		}
	}

	def getVariableTemplateProposals(TemplateContext templateContext, Region region,
		List<ICompletionProposal> p) {
		var Map<String, String> n = new HashMap()
		var Image img = null
		for (var Iterator<String> i = n.keySet().iterator(); i.hasNext();) {
			var String name = (i.next() as String)
			var String description = (n.get(name) as String)
			p.add(new TemplateProposal(generateVariableTemplate(name, description), templateContext, region, img))
		}
	}
}
