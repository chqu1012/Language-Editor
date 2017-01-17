package de.dc.editor.lang.ui.editors

import de.dc.editor.lang.model.Color
import de.dc.editor.lang.model.Template
import java.util.Collection
import java.util.HashMap
import org.eclipse.emf.common.command.BasicCommandStack
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain
import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory
import org.eclipse.emf.edit.ui.action.CreateChildAction
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter
import org.eclipse.emf.edit.ui.dnd.LocalTransfer
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.emf.edit.ui.view.ExtendedPropertySheetPage
import org.eclipse.jface.action.IMenuListener
import org.eclipse.jface.action.IMenuManager
import org.eclipse.jface.action.MenuManager
import org.eclipse.jface.util.LocalSelectionTransfer
import org.eclipse.jface.viewers.DoubleClickEvent
import org.eclipse.jface.viewers.IDoubleClickListener
import org.eclipse.jface.viewers.ISelection
import org.eclipse.jface.viewers.ISelectionChangedListener
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.jface.viewers.SelectionChangedEvent
import org.eclipse.jface.viewers.StructuredSelection
import org.eclipse.jface.viewers.TreeViewer
import org.eclipse.swt.SWT
import org.eclipse.swt.dnd.DND
import org.eclipse.swt.dnd.FileTransfer
import org.eclipse.swt.dnd.Transfer
import org.eclipse.swt.events.KeyEvent
import org.eclipse.swt.events.KeyListener
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.RGB
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.ColorDialog
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Menu
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Text
import org.eclipse.ui.dialogs.FilteredTree
import org.eclipse.ui.dialogs.PatternFilter
import org.eclipse.ui.part.ViewPart
import org.eclipse.ui.views.properties.IPropertySheetPage
import org.eclipse.ui.views.properties.PropertySheetPage
import de.dc.editor.lang.model.provider.ModelItemProviderAdapterFactory

class LanguageView extends ViewPart implements IMenuListener {
	
	static final String ID = "de.dc.editor.lang.ui.LanguageView"

	ComposedAdapterFactory adapterFactory
	AdapterFactoryEditingDomain editingDomain
	TreeViewer viewer
	IPropertySheetPage page
	AdapterFactoryContentProvider contentProvider
	Text text

