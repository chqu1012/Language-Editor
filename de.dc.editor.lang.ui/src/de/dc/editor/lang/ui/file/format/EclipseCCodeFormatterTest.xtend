package de.dc.editor.lang.ui.file.format

class EclipseCCodeFormatterTest {
	def static void main(String[] args) {
		var EclipseCCodeFormatter test = new EclipseCCodeFormatter()
		val src = test.format('''
#include <iostream>                                    
int main(){  std::cout << "Hallo, du schöne Welt!" << std::endl; return 0;}
		''')
		
		println(src)
	}
}
