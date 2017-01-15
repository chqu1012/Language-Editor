/**
 */
package de.dc.editor.lang.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Keyword Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.dc.editor.lang.model.KeywordGroup#getColor <em>Color</em>}</li>
 *   <li>{@link de.dc.editor.lang.model.KeywordGroup#getKeyList <em>Key List</em>}</li>
 * </ul>
 *
 * @see de.dc.editor.lang.model.ModelPackage#getKeywordGroup()
 * @model
 * @generated
 */
public interface KeywordGroup extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Color</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Color</em>' containment reference.
	 * @see #setColor(Color)
	 * @see de.dc.editor.lang.model.ModelPackage#getKeywordGroup_Color()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Color getColor();

	/**
	 * Sets the value of the '{@link de.dc.editor.lang.model.KeywordGroup#getColor <em>Color</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Color</em>' containment reference.
	 * @see #getColor()
	 * @generated
	 */
	void setColor(Color value);

	/**
	 * Returns the value of the '<em><b>Key List</b></em>' containment reference list.
	 * The list contents are of type {@link de.dc.editor.lang.model.Key}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key List</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key List</em>' containment reference list.
	 * @see de.dc.editor.lang.model.ModelPackage#getKeywordGroup_KeyList()
	 * @model containment="true"
	 * @generated
	 */
	EList<Key> getKeyList();

} // KeywordGroup
