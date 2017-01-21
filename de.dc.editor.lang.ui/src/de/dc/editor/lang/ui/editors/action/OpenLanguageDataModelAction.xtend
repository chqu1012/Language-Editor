package de.dc.editor.lang.ui.editors.action

import org.eclipse.emf.edit.ui.util.EditUIUtil
import org.eclipse.jface.action.IAction
import org.eclipse.jface.viewers.ISelection
import org.eclipse.ui.IEditorPart
import org.eclipse.ui.IObjectActionDelegate
import org.eclipse.ui.IWorkbenchPart
import org.eclipse.ui.PlatformUI
import de.dc.editor.lang.model.LanguageDefinition
import de.dc.editor.lang.ui.editors.LanguageDefinitionProvider
import de.dc.editor.lang.ui.editors.LanguageEditor

class OpenLanguageDataModelAction implements IObjectActionDelegate {
	override void run(IAction action) {
		try {
			var IEditorPart editorPart = getActiveEditor()
			if (editorPart instanceof LanguageEditor) {
				var LanguageEditor editor = (editorPart as LanguageEditor)
				var String fileExtension = editor.getFileExtension()
				var LanguageDefinition model = LanguageDefinitionProvider.instance.
					getDefinitionByExtension(fileExtension)
				EditUIUtil.openEditor(model)
			}
		} catch (Exception e) {
			e.printStackTrace()
		}

	}

	def private IEditorPart getActiveEditor() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()
	}

	override void selectionChanged(IAction action, ISelection selection) {
	}

	override void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
}
