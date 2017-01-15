package de.dc.editor.lang.ui.editors;

import org.eclipse.ui.PlatformUI;

public class ILanguageConstants {

	public static final String LANGUAGE_PATH = "LANGUAGE_MODEL_PATH";
	public static final String MODEL_PATH = PlatformUI.getPreferenceStore().getString(LANGUAGE_PATH);
}
