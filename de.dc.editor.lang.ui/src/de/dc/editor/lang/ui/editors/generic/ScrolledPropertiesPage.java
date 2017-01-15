package de.dc.editor.lang.ui.editors.generic;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public class ScrolledPropertiesPage  extends FormPage {
	private gfEditor ourEditor;
	public 	ScrolledPropertiesBlock block;
	
	public ScrolledPropertiesPage( gfEditor editor) {
		super(editor, "MD", "Master Details");
		ourEditor=  editor;
	}
	protected void createFormContent(final IManagedForm managedForm) {
		ScrolledForm form = managedForm.getForm();
		form.setText("Model Definition");
		block = new ScrolledPropertiesBlock(this,ourEditor);
		block.createContent(managedForm);
	}
}