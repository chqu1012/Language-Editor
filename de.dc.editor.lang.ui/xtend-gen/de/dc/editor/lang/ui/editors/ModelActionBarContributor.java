package de.dc.editor.lang.ui.editors;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.action.ControlAction;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.emf.edit.ui.action.CreateSiblingAction;
import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor;
import org.eclipse.emf.edit.ui.action.LoadResourceAction;
import org.eclipse.emf.edit.ui.action.ValidateAction;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.SubContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class ModelActionBarContributor extends EditingDomainActionBarContributor implements ISelectionChangedListener {
  protected IEditorPart activeEditorPart;
  
  protected ISelectionProvider selectionProvider;
  
  protected Collection<IAction> createChildActions;
  
  protected IMenuManager createChildMenuManager;
  
  protected Collection<IAction> createSiblingActions;
  
  protected IMenuManager createSiblingMenuManager;
  
  public ModelActionBarContributor() {
    super(EditingDomainActionBarContributor.ADDITIONS_LAST_STYLE);
    LoadResourceAction _loadResourceAction = new LoadResourceAction();
    this.loadResourceAction = _loadResourceAction;
    ValidateAction _validateAction = new ValidateAction();
    this.validateAction = _validateAction;
    ControlAction _controlAction = new ControlAction();
    this.controlAction = _controlAction;
  }
  
  @Override
  public void contributeToMenu(final IMenuManager menuManager) {
    super.contributeToMenu(menuManager);
    IMenuManager submenuManager = new MenuManager("NEU", "de.dc.editor.lang.modelMenuID");
    menuManager.insertAfter("additions", submenuManager);
    Separator _separator = new Separator("settings");
    submenuManager.add(_separator);
    Separator _separator_1 = new Separator("actions");
    submenuManager.add(_separator_1);
    Separator _separator_2 = new Separator("additions");
    submenuManager.add(_separator_2);
    Separator _separator_3 = new Separator("additions-end");
    submenuManager.add(_separator_3);
    MenuManager _menuManager = new MenuManager("Create Children");
    this.createChildMenuManager = _menuManager;
    submenuManager.insertBefore("additions", this.createChildMenuManager);
    MenuManager _menuManager_1 = new MenuManager("Create Siblings");
    this.createSiblingMenuManager = _menuManager_1;
    submenuManager.insertBefore("additions", this.createSiblingMenuManager);
    final Procedure1<IMenuManager> _function = (IMenuManager menu) -> {
      menu.updateAll(true);
    };
    submenuManager.addMenuListener(((IMenuListener) new IMenuListener() {
        public void menuAboutToShow(IMenuManager manager) {
          _function.apply(manager);
        }
    }));
    this.addGlobalActions(submenuManager);
  }
  
  @Override
  public void setActiveEditor(final IEditorPart part) {
    super.setActiveEditor(part);
    this.activeEditorPart = part;
    if ((this.selectionProvider != null)) {
      this.selectionProvider.removeSelectionChangedListener(this);
    }
    if ((part == null)) {
      this.selectionProvider = null;
    } else {
      IWorkbenchPartSite _site = part.getSite();
      ISelectionProvider _selectionProvider = _site.getSelectionProvider();
      this.selectionProvider = _selectionProvider;
      this.selectionProvider.addSelectionChangedListener(this);
      ISelection _selection = this.selectionProvider.getSelection();
      boolean _tripleNotEquals = (_selection != null);
      if (_tripleNotEquals) {
        ISelection _selection_1 = this.selectionProvider.getSelection();
        SelectionChangedEvent _selectionChangedEvent = new SelectionChangedEvent(this.selectionProvider, _selection_1);
        this.selectionChanged(_selectionChangedEvent);
      }
    }
  }
  
  @Override
  public void selectionChanged(final SelectionChangedEvent event) {
    if ((this.createChildMenuManager != null)) {
      this.depopulateManager(this.createChildMenuManager, this.createChildActions);
    }
    if ((this.createSiblingMenuManager != null)) {
      this.depopulateManager(this.createSiblingMenuManager, this.createSiblingActions);
    }
    Collection<?> newChildDescriptors = null;
    Collection<?> newSiblingDescriptors = null;
    ISelection selection = event.getSelection();
    if (((selection instanceof IStructuredSelection) && (((IStructuredSelection) selection).size() == 1))) {
      Object object = ((IStructuredSelection) selection).getFirstElement();
      EditingDomain domain = ((IEditingDomainProvider) this.activeEditorPart).getEditingDomain();
      Collection<?> _newChildDescriptors = domain.getNewChildDescriptors(object, null);
      newChildDescriptors = _newChildDescriptors;
      Collection<?> _newChildDescriptors_1 = domain.getNewChildDescriptors(null, object);
      newSiblingDescriptors = _newChildDescriptors_1;
    }
    Collection<IAction> _generateCreateChildActions = this.generateCreateChildActions(newChildDescriptors, selection);
    this.createChildActions = _generateCreateChildActions;
    Collection<IAction> _generateCreateSiblingActions = this.generateCreateSiblingActions(newSiblingDescriptors, selection);
    this.createSiblingActions = _generateCreateSiblingActions;
    if ((this.createChildMenuManager != null)) {
      this.populateManager(this.createChildMenuManager, this.createChildActions, null);
      this.createChildMenuManager.update(true);
    }
    if ((this.createSiblingMenuManager != null)) {
      this.populateManager(this.createSiblingMenuManager, this.createSiblingActions, null);
      this.createSiblingMenuManager.update(true);
    }
  }
  
  protected Collection<IAction> generateCreateChildActions(final Collection<?> descriptors, final ISelection selection) {
    Collection<IAction> actions = new ArrayList<IAction>();
    if ((descriptors != null)) {
      for (final Object descriptor : descriptors) {
        CreateChildAction _createChildAction = new CreateChildAction(this.activeEditorPart, selection, descriptor);
        actions.add(_createChildAction);
      }
    }
    return actions;
  }
  
  protected Collection<IAction> generateCreateSiblingActions(final Collection<?> descriptors, final ISelection selection) {
    Collection<IAction> actions = new ArrayList<IAction>();
    if ((descriptors != null)) {
      for (final Object descriptor : descriptors) {
        CreateSiblingAction _createSiblingAction = new CreateSiblingAction(this.activeEditorPart, selection, descriptor);
        actions.add(_createSiblingAction);
      }
    }
    return actions;
  }
  
  protected void populateManager(final IContributionManager manager, final Collection<? extends IAction> actions, final String contributionID) {
    if ((actions != null)) {
      for (final IAction action : actions) {
        if ((contributionID != null)) {
          manager.insertBefore(contributionID, action);
        } else {
          manager.add(action);
        }
      }
    }
  }
  
  protected void depopulateManager(final IContributionManager manager, final Collection<? extends IAction> actions) {
    if ((actions != null)) {
      IContributionItem[] items = manager.getItems();
      for (int i = 0; (i < items.length); i++) {
        {
          IContributionItem contributionItem = items[i];
          while ((contributionItem instanceof SubContributionItem)) {
            IContributionItem _innerItem = ((SubContributionItem) contributionItem).getInnerItem();
            contributionItem = _innerItem;
          }
          if ((contributionItem instanceof ActionContributionItem)) {
            IAction action = ((ActionContributionItem) contributionItem).getAction();
            boolean _contains = actions.contains(action);
            if (_contains) {
              manager.remove(contributionItem);
            }
          }
        }
      }
    }
  }
  
  @Override
  public void menuAboutToShow(final IMenuManager menuManager) {
    super.menuAboutToShow(menuManager);
    MenuManager submenuManager = null;
    MenuManager _menuManager = new MenuManager("Create Child");
    submenuManager = _menuManager;
    this.populateManager(submenuManager, this.createChildActions, null);
    menuManager.insertBefore("edit", submenuManager);
    MenuManager _menuManager_1 = new MenuManager("Create Child Item");
    submenuManager = _menuManager_1;
    this.populateManager(submenuManager, this.createSiblingActions, null);
    menuManager.insertBefore("edit", submenuManager);
  }
  
  @Override
  public boolean removeAllReferencesOnDelete() {
    return true;
  }
}
