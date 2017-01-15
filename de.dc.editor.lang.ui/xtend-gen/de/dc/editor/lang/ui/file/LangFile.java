package de.dc.editor.lang.ui.file;

import de.dc.editor.lang.model.LanguageDefinition;
import de.dc.editor.lang.model.ModelFactory;
import de.dc.editor.lang.model.ModelPackage;
import de.dc.emf.util.XmiFile;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class LangFile extends XmiFile<LanguageDefinition> {
  @Override
  public String packageUri() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(ModelPackage.eNS_URI, "");
    return _builder.toString();
  }
  
  @Override
  public String extensions() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(ModelPackage.eNS_PREFIX, "");
    return _builder.toString();
  }
  
  @Override
  public EFactory factory() {
    return ModelFactory.eINSTANCE;
  }
  
  @Override
  public EPackage modelPackage() {
    return ModelPackage.eINSTANCE;
  }
}
