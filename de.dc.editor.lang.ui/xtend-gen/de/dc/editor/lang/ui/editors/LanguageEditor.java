package de.dc.editor.lang.ui.editors;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import de.dc.editor.lang.ui.editors.LanguageConfiguration;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.internal.resources.File;
import org.eclipse.jdt.internal.ui.packageview.PackageExplorerPart;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IFileEditorMapping;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.internal.registry.EditorDescriptor;
import org.eclipse.ui.internal.registry.EditorRegistry;
import org.eclipse.ui.internal.registry.FileEditorMapping;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class LanguageEditor extends TextEditor {
  public LanguageEditor() {
    IWorkbench _workbench = PlatformUI.getWorkbench();
    IWorkbenchWindow _activeWorkbenchWindow = _workbench.getActiveWorkbenchWindow();
    IWorkbenchPage _activePage = _activeWorkbenchWindow.getActivePage();
    IViewPart _findView = _activePage.findView("org.eclipse.jdt.ui.PackageExplorer");
    final PackageExplorerPart explorer = ((PackageExplorerPart) _findView);
    TreeViewer _treeViewer = explorer.getTreeViewer();
    ITreeSelection _structuredSelection = _treeViewer.getStructuredSelection();
    final Object firstElement = _structuredSelection.getFirstElement();
    if ((firstElement instanceof File)) {
      final File file = ((File) firstElement);
      final String ext = file.getFileExtension();
      final String editorId = "de.dc.editor.lang.ui.editors.LangEditor";
      IWorkbench _workbench_1 = PlatformUI.getWorkbench();
      IEditorRegistry _editorRegistry = _workbench_1.getEditorRegistry();
      final EditorRegistry editorReg = ((EditorRegistry) _editorRegistry);
      IEditorDescriptor _findEditor = editorReg.findEditor(editorId);
      final EditorDescriptor editor = ((EditorDescriptor) _findEditor);
      final FileEditorMapping mapping = new FileEditorMapping(ext);
      mapping.addEditor(editor);
      mapping.setDefaultEditor(editor);
      final IFileEditorMapping[] mappings = editorReg.getFileEditorMappings();
      final Function1<IFileEditorMapping, Boolean> _function = (IFileEditorMapping it) -> {
        String _extension = it.getExtension();
        return Boolean.valueOf(Objects.equal(_extension, ext));
      };
      Iterable<IFileEditorMapping> _filter = IterableExtensions.<IFileEditorMapping>filter(((Iterable<IFileEditorMapping>)Conversions.doWrapArray(mappings)), _function);
      int _size = IterableExtensions.size(_filter);
      final boolean extExist = (_size > 0);
      final List<IFileEditorMapping> newMappings = new ArrayList<IFileEditorMapping>();
      if ((!extExist)) {
        Iterables.<IFileEditorMapping>addAll(newMappings, ((Iterable<? extends IFileEditorMapping>)Conversions.doWrapArray(mappings)));
        newMappings.add(mapping);
      }
      FileEditorMapping[] _array = newMappings.<FileEditorMapping>toArray(new FileEditorMapping[] {});
      editorReg.setFileEditorMappings(_array);
    }
    LanguageConfiguration _languageConfiguration = new LanguageConfiguration();
    this.setSourceViewerConfiguration(_languageConfiguration);
  }
  
  @Override
  public void createPartControl(final Composite parent) {
    super.createPartControl(parent);
  }
}
