/*
 * Created on Oct 19, 2004
 *
 */
package de.dc.editor.lang.ui.editors.generic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.ControlEvent;
//import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.editor.FormEditor;

import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.PropertySheetPage;


/**
 * @author swb
 *
generic form editor
 */
public class gfEditor extends FormEditor 
	implements 
		ISelectionProvider, 
		IMenuListener,
		IViewerProvider,
		IEditingDomainProvider {
	
	boolean debug=false;
	static FormToolkit toolkit;	
	protected AdapterFactoryEditingDomain editingDomain;
	protected ComposedAdapterFactory adapterFactory;
	protected Viewer currentViewer;
	protected PropertySheetPage propertySheetPage;
	protected IContentOutlinePage contentOutlinePage;
	protected IStatusLineManager contentOutlineStatusLineManager;
	protected TreeViewer contentOutlineViewer;
	protected ISelectionChangedListener selectionChangedListener;
	protected Collection selectionChangedListeners = new ArrayList();
	protected ScrolledPropertiesPage mdPage=null;
	
	
	/**
	 * 
	 */
	public gfEditor() {
		super();

		List factories = new ArrayList();
		factories.add(new ResourceItemProviderAdapterFactory());
		
		// ?? add specific xyzItemProviderFactory's here 
		//factories.add(new LibraryItemProviderAdapterFactory());
		
		factories.add(new ReflectiveItemProviderAdapterFactory());

		adapterFactory = new ComposedAdapterFactory(factories);

		// Create the command stack that will notify this editor as commands are executed.
		//
		BasicCommandStack commandStack = new BasicCommandStack();

		// Add a listener to set the most recent command's affected objects to be the selection of the viewer with focus.
		//
		commandStack.addCommandStackListener
			(new CommandStackListener() {
				 public void commandStackChanged(final EventObject event) {
					 getContainer().getDisplay().asyncExec
						 (new Runnable() {
							  public void run() {
								  firePropertyChange(IEditorPart.PROP_DIRTY);
								  if (debug) System.out.println("CommandStackListener: "+event.toString());
								  // Try to select the affected objects.
								  //
								  Command mostRecentCommand = ((CommandStack)event.getSource()).getMostRecentCommand();
								  if (mostRecentCommand != null) {
									  setSelectionToViewer(mostRecentCommand.getAffectedObjects());
								  }
								  if (propertySheetPage != null) {
									  propertySheetPage.refresh();
								  }
								  
							  }
						  });
				 }
			 });

		// Create the editing domain with a special command stack.
		//
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack, new HashMap());
	}

	
	public EditingDomainActionBarContributor getActionBarContributor() {
		return (EditingDomainActionBarContributor)getEditorSite().getActionBarContributor();
	}

	public IActionBars getActionBars() {
		return getActionBarContributor().getActionBars();
	}
	public class ReverseAdapterFactoryContentProvider extends AdapterFactoryContentProvider {
		public ReverseAdapterFactoryContentProvider(AdapterFactory adapterFactory) {
			super(adapterFactory);
		}

		public Object [] getElements(Object object) {
			Object parent = super.getParent(object);
			return (parent == null ? Collections.EMPTY_SET : Collections.singleton(parent)).toArray();
		}

		public Object [] getChildren(Object object) {
			Object parent = super.getParent(object);
			return (parent == null ? Collections.EMPTY_SET : Collections.singleton(parent)).toArray();
		}

		public boolean hasChildren(Object object) {
			Object parent = super.getParent(object);
			return parent != null;
		}

		public Object getParent(Object object) {
			return null;
		}
	}


	/*
	 * dispose: not my job ?
	 * 
	 */
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
		getSite().getPage().removePartListener(partListener);
		adapterFactory.dispose();
		if (getActionBarContributor().getActiveEditor() == this) {
			getActionBarContributor().setActiveEditor(null);
		}
		if (propertySheetPage != null) {
			propertySheetPage.dispose();
		}
		if (contentOutlinePage != null) {
			contentOutlinePage.dispose();
		}
		super.dispose();
	}
	/**/
	

	protected void firePropertyChange(int action) {
		super.firePropertyChange(action);
	}
	
	public AdapterFactory getAdapterFactory() {
		return adapterFactory;
	}
	
	public void setSelectionToViewer(Collection collection) {
		final Collection theSelection = collection;
		// Make sure it's okay.
		//
		if (theSelection != null && !theSelection.isEmpty()) {
			// I don't know if this should be run this deferred
			// because we might have to give the editor a chance to process the viewer update events
			// and hence to update the views first.
			//
			//
			Runnable runnable =
				new Runnable() {
					public void run() {
						// Try to select the items in the current content viewer of the editor.
						//
						if (currentViewer != null) {
							currentViewer.setSelection(new StructuredSelection(theSelection.toArray()), true);
						}
					}
				};
			runnable.run();
		}
	}

