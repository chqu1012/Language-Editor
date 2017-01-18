package de.dc.editor.lang.ui.editors.action;

import org.eclipse.emf.edit.ui.util.EditUIUtil;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import de.dc.editor.lang.model.LanguageDefinition;
import de.dc.editor.lang.ui.editors.LanguageDefinitionProvider;
import de.dc.editor.lang.ui.editors.LanguageEditor;

public class OpenLanguageDataModelAction implements IObjectActionDelegate {

	@Override
	public void run(IAction action) {
		try {
			IEditorPart editorPart = getActiveEditor();
			if (editorPart instanceof LanguageEditor) {
				LanguageEditor editor = (LanguageEditor)editorPart;
				String fileExtension = editor.getFileExtension();
				LanguageDefinition model = LanguageDefinitionProvider.instance.getDefinitionByExtension(fileExtension);
				EditUIUtil.openEditor(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private IEditorPart getActiveEditor() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
}
