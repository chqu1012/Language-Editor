package de.dc.editor.lang.ui.visitor

import de.dc.editor.lang.model.Color
import de.dc.editor.lang.model.LanguageDefinition
import de.dc.editor.lang.model.util.ModelSwitch
import java.util.ArrayList
import java.util.List
import org.eclipse.jface.dialogs.MessageDialog
import org.eclipse.swt.graphics.RGB
import org.eclipse.swt.widgets.ColorDialog
import org.eclipse.swt.widgets.Shell
import org.eclipse.ui.IFileEditorMapping
import org.eclipse.ui.PlatformUI
import org.eclipse.ui.internal.registry.EditorDescriptor
import org.eclipse.ui.internal.registry.EditorRegistry
import org.eclipse.ui.internal.registry.FileEditorMapping
import de.dc.editor.lang.ui.editors.LanguageEditor

class DoubleClickSwitch extends ModelSwitch<Object> {
	
	override caseColor(Color c) {
		val shell = new Shell()
		val dlg = new ColorDialog(shell)=>[
			text = "Choose a Color"
			RGB = new RGB(c.r, c.g, c.b)
		]
        val rgb = dlg.open
        if (rgb != null) {
        	c.r=rgb.red
        	c.g=rgb.green
        	c.b=rgb.blue
        }
        c
	}
	
	override caseLanguageDefinition(LanguageDefinition object) {
		val editorReg = PlatformUI.getWorkbench().getEditorRegistry() as EditorRegistry
		val message = 'Do you want to registrate the file extension '+object.fileExtension+'?'
		val mappings = editorReg.getFileEditorMappings()
		
		val hasExtension = mappings.filter[it.extension==object.fileExtension].size>0
		if(!hasExtension){
			val registrate=MessageDialog.openQuestion(new Shell, "File Association", message)
			if(registrate){
		   		val editor =  editorReg.findEditor("de.dc.editor.lang.ui.editors.LangEditor") as EditorDescriptor
			    val mapping = new FileEditorMapping(object.fileExtension)
			    mapping.addEditor(editor)
			    mapping.setDefaultEditor(editor)
			
			    val extExist = mappings.filter[it.extension==object.fileExtension].size>0
				val List<IFileEditorMapping> newMappings = new ArrayList		    
				if(!extExist){
					newMappings+=mappings
					newMappings+=mapping
			    }
			    editorReg.setFileEditorMappings(newMappings.toArray(#[]))
			}
		}
		object
	}
	
}
