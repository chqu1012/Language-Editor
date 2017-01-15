/*
 * Created on Aug 31, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.dc.editor.lang.ui.editors.generic;

import java.util.Collection;

import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.forms.editor.FormEditor;



/**
 * @author swb
 *
 */
public class ParsedSelection {

	String textAnswer;
	
	/**
	 * 
	 */
	public ParsedSelection() {
		super();
	}

	public ParsedSelection(ISelection selection, gfEditor ourEditor) {
		super();
		Collection collection = ((IStructuredSelection)selection).toList();
		switch (collection.size()) {
			case 0: {
				//System.out.println(RceEditorPlugin.INSTANCE.getString("_UI_NoObjectSelected"));
				this.textAnswer= ("_UI_NoObjectSelected");
				break;
			}
			case 1: {
				
				String text = "what do we do to get the adaptor factory from here?"; 
				//
				text= new AdapterFactoryItemDelegator(ourEditor.getAdapterFactory()).getText(collection.iterator().next());
				//System.out.println(RceEditorPlugin.INSTANCE.getString("_UI_SingleObjectSelected") + text +"\n");
				this.textAnswer= ("_UI_SingleObjectSelected: ") + text +"\n";
				break;
			}
			default: {
				//System.out.println(RceEditorPlugin.INSTANCE.getString("_UI_MultiObjectSelected")+ Integer.toString(collection.size()));
				this.textAnswer= ("_UI_MultiObjectSelected: ")+ Integer.toString(collection.size());
				break;
			}
		}
		
	}

}
