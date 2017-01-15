/**
 */
package de.dc.editor.lang.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Language Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.dc.editor.lang.model.LanguageDefinition#getFileExtension <em>File Extension</em>}</li>
 *   <li>{@link de.dc.editor.lang.model.LanguageDefinition#getContentProposals <em>Content Proposals</em>}</li>
 *   <li>{@link de.dc.editor.lang.model.LanguageDefinition#getKeywordGroups <em>Keyword Groups</em>}</li>
 * </ul>
 *
 * @see de.dc.editor.lang.model.ModelPackage#getLanguageDefinition()
 * @model
 * @generated
 */
public interface LanguageDefinition extends NamedElement {
	/**
	 * Returns the value of the '<em><b>File Extension</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Extension</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Extension</em>' attribute.
	 * @see #setFileExtension(String)
	 * @see de.dc.editor.lang.model.ModelPackage#getLanguageDefinition_FileExtension()
	 * @model default="" unique="false"
	 * @generated
	 */
	String getFileExtension();

	/**
	 * Sets the value of the '{@link de.dc.editor.lang.model.LanguageDefinition#getFileExtension <em>File Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Extension</em>' attribute.
	 * @see #getFileExtension()
	 * @generated
	 */
	void setFileExtension(String value);

	/**
	 * Returns the value of the '<em><b>Content Proposals</b></em>' containment reference list.
	 * The list contents are of type {@link de.dc.editor.lang.model.ContentProposal}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content Proposals</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content Proposals</em>' containment reference list.
	 * @see de.dc.editor.lang.model.ModelPackage#getLanguageDefinition_ContentProposals()
	 * @model containment="true"
	 * @generated
	 */
	EList<ContentProposal> getContentProposals();

	/**
	 * Returns the value of the '<em><b>Keyword Groups</b></em>' containment reference list.
	 * The list contents are of type {@link de.dc.editor.lang.model.KeywordGroup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Keyword Groups</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Keyword Groups</em>' containment reference list.
	 * @see de.dc.editor.lang.model.ModelPackage#getLanguageDefinition_KeywordGroups()
	 * @model containment="true"
	 * @generated
	 */
	EList<KeywordGroup> getKeywordGroups();

} // LanguageDefinition
