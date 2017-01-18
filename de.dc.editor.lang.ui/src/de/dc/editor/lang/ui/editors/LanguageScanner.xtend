package de.dc.editor.lang.ui.editors

import de.dc.editor.lang.model.Key
import de.dc.editor.lang.model.KeywordGroup
import java.io.FileNotFoundException
import java.io.IOException
import java.util.ArrayList
import java.util.List
import org.eclipse.jface.text.TextAttribute
import org.eclipse.jface.text.rules.IRule
import org.eclipse.jface.text.rules.IWordDetector
import org.eclipse.jface.text.rules.RuleBasedScanner
import org.eclipse.jface.text.rules.Token
import org.eclipse.jface.text.rules.WordRule
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.widgets.Display
import org.eclipse.jface.text.rules.SingleLineRule
import org.eclipse.jface.text.rules.MultiLineRule
import org.eclipse.jface.text.rules.EndOfLineRule

class LanguageScanner extends RuleBasedScanner {
	
	String fileExtension
	
	new(String fileExtension) {
		this.fileExtension=fileExtension
		init
	}

	def private void init() throws FileNotFoundException, IOException {
		val model = LanguageDefinitionProvider.instance.getDefinitionByExtension(fileExtension)
		if(model!=null){
			var rules = new ArrayList<IRule>
			for (KeywordGroup group : model.keywordGroups) {
				var color = group.color
				var rule = new WordRule(new IWordDetector() {
					override boolean isWordStart(char c) {
						return Character.isJavaIdentifierStart(c)
					}
	
					override boolean isWordPart(char c) {
						return Character.isJavaIdentifierStart(c)
					}
				})
				for (Key key : group.keyList) {
					rule.addWord(key.value,
						new Token(new TextAttribute(new Color(Display.current, color.r, color.g, color.b),
								null, SWT.BOLD)))
					rules+=rule
				}
			}
			if(model.useMultiQuotesHighlighting){
				rules+=new SingleLineRule("\"", "\"", new Token(new TextAttribute(Display.current.getSystemColor(SWT.COLOR_GREEN))), '\\')
			}
		    if(model.useSingleQuotesHighlighting){
		    	rules+=new SingleLineRule("\'", "\'", new Token(new TextAttribute(Display.current.getSystemColor(SWT.COLOR_GREEN))), '\\')
	    	}
	    	if(model.useMultiLineCommentHighlighting){
			    rules+=new MultiLineRule("/*", "*/", new Token(new TextAttribute(Display.current.getSystemColor(SWT.COLOR_BLUE))))
	    	}
	    	if (model.useSingleLineCommentHighlighting) {
			    rules+=new EndOfLineRule("//", new Token(new TextAttribute(Display.current.getSystemColor(SWT.COLOR_BLUE))))
	    	}
			setRules(rules.toArray(newArrayOfSize(0)))
		}
	}
}
