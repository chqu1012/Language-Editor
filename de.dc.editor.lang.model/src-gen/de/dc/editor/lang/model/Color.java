/**
 */
package de.dc.editor.lang.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Color</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.dc.editor.lang.model.Color#getR <em>R</em>}</li>
 *   <li>{@link de.dc.editor.lang.model.Color#getG <em>G</em>}</li>
 *   <li>{@link de.dc.editor.lang.model.Color#getB <em>B</em>}</li>
 * </ul>
 *
 * @see de.dc.editor.lang.model.ModelPackage#getColor()
 * @model
 * @generated
 */
public interface Color extends NamedElement {
	/**
	 * Returns the value of the '<em><b>R</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>R</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>R</em>' attribute.
	 * @see #setR(int)
	 * @see de.dc.editor.lang.model.ModelPackage#getColor_R()
	 * @model default="0" unique="false"
	 * @generated
	 */
	int getR();

	/**
	 * Sets the value of the '{@link de.dc.editor.lang.model.Color#getR <em>R</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>R</em>' attribute.
	 * @see #getR()
	 * @generated
	 */
	void setR(int value);

	/**
	 * Returns the value of the '<em><b>G</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>G</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>G</em>' attribute.
	 * @see #setG(int)
	 * @see de.dc.editor.lang.model.ModelPackage#getColor_G()
	 * @model default="0" unique="false"
	 * @generated
	 */
	int getG();

	/**
	 * Sets the value of the '{@link de.dc.editor.lang.model.Color#getG <em>G</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>G</em>' attribute.
	 * @see #getG()
	 * @generated
	 */
	void setG(int value);

	/**
	 * Returns the value of the '<em><b>B</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>B</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>B</em>' attribute.
	 * @see #setB(int)
	 * @see de.dc.editor.lang.model.ModelPackage#getColor_B()
	 * @model default="0" unique="false"
	 * @generated
	 */
	int getB();

	/**
	 * Sets the value of the '{@link de.dc.editor.lang.model.Color#getB <em>B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>B</em>' attribute.
	 * @see #getB()
	 * @generated
	 */
	void setB(int value);

} // Color
