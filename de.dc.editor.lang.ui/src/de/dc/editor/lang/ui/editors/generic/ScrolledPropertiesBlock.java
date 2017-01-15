/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package de.dc.editor.lang.ui.editors.generic;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.DelegatingWrapperItemProvider;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTableTreeEditor;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.forms.*;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
//import org.eclipse.ui.forms.examples.internal.ExamplesPlugin;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;



/**
 * @author dejan
 */
/* 
 * @modified swb
 */

/**
 *
 */
public class ScrolledPropertiesBlock 
		extends MasterDetailsBlock 
			implements ISelectionChangedListener {
	
	private FormPage page;
	protected gfEditor ourEditor;
	private SelectionChangedEvent selectionEvent;
	protected  TreeViewer treeViewer;  
	private IDetailsPageProvider myDetailsPageProvider;
	boolean debug = false; // true for console debug output
	
	public AdapterFactoryTreeEditor ADFTE;

	public ScrolledPropertiesBlock(FormPage page, gfEditor theEditor, TreeViewer ourViewer) {
		this.page = page;
		this.ourEditor=theEditor;
		this.myDetailsPageProvider=new DetailsPageProvider(ourEditor);
		this.treeViewer=ourViewer;
	}
		
	protected void 
	createMasterPart(
			final IManagedForm managedForm,
			Composite parent) {
		final ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		//  
		Section section = 
			toolkit.createSection(parent, Section.DESCRIPTION);
		section.setText("Model");
		section.setDescription(
				"The list contains objects from the model whose "+
				"details are editable on the right"
		);
		section.marginWidth = 10;
		section.marginHeight = 5;
		toolkit.createCompositeSeparator(section);
		
		// 
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		
		GridLayout layout = new GridLayout();
		
		layout.numColumns = 2;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		
			Tree tr = null;
			tr = toolkit.createTree(client, SWT.NULL);	
			TreeViewer viewer = new TreeViewer(tr);
			this.treeViewer=viewer;
			
		
			GridData gd = new GridData(GridData.FILL_BOTH);
			gd.heightHint = 20;
			gd.widthHint = 100;
			tr.setLayoutData(gd);	
			
			
		toolkit.paintBordersFor(client);
		
		Button b = toolkit.createButton(client, "Menu...", SWT.PUSH);
		b.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ISelection ts=treeViewer.getSelection();
				if (debug) {
				ParsedSelection p=new ParsedSelection(treeViewer.getSelection(),ourEditor);
				MessageDialog.openInformation(null,"SWT",
						"Button Selected"+
						" what is the current selection?"+
						treeViewer.getSelection()+" \n"+
						p.textAnswer);
				}
				treeViewer.getControl().getMenu().setVisible(true);
				treeViewer.setSelection(ts);
				
			}
		});
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		b.setLayoutData(gd);
		section.setClient(client);		
				
		final 
		SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);

	
		
		ResourceSet resourceSet = ourEditor.getEditingDomain().getResourceSet();
		Resource resource = (Resource) resourceSet.getResources().get(0);
									
			viewer.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					
					managedForm.fireSelectionChanged(spart, event.getSelection());	
					
					//System.out.println("TreeViewer.selectionChanged: "+event.getSelection());
					ISelection selection =event.getSelection();
					/*
					if (selection instanceof IStructuredSelection) {						
						ParsedSelection p =new ParsedSelection(selection,ourEditor);
						// System.out.println("p.textAnswer="+p.textAnswer);
					}
					*/			
				}
			});
			
			page.getSite().setSelectionProvider(viewer);

			/* again - we need to get to the EMF constructs... 
			 * 
			 */
			
			
			viewer.setContentProvider(new AdapterFactoryContentProvider(
					ourEditor.getAdapterFactory()));

			viewer.setLabelProvider(new AdapterFactoryLabelProvider(
					ourEditor.getAdapterFactory()));
			
			//??
			ADFTE = new AdapterFactoryTreeEditor(viewer.getTree(), ourEditor.getAdapterFactory());
			
			//this is its own problem
			ourEditor.createContextMenuFor(viewer);
	
			//viewer.setInput(resource.getContents().get(0));
			viewer.setInput(resource);
		
			viewer.addSelectionChangedListener(this);
			
				
		if (debug) {
					TreeIterator i=resource.getAllContents();
					System.out.println("resource.getAllContents()");
					while (i.hasNext()) {
						System.out.println(i.next());
					}
		}
			
	

	}
	
	protected void createToolBarActions(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		Action haction = new Action("horizontal", Action.AS_RADIO_BUTTON) {
			public void run() {
				sashForm.setOrientation(SWT.HORIZONTAL);
				form.reflow(true);
			}
		};
		haction.setChecked(true);
		haction.setToolTipText("Horizontal orientation");
		
		/*
		haction.setImageDescriptor(Bd7Plugin.getDefault()
				.getImageRegistry()
				.getDescriptor(Bd7Plugin.IMG_HORIZONTAL));
		*/
		Action vaction = new Action("vertical", Action.AS_RADIO_BUTTON) {
			public void run() {
				sashForm.setOrientation(SWT.VERTICAL);
				form.reflow(true);
			}
		};
		vaction.setChecked(false);
		vaction.setToolTipText("Vertical orientation");
		/*
		vaction.setImageDescriptor(Bd7Plugin.getDefault()
				.getImageRegistry().getDescriptor(Bd7Plugin.IMG_VERTICAL));
		*/
		
		form.getToolBarManager().add(haction);
		form.getToolBarManager().add(vaction);
	}
	
	protected
	void registerPages(DetailsPart detailsPart) {
		detailsPart.setPageProvider(myDetailsPageProvider);
		//detailsPart.registerPage(Object.class, new ObjectDetailsPage());
		//detailsPart.registerPage(TypeTwo.class, new TypeTwoDetailsPage());
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	
	public void selectionChanged(SelectionChangedEvent event) {
		// TODO Auto-generated method stub
		this.selectionEvent=event;
		//ourEditor.setSelection(selectionEvent.getSelection());	
	
		
	}
}