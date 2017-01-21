/**
 */
package de.dc.editor.lang.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Content</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.dc.editor.lang.model.Content#getDescription <em>Description</em>}</li>
 *   <li>{@link de.dc.editor.lang.model.Content#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see de.dc.editor.lang.model.ModelPackage#getContent()
 * @model abstract="true"
 * @generated
 */
public interface Content extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see de.dc.editor.lang.model.ModelPackage#getContent_Description()
	 * @model default="" unique="false"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link de.dc.editor.lang.model.Content#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see de.dc.editor.lang.model.ModelPackage#getContent_Value()
	 * @model default="" unique="false"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link de.dc.editor.lang.model.Content#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // Content
