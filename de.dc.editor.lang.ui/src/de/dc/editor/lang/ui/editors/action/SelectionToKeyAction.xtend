package de.dc.editor.lang.ui.editors.action

import de.dc.editor.lang.model.KeywordGroup
import de.dc.editor.lang.model.LanguageDefinition
import de.dc.editor.lang.model.ModelFactory
import de.dc.editor.lang.ui.editors.LanguageDefinitionProvider
import org.eclipse.jface.viewers.LabelProvider
import org.eclipse.swt.widgets.Shell
import org.eclipse.ui.dialogs.ElementListSelectionDialog

class SelectionToKeyAction extends AbstractLanguageAction {
	
	override runAction(LanguageDefinition definition, String selectedText) {
		val dialog = new ElementListSelectionDialog(new Shell, new LabelProvider {
			override getText(Object element) {
				val c = element as KeywordGroup
				c.name
			}
		})
		dialog.elements = definition.keywordGroups
		val returnCode = dialog.open
		if (returnCode == 0) {
			val proposal = dialog.firstResult as KeywordGroup
			proposal.keyList += ModelFactory.eINSTANCE.createKey => [
				name = selectedText
				value = selectedText
			]
			LanguageDefinitionProvider.instance.storeDefinition(definition)
		}
	}
}
