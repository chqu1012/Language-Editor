package de.dc.editor.lang.ui.editors.action

import de.dc.editor.lang.ui.editors.LanguageDefinitionProvider
import de.dc.editor.lang.ui.editors.LanguageEditor
import org.eclipse.jface.action.IAction
import org.eclipse.jface.text.ITextSelection
import org.eclipse.jface.viewers.ISelection
import org.eclipse.swt.widgets.Display
import org.eclipse.ui.IObjectActionDelegate
import org.eclipse.ui.IWorkbenchPart
import org.eclipse.ui.PlatformUI
import org.eclipse.ui.dialogs.ElementListSelectionDialog
import org.eclipse.jface.viewers.LabelProvider
import org.eclipse.swt.widgets.Shell
import de.dc.editor.lang.model.ContentProposal
import de.dc.editor.lang.model.ModelFactory

class SelectionToContentProposalAction implements IObjectActionDelegate {
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
					val dialog = new ElementListSelectionDialog(new Shell, new LabelProvider {
						override getText(Object element) {
							val c = element as ContentProposal
							c.name
						}
					})
					dialog.elements = model.contentProposals
					val returnCode = dialog.open
					if(returnCode==0){
						val proposal = dialog.firstResult as ContentProposal
						proposal.contents+= ModelFactory.eINSTANCE.createTemplate=>[
							name = selectedText
							pattern = selectedText
						]
						LanguageDefinitionProvider.instance.storeDefinition(model)
					}
				}
			}
		}
	}

	def getActiveEditor() {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()
	}

	override selectionChanged(IAction action, ISelection selection) {
	}

	override setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
}
