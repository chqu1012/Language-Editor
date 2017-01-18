package de.dc.editor.lang.ui.editors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import de.dc.editor.lang.model.LanguageDefinition;
import de.dc.editor.lang.ui.file.LangFile;

public enum LanguageDefinitionProvider {
	instance;
	
	Map<String, LanguageDefinition> definitions;
	
	public LanguageDefinition getDefinitionByExtension(String fileExtension){
		definitions = new HashMap<>();
		String pathsString = PlatformUI.getPreferenceStore().getString(ILanguageConstants.LANGUAGE_FILES_PATH);
		String[] paths = pathsString.split(ILanguageConstants.LANGUAGE_FILES_SEPARATOR);
		for (String path : paths) {
			LangFile file = new LangFile();
			LanguageDefinition model;
			try {
				model = file.load(path);
				definitions.put(model.getFileExtension(), model);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		LanguageDefinition definition = definitions.get(fileExtension);
		if (definition == null) {
			MessageDialog.openError(new Shell(), "Language Definition Error", "No "+fileExtension+" file extension registered in the file association");
		}
		return definition;
	}
}
