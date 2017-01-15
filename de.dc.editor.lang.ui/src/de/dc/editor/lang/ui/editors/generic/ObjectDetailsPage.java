package de.dc.editor.lang.ui.editors.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EAttributeImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EReferenceImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class 
ObjectDetailsPage 
	implements 
		IDetailsPage, 
		IPartListener, 
		ISelectionChangedListener {
	private boolean debug = true;	// true for console debug output
	private IManagedForm mform;		
	private FormToolkit toolkit;
	private Object input;
	private AdapterFactory AF;
	private AdapterFactoryItemDelegator Afid;
	private AdapterFactoryContentProvider Afcp;
	private AdapterFactoryLabelProvider Aflp;
	private Composite detailsclient;	
	private Section detailsection; 
	private gfEditor ourEditor;

	/**
	 * @param key
	 * @param ourEditor
	 */
	public ObjectDetailsPage(Object key, gfEditor ourEditor) {			
			this.input=key;
			this.ourEditor= ourEditor;
			this.AF= ourEditor.getAdapterFactory();
			this.Afid= new AdapterFactoryItemDelegator(AF);
			this.Afcp= new AdapterFactoryContentProvider(AF);
			this.Aflp= new AdapterFactoryLabelProvider(AF);
			ourEditor.getSite().getPage().addPartListener(this);
	}

	public void createContents(Composite parent) {	
		// layout
		TableWrapLayout layout = new TableWrapLayout();
		layout.topMargin = 5;
		layout.leftMargin = 5;
		layout.rightMargin = 2;
		layout.bottomMargin = 2;
		layout.numColumns=2;
		parent.setLayout(layout);		
		toolkit = mform.getToolkit();
		// create details section
		detailsection = toolkit.createSection(parent, Section.DESCRIPTION);
		detailsection.marginWidth = 10;		
		detailsection.setText(Afid.getText(input)); 		
		detailsection
		.setDescription("Set the properties of the selected object.");			
		TableWrapData td = 
			new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		td.colspan=2;
		td.grabHorizontal = true;
		detailsection.setLayoutData(td);
		toolkit.createCompositeSeparator(detailsection);		
		// create details client Composite
		detailsclient = toolkit.createComposite(detailsection);		
		GridLayout glayout = new GridLayout();
		glayout.marginWidth = glayout.marginHeight = 0;
		glayout.numColumns = 2;
		detailsclient.setLayout(glayout);			
		// client content separator
		toolkit.createCompositeSeparator(detailsection);
		// create client details page content for input
		detailsPageSwitch(input);	
		// add the client to the section
		detailsection.setClient(detailsclient);
		// add the section to the part
		final SectionPart spart = new SectionPart(detailsection);
		// add the part to the form
		mform.addPart(spart);				
	}	
	
	
	/*
	 * page switch 				
	 */
	 SampleSection SampleSectionHolder;	
	  private void detailsPageSwitch(Object input) {
	
			// switch on input for specific details 
	  	
	  		// sample
				SampleSectionHolder=new SampleSection();
				SampleSectionHolder.createSection(input);
	  }
	  
	  public void update() {
	  	if (debug) System.out.println("ObjectDetailsPage.update()");
		detailsection.setText(Afid.getText(input));
	  	SampleSectionHolder.updateSection(input);
	  }
	  

	  /*
	   // Sample Section Class
	    * 
	    * @author swb
	    *
	    */
	  private class SampleSection {
		
		private ArrayList detailPropertyDescriptors=null;
		private HashMap widgetlist=null;	
		
	  	SampleSection() {
	  		// toolkit = mform.getToolkit();
	  		;//
	  	}	  	
		private void labelMaker (String s) {
			Label l = toolkit.createLabel(detailsclient,s,SWT.NONE);
			GridData gd=new GridData();
			//gd.widthHint=75;
			l.setLayoutData(gd);
		}
		
		private void attributefieldUpdater ( Object dtype, final IItemPropertyDescriptor desc) {
			if (debug) System.out.println("attribute update for "+desc.getDisplayName(input));
			
//			 StyledText fields for strings and integers
			if ( ((EObject)Afid.getEditableValue(input))
					.eGet((EStructuralFeature)desc
							.getFeature(input)) != null ) {
				((StyledText)widgetlist.get(desc.getId(input)))
				.setText(
					((EObject)Afid.getEditableValue(input))
					.eGet(
					(EStructuralFeature)desc.getFeature(input))
						.toString());
			} else { 
				((StyledText)widgetlist.get(desc.getId(input)))
				.setText(""); 
			}
			
			// other fields?	
		}
		
		private void attributefieldMaker(
				Object dtype, 
				final IItemPropertyDescriptor desc){			
			StyledText dtxt=null;			
			String dString = "";
			//if (debug) System.out.println("grasp for straw"+ dtype.getClass());

			if (dtype == null) { dtype = new String(""); }
			//if (dtype != null) 
				dString= new String(dtype.toString());	
			//dtxt= toolkit.createText(detailsclient,dString,SWT.NONE);
			dtxt= new StyledText(detailsclient, SWT.NONE);
			dtxt.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER); 
			toolkit.adapt(dtxt,true,true);
			dtxt.setText(dString);
			StyleRange styleRange = new StyleRange();
			styleRange.start = 0;
			styleRange.length = dString.length();
			styleRange.fontStyle = SWT.BOLD;
			styleRange.foreground = detailsclient.getDisplay().getSystemColor(SWT.COLOR_BLUE);
			dtxt.setStyleRange(styleRange);
			//if (! (dtype instanceof BasicFeatureMap))
			dtxt.setEditable(true); 
			
			GridData gd = new GridData();
			//	gd.heightHint=200;
			gd.widthHint=200;
			dtxt.setLayoutData(gd);
			if (debug) System.out.println("string?: " + (boolean)(dtype instanceof String));
			if (dtype instanceof String ) {
					// System.out.println("STRING!!!");
				 	dtxt.addModifyListener(
				 		new ModifyListener(){
						public void 
						modifyText(ModifyEvent e) {
							for (Iterator 
							j=detailPropertyDescriptors
							.iterator();
							j.hasNext();) {
								IItemPropertyDescriptor 
								idesc =
								(IItemPropertyDescriptor)
								j.next();
								
								if (idesc.getId(input)
									.equals(
									 desc.getId(input)) ) 
								{
								 	// this works for string property
								 	idesc
									.setPropertyValue(
											input,
											((StyledText)e.widget)
											    .getText());													 	
								 }
							}
						}});						 	
			} else if (dtype instanceof Integer) {
					// System.out.println("INTEGER!!!");
					dtxt.addModifyListener(new ModifyListener(){
						public void modifyText(ModifyEvent e) {
							for (Iterator j=detailPropertyDescriptors.iterator();j.hasNext();) {
								 IItemPropertyDescriptor idesc = (IItemPropertyDescriptor)j.next();
								 if (idesc.getId(input).equals(desc.getId(input)) ) {
								 	// this works for Integer property
								 	idesc.setPropertyValue(input, new Integer(Integer.parseInt(((StyledText)e.widget).getText())));																						 	
								 }
							}
						}});
			} else { 
				if (((EObject)Afid.getEditableValue(input))
				.eGet((EStructuralFeature)desc.getFeature(input))
				!= null)
					System.out.println(dtype.getClass()+" !!! ");
			}						 	
			widgetlist.put(desc.getId(input),dtxt);
			
			if (((EObject)Afid.getEditableValue(input))
					.eGet((EStructuralFeature)desc.getFeature(input))
					!= null)
				if (debug) System.out.println("widgetlist entry for "+desc.getId(input)+" contains "+((StyledText)widgetlist.get(desc.getId(input))).getText());
		

		}
		
		/**
		 * @param input
		 */
		public void createSection( final Object input) {
			GridData gd;			
			detailPropertyDescriptors=
				(ArrayList) Afid.getPropertyDescriptors(input);
			widgetlist = new HashMap();
			
			// details details details
			// thing name
			if (debug) System.out.println("createSection: "+Aflp.getText(input));
			// thing properties
			for (Iterator i = detailPropertyDescriptors.iterator();
				i.hasNext();)
			{	
				final IItemPropertyDescriptor 
				desc = (IItemPropertyDescriptor)i.next();				
				String itemID = desc.getId(input);
				String displayName 	= desc.getDisplayName(input);
				if (debug) System.out.println("property descriptor:"+" displayName= "+displayName+" | ID= "+itemID);
				
				EAttribute thingamajig	= 
					(EAttribute) desc.getFeature(input);
				
				Object whichamajig = ((EObject)Afid.getEditableValue(input)).eGet((EStructuralFeature)thingamajig);
								
				if (debug) System.out.println("thingamajig= "+thingamajig+ "\nwhichamajig= "+whichamajig);				
				labelMaker(" ");labelMaker(" "); // blankrow of 2 columns whitespace 					 
				// item label
				labelMaker(displayName); 
					 
				// item edit ui control
				
				if ( thingamajig instanceof EAttribute ) { 					
					if (debug) 	{
							System.out.print("\n\nATTRIBUTE:: "+displayName+"= " + whichamajig);
							if (whichamajig != null) System.out.println(" | TYPE:: " + whichamajig.getClass());
							System.out.println("\n");							
					}
					
					attributefieldMaker (whichamajig, desc);
					
				} else if (desc.getFeature(input) 
						instanceof EReferenceImpl) {
					 labelMaker("Reference: "+
						 		((EObject)Afid.getEditableValue(input))
										.eGet((EStructuralFeature)desc
												.getFeature(input))
											
					 );					 							 
				} else {
					labelMaker( desc.getFeature(input)
							.toString() + " ?: " +
						 		((EObject)Afid.getEditableValue(input))
									.eGet((EStructuralFeature)desc
											.getFeature(input))
										
					 );
				}
					 
			} // end foreach property descriptor	
			labelMaker(" ");				
			toolkit.paintBordersFor(detailsclient);			
		}
		
		public void updateSection(Object input) {			
			for ( Iterator i = detailPropertyDescriptors.iterator();
				  i.hasNext();) {
				IItemPropertyDescriptor desc = 
					(IItemPropertyDescriptor)i.next();				
				if ( widgetlist.get(desc.getId(input)) != null ) {					
					Object thingamajig	= desc.getFeature(input);					
					Object whichamajig = ((EObject)Afid.getEditableValue(input))
								.eGet((EStructuralFeature)thingamajig);
					
					if ( thingamajig instanceof EAttribute) {
						attributefieldUpdater(whichamajig,desc);
					}
					
										
				}
			}
		}	
		
	}
	  
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#initialize(org.eclipse.ui.forms.IManagedForm)
	 */
		public void initialize(IManagedForm form) {

			this.mform = form;
		}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#isDirty()
	 */
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#commit(boolean)
	 */
	public void commit(boolean onSave) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#setFormInput(java.lang.Object)
	 */
	public boolean setFormInput(Object input) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#setFocus()
	 */
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#isStale()
	 */
	public boolean isStale() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#refresh()
	 */
	public void refresh() {
		// TODO Auto-generated method stub
		// update();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IPartSelectionListener#selectionChanged(org.eclipse.ui.forms.IFormPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IFormPart part, ISelection selection) {
		// TODO Auto-generated method stub
		update();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		// TODO Auto-generated method stub
		update();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partActivated(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partBroughtToTop(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partBroughtToTop(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partClosed(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partDeactivated(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partDeactivated(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partOpened(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub		
	}

}
