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
 *   <li>{@link de.dc.editor.lang.model.ContentProposal#getImage <em>Image</em>}</li>
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

	/**
	 * Returns the value of the '<em><b>Image</b></em>' attribute.
	 * The literals are from the enumeration {@link de.dc.editor.lang.model.Image}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Image</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Image</em>' attribute.
	 * @see de.dc.editor.lang.model.Image
	 * @see #setImage(Image)
	 * @see de.dc.editor.lang.model.ModelPackage#getContentProposal_Image()
	 * @model unique="false"
	 * @generated
	 */
	Image getImage();

	/**
	 * Sets the value of the '{@link de.dc.editor.lang.model.ContentProposal#getImage <em>Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Image</em>' attribute.
	 * @see de.dc.editor.lang.model.Image
	 * @see #getImage()
	 * @generated
	 */
	void setImage(Image value);

} // ContentProposal
