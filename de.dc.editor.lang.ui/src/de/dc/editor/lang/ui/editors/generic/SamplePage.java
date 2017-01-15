/*
 * Created on Oct 23, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.dc.editor.lang.ui.editors.generic;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * @author swb
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SamplePage extends FormPage {

	/**
	 * @param editor
	 * @param id
	 * @param title
	 */
	public SamplePage(FormEditor editor, String id, String title) {
		super(editor, id, title);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param title
	 */
	public SamplePage(String id, String title) {
		super(id, title);
		// TODO Auto-generated constructor stub
	}
	protected void createFormContent(IManagedForm mForm) {
		ScrolledForm form = mForm.getForm();
		form.setText("Sample Page");
		Font font = new Font(form.getBody().getDisplay(),
				new FontData("Arial",12,SWT.NORMAL));
		
		//GridLayout layout = new GridLayout();
		TableWrapLayout layout = new TableWrapLayout();				
		form.getBody().setLayout(layout);
		layout.numColumns=2;

		StringBuffer buf = new StringBuffer();
		buf.append(
				"<form>"+
				"<p>"+
				" Sample Page text</p>"+				
				"</form> "+
				" "+
				" "+
				" ");
		FormToolkit toolkit=mForm.getToolkit();
		FormText rtext = toolkit.createFormText(form.getBody(),true);
		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.colspan=2;
		rtext.setLayoutData(td);
		rtext.setColor("header", toolkit.getColors().getColor(FormColors.TITLE));
		//rtext.setImage("image",?);
		rtext.setFont("header", JFaceResources.getHeaderFont());
		rtext.setFont(font);
		rtext.setText(buf.toString(),true, false);
		
		rtext.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				System.out.println("Link Activated: "+e.getLabel()+ " "+e.getHref());
			}});
		
		rtext.addControlListener(new ControlListener() {
			public void controlMoved(ControlEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("moved");
			}

			public void controlResized(ControlEvent e) {
				// TODO Auto-generated method stub
				// System.out.println("resized");
			}});
	
	}

}
