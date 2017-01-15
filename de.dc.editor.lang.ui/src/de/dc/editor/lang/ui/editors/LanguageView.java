package de.dc.editor.lang.ui.editors;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.view.ExtendedPropertySheetPage;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

import de.dc.editor.lang.model.Color;
import de.dc.editor.lang.model.provider.ModelItemProviderAdapterFactory;

public class LanguageView extends ViewPart implements IMenuListener{

	private ComposedAdapterFactory adapterFactory;
	private AdapterFactoryEditingDomain editingDomain;
	private TreeViewer viewer;
	 
	IPropertySheetPage page;
	private AdapterFactoryContentProvider contentProvider;

	@Override
	public void createPartControl(Composite parent) {
		initializeEditingDomain();
		
		PatternFilter filter = new PatternFilter();
		FilteredTree tree = new FilteredTree(parent, SWT.MULTI | SWT.H_SCROLL
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
		
		getViewSite().setSelectionProvider(viewer);
//		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().
		
		if(ILanguageConstants.MODEL_PATH!=null){
			URI resourceURI = URI.createFileURI(ILanguageConstants.MODEL_PATH);
			editingDomain.getResourceSet().getResource(resourceURI, true);
			viewer.setInput(editingDomain.getResourceSet());
			viewer.setSelection(new StructuredSelection(editingDomain.getResourceSet().getResources().get(0)), true);
		}		
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
		}
		return super.getAdapter(adapter);
	}
	
	public IPropertySheetPage getPropertySheetPage() {
		PropertySheetPage propertySheetPage = new ExtendedPropertySheetPage(editingDomain);
		propertySheetPage.setPropertySourceProvider(contentProvider);
		return propertySheetPage;
	}
	@Override
	public void setFocus() {
	}

}
