package de.dc.editor.lang.ui.editors.custom

import org.eclipse.ui.editors.text.TextEditor

class LangEditor extends TextEditor {
	
	new() {
		sourceViewerConfiguration=new LangConfiguration
//		documentProvider=new StorageDocumentProvider
	}
	
//	override createPartControl(Composite parent) {
//		val layout = new GridLayout(1, false)=>[
//			marginWidth=0
//			marginHeight=0
//		]
//		parent.layout = layout
//		val toolbar = new EditorToolbar(parent )
//		toolbar.layoutData = new GridData(SWT::FILL, SWT::FILL, true, false)
//		val composite = new Composite(parent, 0)
//		composite.layout = new FillLayout
//		composite.layoutData = new GridData(SWT::FILL, SWT::FILL, true, true)
//		super.createPartControl(composite)
//	}
	
}
