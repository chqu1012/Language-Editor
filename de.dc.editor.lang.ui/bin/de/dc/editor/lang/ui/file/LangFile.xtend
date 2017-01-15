package de.dc.editor.lang.ui.file

import de.dc.emf.util.XmiFile
import de.dc.editor.lang.model.LanguageDefinition
import de.dc.editor.lang.model.ModelPackage
import de.dc.editor.lang.model.ModelFactory

class LangFile extends XmiFile<LanguageDefinition>{
	
	override packageUri()'''«ModelPackage.eNS_URI»'''
	override extensions()'''«ModelPackage.eNS_PREFIX»'''
	override factory(){ModelFactory.eINSTANCE}
	override modelPackage() {ModelPackage.eINSTANCE}
	
}