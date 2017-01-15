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

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.*;
import org.eclipse.ui.forms.widgets.*;

import de.dc.editor.lang.model.Color;



/**
 * @author dejan
 */
/* 
 * @modified swb
 */

public class ScrolledPropertiesPage  extends FormPage {
	private gfEditor ourEditor;
	public 	ScrolledPropertiesBlock block;
	private FormToolkit toolkit;	
	public 	TreeViewer 	ourViewer = null;
	
	public ScrolledPropertiesPage( gfEditor editor) {
		super(editor, "MD", "Master Details");
		ourEditor=  editor;
	}
	protected void createFormContent(final IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		this.toolkit = managedForm.getToolkit();
		form.setText("Model Definition");
		ourViewer = (TreeViewer) createViewer(form);
		ourViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = ourViewer.getSelection();	
				if (selection instanceof IStructuredSelection) {
					IStructuredSelection ss = (IStructuredSelection) selection;
					if (ss.getFirstElement() instanceof Color) {
						Color color = (Color) ss.getFirstElement();
						Shell shell = new Shell();
						ColorDialog dlg = new ColorDialog(shell);
				        dlg.setText("Choose a Color");
				        RGB rgb = dlg.open();
				        if (rgb != null) {
				        	color.setR(rgb.red);
				        	color.setG(rgb.green);
				        	color.setB(rgb.blue);
				        }
					}							
				}
			}
		});
		block = new ScrolledPropertiesBlock(this,ourEditor,ourViewer);
		block.createContent(managedForm);
	}

	public Viewer createViewer(Composite parent) {
		PatternFilter filter = new PatternFilter();
		FilteredTree tree = new FilteredTree(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL, filter, true);

		return tree.getViewer();
	}
	

}