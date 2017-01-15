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

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.*;
import org.eclipse.ui.forms.widgets.*;



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
		block = new ScrolledPropertiesBlock(this,ourEditor,ourViewer);
		block.createContent(managedForm);
	}

	public Viewer createViewer(Composite parent) {
		return(ourViewer); 
	}
	

}