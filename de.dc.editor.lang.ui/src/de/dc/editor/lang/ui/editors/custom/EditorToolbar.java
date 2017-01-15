package de.dc.editor.lang.ui.editors.custom;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class EditorToolbar extends Composite {

	public EditorToolbar(Composite parent) {
		super(parent, 0);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		
		TabItem tbtmStart = new TabItem(tabFolder, SWT.NONE);
		tbtmStart.setText("Start");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmStart.setControl(composite);
		composite.setLayout(new GridLayout(1, false));
		
		ListViewer listViewer = new ListViewer(composite, SWT.BORDER | SWT.V_SCROLL);
		List list = listViewer.getList();
		GridData gd_list = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_list.widthHint = 200;
		list.setLayoutData(gd_list);
		listViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
				IEditorInput editorInput = activeEditor.getEditorInput();
//				if (editorInput instanceof FileEditorInput) {
//					FileEditorInput input = (FileEditorInput) editorInput;
//				}
			}
		});
		listViewer.setContentProvider(ArrayContentProvider.getInstance());
		listViewer.setLabelProvider(new LabelProvider(){
			@Override
			public String getText(Object element) {
				return super.getText(element);
			}
		});
		Object input=new String[]{"Eins", "Zwei"};
		listViewer.setInput(input);
		Label lblSnippets = new Label(composite, SWT.CENTER);
		lblSnippets.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblSnippets.setText("Snippets");
		
		TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("Edit");
		
		TabItem tbtmSearch = new TabItem(tabFolder, SWT.NONE);
		tbtmSearch.setText("Search");

	}

	@Override
	protected void checkSubclass() {
	}
}
