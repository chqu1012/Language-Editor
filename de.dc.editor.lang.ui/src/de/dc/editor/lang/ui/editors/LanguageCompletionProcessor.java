package de.dc.editor.lang.ui.editors;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jface.text.templates.DocumentTemplateContext;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.dc.editor.lang.model.Content;
import de.dc.editor.lang.model.ContentProposal;
import de.dc.editor.lang.model.Function;
import de.dc.editor.lang.model.LanguageDefinition;
import de.dc.editor.lang.model.Token;
import de.dc.editor.lang.ui.file.LangFile;

public class LanguageCompletionProcessor implements IContentAssistProcessor {

	private static final String CONTEXT_ID = "language_variables"; //$NON-NLS-1$
	private final TemplateContextType fContextType = new TemplateContextType(CONTEXT_ID, "Common Language Variables"); //$NON-NLS-1$
	private final TemplateContextType fKeywordContextType = new TemplateContextType("language_keywords", //$NON-NLS-1$
			"Language Keywords");
	private final TemplateContextType fFunctionContextType = new TemplateContextType("functions",
			"Language Functions");

	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		IDocument document = viewer.getDocument();
		Region region = new Region(offset, 0);

		TemplateContext templateContext = new DocumentTemplateContext(fContextType, document, offset, 0);
		TemplateContext keywordContext = new DocumentTemplateContext(fKeywordContextType, document, offset, 0);
		TemplateContext functionContext = new DocumentTemplateContext(fFunctionContextType, document, offset, 0);

		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();

		try {
			getVariableTemplateProposals(templateContext, region, proposals);
			getKeywordTemplateProposals(keywordContext, region, proposals);
			getAddTaskTemplateProposals(templateContext, region, proposals);
			getFunctionTemplateProposals(functionContext, region, proposals);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (ICompletionProposal[]) proposals.toArray(new ICompletionProposal[proposals.size()]);
	}

	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		return null;
	}

	private Template generateVariableTemplate(String name, String description) {

		return new Template(name, description, CONTEXT_ID, name + " = \"${" + name.toLowerCase() + "}\"", false);
	}

	private void getAddTaskTemplateProposals(TemplateContext templateContext, Region region,
			List<ICompletionProposal> p) throws FileNotFoundException, IOException {
		LangFile file = new LangFile();
		LanguageDefinition model = file.load(ILanguageConstants.MODEL_PATH);
		for (ContentProposal prop : model.getContentProposals()) {
			Collections.sort(prop.getContents(), new Comparator<Content>() {
				@Override
				public int compare(Content o1, Content o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			for (Content content : prop.getContents()) {
				if (content instanceof de.dc.editor.lang.model.Template) {
						String name = content.getName();
						String descr = content.getDescription();
						String pattern = content.getPattern();
						
						p.add(new TemplateProposal(new Template(name, descr, CONTEXT_ID, pattern, false), templateContext, region,
								null));
				}
			}
		}


	}

	public char[] getCompletionProposalAutoActivationCharacters() {
		return null;
	}

	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

	public String getErrorMessage() {
		return null;
	}

	private void getFunctionTemplateProposals(TemplateContext templateContext, Region region,
			List<ICompletionProposal> p) throws FileNotFoundException, IOException {
		LangFile file = new LangFile();
		LanguageDefinition model = file.load(ILanguageConstants.MODEL_PATH);
		for (ContentProposal prop : model.getContentProposals()) {
			ECollections.sort(prop.getContents(), new Comparator<Content>() {
				@Override
				public int compare(Content o1, Content o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			Image img = PlatformUI.getWorkbench().getSharedImages().getImage(prop.getImage().getName());
			for (Content content : prop.getContents()) {
				if (content instanceof Function) {
					String name = content.getName();
					String descr = content.getDescription();
					String pattern = content.getPattern();
					p.add(new TemplateProposal(new Template(name, descr, CONTEXT_ID,
							pattern, false), templateContext, region, img));
				}
			}
		}

	}

	private void getKeywordTemplateProposals(TemplateContext templateContext, Region region, List<ICompletionProposal> proposals) throws FileNotFoundException, IOException {
		LangFile file = new LangFile();
		LanguageDefinition model = file.load(ILanguageConstants.MODEL_PATH);
		for (ContentProposal prop : model.getContentProposals()) {
			ECollections.sort(prop.getContents(), new Comparator<Content>() {
				@Override
				public int compare(Content o1, Content o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			Image img = PlatformUI.getWorkbench().getSharedImages().getImage(prop.getImage().getName());
			for (Content content : prop.getContents()) {
				if (content instanceof Token) {
					String name = content.getName();
					String descr = content.getDescription();
					String pattern = content.getPattern();
					
					proposals.add(new TemplateProposal(
							new Template(name, descr, CONTEXT_ID, pattern, false),
							templateContext, region, img));
				}
			}
		}
	}

	private void getVariableTemplateProposals(TemplateContext templateContext, Region region,
			List<ICompletionProposal> p) {
		Map<String, String> n = new HashMap<>();
		Image img =null;
		for (Iterator<String> i = n.keySet().iterator(); i.hasNext();) {
			String name = (String) i.next();
			String description = (String) n.get(name);
			p.add(new TemplateProposal(generateVariableTemplate(name, description), templateContext, region, img));
		}
	}
}