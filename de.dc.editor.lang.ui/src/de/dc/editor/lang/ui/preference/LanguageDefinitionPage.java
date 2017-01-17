package de.dc.editor.lang.ui.preference;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

import de.dc.editor.lang.ui.editors.ILanguageConstants;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class LanguageDefinitionPage extends PreferencePage implements IWorkbenchPreferencePage {
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	
	java.util.List<String> input = new ArrayList<>();

	private ListViewer listViewer;
	
	public LanguageDefinitionPage() {
		setTitle("Language Definition");
	}

	@Override
	public Control createContents(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(1, false));
		
		Button btnAddLanguageDefinition = formToolkit.createButton(container, "Add Language Definition", SWT.NONE);
		btnAddLanguageDefinition.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(new Shell(), SWT.OPEN);
				fd.setFilterExtensions(new String[]{"*.model", "*.*"});
				String path = fd.open();
				if(path!=null){
					input.add(path);
					listViewer.refresh();
					String content = PlatformUI.getPreferenceStore().getString(ILanguageConstants.LANGUAGE_FILES_PATH)+ILanguageConstants.LANGUAGE_FILES_SEPARATOR+path;
					PlatformUI.getPreferenceStore().setValue(ILanguageConstants.LANGUAGE_FILES_PATH, content);
				}
			}
		});
		btnAddLanguageDefinition.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		listViewer = new ListViewer(container, SWT.BORDER | SWT.V_SCROLL);
		List list = listViewer.getList();
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		listViewer.setContentProvider(ArrayContentProvider.getInstance());
		listViewer.setLabelProvider(new LabelProvider(){
			@Override
			public String getText(Object element) {
				return super.getText(element);
			}
		});
		

		String languageFilePaths = PlatformUI.getPreferenceStore().getString(ILanguageConstants.LANGUAGE_FILES_PATH);
		if(languageFilePaths==""){
			PlatformUI.getPreferenceStore().setValue(ILanguageConstants.LANGUAGE_FILES_PATH, "");
		}else{
			String[] fields = languageFilePaths.split(ILanguageConstants.LANGUAGE_FILES_SEPARATOR);
			input.addAll(Arrays.asList(fields));
		}
		listViewer.setInput(input);
		
		return container;
	}

	public void init(IWorkbench workbench) {
		// Initialize the preference page
	}
}
