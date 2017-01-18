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
 *   <li>{@link de.dc.editor.lang.model.impl.LanguageDefinitionImpl#isUseSingleLineCommentHighlighting <em>Use Single Line Comment Highlighting</em>}</li>
 *   <li>{@link de.dc.editor.lang.model.impl.LanguageDefinitionImpl#isUseMultiLineCommentHighlighting <em>Use Multi Line Comment Highlighting</em>}</li>
 *   <li>{@link de.dc.editor.lang.model.impl.LanguageDefinitionImpl#isUseSingleQuotesHighlighting <em>Use Single Quotes Highlighting</em>}</li>
 *   <li>{@link de.dc.editor.lang.model.impl.LanguageDefinitionImpl#isUseMultiQuotesHighlighting <em>Use Multi Quotes Highlighting</em>}</li>
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
	 * The default value of the '{@link #isUseSingleLineCommentHighlighting() <em>Use Single Line Comment Highlighting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseSingleLineCommentHighlighting()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_SINGLE_LINE_COMMENT_HIGHLIGHTING_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isUseSingleLineCommentHighlighting() <em>Use Single Line Comment Highlighting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseSingleLineCommentHighlighting()
	 * @generated
	 * @ordered
	 */
	protected boolean useSingleLineCommentHighlighting = USE_SINGLE_LINE_COMMENT_HIGHLIGHTING_EDEFAULT;

	/**
	 * The default value of the '{@link #isUseMultiLineCommentHighlighting() <em>Use Multi Line Comment Highlighting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseMultiLineCommentHighlighting()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_MULTI_LINE_COMMENT_HIGHLIGHTING_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isUseMultiLineCommentHighlighting() <em>Use Multi Line Comment Highlighting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseMultiLineCommentHighlighting()
	 * @generated
	 * @ordered
	 */
	protected boolean useMultiLineCommentHighlighting = USE_MULTI_LINE_COMMENT_HIGHLIGHTING_EDEFAULT;

	/**
	 * The default value of the '{@link #isUseSingleQuotesHighlighting() <em>Use Single Quotes Highlighting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseSingleQuotesHighlighting()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_SINGLE_QUOTES_HIGHLIGHTING_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUseSingleQuotesHighlighting() <em>Use Single Quotes Highlighting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseSingleQuotesHighlighting()
	 * @generated
	 * @ordered
	 */
	protected boolean useSingleQuotesHighlighting = USE_SINGLE_QUOTES_HIGHLIGHTING_EDEFAULT;

	/**
	 * The default value of the '{@link #isUseMultiQuotesHighlighting() <em>Use Multi Quotes Highlighting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseMultiQuotesHighlighting()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_MULTI_QUOTES_HIGHLIGHTING_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUseMultiQuotesHighlighting() <em>Use Multi Quotes Highlighting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseMultiQuotesHighlighting()
	 * @generated
	 * @ordered
	 */
	protected boolean useMultiQuotesHighlighting = USE_MULTI_QUOTES_HIGHLIGHTING_EDEFAULT;

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
	public boolean isUseSingleLineCommentHighlighting() {
		return useSingleLineCommentHighlighting;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUseSingleLineCommentHighlighting(boolean newUseSingleLineCommentHighlighting) {
		boolean oldUseSingleLineCommentHighlighting = useSingleLineCommentHighlighting;
		useSingleLineCommentHighlighting = newUseSingleLineCommentHighlighting;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LANGUAGE_DEFINITION__USE_SINGLE_LINE_COMMENT_HIGHLIGHTING, oldUseSingleLineCommentHighlighting, useSingleLineCommentHighlighting));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUseMultiLineCommentHighlighting() {
		return useMultiLineCommentHighlighting;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUseMultiLineCommentHighlighting(boolean newUseMultiLineCommentHighlighting) {
		boolean oldUseMultiLineCommentHighlighting = useMultiLineCommentHighlighting;
		useMultiLineCommentHighlighting = newUseMultiLineCommentHighlighting;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LANGUAGE_DEFINITION__USE_MULTI_LINE_COMMENT_HIGHLIGHTING, oldUseMultiLineCommentHighlighting, useMultiLineCommentHighlighting));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUseSingleQuotesHighlighting() {
		return useSingleQuotesHighlighting;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUseSingleQuotesHighlighting(boolean newUseSingleQuotesHighlighting) {
		boolean oldUseSingleQuotesHighlighting = useSingleQuotesHighlighting;
		useSingleQuotesHighlighting = newUseSingleQuotesHighlighting;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LANGUAGE_DEFINITION__USE_SINGLE_QUOTES_HIGHLIGHTING, oldUseSingleQuotesHighlighting, useSingleQuotesHighlighting));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUseMultiQuotesHighlighting() {
		return useMultiQuotesHighlighting;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUseMultiQuotesHighlighting(boolean newUseMultiQuotesHighlighting) {
		boolean oldUseMultiQuotesHighlighting = useMultiQuotesHighlighting;
		useMultiQuotesHighlighting = newUseMultiQuotesHighlighting;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LANGUAGE_DEFINITION__USE_MULTI_QUOTES_HIGHLIGHTING, oldUseMultiQuotesHighlighting, useMultiQuotesHighlighting));
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
			case ModelPackage.LANGUAGE_DEFINITION__USE_SINGLE_LINE_COMMENT_HIGHLIGHTING:
				return isUseSingleLineCommentHighlighting();
			case ModelPackage.LANGUAGE_DEFINITION__USE_MULTI_LINE_COMMENT_HIGHLIGHTING:
				return isUseMultiLineCommentHighlighting();
			case ModelPackage.LANGUAGE_DEFINITION__USE_SINGLE_QUOTES_HIGHLIGHTING:
				return isUseSingleQuotesHighlighting();
			case ModelPackage.LANGUAGE_DEFINITION__USE_MULTI_QUOTES_HIGHLIGHTING:
				return isUseMultiQuotesHighlighting();
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
			case ModelPackage.LANGUAGE_DEFINITION__USE_SINGLE_LINE_COMMENT_HIGHLIGHTING:
				setUseSingleLineCommentHighlighting((Boolean)newValue);
				return;
			case ModelPackage.LANGUAGE_DEFINITION__USE_MULTI_LINE_COMMENT_HIGHLIGHTING:
				setUseMultiLineCommentHighlighting((Boolean)newValue);
				return;
			case ModelPackage.LANGUAGE_DEFINITION__USE_SINGLE_QUOTES_HIGHLIGHTING:
				setUseSingleQuotesHighlighting((Boolean)newValue);
				return;
			case ModelPackage.LANGUAGE_DEFINITION__USE_MULTI_QUOTES_HIGHLIGHTING:
				setUseMultiQuotesHighlighting((Boolean)newValue);
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
			case ModelPackage.LANGUAGE_DEFINITION__USE_SINGLE_LINE_COMMENT_HIGHLIGHTING:
				setUseSingleLineCommentHighlighting(USE_SINGLE_LINE_COMMENT_HIGHLIGHTING_EDEFAULT);
				return;
			case ModelPackage.LANGUAGE_DEFINITION__USE_MULTI_LINE_COMMENT_HIGHLIGHTING:
				setUseMultiLineCommentHighlighting(USE_MULTI_LINE_COMMENT_HIGHLIGHTING_EDEFAULT);
				return;
			case ModelPackage.LANGUAGE_DEFINITION__USE_SINGLE_QUOTES_HIGHLIGHTING:
				setUseSingleQuotesHighlighting(USE_SINGLE_QUOTES_HIGHLIGHTING_EDEFAULT);
				return;
			case ModelPackage.LANGUAGE_DEFINITION__USE_MULTI_QUOTES_HIGHLIGHTING:
				setUseMultiQuotesHighlighting(USE_MULTI_QUOTES_HIGHLIGHTING_EDEFAULT);
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
			case ModelPackage.LANGUAGE_DEFINITION__USE_SINGLE_LINE_COMMENT_HIGHLIGHTING:
				return useSingleLineCommentHighlighting != USE_SINGLE_LINE_COMMENT_HIGHLIGHTING_EDEFAULT;
			case ModelPackage.LANGUAGE_DEFINITION__USE_MULTI_LINE_COMMENT_HIGHLIGHTING:
				return useMultiLineCommentHighlighting != USE_MULTI_LINE_COMMENT_HIGHLIGHTING_EDEFAULT;
			case ModelPackage.LANGUAGE_DEFINITION__USE_SINGLE_QUOTES_HIGHLIGHTING:
				return useSingleQuotesHighlighting != USE_SINGLE_QUOTES_HIGHLIGHTING_EDEFAULT;
			case ModelPackage.LANGUAGE_DEFINITION__USE_MULTI_QUOTES_HIGHLIGHTING:
				return useMultiQuotesHighlighting != USE_MULTI_QUOTES_HIGHLIGHTING_EDEFAULT;
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
		result.append(", useSingleLineCommentHighlighting: ");
		result.append(useSingleLineCommentHighlighting);
		result.append(", useMultiLineCommentHighlighting: ");
		result.append(useMultiLineCommentHighlighting);
		result.append(", useSingleQuotesHighlighting: ");
		result.append(useSingleQuotesHighlighting);
		result.append(", useMultiQuotesHighlighting: ");
		result.append(useMultiQuotesHighlighting);
		result.append(')');
		return result.toString();
	}

} //LanguageDefinitionImpl
