package de.dc.editor.lang.ui.editors

import java.util.ArrayList
import java.util.List
import org.eclipse.core.internal.resources.File
import org.eclipse.jdt.internal.ui.packageview.PackageExplorerPart
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.IFileEditorMapping
import org.eclipse.ui.PlatformUI
import org.eclipse.ui.editors.text.TextEditor
import org.eclipse.ui.internal.registry.EditorDescriptor
import org.eclipse.ui.internal.registry.EditorRegistry
import org.eclipse.ui.internal.registry.FileEditorMapping

class LanguageEditor extends TextEditor{

	new() {
		val explorer = PlatformUI.workbench.activeWorkbenchWindow.activePage.findView("org.eclipse.jdt.ui.PackageExplorer") as PackageExplorerPart
		val firstElement = explorer.treeViewer.structuredSelection.firstElement
		if (firstElement instanceof File) {
			val file = firstElement as File
			
			val ext = file.fileExtension;
    		val editorId = "de.dc.editor.lang.ui.editors.LangEditor";
    
			val editorReg = PlatformUI.getWorkbench().getEditorRegistry() as EditorRegistry
		    val editor =  editorReg.findEditor(editorId) as EditorDescriptor
		    val mapping = new FileEditorMapping(ext)
		    mapping.addEditor(editor)
		    mapping.setDefaultEditor(editor)
		
		    val mappings = editorReg.getFileEditorMappings()
		    val extExist = mappings.filter[it.extension==ext].size>0
			val List<IFileEditorMapping> newMappings = new ArrayList		    
			if(!extExist){
				newMappings+=mappings
				newMappings+=mapping
		    }
		    editorReg.setFileEditorMappings(newMappings.toArray(#[]))
		}
		setSourceViewerConfiguration(new LanguageConfiguration())
	}
	
	override createPartControl(Composite parent) {
		super.createPartControl(parent)
	}
	
	
}
