package de.dc.editor.lang.ui.editors.custom

import java.util.ArrayList
import java.util.List
import org.eclipse.jface.text.IDocument
import org.eclipse.jface.text.ITextViewer
import org.eclipse.jface.text.contentassist.CompletionProposal
import org.eclipse.jface.text.contentassist.ContextInformation
import org.eclipse.jface.text.contentassist.ContextInformationValidator
import org.eclipse.jface.text.contentassist.ICompletionProposal
import org.eclipse.jface.text.contentassist.IContentAssistProcessor
import org.eclipse.swt.graphics.Point
import org.eclipse.xtend.lib.annotations.Accessors

class LangCompletionProcessor implements IContentAssistProcessor {

	override ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int documentOffset) {
		var doc = viewer.document
		var selectedRange = viewer.selectedRange
		var propList = new ArrayList
		if (selectedRange.y > 0) {
			var text = doc.get(selectedRange.x, selectedRange.y)
			computeStyleProposals(text, selectedRange, propList)
		} else {
			var qualifier = getQualifier(doc, documentOffset)
			computeStructureProposals(qualifier, documentOffset, propList)
		}
		var proposals = newArrayOfSize(propList.size)
		propList.toArray(proposals)
		proposals
	}

	def getQualifier(IDocument doc, int documentOffset_finalParam_) {
		var documentOffset = documentOffset_finalParam_
		var buf = new StringBuffer
		while (true) {
			var char c = (doc.getChar({documentOffset = documentOffset - 1})) as char
			if (c === Character.valueOf('>').charValue || Character.isWhitespace(c)){return ""}
			if(c === Character.valueOf('}').charValue){return ""}
			if(c === Character.valueOf(')').charValue){return ""}
			buf.append(c)
			if(c === Character.valueOf('<').charValue) return buf.reverse.toString
			if(c === Character.valueOf('{').charValue) return buf.reverse.toString
			if(c === Character.valueOf('(').charValue) return buf.reverse.toString
		}
	}

	@Accessors List<String> STRUCTTAGS1 = new ArrayList
	@Accessors List<String> STRUCTTAGS2 = new ArrayList

	def computeStructureProposals(String qualifier, int documentOffset, List<CompletionProposal> propList) {
		println(qualifier)
		var int qlen = qualifier.length
		for (var i = 0; i < STRUCTTAGS1.length; i++) {
			var startTag = STRUCTTAGS1.get(i)
			if (startTag.startsWith(qualifier)) {
				var text = startTag
				var int cursor = startTag.length
				var proposal = new CompletionProposal(text, documentOffset - qlen, qlen, cursor)
				propList.add(proposal)
			}
		}
	}

	final static String[] STYLETAGS = #["b", "i", "code", "strong"]
	final static String[] STYLELABELS = #["bold", "italic", "code", "strong"]

	def private void computeStyleProposals(String selectedText, Point selectedRange, List propList) {
		for (var i = 0; i < STYLETAGS.length; i++) {
			var tag = {
				val _rdIndx_STYLETAGS = i
				STYLETAGS.get(_rdIndx_STYLETAGS)
			}
			var replacement = '''<«tag»>«selectedText»</«tag»>'''
			var cursor = tag.length + 2
			var contextInfo = new ContextInformation(
				null, '''«{val _rdIndx_STYLELABELS=i STYLELABELS.get(_rdIndx_STYLELABELS)}» Style''')
			var proposal = new CompletionProposal(replacement, selectedRange.x, selectedRange.y,
				cursor, null, {
					val _rdIndx_STYLELABELS = i
					STYLELABELS.get(_rdIndx_STYLELABELS)
				}, contextInfo, replacement)
			propList.add(proposal)
		}
	}

	override computeContextInformation(ITextViewer viewer, int documentOffset) {
		var selectedRange = viewer.getSelectedRange
		if (selectedRange.y > 0) {
			var contextInfos = newArrayOfSize(STYLELABELS.length)
			for (var int i = 0; i < STYLELABELS.length; i++) {
				val _wrVal_contextInfos = contextInfos
				val _wrIndx_contextInfos = i
				_wrVal_contextInfos.set(_wrIndx_contextInfos,
					new ContextInformation('''<«{val _rdIndx_STYLETAGS=i STYLETAGS.get(_rdIndx_STYLETAGS)}»>''', '''«{val _rdIndx_STYLELABELS=i STYLELABELS.get(_rdIndx_STYLELABELS)}» Style'''))
			}
			return contextInfos
		}
		return newArrayOfSize(0)
	}

	override getCompletionProposalAutoActivationCharacters() {
//	 #[Character.valueOf('<').charValue]
	}

	override getContextInformationAutoActivationCharacters() {
		 null
	}

	override getErrorMessage() {
		 null
	}

	override  getContextInformationValidator() {
		 new ContextInformationValidator(this)
	}
}