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

class LanguageScanner extends RuleBasedScanner {
	
	String fileExtension
	
	new(String fileExtension) {
		this.fileExtension=fileExtension
		init
	}

	def private void init() throws FileNotFoundException, IOException {
		val model = LanguageDefinitionProvider.instance.getDefinitionByExtension(fileExtension);
		if(model!=null){
			var List<IRule> rules = new ArrayList<IRule>()
			for (KeywordGroup group : model.getKeywordGroups()) {
				var de.dc.editor.lang.model.Color color = group.getColor()
				var WordRule rule = new WordRule(new IWordDetector() {
					override boolean isWordStart(char c) {
						return Character.isJavaIdentifierStart(c)
					}
	
					override boolean isWordPart(char c) {
						return Character.isJavaIdentifierStart(c)
					}
				})
				for (Key key : group.getKeyList()) {
					rule.addWord(key.getValue(),
						new Token(
							new TextAttribute(new Color(Display.getCurrent(), color.getR(), color.getG(), color.getB()),
								null, SWT.BOLD)))
					rules.add(rule)
				}
			}
			setRules(rules.toArray(newArrayOfSize(0)))
		}
	}
}
