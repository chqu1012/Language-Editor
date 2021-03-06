package de.dc.editor.lang.ui.editors

import org.eclipse.ui.PlatformUI

class ILanguageConstants {
	public static final String LANGUAGE_PATH = "LANGUAGE_MODEL_PATH"
	public static final String LANGUAGE_FILES = "LANGUAGE_MODEL_FILES"
	public static final String LANGUAGE_FILES_SEPARATOR = "#"
	public static final String LANGUAGE_FILES_PATH = PlatformUI.preferenceStore.getString(LANGUAGE_FILES)
	public static final String MODEL_PATH = PlatformUI.preferenceStore.getString(LANGUAGE_PATH)
}
