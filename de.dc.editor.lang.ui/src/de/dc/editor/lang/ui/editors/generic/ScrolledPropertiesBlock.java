package de.dc.editor.lang.ui.editors.generic;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IDetailsPageProvider;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
//import org.eclipse.ui.forms.examples.internal.ExamplesPlugin;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import de.dc.editor.lang.model.Color;
import de.dc.editor.lang.model.LanguageDefinition;
import de.dc.editor.lang.ui.file.LangFile;
import de.dc.editor.lang.ui.preference.LanguageDefinitionPage;
import de.dc.editor.lang.ui.visitor.DoubleClickSwitch;

public class ScrolledPropertiesBlock extends MasterDetailsBlock implements ISelectionChangedListener {

	private FormPage page;
	protected gfEditor ourEditor;
	private SelectionChangedEvent selectionEvent;
	private IDetailsPageProvider myDetailsPageProvider;

	public AdapterFactoryTreeEditor ADFTE;
	private Resource resource;

	public ScrolledPropertiesBlock(FormPage page, gfEditor theEditor) {
		this.page = page;
		this.ourEditor = theEditor;
		this.myDetailsPageProvider = new DetailsPageProvider(ourEditor);
	}

	protected void createMasterPart(IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.DESCRIPTION);
		section.setText("Model");
		section.setDescription("The list contains objects from the model whose " + "details are editable on the right");
		section.marginWidth = 10;
		section.marginHeight = 5;
		toolkit.createCompositeSeparator(section);

		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);

		DoubleClickSwitch doubleClickSwitch = new DoubleClickSwitch();
		
		PatternFilter filter = new PatternFilter();
		FilteredTree tree = new FilteredTree(client, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL, filter, true);
		tree.getViewer().addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = tree.getViewer().getSelection();
				if (selection instanceof IStructuredSelection) {
					IStructuredSelection ss=(IStructuredSelection)selection;
					doubleClickSwitch.doSwitch((EObject) ss.getFirstElement());
					try {
						resource.save(null);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 100;
		tree.setLayoutData(gd);

		toolkit.paintBordersFor(client);

		Button b = toolkit.createButton(client, "Menu...", SWT.PUSH);
		b.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ISelection ts = tree.getViewer().getSelection();
				tree.getViewer().getControl().getMenu().setVisible(true);
				tree.getViewer().setSelection(ts);

			}
		});
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		b.setLayoutData(gd);
		section.setClient(client);

		final SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);

		ResourceSet resourceSet = ourEditor.getEditingDomain().getResourceSet();
		resource = (Resource) resourceSet.getResources().get(0);
		
		tree.getViewer().addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				managedForm.fireSelectionChanged(spart, event.getSelection());
			}
		});

		page.getSite().setSelectionProvider(tree.getViewer());
		tree.getViewer().setContentProvider(new AdapterFactoryContentProvider(ourEditor.getAdapterFactory()));
		tree.getViewer().setLabelProvider(new AdapterFactoryLabelProvider(ourEditor.getAdapterFactory()));
		ADFTE = new AdapterFactoryTreeEditor(tree.getViewer().getTree(), ourEditor.getAdapterFactory());

		// this is its own problem
		ourEditor.createContextMenuFor(tree.getViewer());
		tree.getViewer().setInput(resource);
		tree.getViewer().addSelectionChangedListener(this);
	}

	protected void createToolBarActions(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		Action haction = new Action("horizontal", Action.AS_RADIO_BUTTON) {
			public void run() {
				sashForm.setOrientation(SWT.HORIZONTAL);
				form.reflow(true);
			}
		};
		haction.setChecked(true);
		haction.setToolTipText("Horizontal orientation");

		Action vaction = new Action("vertical", Action.AS_RADIO_BUTTON) {
			public void run() {
				sashForm.setOrientation(SWT.VERTICAL);
				form.reflow(true);
			}
		};
		vaction.setChecked(false);
		vaction.setToolTipText("Vertical orientation");
		form.getToolBarManager().add(haction);
		form.getToolBarManager().add(vaction);
	}

	protected void registerPages(DetailsPart detailsPart) {
		detailsPart.setPageProvider(myDetailsPageProvider);
	}

	public void selectionChanged(SelectionChangedEvent event) {
		this.selectionEvent = event;
	}
}