/** 
 */
package de.dc.editor.lang.ui.editors

import java.util.ArrayList
import java.util.Collection
import org.eclipse.emf.edit.domain.EditingDomain
import org.eclipse.emf.edit.domain.IEditingDomainProvider
import org.eclipse.emf.edit.ui.action.ControlAction
import org.eclipse.emf.edit.ui.action.CreateChildAction
import org.eclipse.emf.edit.ui.action.CreateSiblingAction
import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor
import org.eclipse.emf.edit.ui.action.LoadResourceAction
import org.eclipse.emf.edit.ui.action.ValidateAction
import org.eclipse.jface.action.ActionContributionItem
import org.eclipse.jface.action.IAction
import org.eclipse.jface.action.IContributionManager
import org.eclipse.jface.action.IMenuListener
import org.eclipse.jface.action.IMenuManager
import org.eclipse.jface.action.MenuManager
import org.eclipse.jface.action.Separator
import org.eclipse.jface.action.SubContributionItem
import org.eclipse.jface.viewers.ISelection
import org.eclipse.jface.viewers.ISelectionChangedListener
import org.eclipse.jface.viewers.ISelectionProvider
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.jface.viewers.SelectionChangedEvent
import org.eclipse.ui.IEditorPart

class ModelActionBarContributor extends EditingDomainActionBarContributor implements ISelectionChangedListener {
	protected IEditorPart activeEditorPart
	protected ISelectionProvider selectionProvider
	protected Collection<IAction> createChildActions
	protected IMenuManager createChildMenuManager
	protected Collection<IAction> createSiblingActions
	protected IMenuManager createSiblingMenuManager
	new() {
		super(ADDITIONS_LAST_STYLE)
		loadResourceAction = new LoadResourceAction()
		validateAction = new ValidateAction()
		controlAction = new ControlAction()
	}
	override void contributeToMenu(IMenuManager menuManager) {
		super.contributeToMenu(menuManager)
		var IMenuManager submenuManager = new MenuManager("NEU", "de.dc.editor.lang.modelMenuID")
		menuManager.insertAfter("additions", submenuManager)
		submenuManager.add(new Separator("settings"))
		submenuManager.add(new Separator("actions"))
		submenuManager.add(new Separator("additions"))
		submenuManager.add(new Separator("additions-end"))
		// Prepare for CreateChild item addition or removal.
		//
		createChildMenuManager = new MenuManager("Create Children")
		submenuManager.insertBefore("additions", createChildMenuManager)
		// Prepare for CreateSibling item addition or removal.
		//
		createSiblingMenuManager = new MenuManager("Create Siblings")
		submenuManager.insertBefore("additions", createSiblingMenuManager)
		// Force an update because Eclipse hides empty menus now.
		//
		submenuManager.addMenuListener(([IMenuManager menu|menu.updateAll(true)] as IMenuListener))
		addGlobalActions(submenuManager)
	}

	override void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part)
		activeEditorPart = part
		if (selectionProvider !== null) {
			selectionProvider.removeSelectionChangedListener(this)
		}
		if (part === null) {
			selectionProvider = null
		} else {
			selectionProvider = part.getSite().getSelectionProvider()
			selectionProvider.addSelectionChangedListener(this)
			if (selectionProvider.getSelection() !== null) {
				selectionChanged(new SelectionChangedEvent(selectionProvider, selectionProvider.getSelection()))
			}
		}
	}

	override void selectionChanged(SelectionChangedEvent event) {
		if (createChildMenuManager !== null) {
			depopulateManager(createChildMenuManager, createChildActions)
		}
		if (createSiblingMenuManager !== null) {
			depopulateManager(createSiblingMenuManager, createSiblingActions)
		}
		var Collection<?> newChildDescriptors = null
		var Collection<?> newSiblingDescriptors = null
		var ISelection selection = event.getSelection()
		if (selection instanceof IStructuredSelection && ((selection as IStructuredSelection)).size() === 1) {
			var Object object = ((selection as IStructuredSelection)).getFirstElement()
			var EditingDomain domain = ((activeEditorPart as IEditingDomainProvider)).getEditingDomain()
			newChildDescriptors = domain.getNewChildDescriptors(object, null)
			newSiblingDescriptors = domain.getNewChildDescriptors(null, object)
		}
		createChildActions = generateCreateChildActions(newChildDescriptors, selection)
		createSiblingActions = generateCreateSiblingActions(newSiblingDescriptors, selection)
		if (createChildMenuManager !== null) {
			populateManager(createChildMenuManager, createChildActions, null)
			createChildMenuManager.update(true)
		}
		if (createSiblingMenuManager !== null) {
			populateManager(createSiblingMenuManager, createSiblingActions, null)
			createSiblingMenuManager.update(true)
		}
	}

	def protected Collection<IAction> generateCreateChildActions(Collection<?> descriptors, ISelection selection) {
		var Collection<IAction> actions = new ArrayList<IAction>()
		if (descriptors !== null) {
			for (Object descriptor : descriptors) {
				actions.add(new CreateChildAction(activeEditorPart, selection, descriptor))
			}
		}
		return actions
	}

	def protected Collection<IAction> generateCreateSiblingActions(Collection<?> descriptors, ISelection selection) {
		var Collection<IAction> actions = new ArrayList<IAction>()
		if (descriptors !== null) {
			for (Object descriptor : descriptors) {
				actions.add(new CreateSiblingAction(activeEditorPart, selection, descriptor))
			}
		}
		return actions
	}

	def protected void populateManager(IContributionManager manager, Collection<? extends IAction> actions,
		String contributionID) {
		if (actions !== null) {
			for (IAction action : actions) {
				if (contributionID !== null) {
					manager.insertBefore(contributionID, action)
				} else {
					manager.add(action)
				}
			}
		}
	}

	def protected void depopulateManager(IContributionManager manager, Collection<? extends IAction> actions) {
		if (actions !== null) {
			var items = manager.items
			for (var int i = 0; i < items.length; i++) {
				var contributionItem =items.get(i)
				while (contributionItem instanceof SubContributionItem) {
					contributionItem = ((contributionItem as SubContributionItem)).getInnerItem()
				}
				if (contributionItem instanceof ActionContributionItem) {
					var action = ((contributionItem as ActionContributionItem)).getAction()
					if (actions.contains(action)) {
						manager.remove(contributionItem)
					}
				}
			}
		}
	}

	override void menuAboutToShow(IMenuManager menuManager) {
		super.menuAboutToShow(menuManager)
		var MenuManager submenuManager = null
		submenuManager = new MenuManager("Create Child")
		populateManager(submenuManager, createChildActions, null)
		menuManager.insertBefore("edit", submenuManager)
		submenuManager = new MenuManager("Create Child Item")
		populateManager(submenuManager, createSiblingActions, null)
		menuManager.insertBefore("edit", submenuManager)
	}

	override removeAllReferencesOnDelete() {
		true
	}
}
