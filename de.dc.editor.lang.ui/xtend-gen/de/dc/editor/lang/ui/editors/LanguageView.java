package de.dc.editor.lang.ui.editors;

import de.dc.editor.lang.model.Color;
import de.dc.editor.lang.model.Template;
import de.dc.editor.lang.ui.editors.ILanguageConstants;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
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
import org.eclipse.swt.dnd.ByteArrayTransfer;
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
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class LanguageView extends ViewPart implements IMenuListener {
  private final static String ID = "de.dc.editor.lang.ui.LanguageView";
  
  private ComposedAdapterFactory adapterFactory;
  
  private AdapterFactoryEditingDomain editingDomain;
  
  private TreeViewer viewer;
  
  private IPropertySheetPage page;
  
  private AdapterFactoryContentProvider contentProvider;
  
  private Text text;
  
  @Override
  public void createPartControl(final Composite parent) {
    GridLayout _gridLayout = new GridLayout(1, false);
    parent.setLayout(_gridLayout);
    Button saveButton = new Button(parent, SWT.PUSH);
    GridData _gridData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
    saveButton.setLayoutData(_gridData);
    saveButton.setText("Save");
    saveButton.addSelectionListener(
      new SelectionAdapter() {
        @Override
        public void widgetSelected(final SelectionEvent e) {
          try {
            ResourceSet _resourceSet = LanguageView.this.editingDomain.getResourceSet();
            URI _createFileURI = URI.createFileURI(ILanguageConstants.MODEL_PATH);
            Resource _resource = _resourceSet.getResource(_createFileURI, 
              true);
            _resource.save(null);
          } catch (Throwable _e) {
            throw Exceptions.sneakyThrow(_e);
          }
        }
      });
    this.initializeEditingDomain();
    PatternFilter filter = new PatternFilter();
    FilteredTree tree = new FilteredTree(parent, ((SWT.MULTI | SWT.H_SCROLL) | SWT.V_SCROLL), filter, true);
    GridData _gridData_1 = new GridData(SWT.FILL, SWT.FILL, true, true);
    tree.setLayoutData(_gridData_1);
    TreeViewer _viewer = tree.getViewer();
    this.viewer = _viewer;
    AdapterFactoryContentProvider _adapterFactoryContentProvider = new AdapterFactoryContentProvider(this.adapterFactory);
    this.contentProvider = _adapterFactoryContentProvider;
    this.viewer.setContentProvider(this.contentProvider);
    AdapterFactoryLabelProvider _adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(this.adapterFactory);
    this.viewer.setLabelProvider(_adapterFactoryLabelProvider);
    final Procedure1<DoubleClickEvent> _function = (DoubleClickEvent event) -> {
      ISelection selection = this.viewer.getSelection();
      if ((selection instanceof IStructuredSelection)) {
        IStructuredSelection ss = ((IStructuredSelection) selection);
        Object _firstElement = ss.getFirstElement();
        if ((_firstElement instanceof Color)) {
          Object _firstElement_1 = ss.getFirstElement();
          Color color = ((Color) _firstElement_1);
          Shell shell = new Shell();
          ColorDialog dlg = new ColorDialog(shell);
          dlg.setText("Choose a Color");
          RGB rgb = dlg.open();
          if ((rgb != null)) {
            color.setR(rgb.red);
            color.setG(rgb.green);
            color.setB(rgb.blue);
          }
        }
      }
    };
    this.viewer.addDoubleClickListener(((IDoubleClickListener) new IDoubleClickListener() {
        public void doubleClick(DoubleClickEvent event) {
          _function.apply(event);
        }
    }));
    final Procedure1<SelectionChangedEvent> _function_1 = (SelectionChangedEvent event) -> {
      ISelection selection = this.viewer.getSelection();
      if ((selection instanceof IStructuredSelection)) {
        IStructuredSelection ss = ((IStructuredSelection) selection);
        Object _firstElement = ss.getFirstElement();
        if ((_firstElement instanceof Template)) {
          Object _firstElement_1 = ss.getFirstElement();
          String _value = ((Template) _firstElement_1).getValue();
          this.text.setText(_value);
        }
      }
    };
    this.viewer.addSelectionChangedListener(((ISelectionChangedListener) new ISelectionChangedListener() {
        public void selectionChanged(SelectionChangedEvent event) {
          _function_1.apply(event);
        }
    }));
    this.hookContextMenu(this.viewer);
    IViewSite _viewSite = this.getViewSite();
    _viewSite.setSelectionProvider(this.viewer);
    Composite composite = new Composite(parent, SWT.NONE);
    GridLayout _gridLayout_1 = new GridLayout(3, false);
    composite.setLayout(_gridLayout_1);
    Label lblTemplate = new Label(composite, SWT.NONE);
    lblTemplate.setText("Template");
    final Button btnApply = new Button(composite, SWT.NONE);
    btnApply.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(final SelectionEvent e) {
        ISelection selection = LanguageView.this.viewer.getSelection();
        if ((selection instanceof IStructuredSelection)) {
          IStructuredSelection ss = ((IStructuredSelection) selection);
          Object _firstElement = ss.getFirstElement();
          if ((_firstElement instanceof Template)) {
            Object _firstElement_1 = ss.getFirstElement();
            Template template = ((Template) _firstElement_1);
            String _text = LanguageView.this.text.getText();
            template.setValue(_text);
          }
          btnApply.setEnabled(false);
        }
      }
    });
    btnApply.setText("Apply");
    btnApply.setEnabled(false);
    Button btnVariable = new Button(composite, SWT.NONE);
    btnVariable.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(final SelectionEvent e) {
        int caretPosition = LanguageView.this.text.getCaretPosition();
        LanguageView.this.text.insert("${NAME}");
        LanguageView.this.text.forceFocus();
        LanguageView.this.text.setSelection((caretPosition + 2), (caretPosition + 6));
      }
    });
    btnVariable.setText("Variable");
    Text _text = new Text(parent, ((SWT.BORDER | SWT.V_SCROLL) | SWT.MULTI));
    this.text = _text;
    GridData gd_text = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
    gd_text.heightHint = 100;
    this.text.setLayoutData(gd_text);
    this.text.addKeyListener(new KeyListener() {
      @Override
      public void keyReleased(final KeyEvent e) {
        btnApply.setEnabled(true);
      }
      
      @Override
      public void keyPressed(final KeyEvent e) {
      }
    });
    if ((ILanguageConstants.MODEL_PATH != null)) {
      URI resourceURI = URI.createFileURI(ILanguageConstants.MODEL_PATH);
      ResourceSet _resourceSet = this.editingDomain.getResourceSet();
      _resourceSet.getResource(resourceURI, true);
      ResourceSet _resourceSet_1 = this.editingDomain.getResourceSet();
      this.viewer.setInput(_resourceSet_1);
      ResourceSet _resourceSet_2 = this.editingDomain.getResourceSet();
      EList<Resource> _resources = _resourceSet_2.getResources();
      Resource _get = _resources.get(0);
      StructuredSelection _structuredSelection = new StructuredSelection(_get);
      this.viewer.setSelection(_structuredSelection, true);
    }
  }
  
  protected void initializeEditingDomain() {
    ComposedAdapterFactory _composedAdapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
    this.adapterFactory = _composedAdapterFactory;
    ResourceItemProviderAdapterFactory _resourceItemProviderAdapterFactory = new ResourceItemProviderAdapterFactory();
    this.adapterFactory.addAdapterFactory(_resourceItemProviderAdapterFactory);
    ReflectiveItemProviderAdapterFactory _reflectiveItemProviderAdapterFactory = new ReflectiveItemProviderAdapterFactory();
    this.adapterFactory.addAdapterFactory(_reflectiveItemProviderAdapterFactory);
    BasicCommandStack commandStack = new BasicCommandStack();
    HashMap<Resource, Boolean> _hashMap = new HashMap<Resource, Boolean>();
    AdapterFactoryEditingDomain _adapterFactoryEditingDomain = new AdapterFactoryEditingDomain(this.adapterFactory, commandStack, _hashMap);
    this.editingDomain = _adapterFactoryEditingDomain;
  }
  
  private void hookContextMenu(final TreeViewer viewer) {
    MenuManager menuManager = new MenuManager();
    menuManager.setRemoveAllWhenShown(true);
    menuManager.addMenuListener(this);
    Tree _tree = viewer.getTree();
    Menu menu = menuManager.createContextMenu(_tree);
    Tree _tree_1 = viewer.getTree();
    _tree_1.setMenu(menu);
    int dndOperations = ((DND.DROP_COPY | DND.DROP_MOVE) | DND.DROP_LINK);
    LocalTransfer _instance = LocalTransfer.getInstance();
    LocalSelectionTransfer _transfer = LocalSelectionTransfer.getTransfer();
    FileTransfer _instance_1 = FileTransfer.getInstance();
    Transfer[] transfers = ((Transfer[]) ((Transfer[])Conversions.unwrapArray(Collections.<ByteArrayTransfer>unmodifiableList(CollectionLiterals.<ByteArrayTransfer>newArrayList(_instance, _transfer, _instance_1)), Transfer.class)));
    ViewerDragAdapter _viewerDragAdapter = new ViewerDragAdapter(viewer);
    viewer.addDragSupport(dndOperations, transfers, _viewerDragAdapter);
    EditingDomainViewerDropAdapter _editingDomainViewerDropAdapter = new EditingDomainViewerDropAdapter(this.editingDomain, viewer);
    viewer.addDropSupport(dndOperations, transfers, _editingDomainViewerDropAdapter);
  }
  
  @Override
  public void menuAboutToShow(final IMenuManager manager) {
    ISelection _selection = this.viewer.getSelection();
    IStructuredSelection ss = ((IStructuredSelection) _selection);
    Object firstElement = ss.getFirstElement();
    MenuManager childMenu = new MenuManager("New Child");
    manager.add(childMenu);
    Collection<?> newChildDescriptors = this.editingDomain.getNewChildDescriptors(firstElement, null);
    for (final Object descriptor : newChildDescriptors) {
      CreateChildAction _createChildAction = new CreateChildAction(this.editingDomain, ss, descriptor);
      childMenu.add(_createChildAction);
    }
  }
  
  @Override
  public Object getAdapter(final Class adapter) {
    boolean _equals = adapter.equals(IPropertySheetPage.class);
    if (_equals) {
      if ((this.page == null)) {
        IPropertySheetPage _propertySheetPage = this.getPropertySheetPage();
        this.page = _propertySheetPage;
      }
      return this.page;
    }
    return super.<Object>getAdapter(adapter);
  }
  
  public IPropertySheetPage getPropertySheetPage() {
    PropertySheetPage propertySheetPage = new ExtendedPropertySheetPage(this.editingDomain);
    propertySheetPage.setPropertySourceProvider(this.contentProvider);
    return propertySheetPage;
  }
  
  @Override
  public void setFocus() {
  }
}
