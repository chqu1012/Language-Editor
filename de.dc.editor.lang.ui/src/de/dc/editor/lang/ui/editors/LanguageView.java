package de.dc.editor.lang.ui.editors;

import java.io.IOException;
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
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

import de.dc.editor.lang.model.Color;
import de.dc.editor.lang.model.Template;
import de.dc.editor.lang.model.provider.ModelItemProviderAdapterFactory;

public class LanguageView extends ViewPart implements IMenuListener {
	public LanguageView() {
	}

	private ComposedAdapterFactory adapterFactory;
	private AdapterFactoryEditingDomain editingDomain;
	private TreeViewer viewer;

	IPropertySheetPage page;
	private AdapterFactoryContentProvider contentProvider;
	private Text text;
	
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		Button saveButton = new Button(parent, SWT.PUSH);
		saveButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		saveButton.setText("Save");
		saveButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					editingDomain.getResourceSet().getResource(URI.createFileURI(ILanguageConstants.MODEL_PATH), true)
							.save(null);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		initializeEditingDomain();

		PatternFilter filter = new PatternFilter();
		FilteredTree tree = new FilteredTree(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL, filter, true);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
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
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = viewer.getSelection();
				if (selection instanceof IStructuredSelection) {
					IStructuredSelection ss = (IStructuredSelection) selection;
					if (ss.getFirstElement() instanceof Template) {
						text.setText(((Template)ss.getFirstElement()).getPattern());
					}					
				}				
			}
		});
		hookContextMenu(viewer);

		getViewSite().setSelectionProvider(viewer);
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		
		Label lblTemplate = new Label(composite, SWT.NONE);
		lblTemplate.setText("Template");
		
		Button btnApply = new Button(composite, SWT.NONE);
		btnApply.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection selection = viewer.getSelection();
				if (selection instanceof IStructuredSelection) {
					IStructuredSelection ss = (IStructuredSelection) selection;
					if (ss.getFirstElement() instanceof Template) {
						Template template = (Template) ss.getFirstElement() ;
						template.setPattern(text.getText());
					}					
				}
			}
		});
		btnApply.setText("Apply");
		btnApply.setEnabled(false);
		btnApply.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				super.widgetDefaultSelected(e);
				btnApply.setEnabled(false);
			}
		});
		
		text = new Text(parent, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_text = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_text.heightHint = 100;
		text.setLayoutData(gd_text);
		text.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnApply.setEnabled(true);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		if (ILanguageConstants.MODEL_PATH != null) {
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

	private void hookContextMenu(TreeViewer viewer) {
		MenuManager menuManager = new MenuManager();
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(this);
		Menu menu = menuManager.createContextMenu(viewer.getTree());
		viewer.getTree().setMenu(menu);

		int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance(), LocalSelectionTransfer.getTransfer(),
				FileTransfer.getInstance() };
		viewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(viewer));
		viewer.addDropSupport(dndOperations, transfers, new EditingDomainViewerDropAdapter(editingDomain, viewer));
	}

	@Override
	public void menuAboutToShow(IMenuManager manager) {
		IStructuredSelection ss = (IStructuredSelection) viewer.getSelection();
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
			if (page == null) {
				// page = new ExtendedPropertySheetPage(editingDomain);
				// page.setPropertySourceProvider(new
				// AdapterFactoryContentProvider(adapterFactory));
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
