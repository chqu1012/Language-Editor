package de.dc.editor.lang.ui.file.format;

import de.dc.editor.lang.ui.file.format.EclipseCCodeFormatter;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class EclipseCCodeFormatterTest {
  public static void main(final String[] args) {
    EclipseCCodeFormatter test = new EclipseCCodeFormatter();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include <iostream>                                    ");
    _builder.newLine();
    _builder.append("int main(){  std::cout << \"Hallo, du schöne Welt!\" << std::endl; return 0;}");
    _builder.newLine();
    final String src = test.format(_builder.toString());
    InputOutput.<String>println(src);
  }
}