/////////

	Form homePage() { return tempPage("Home"); }
		
	Form tempPage(String fname) {
		Form tempform = toolkit.createForm(getContainer());
		tempform.setText(fname);		
		Font font = new Font(tempform.getBody().getDisplay(),
				new FontData("Arial",12,SWT.NORMAL));
		
		TableWrapLayout layout = new TableWrapLayout();				
		tempform.getBody().setLayout(layout);
		layout.numColumns=2;

		StringBuffer buf = new StringBuffer();
		buf.append(
				"<form>"+
				"<p> " +
				fname +
				"</p>"+"</form>");
		
		FormText rtext = toolkit.createFormText(tempform.getBody(),true);
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
		
		return (tempform);
	}
	

	public void createContextMenuFor(StructuredViewer viewer) {
		MenuManager contextMenu = new MenuManager("#PopUp");
		contextMenu.add(new Separator("additions"));
		contextMenu.setRemoveAllWhenShown(true);
		contextMenu.addMenuListener(this);
		Menu menu= contextMenu.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(contextMenu, viewer);

		int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };
		viewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(viewer));
		viewer.addDropSupport(dndOperations, transfers, new EditingDomainViewerDropAdapter(editingDomain, viewer));
	}

	/**
	 * This is the method called to load a resource into the editing domain's resource set based on the editor's input.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createModel() {
		
		// I assume that the input is a file object.
		//
		IFileEditorInput modelFile = (IFileEditorInput)getEditorInput();

		try {
			// Load the resource through the editing domain.
			//
			Resource resource =
				editingDomain.loadResource
					(URI.createPlatformResourceURI(modelFile.getFile().getFullPath().toString()).toString());
			if ( debug) System.out.println("resource loaded: "+resource.toString());
		}
		catch (Exception exception) {
			// Lib2EditorPlugin.INSTANCE.log(exception);
			// TODO: implement INSTANCE.log
			if ( debug) System.out.println("could not load resource: "+exception.getMessage());
		}
	}


	/**
	 * If there is just one page in the multi-page editor part, this hides
	 * the single tab at the bottom.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void hideTabs() {
		if (getPageCount() <= 1) {
			setPageText(0, "");
			if (getContainer() instanceof CTabFolder) {
				((CTabFolder)getContainer()).setTabHeight(1);
				Point point = getContainer().getSize();
				getContainer().setSize(point.x, point.y + 6);
			}
		}
	}

	/**
	 * This is used to track the active viewer.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void pageChange(int pageIndex) {
		
		super.pageChange(pageIndex);

		// This is a temporary workaround... EATM
		//
		Control control = getControl(pageIndex);
		if (control != null) {
			control.setVisible(true);
			control.setFocus();
		}

		if (contentOutlinePage != null) {
			;//handleContentOutlineSelection(contentOutlinePage.getSelection());
		}	
	}

	/**
	 * This is how the framework determines which interfaces we implement.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getAdapter(Class key) {
		if (key.equals(IContentOutlinePage.class)) {
			return getContentOutlinePage();
		}
		else if (key.equals(IPropertySheetPage.class)) {
			return getPropertySheetPage();
		}
		else if (key.equals(IGotoMarker.class)) {
			return this;
		}
		else {
			return super.getAdapter(key);
		}
	}

	/**
	 * This accesses a cached version of the content outliner.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IContentOutlinePage getContentOutlinePage() {
		if (contentOutlinePage == null) {
			// The content outline is just a tree.
			//
			class MyContentOutlinePage extends ContentOutlinePage {
				public void createControl(Composite parent) {
					super.createControl(parent);
					contentOutlineViewer = getTreeViewer();
					contentOutlineViewer.addSelectionChangedListener(this);

					// Set up the tree viewer.
					//
					contentOutlineViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
					contentOutlineViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
					contentOutlineViewer.setInput(editingDomain.getResourceSet());

					// Make sure our popups work.
					//
					createContextMenuFor(contentOutlineViewer);

					if (!editingDomain.getResourceSet().getResources().isEmpty()) {
					  // Select the root object in the view.
					  //
					  ArrayList selection = new ArrayList();
					  selection.add(editingDomain.getResourceSet().getResources().get(0));
					  contentOutlineViewer.setSelection(new StructuredSelection(selection), true);
					}
				}

				public void makeContributions(IMenuManager menuManager, IToolBarManager toolBarManager, IStatusLineManager statusLineManager) {
					super.makeContributions(menuManager, toolBarManager, statusLineManager);
					contentOutlineStatusLineManager = statusLineManager;
				}

				public void setActionBars(IActionBars actionBars) {
					super.setActionBars(actionBars);
					getActionBarContributor().shareGlobalActions(this, actionBars);
				}
			}

			contentOutlinePage = new MyContentOutlinePage();

			// Listen to selection so that we can handle it is a special way.
			//
			contentOutlinePage.addSelectionChangedListener
				(new ISelectionChangedListener() {
					 // This ensures that we handle selections correctly.
					 //
					 public void selectionChanged(SelectionChangedEvent event) {
						 ;//handleContentOutlineSelection(event.getSelection());
					 }
				 });
		}

		return contentOutlinePage;
	}

	/**
	 * This accesses a cached version of the property sheet.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IPropertySheetPage getPropertySheetPage() {
		if (propertySheetPage == null) {
			propertySheetPage =
				new PropertySheetPage() {
					public void makeContributions(IMenuManager menuManager, IToolBarManager toolBarManager, IStatusLineManager statusLineManager) {
						super.makeContributions(menuManager, toolBarManager, statusLineManager);
					}

					public void setActionBars(IActionBars actionBars) {
						super.setActionBars(actionBars);
						getActionBarContributor().shareGlobalActions(this, actionBars);
					}
				};
			propertySheetPage.setPropertySourceProvider(new AdapterFactoryContentProvider(adapterFactory));
		}

		return propertySheetPage;
	}

	/**
	 * This is for implementing {@link IEditorPart} and simply tests the command stack.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDirty() {
		return ((BasicCommandStack)editingDomain.getCommandStack()).isSaveNeeded();
	}

	/**
	 * This is for implementing {@link IEditorPart} and simply saves the model file.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void doSave(IProgressMonitor progressMonitor) {
		// Do the work within an operation because this is a long running activity that modifies the workbench.
		//
		WorkspaceModifyOperation operation =
			new WorkspaceModifyOperation() {
				// This is the method that gets invoked when the operation runs.
				//
				public void execute(IProgressMonitor monitor) {
					try {
						// Save the resource to the file system.
						//
						Resource savedResource = (Resource)editingDomain.getResourceSet().getResources().get(0);
						savedResources.add(savedResource);
						savedResource.save(Collections.EMPTY_MAP);
					}
					catch (Exception exception) {
						exception.printStackTrace();
					}
				}
			};

		try {
			// This runs the options, and shows progress.
			//
			new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);

			// Refresh the necessary state.
			//
			((BasicCommandStack)editingDomain.getCommandStack()).saveIsDone();
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
		catch (Exception exception) {
			// Something went wrong that shouldn't.
			//
			exception.printStackTrace();
		}
	}

	/**
	 * This always returns true because it is not currently supported.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * This also changes the editor's input.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void doSaveAs() {
		SaveAsDialog saveAsDialog= new SaveAsDialog(getSite().getShell());
		saveAsDialog.open();
		IPath path= saveAsDialog.getResult();
		if (path != null) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			if (file != null) {
				doSaveAs(URI.createPlatformResourceURI(file.getFullPath().toString()), new FileEditorInput(file));
			}
		}
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void doSaveAs(URI uri, IEditorInput editorInput) {
		((Resource)editingDomain.getResourceSet().getResources().get(0)).setURI(uri);
		setInput(editorInput);
		setPartName(editorInput.getName());
		IProgressMonitor progressMonitor =
			getActionBars().getStatusLineManager() != null ?
				getActionBars().getStatusLineManager().getProgressMonitor() :
				new NullProgressMonitor();
		doSave(progressMonitor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void gotoMarker(IMarker marker) {
		try {
			if (marker.getType().equals(EValidator.MARKER)) {
				String uriAttribute = marker.getAttribute(EValidator.URI_ATTRIBUTE, null);
				if (uriAttribute != null) {
					URI uri = URI.createURI(uriAttribute);
					EObject eObject = editingDomain.getResourceSet().getEObject(uri, true);
					if (eObject != null) {
					  setSelectionToViewer(Collections.singleton(editingDomain.getWrapper(eObject)));
					}
				}
			}
		}
		catch (CoreException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * This is called during startup.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IEditorSite site;

	private ISelection editorSelection;

	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		
		setSite(site);
		this.site=site;
		setInput(editorInput);
		setPartName(editorInput.getName());
		site.setSelectionProvider(this);
		site.getPage().addPartListener(partListener);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener, IResourceChangeEvent.POST_CHANGE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFocus() { 
	if (debug) System.out.println("setting Focus");
	//getControl( getActivePage()).setFocus(); 
	}

	/**
	 * This implements {@link org.eclipse.jface.viewers.ISelectionProvider}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	/**
	 * This implements {@link org.eclipse.jface.viewers.ISelectionProvider}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}

	/**
	 * This implements {@link org.eclipse.jface.viewers.ISelectionProvider} to return this editor's overall selection.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISelection getSelection() {
		return editorSelection;
	}

	/**
	 * This implements {@link org.eclipse.jface.viewers.ISelectionProvider} to set this editor's overall selection.
	 * Calling this result will notify the listeners.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSelection(ISelection selection) {
		editorSelection = selection;

		if (selection != null ) {
			for (Iterator listeners = selectionChangedListeners.iterator(); listeners.hasNext(); ) {
				ISelectionChangedListener listener = (ISelectionChangedListener)listeners.next();
				listener.selectionChanged(new SelectionChangedEvent(this, selection));
			}
			setStatusLineManager(selection);
		}
	}

//	String getString(String key) {
//		return GfePlugin.getResourceString(key);
//	}
//	String getString(String key, Object obj) {
//		return GfePlugin.getResourceString(key + obj.toString());
//	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatusLineManager(ISelection selection) {
		IStatusLineManager statusLineManager = getActionBars().getStatusLineManager();
		if (statusLineManager != null) {
			if (currentViewer == contentOutlineViewer) {
				statusLineManager = contentOutlineStatusLineManager;
			}
	
			if (selection instanceof IStructuredSelection) {
				Collection collection = ((IStructuredSelection)selection).toList();
				switch (collection.size()) {
					case 0: {
//						statusLineManager.setMessage(getString("_UI_NoObjectSelected: "));
						break;
					}
					case 1: {
//						String text = new AdapterFactoryItemDelegator(adapterFactory).getText(collection.iterator().next());
//						statusLineManager.setMessage(getString("_UI_SingleObjectSelected: ", text));
						break;
					}
					default: {
//						statusLineManager.setMessage(getString("_UI_MultiObjectSelected: ", Integer.toString(collection.size())));
						break;
					}
				}
			}
			else {
				statusLineManager.setMessage("");
			}
		}
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.jface.action.IMenuManager)
	 */
	public void menuAboutToShow(IMenuManager menuManager) {
		((IMenuListener)getEditorSite().getActionBarContributor()).menuAboutToShow(menuManager);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
	 */
	public EditingDomain getEditingDomain() {
		// TODO Auto-generated method stub
		return editingDomain;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.ui.viewer.IViewerProvider#getViewer()
	 */
	public Viewer getViewer() {
		// TODO Auto-generated method stub
		return currentViewer;
	}
	/**
	 * This makes sure that one content viewer, either for the current page or the outline view, if it has focus,
	 * is the current one.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurrentViewer(Viewer viewer) {
		// If it is changing...
		//
		if (currentViewer != viewer) {
			if (selectionChangedListener == null) {
				// Create the listener on demand.
				//
				selectionChangedListener =
					new ISelectionChangedListener() {
						// This just notifies those things that are affected by the section.
						//
						public void selectionChanged(SelectionChangedEvent selectionChangedEvent) {
							setSelection(selectionChangedEvent.getSelection());
						}
					};
			}

			// Stop listening to the old one.
			//
			if (currentViewer != null) {
				currentViewer.removeSelectionChangedListener(selectionChangedListener);
			}

			// Start listening to the new one.
			//
			if (viewer != null) {
				viewer.addSelectionChangedListener(selectionChangedListener);
			}

			// Remember it.
			//
			currentViewer = viewer;

			// Set the editors selection based on the current viewer's selection.
			//
			setSelection(currentViewer == null ? StructuredSelection.EMPTY : currentViewer.getSelection());
		}
	}


	/**
	 * This listens for when the outline becomes active
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IPartListener partListener =
		new IPartListener() {
			public void partActivated(IWorkbenchPart p) {
				if (p instanceof ContentOutline) {
					if (((ContentOutline)p).getCurrentPage() == contentOutlinePage) {
						getActionBarContributor().setActiveEditor(gfEditor.this);
						setCurrentViewer(contentOutlineViewer);
					}
				}
				else if (p instanceof PropertySheet) {
					if (((PropertySheet)p).getCurrentPage() == propertySheetPage) {
						getActionBarContributor().setActiveEditor(gfEditor.this);
						handleActivate();
					}
				}
				else if (p == gfEditor.this) {
					handleActivate();
				}
			}
			public void partBroughtToTop(IWorkbenchPart p) {
			}
			public void partClosed(IWorkbenchPart p) {
			}
			public void partDeactivated(IWorkbenchPart p) {
			}
			public void partOpened(IWorkbenchPart p) {
			}
		};

	/**
	 * Resources that have been removed since last activation.
	 * @generated
	 */
	Collection removedResources = new ArrayList();

	/**
	 * Resources that have been changed since last activation.
	 * @generated
	 */
	Collection changedResources = new ArrayList();

	/**
	 * Resources that have been saved.
	 * @generated
	 */
	Collection savedResources = new ArrayList();

	/**
	 * This listens for workspace changes.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IResourceChangeListener resourceChangeListener =
		new IResourceChangeListener() {
			public void resourceChanged(IResourceChangeEvent event) {
				ResourceSet resourceSet = editingDomain.getResourceSet();
				// Only listening to these.
				// if (event.getType() == IResourceDelta.POST_CHANGE)
				{
					IResourceDelta delta = event.getDelta();
					try {
						class ResourceDeltaVisitor implements IResourceDeltaVisitor {
							protected ResourceSet resourceSet = editingDomain.getResourceSet();
							protected Collection changedResources = new ArrayList();
							protected Collection removedResources = new ArrayList();

							public boolean visit(IResourceDelta delta) {
								if (delta.getFlags() != IResourceDelta.MARKERS &&
								      delta.getResource().getType() == IResource.FILE) {
									if ((delta.getKind() & (IResourceDelta.CHANGED | IResourceDelta.REMOVED)) != 0) {
										Resource resource = resourceSet.getResource(URI.createURI(delta.getFullPath().toString()), false);
										if (resource != null) {
											if ((delta.getKind() & IResourceDelta.REMOVED) != 0) {
												removedResources.add(resource);
											}
											else {
												changedResources.add(resource);
											}
										}
									}
								}

								return true;
							}

							public Collection getChangedResources() {
								return changedResources;
							}

							public Collection getRemovedResources() {
								return removedResources;
							}
						}

						ResourceDeltaVisitor visitor = new ResourceDeltaVisitor();
						delta.accept(visitor);

						removedResources.addAll(visitor.getRemovedResources());
						if (!visitor.getRemovedResources().isEmpty() && !isDirty()) {
							getSite().getShell().getDisplay().asyncExec
								(new Runnable() {
									public void run() {
										getSite().getPage().closeEditor(gfEditor.this, false);
										gfEditor.this.dispose();
									}
								 });
						}

						changedResources.addAll(visitor.getChangedResources());
					}
					catch (CoreException exception) {
						exception.printStackTrace();
					}
				}
			}
		};

	/**
	 * Handles activation of the editor or it's associated views.
	 * @generated
	 */
	protected void handleActivate() {
		// Recompute the read only state.
		//
		if (editingDomain.getResourceToReadOnlyMap() != null) {
		  editingDomain.getResourceToReadOnlyMap().clear();

		  // Refresh any actions that may become enabled or disabled.
		  //
		  setSelection(getSelection());
		}

		if (!removedResources.isEmpty()) {
			if (handleDirtyConflict()) {
				getSite().getPage().closeEditor(gfEditor.this, false);
				gfEditor.this.dispose();
			}
			else {
				removedResources.clear();
				changedResources.clear();
				savedResources.clear();
			}
		}
		else if (!changedResources.isEmpty()) {
			changedResources.removeAll(savedResources);
			handleChangedResources();
			changedResources.clear();
			savedResources.clear();
		}
	}


	/**
	 * Handles what to do with changed resources on activation.
	 * @generated
	 */
	protected void handleChangedResources() {
		if (!changedResources.isEmpty() && (!isDirty() || handleDirtyConflict())) {
		  editingDomain.getCommandStack().flush();

			for (Iterator i = changedResources.iterator(); i.hasNext(); ) {
				Resource resource = (Resource)i.next();
				if (resource.isLoaded()) {
					resource.unload();
					try {
						resource.load(Collections.EMPTY_MAP);
					}
					catch (IOException exception) {
						// 
						exception.printStackTrace();
						
					}
				}
			}
		}
	}

	/**
	 * Shows a dialog that asks if conflicting changes should be discarded.
	 * @generated
	 */
	protected boolean handleDirtyConflict() {
		return
			MessageDialog.openQuestion
				(getSite().getShell(),
//				 GfePlugin.getResourceString("_UI_FileConflict_label"),
//				 GfePlugin.getResourceString("_WARN_FileConflict"));
						"_UI_FileConflict_label",
						 "_WARN_FileConflict");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
	 */
	protected void addPages() {

		createModel();
		
		toolkit=new FormToolkit(getContainer().getDisplay());
		int indexf = 0;		
		
			// indexf=addPage(homePage()); setPageText(indexf, "Home");

			/*
			 * 
			 // external sample page
			  * 
			 
			try {
				indexf=addPage(new SamplePage(this,"samplePageID","SamplePageTitle"));
			} catch (PartInitException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			setPageText(indexf,"Sample Page");	
			*/
		
			// md page
			try {
				indexf=addPage(new ScrolledPropertiesPage(this));
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setPageText(indexf,"Master Details");
	}
}
