/**
 */
package de.dc.editor.lang.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Content Proposal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.dc.editor.lang.model.ContentProposal#getContents <em>Contents</em>}</li>
 * </ul>
 *
 * @see de.dc.editor.lang.model.ModelPackage#getContentProposal()
 * @model
 * @generated
 */
public interface ContentProposal extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Contents</b></em>' containment reference list.
	 * The list contents are of type {@link de.dc.editor.lang.model.Content}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contents</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contents</em>' containment reference list.
	 * @see de.dc.editor.lang.model.ModelPackage#getContentProposal_Contents()
	 * @model containment="true"
	 * @generated
	 */
	EList<Content> getContents();

} // ContentProposal
