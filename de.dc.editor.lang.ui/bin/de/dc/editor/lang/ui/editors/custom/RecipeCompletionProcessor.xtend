package de.dc.editor.lang.ui.editors.custom

import de.dc.editor.lang.model.Content
import de.dc.editor.lang.model.ContentProposal
import de.dc.editor.lang.model.Function
import de.dc.editor.lang.model.LanguageDefinition
import de.dc.editor.lang.model.Token
import de.dc.editor.lang.ui.file.LangFile
import java.io.IOException
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.Iterator
import java.util.List
import java.util.Map
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
import org.eclipse.ui.ISharedImages
import org.eclipse.ui.PlatformUI

package class RecipeCompletionProcessor implements IContentAssistProcessor {
	static String CONTEXT_ID = "bitbake_variables"
	TemplateContextType fContextType = new TemplateContextType(CONTEXT_ID, "Common BitBake Variables")
	TemplateContextType fKeywordContextType = new TemplateContextType("bitbake_keywords", "BitBake Keywords")
	TemplateContextType fFunctionContextType = new TemplateContextType("bitbake_functions", "BitBake Functions")

	package new() {
	}

	override ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		var document = viewer.getDocument
		var region = new Region(offset, 0)
		var templateContext = new DocumentTemplateContext(fContextType, document, offset, 0)
		var keywordContext = new DocumentTemplateContext(fKeywordContextType, document, offset, 0)
		var functionContext = new DocumentTemplateContext(fFunctionContextType, document, offset, 0)
		var proposals = new ArrayList<ICompletionProposal>
		getVariableTemplateProposals(templateContext, region, proposals)
		getKeywordTemplateProposals(keywordContext, region, proposals)
		getAddTaskTemplateProposals(templateContext, region, proposals)
		getFunctionTemplateProposals(functionContext, region, proposals)
		proposals.toArray(#[])
	}

	override computeContextInformation(ITextViewer viewer, int offset) {
		 null
	}

	def private Template generateVariableTemplate(String name, String description) {
		 new Template(name, description, CONTEXT_ID, '''«name» = "${«name.toLowerCase()»}"''', false)
	}

	def private void getAddTaskTemplateProposals(TemplateContext templateContext, Region region, List<ICompletionProposal> p) {
		var keywords = new ArrayList<Content>
		Collections.sort(keywords, [Content o1, Content o2| o1.getName().compareTo(o2.getName())])
		var LangFile file = new LangFile()
		var LanguageDefinition model = file.load("C:\\Development\\eclipse-4.7-dsl\\workspace\\de.dc.editor.lang.resource\\java.model")
		for (ContentProposal prop : model.getContentProposals()) {
			for (Content content : prop.getContents.filter[it instanceof de.dc.editor.lang.model.Template]) {
				keywords.add(content)
			}
		}
		keywords.forEach[key|
			val name = key.name
			val descr = key.description
			val pattern = key.pattern
			p+=new TemplateProposal(new Template(name, descr, CONTEXT_ID, pattern, false), templateContext, region, null)
		]
	}

	override char[] getCompletionProposalAutoActivationCharacters() {null}
	override char[] getContextInformationAutoActivationCharacters() {null}
	override IContextInformationValidator getContextInformationValidator() {null}
	override String getErrorMessage() {null}

	def private void getFunctionTemplateProposals(TemplateContext templateContext, Region region, List<ICompletionProposal> p) {
		var Image img = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_INFO_TSK)
		var List<Content> keywords = new ArrayList<Content>
		Collections.sort(keywords, [Content o1, Content o2| o1.getName().compareTo(o2.getName())])
		var LangFile file = new LangFile()
		var LanguageDefinition model = file.load("C:\\Development\\eclipse-4.7-dsl\\workspace\\de.dc.editor.lang.resource\\java.model")
		for (ContentProposal prop : model.getContentProposals()) {
			for (Content content : prop.getContents.filter[it instanceof Function]) {
				keywords.add(content)
			}
		}

		for (var int i = 0; i < keywords.length; {i = i + 1}) {
			val name = keywords.get(i).name
			val description = keywords.get(i).description
			val pattern = keywords.get(i).pattern
			p+=new TemplateProposal(new Template(name, description, CONTEXT_ID, pattern, false), templateContext, region, img)
		}
	}

	def private void getKeywordTemplateProposals(TemplateContext templateContext, Region region,
		List<ICompletionProposal> proposals) {
		var List<Content> keywords = new ArrayList<Content>()
		var Image img = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ELCL_STOP)
		Collections.sort(keywords, [Content o1, Content o2| o1.getName().compareTo(o2.getName())])
		try {
			var LangFile file = new LangFile()
			var LanguageDefinition model = file.load(
				"C:\\Development\\eclipse-4.7-dsl\\workspace\\de.dc.editor.lang.resource\\java.model")
			for (ContentProposal prop : model.getContentProposals()) {
				for (Content content : prop.getContents.filter[it instanceof Token]) {
					keywords.add(content)
				}
			}
		} catch (IOException e) {
			e.printStackTrace()
		}

		for (var int i = 0; i < keywords.size(); {
			i = i + 1
		}) {
			var String name = keywords.get(i).getName()
			var String description = keywords.get(i).getDescription()
			proposals+= new TemplateProposal(new Template(name, description, CONTEXT_ID, '''«name» ''', false), templateContext,
					region, img)
		}
	}

	def private void getVariableTemplateProposals(TemplateContext templateContext, Region region,
		List<ICompletionProposal> p) {
		var Map<String, String> n = new HashMap()
		// BBLanguageHelper.getCommonBitbakeVariables();
		var Image img = null
		// Activator.getDefault().getImageRegistry().get(Activator.IMAGE_VARIABLE);
		for (var Iterator<String> i = n.keySet().iterator(); i.hasNext();) {
			var String name = (i.next() as String)
			var String description = (n.get(name) as String)
			p.add(new TemplateProposal(generateVariableTemplate(name, description), templateContext, region, img))
		}
	}
}
