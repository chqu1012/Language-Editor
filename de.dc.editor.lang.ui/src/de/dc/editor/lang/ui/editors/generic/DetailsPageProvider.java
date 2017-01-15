/*
 * Created on Aug 30, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.dc.editor.lang.ui.editors.generic;



import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IDetailsPageProvider;
import org.eclipse.ui.forms.editor.FormEditor;


 
/**
 * @author swb
 *
 */
public class DetailsPageProvider implements IDetailsPageProvider {
	boolean debug=false;
	protected gfEditor ourEditor;
	/**
	 * @param ourEditor
	 */
	public DetailsPageProvider(gfEditor ourEditor) {
		
		// TODO Auto-generated constructor stub
		this.ourEditor=ourEditor;
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPageProvider#getPageKey(java.lang.Object)
	 */
	public Object getPageKey(Object object) {
		Object key=null;
		
		return object;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPageProvider#getPage(java.lang.Object)
	 */
	public IDetailsPage getPage(Object key) {
		if (debug) System.out.println("getPage "+key);
		//return null;
		return new ObjectDetailsPage(key,ourEditor);
	}

}
