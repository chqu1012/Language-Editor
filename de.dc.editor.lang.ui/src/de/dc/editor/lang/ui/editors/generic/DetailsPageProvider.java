package de.dc.editor.lang.ui.editors.generic;

import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IDetailsPageProvider;
 
public class DetailsPageProvider implements IDetailsPageProvider {
	boolean debug=false;
	protected gfEditor ourEditor;

	public DetailsPageProvider(gfEditor ourEditor) {
		this.ourEditor=ourEditor;
		
	}

	public Object getPageKey(Object object) {
		return object;
	}

	public IDetailsPage getPage(Object key) {
		return new ObjectDetailsPage(key,ourEditor);
	}
}
