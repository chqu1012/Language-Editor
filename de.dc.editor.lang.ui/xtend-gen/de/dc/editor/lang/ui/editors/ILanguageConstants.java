package de.dc.editor.lang.ui.editors;

import org.eclipse.ui.PlatformUI;

@SuppressWarnings("all")
public class ILanguageConstants {
  public final static String LANGUAGE_PATH = "LANGUAGE_MODEL_PATH";
  
  public final static String LANGUAGE_FILES = "LANGUAGE_MODEL_FILES";
  
  public final static String LANGUAGE_FILES_SEPARATOR = "#";
  
  public final static String LANGUAGE_FILES_PATH = PlatformUI.getPreferenceStore().getString(ILanguageConstants.LANGUAGE_FILES);
  
  public final static String MODEL_PATH = PlatformUI.getPreferenceStore().getString(ILanguageConstants.LANGUAGE_PATH);
}
