package de.dc.editor.lang.ui.editors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.PlatformUI;

import de.dc.editor.lang.model.LanguageDefinition;
import de.dc.editor.lang.ui.file.LangFile;

public enum LanguageDefinitionProvider {
	instance;
	
	private Map<String, LanguageDefinition> definitions;
	private LangFile file = new LangFile();
	
	public void storeDefinition(LanguageDefinition definition){
		file.write(definition, "model");
	}
	
	public String getDefinitionPathByExtension(String fileExtension){
		String pathsString = PlatformUI.getPreferenceStore().getString(ILanguageConstants.LANGUAGE_FILES_PATH);
		String[] paths = pathsString.split(ILanguageConstants.LANGUAGE_FILES_SEPARATOR);
		LangFile file = new LangFile();
		for (String path : paths) {
			try {
				LanguageDefinition model = file.load(path);
				if(model.getFileExtension().equals(fileExtension)){
					return path;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public LanguageDefinition getDefinitionByExtension(String fileExtension){
		definitions = new HashMap<>();
		String pathsString = PlatformUI.getPreferenceStore().getString(ILanguageConstants.LANGUAGE_FILES_PATH);
		String[] paths = pathsString.split(ILanguageConstants.LANGUAGE_FILES_SEPARATOR);
		for (String path : paths) {
			LanguageDefinition model;
			try {
				model = file.load(path);
				definitions.put(model.getFileExtension(), model);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (definitions.get(fileExtension)==null) {
//			MessageDialog.openError(new Shell(), "File Extension Error", "File extension not supported in Language Editor, please enhanced the file association in preferences.");
			System.err.println("File extension not supported in Language Editor, please enhanced the file association in preferences.");
			
		}
		return definitions.get(fileExtension);
	}
}
