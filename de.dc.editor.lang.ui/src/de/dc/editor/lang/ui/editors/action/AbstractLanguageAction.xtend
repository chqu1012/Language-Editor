package de.dc.editor.lang.ui.editors.action

import de.dc.editor.lang.model.LanguageDefinition
import de.dc.editor.lang.ui.editors.LanguageDefinitionProvider
import de.dc.editor.lang.ui.editors.LanguageEditor
import org.eclipse.jface.action.IAction
import org.eclipse.jface.text.ITextSelection
import org.eclipse.jface.viewers.ISelection
import org.eclipse.ui.IObjectActionDelegate
import org.eclipse.ui.IWorkbenchPart
import org.eclipse.ui.PlatformUI

abstract class AbstractLanguageAction implements IObjectActionDelegate {

	override run(IAction action) {
		var editorPart = activeEditor
		if (editorPart instanceof LanguageEditor) {
			var editor = editorPart as LanguageEditor
			var fileExtension = editor.fileExtension
			val model = LanguageDefinitionProvider.instance.getDefinitionByExtension(fileExtension)
			val selectionProvider = editorPart.selectionProvider
			if (selectionProvider != null) {
				val iSelection = selectionProvider.selection
				if (!iSelection.isEmpty()) {
					val selectedText = (iSelection as ITextSelection).text
					model.runAction(selectedText)
				}
			}
		}
	}

	def abstract void runAction(LanguageDefinition definition, String selectedText)

	def getActiveEditor() {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()
	}

	override selectionChanged(IAction action, ISelection selection) {
	}

	override setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

}
