package de.dc.editor.lang.ui.editors;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.view.ExtendedPropertySheetPage;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

import de.dc.editor.lang.model.Color;
import de.dc.editor.lang.model.provider.ModelItemProviderAdapterFactory;

public class LanguageEditor extends TextEditor implements IMenuListener{
	
	private ComposedAdapterFactory adapterFactory;
	private AdapterFactoryEditingDomain editingDomain;
	private TreeViewer viewer;
	 
	IPropertySheetPage page;
	ContentOutlinePage outline;
	
	private AdapterFactoryContentProvider contentProvider;

	public LanguageEditor() {
		setSourceViewerConfiguration(new LanguageConfiguration());
	}
	
	@Override
	public void createPartControl(Composite parent) {
		TabFolder tabFolder = new TabFolder(parent, SWT.BOTTOM);
		
		Composite codeComposite = createTabItem(tabFolder, "Code");
		Composite langDefComposite = createTabItem(tabFolder, "Language Definition");
		
		initializeEditingDomain();
		
		PatternFilter filter = new PatternFilter();
		FilteredTree tree = new FilteredTree(langDefComposite, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL, filter, true);

		viewer = tree.getViewer();
		contentProvider = new AdapterFactoryContentProvider(adapterFactory);
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = viewer.getSelection();	
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

		hookContextMenu(viewer);
		
//		getSite().setSelectionProvider(viewer);
//		getEditorSite().setSelectionProvider(viewer);
		
		if(ILanguageConstants.MODEL_PATH!=null){
			URI resourceURI = URI.createFileURI(ILanguageConstants.MODEL_PATH);
			editingDomain.getResourceSet().getResource(resourceURI, true);
			viewer.setInput(editingDomain.getResourceSet());
			viewer.setSelection(new StructuredSelection(editingDomain.getResourceSet().getResources().get(0)), true);
		}
		
		super.createPartControl(codeComposite);
	}

	private Composite createTabItem(TabFolder tabFolder, String name) {
		TabItem item = new TabItem(tabFolder, SWT.NONE);
		item.setText(name);
		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setLayout(new FillLayout());
		item.setControl(composite);
		return composite;
	}
	
	protected void initializeEditingDomain() {
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ModelItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		BasicCommandStack commandStack = new BasicCommandStack();
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack, new HashMap<Resource, Boolean>());
	}
	
	private void hookContextMenu(TreeViewer viewer){
		MenuManager menuManager = new MenuManager();
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(this);
		Menu menu = menuManager.createContextMenu(viewer.getTree());
		viewer.getTree().setMenu(menu);
		
		int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance(), LocalSelectionTransfer.getTransfer(), FileTransfer.getInstance() };
		viewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(viewer));
		viewer.addDropSupport(dndOperations, transfers, new EditingDomainViewerDropAdapter(editingDomain, viewer));
	}

	@Override
	public void menuAboutToShow(IMenuManager manager) {
		IStructuredSelection ss = (IStructuredSelection)viewer.getSelection();
		Object firstElement = ss.getFirstElement();
		MenuManager childMenu = new MenuManager("New Child");
		manager.add(childMenu);
		Collection<?> newChildDescriptors = editingDomain.getNewChildDescriptors(firstElement, null);
		for (Object descriptor : newChildDescriptors) {
			childMenu.add(new CreateChildAction(editingDomain, ss, descriptor));
		}
	}
	
	@Override
	public Object getAdapter(Class adapter) {
		if (adapter.equals(IPropertySheetPage.class)) {
			if (page==null) {
//				page = new ExtendedPropertySheetPage(editingDomain);
//				page.setPropertySourceProvider(new AdapterFactoryContentProvider(adapterFactory));
				page = getPropertySheetPage();
			}
			return page;
		}else if (IContentOutlinePage.class.equals(adapter)) {
	         if (outline == null) {
	             outline = new ContentOutlinePage() {
				}; //CoolLanguageContentOutlinePage(getDocumentProvider(), this);
	          }
	          return outline;
	       }
		return super.getAdapter(adapter);
	}
	
	public IPropertySheetPage getPropertySheetPage() {
		PropertySheetPage propertySheetPage = new ExtendedPropertySheetPage(editingDomain);
		propertySheetPage.setPropertySourceProvider(contentProvider);
		return propertySheetPage;
	}
}
