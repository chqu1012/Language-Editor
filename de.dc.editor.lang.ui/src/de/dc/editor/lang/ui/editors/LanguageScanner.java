package de.dc.editor.lang.ui.editors;

import static de.dc.editor.lang.ui.editors.ILanguageConstants.MODEL_PATH;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import de.dc.editor.lang.model.Key;
import de.dc.editor.lang.model.KeywordGroup;
import de.dc.editor.lang.model.LanguageDefinition;
import de.dc.editor.lang.model.ModelPackage;
import de.dc.editor.lang.ui.file.LangFile;

public class LanguageScanner extends RuleBasedScanner{

	public LanguageScanner() {
		try {
			if(MODEL_PATH==null || MODEL_PATH.equals("")){
				FileDialog fd = new FileDialog(new Shell(), SWT.OPEN);
				String open = fd.open();
				if(open!=null){
					PlatformUI.getPreferenceStore().setValue(ILanguageConstants.LANGUAGE_PATH, open);
					init();
				}else{
					MessageDialog.openError(new Shell(), "Language Definition Error", "File cannot be found, please set up the language definition in the preferences.");
				}
			}else{
				init();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() throws FileNotFoundException, IOException {
		LangFile file = new LangFile();
		LanguageDefinition model = file.load(ILanguageConstants.MODEL_PATH);
		List<IRule> rules = new ArrayList<IRule>();
		for (KeywordGroup group : model.getKeywordGroups()) {
			de.dc.editor.lang.model.Color color = group.getColor();
			WordRule rule = new WordRule(new IWordDetector() {
				@Override
				public boolean isWordStart(char c) {
					return Character.isJavaIdentifierStart(c);
				}
				@Override
				public boolean isWordPart(char c) {
					return Character.isJavaIdentifierStart(c);
				}
			});
			for (Key key : group.getKeyList()) {
				rule.addWord(key.getValue(), new Token(new TextAttribute(new Color(Display.getCurrent(), color.getR(), color.getG(), color.getB()), null, SWT.BOLD)));
				rules.add(rule);
			}
		}
		setRules(rules.toArray(new IRule[0]));
	}
}
