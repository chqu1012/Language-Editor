package de.dc.editor.lang.ui.preference;

import java.io.IOException;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import de.dc.editor.lang.model.LanguageDefinition;
import de.dc.editor.lang.model.provider.ModelItemProviderAdapterFactory;
import de.dc.editor.lang.ui.file.LangFile;
import org.eclipse.swt.widgets.Link;

public class LanguageDefinitionPage extends PreferencePage implements IWorkbenchPreferencePage {
	private Text text;

	ComposedAdapterFactory factory;

	private TreeViewer keyViewer;
	
	/**
	 * Create the preference page.
	 */
	public LanguageDefinitionPage() {
		setTitle("Language Definition");
	}

	/**
	 * Create contents of the preference page.
	 * @param parent
	 */
	@Override
	public Control createContents(Composite parent) {
		factory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		factory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		factory.addAdapterFactory(new ModelItemProviderAdapterFactory());
		factory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(3, false));
		
		Link openLink = new Link(container, SWT.NONE);
		openLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getShell().dispose();
			}
		});
		openLink.setText("<a>Language File:</a>");
		
		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		String path = PlatformUI.getPreferenceStore().getString("language.definition.path");
		System.out.println(path);
		if(path!=null){
			text.setText(path);
		}
		Button openButton = new Button(container, SWT.NONE);
		openButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(new Shell(), SWT.OPEN);
				String path = fd.open();
				if (path!=null) {
					text.setText(path);
					PlatformUI.getPreferenceStore().setValue("language.definition.path", path);
					loadModel(path, keyViewer);
				}
			}
		});
		openButton.setText("...");
		
		Label lblKeywords = new Label(container, SWT.NONE);
		lblKeywords.setText("Keywords and Proposals");
		
		keyViewer = new TreeViewer(container, SWT.BORDER | SWT.V_SCROLL);
		Tree keyList = keyViewer.getTree();
		keyList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		keyViewer.setLabelProvider(new AdapterFactoryLabelProvider(factory));
		keyViewer.setContentProvider(new AdapterFactoryContentProvider(factory));
		new AdapterFactoryTreeEditor(keyViewer.getTree(), factory);
		
		loadModel(path, keyViewer);
		
		return container;
	}

	private void loadModel(String path, TreeViewer keyViewer) {
		if(path!=null){
			LangFile file = new LangFile();
			LanguageDefinition def;
			try {
				def = file.load(path);
				keyViewer.setInput(def);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Initialize the preference page.
	 */
	public void init(IWorkbench workbench) {
		// Initialize the preference page
	}
}
