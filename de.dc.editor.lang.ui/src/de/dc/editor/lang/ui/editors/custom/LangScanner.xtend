package de.dc.editor.lang.ui.editors.custom

import de.dc.editor.lang.ui.file.LangFile
import java.util.ArrayList
import org.eclipse.jface.text.TextAttribute
import org.eclipse.jface.text.rules.IRule
import org.eclipse.jface.text.rules.IWordDetector
import org.eclipse.jface.text.rules.RuleBasedScanner
import org.eclipse.jface.text.rules.Token
import org.eclipse.jface.text.rules.WordRule
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.graphics.Color

class LangScanner extends RuleBasedScanner {
	
	new() {
		val file = new LangFile
		val model = file.load('C:\\Development\\eclipse-4.7-dsl\\workspace\\de.dc.editor.lang.resource\\java.model')
		val rules = new ArrayList<IRule>
		model.keywordGroups.forEach[ group |
			val color = group.color
			val rule = new WordRule(new IWordDetector(){
					override isWordPart(char c) {Character.isJavaIdentifierStart(c)}
					override isWordStart(char c) {Character.isJavaIdentifierPart(c)}
				})
			group.keyList.forEach[ key |
				rule.addWord(key.value, new Token(new TextAttribute(new Color(Display.current, color.r, color.g, color.b), null, SWT.BOLD)))
			]
			rules += rule
		]
		setRules(rules)
	}
	
}