	override createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false))
		var saveButton = new Button(parent, SWT::PUSH)
		saveButton.setLayoutData(new GridData(SWT::BEGINNING, SWT::CENTER, false, false))
		saveButton.setText("Save")
		saveButton.addSelectionListener(
			new SelectionAdapter() {
				override void widgetSelected(SelectionEvent e) {
					editingDomain.resourceSet.getResource(URI::createFileURI(ILanguageConstants::MODEL_PATH),
							true).save(null)
				}
			})
		initializeEditingDomain
		var filter = new PatternFilter
		var tree = new FilteredTree(parent, SWT::MULTI.bitwiseOr(SWT::H_SCROLL).bitwiseOr(SWT::V_SCROLL),
			filter, true)
		tree.setLayoutData(new GridData(SWT::FILL, SWT::FILL, true, true))
		viewer = tree.viewer
		contentProvider = new AdapterFactoryContentProvider(adapterFactory)
		viewer.setContentProvider(contentProvider)
		viewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory))
		viewer.addDoubleClickListener(([DoubleClickEvent event|
			var ISelection selection = viewer.getSelection()
			if (selection instanceof IStructuredSelection) {
				var IStructuredSelection ss = (selection as IStructuredSelection)
				if (ss.getFirstElement() instanceof Color) {
					var Color color = (ss.getFirstElement() as Color)
					var Shell shell = new Shell()
					var ColorDialog dlg = new ColorDialog(shell)
					dlg.setText("Choose a Color")
					var RGB rgb = dlg.open()
					if (rgb !== null) {
						color.setR(rgb.red)
						color.setG(rgb.green)
						color.setB(rgb.blue)
					}
				}
			}
		] as IDoubleClickListener))
		viewer.addSelectionChangedListener(([SelectionChangedEvent event|
			var ISelection selection = viewer.getSelection()
			if (selection instanceof IStructuredSelection) {
				var IStructuredSelection ss = (selection as IStructuredSelection)
				if (ss.getFirstElement() instanceof Template) {
					text.setText(((ss.getFirstElement() as Template)).getPattern())
				}
			}
		] as ISelectionChangedListener))
		hookContextMenu(viewer)
		getViewSite().setSelectionProvider(viewer)
		var Composite composite = new Composite(parent, SWT::NONE)
		composite.setLayout(new GridLayout(3, false))
		var Label lblTemplate = new Label(composite, SWT::NONE)
		lblTemplate.setText("Template")
		val btnApply = new Button(composite, SWT::NONE)
		btnApply.addSelectionListener(new SelectionAdapter() {
			override void widgetSelected(SelectionEvent e) {
				var ISelection selection = viewer.getSelection()
				if (selection instanceof IStructuredSelection) {
					var IStructuredSelection ss = (selection as IStructuredSelection)
					if (ss.getFirstElement() instanceof Template) {
						var Template template = (ss.getFirstElement() as Template)
						template.setPattern(text.getText())
					}
					btnApply.setEnabled(false)
				}
			}
		})
		btnApply.setText("Apply")
		btnApply.setEnabled(false)
		var Button btnVariable = new Button(composite, SWT::NONE)
		btnVariable.addSelectionListener(new SelectionAdapter() {
			override void widgetSelected(SelectionEvent e) {
				var int caretPosition = text.getCaretPosition()
				text.insert("${NAME}")
				text.forceFocus()
				text.setSelection(caretPosition + 2, caretPosition + 6)
			}
		})
		btnVariable.setText("Variable")
		text = new Text(parent, SWT::BORDER.bitwiseOr(SWT::V_SCROLL).bitwiseOr(SWT::MULTI))
		var GridData gd_text = new GridData(SWT::FILL, SWT::FILL, true, false, 1, 1)
		gd_text.heightHint = 100
		text.setLayoutData(gd_text)
		text.addKeyListener(new KeyListener() {
			override void keyReleased(KeyEvent e) {
				btnApply.setEnabled(true)
			}

			override void keyPressed(KeyEvent e) {
			}
		})
		if (ILanguageConstants::MODEL_PATH !== null) {
			var URI resourceURI = URI::createFileURI(ILanguageConstants::MODEL_PATH)
			editingDomain.getResourceSet().getResource(resourceURI, true)
			viewer.setInput(editingDomain.getResourceSet())
			viewer.setSelection(new StructuredSelection(editingDomain.getResourceSet().getResources().get(0)), true)
		}
	}

	def protected void initializeEditingDomain() {
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry::INSTANCE)
		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory())
//		adapterFactory.addAdapterFactory(new de.dc.editor.lang.model.provider.ModelItemProviderAdapterFactory())
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory())
		var BasicCommandStack commandStack = new BasicCommandStack()
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack, new HashMap<Resource, Boolean>())
	}

	def private void hookContextMenu(TreeViewer viewer) {
		var MenuManager menuManager = new MenuManager()
		menuManager.setRemoveAllWhenShown(true)
		menuManager.addMenuListener(this)
		var Menu menu = menuManager.createContextMenu(viewer.getTree())
		viewer.getTree().setMenu(menu)
		var int dndOperations = DND::DROP_COPY.bitwiseOr(DND::DROP_MOVE).bitwiseOr(DND::DROP_LINK)
		var Transfer[] transfers = (#[LocalTransfer::getInstance(), LocalSelectionTransfer::getTransfer(),
			FileTransfer::getInstance()] as Transfer[])
		viewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(viewer))
		viewer.addDropSupport(dndOperations, transfers, new EditingDomainViewerDropAdapter(editingDomain, viewer))
	}

	override void menuAboutToShow(IMenuManager manager) {
		var IStructuredSelection ss = (viewer.getSelection() as IStructuredSelection)
		var Object firstElement = ss.getFirstElement()
		var MenuManager childMenu = new MenuManager("New Child")
		manager.add(childMenu)
		var Collection<?> newChildDescriptors = editingDomain.getNewChildDescriptors(firstElement, null)
		for (Object descriptor : newChildDescriptors) {
			childMenu.add(new CreateChildAction(editingDomain, ss, descriptor))
		}
	}

	override Object getAdapter(Class adapter) {
		if (adapter.equals(typeof(IPropertySheetPage))) {
			if (page === null) {
				page = getPropertySheetPage()
			}
			return page
		}
		return super.getAdapter(adapter)
	}

	def IPropertySheetPage getPropertySheetPage() {
		var PropertySheetPage propertySheetPage = new ExtendedPropertySheetPage(editingDomain)
		propertySheetPage.setPropertySourceProvider(contentProvider)
		return propertySheetPage
	}

	override void setFocus() {
	}
}
