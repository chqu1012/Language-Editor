/**
 */
package de.dc.editor.lang.model.impl;

import de.dc.editor.lang.model.ContentProposal;
import de.dc.editor.lang.model.KeywordGroup;
import de.dc.editor.lang.model.LanguageDefinition;
import de.dc.editor.lang.model.ModelPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Language Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.dc.editor.lang.model.impl.LanguageDefinitionImpl#getFileExtension <em>File Extension</em>}</li>
 *   <li>{@link de.dc.editor.lang.model.impl.LanguageDefinitionImpl#getContentProposals <em>Content Proposals</em>}</li>
 *   <li>{@link de.dc.editor.lang.model.impl.LanguageDefinitionImpl#getKeywordGroups <em>Keyword Groups</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LanguageDefinitionImpl extends NamedElementImpl implements LanguageDefinition {
	/**
	 * The default value of the '{@link #getFileExtension() <em>File Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileExtension()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_EXTENSION_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getFileExtension() <em>File Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileExtension()
	 * @generated
	 * @ordered
	 */
	protected String fileExtension = FILE_EXTENSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getContentProposals() <em>Content Proposals</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContentProposals()
	 * @generated
	 * @ordered
	 */
	protected EList<ContentProposal> contentProposals;

	/**
	 * The cached value of the '{@link #getKeywordGroups() <em>Keyword Groups</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeywordGroups()
	 * @generated
	 * @ordered
	 */
	protected EList<KeywordGroup> keywordGroups;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LanguageDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.LANGUAGE_DEFINITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFileExtension() {
		return fileExtension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFileExtension(String newFileExtension) {
		String oldFileExtension = fileExtension;
		fileExtension = newFileExtension;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LANGUAGE_DEFINITION__FILE_EXTENSION, oldFileExtension, fileExtension));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ContentProposal> getContentProposals() {
		if (contentProposals == null) {
			contentProposals = new EObjectContainmentEList<ContentProposal>(ContentProposal.class, this, ModelPackage.LANGUAGE_DEFINITION__CONTENT_PROPOSALS);
		}
		return contentProposals;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<KeywordGroup> getKeywordGroups() {
		if (keywordGroups == null) {
			keywordGroups = new EObjectContainmentEList<KeywordGroup>(KeywordGroup.class, this, ModelPackage.LANGUAGE_DEFINITION__KEYWORD_GROUPS);
		}
		return keywordGroups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.LANGUAGE_DEFINITION__CONTENT_PROPOSALS:
				return ((InternalEList<?>)getContentProposals()).basicRemove(otherEnd, msgs);
			case ModelPackage.LANGUAGE_DEFINITION__KEYWORD_GROUPS:
				return ((InternalEList<?>)getKeywordGroups()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.LANGUAGE_DEFINITION__FILE_EXTENSION:
				return getFileExtension();
			case ModelPackage.LANGUAGE_DEFINITION__CONTENT_PROPOSALS:
				return getContentProposals();
			case ModelPackage.LANGUAGE_DEFINITION__KEYWORD_GROUPS:
				return getKeywordGroups();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.LANGUAGE_DEFINITION__FILE_EXTENSION:
				setFileExtension((String)newValue);
				return;
			case ModelPackage.LANGUAGE_DEFINITION__CONTENT_PROPOSALS:
				getContentProposals().clear();
				getContentProposals().addAll((Collection<? extends ContentProposal>)newValue);
				return;
			case ModelPackage.LANGUAGE_DEFINITION__KEYWORD_GROUPS:
				getKeywordGroups().clear();
				getKeywordGroups().addAll((Collection<? extends KeywordGroup>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ModelPackage.LANGUAGE_DEFINITION__FILE_EXTENSION:
				setFileExtension(FILE_EXTENSION_EDEFAULT);
				return;
			case ModelPackage.LANGUAGE_DEFINITION__CONTENT_PROPOSALS:
				getContentProposals().clear();
				return;
			case ModelPackage.LANGUAGE_DEFINITION__KEYWORD_GROUPS:
				getKeywordGroups().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ModelPackage.LANGUAGE_DEFINITION__FILE_EXTENSION:
				return FILE_EXTENSION_EDEFAULT == null ? fileExtension != null : !FILE_EXTENSION_EDEFAULT.equals(fileExtension);
			case ModelPackage.LANGUAGE_DEFINITION__CONTENT_PROPOSALS:
				return contentProposals != null && !contentProposals.isEmpty();
			case ModelPackage.LANGUAGE_DEFINITION__KEYWORD_GROUPS:
				return keywordGroups != null && !keywordGroups.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (fileExtension: ");
		result.append(fileExtension);
		result.append(')');
		return result.toString();
	}

} //LanguageDefinitionImpl
