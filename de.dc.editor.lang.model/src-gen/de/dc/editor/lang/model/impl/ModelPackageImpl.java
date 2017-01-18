/**
 */
package de.dc.editor.lang.model.impl;

import de.dc.editor.lang.model.Color;
import de.dc.editor.lang.model.Content;
import de.dc.editor.lang.model.ContentProposal;
import de.dc.editor.lang.model.Function;
import de.dc.editor.lang.model.Image;
import de.dc.editor.lang.model.Key;
import de.dc.editor.lang.model.KeywordGroup;
import de.dc.editor.lang.model.LanguageDefinition;
import de.dc.editor.lang.model.ModelFactory;
import de.dc.editor.lang.model.ModelPackage;
import de.dc.editor.lang.model.NamedElement;
import de.dc.editor.lang.model.Template;
import de.dc.editor.lang.model.Token;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelPackageImpl extends EPackageImpl implements ModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass languageDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contentProposalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tokenEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass templateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass keywordGroupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass keyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass colorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum imageEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see de.dc.editor.lang.model.ModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ModelPackageImpl() {
		super(eNS_URI, ModelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link ModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ModelPackage init() {
		if (isInited) return (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Obtain or create and register package
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ModelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theModelPackage.createPackageContents();

		// Initialize created meta-data
		theModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ModelPackage.eNS_URI, theModelPackage);
		return theModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNamedElement() {
		return namedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamedElement_Name() {
		return (EAttribute)namedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLanguageDefinition() {
		return languageDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLanguageDefinition_FileExtension() {
		return (EAttribute)languageDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLanguageDefinition_ContentProposals() {
		return (EReference)languageDefinitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLanguageDefinition_KeywordGroups() {
		return (EReference)languageDefinitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLanguageDefinition_UseSingleLineCommentHighlighting() {
		return (EAttribute)languageDefinitionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLanguageDefinition_UseMultiLineCommentHighlighting() {
		return (EAttribute)languageDefinitionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLanguageDefinition_UseSingleQuotesHighlighting() {
		return (EAttribute)languageDefinitionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLanguageDefinition_UseMultiQuotesHighlighting() {
		return (EAttribute)languageDefinitionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getContentProposal() {
		return contentProposalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getContentProposal_Contents() {
		return (EReference)contentProposalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContentProposal_Image() {
		return (EAttribute)contentProposalEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getContent() {
		return contentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContent_Description() {
		return (EAttribute)contentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContent_Pattern() {
		return (EAttribute)contentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getToken() {
		return tokenEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFunction() {
		return functionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTemplate() {
		return templateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getKeywordGroup() {
		return keywordGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getKeywordGroup_Color() {
		return (EReference)keywordGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getKeywordGroup_KeyList() {
		return (EReference)keywordGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getKey() {
		return keyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getKey_Value() {
		return (EAttribute)keyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getColor() {
		return colorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColor_R() {
		return (EAttribute)colorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColor_G() {
		return (EAttribute)colorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColor_B() {
		return (EAttribute)colorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getImage() {
		return imageEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFactory getModelFactory() {
		return (ModelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		namedElementEClass = createEClass(NAMED_ELEMENT);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__NAME);

		languageDefinitionEClass = createEClass(LANGUAGE_DEFINITION);
		createEAttribute(languageDefinitionEClass, LANGUAGE_DEFINITION__FILE_EXTENSION);
		createEReference(languageDefinitionEClass, LANGUAGE_DEFINITION__CONTENT_PROPOSALS);
		createEReference(languageDefinitionEClass, LANGUAGE_DEFINITION__KEYWORD_GROUPS);
		createEAttribute(languageDefinitionEClass, LANGUAGE_DEFINITION__USE_SINGLE_LINE_COMMENT_HIGHLIGHTING);
		createEAttribute(languageDefinitionEClass, LANGUAGE_DEFINITION__USE_MULTI_LINE_COMMENT_HIGHLIGHTING);
		createEAttribute(languageDefinitionEClass, LANGUAGE_DEFINITION__USE_SINGLE_QUOTES_HIGHLIGHTING);
		createEAttribute(languageDefinitionEClass, LANGUAGE_DEFINITION__USE_MULTI_QUOTES_HIGHLIGHTING);

		contentProposalEClass = createEClass(CONTENT_PROPOSAL);
		createEReference(contentProposalEClass, CONTENT_PROPOSAL__CONTENTS);
		createEAttribute(contentProposalEClass, CONTENT_PROPOSAL__IMAGE);

		contentEClass = createEClass(CONTENT);
		createEAttribute(contentEClass, CONTENT__DESCRIPTION);
		createEAttribute(contentEClass, CONTENT__PATTERN);

		tokenEClass = createEClass(TOKEN);

		functionEClass = createEClass(FUNCTION);

		templateEClass = createEClass(TEMPLATE);

		keywordGroupEClass = createEClass(KEYWORD_GROUP);
		createEReference(keywordGroupEClass, KEYWORD_GROUP__COLOR);
		createEReference(keywordGroupEClass, KEYWORD_GROUP__KEY_LIST);

		keyEClass = createEClass(KEY);
		createEAttribute(keyEClass, KEY__VALUE);

		colorEClass = createEClass(COLOR);
		createEAttribute(colorEClass, COLOR__R);
		createEAttribute(colorEClass, COLOR__G);
		createEAttribute(colorEClass, COLOR__B);

		// Create enums
		imageEEnum = createEEnum(IMAGE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		languageDefinitionEClass.getESuperTypes().add(this.getNamedElement());
		contentProposalEClass.getESuperTypes().add(this.getNamedElement());
		contentEClass.getESuperTypes().add(this.getNamedElement());
		tokenEClass.getESuperTypes().add(this.getContent());
		functionEClass.getESuperTypes().add(this.getContent());
		templateEClass.getESuperTypes().add(this.getContent());
		keywordGroupEClass.getESuperTypes().add(this.getNamedElement());
		keyEClass.getESuperTypes().add(this.getNamedElement());
		colorEClass.getESuperTypes().add(this.getNamedElement());

		// Initialize classes, features, and operations; add parameters
		initEClass(namedElementEClass, NamedElement.class, "NamedElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamedElement_Name(), theEcorePackage.getEString(), "name", "", 0, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(languageDefinitionEClass, LanguageDefinition.class, "LanguageDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLanguageDefinition_FileExtension(), theEcorePackage.getEString(), "fileExtension", "", 0, 1, LanguageDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLanguageDefinition_ContentProposals(), this.getContentProposal(), null, "contentProposals", null, 0, -1, LanguageDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLanguageDefinition_KeywordGroups(), this.getKeywordGroup(), null, "keywordGroups", null, 0, -1, LanguageDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLanguageDefinition_UseSingleLineCommentHighlighting(), theEcorePackage.getEBoolean(), "useSingleLineCommentHighlighting", "true", 0, 1, LanguageDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLanguageDefinition_UseMultiLineCommentHighlighting(), theEcorePackage.getEBoolean(), "useMultiLineCommentHighlighting", "true", 0, 1, LanguageDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLanguageDefinition_UseSingleQuotesHighlighting(), theEcorePackage.getEBoolean(), "useSingleQuotesHighlighting", "false", 0, 1, LanguageDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLanguageDefinition_UseMultiQuotesHighlighting(), theEcorePackage.getEBoolean(), "useMultiQuotesHighlighting", "false", 0, 1, LanguageDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(contentProposalEClass, ContentProposal.class, "ContentProposal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContentProposal_Contents(), this.getContent(), null, "contents", null, 0, -1, ContentProposal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getContentProposal_Image(), this.getImage(), "image", null, 0, 1, ContentProposal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(contentEClass, Content.class, "Content", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getContent_Description(), theEcorePackage.getEString(), "description", "", 0, 1, Content.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getContent_Pattern(), theEcorePackage.getEString(), "pattern", "", 0, 1, Content.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tokenEClass, Token.class, "Token", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(functionEClass, Function.class, "Function", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(templateEClass, Template.class, "Template", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(keywordGroupEClass, KeywordGroup.class, "KeywordGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getKeywordGroup_Color(), this.getColor(), null, "color", null, 1, 1, KeywordGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getKeywordGroup_KeyList(), this.getKey(), null, "keyList", null, 0, -1, KeywordGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(keyEClass, Key.class, "Key", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getKey_Value(), theEcorePackage.getEString(), "value", "", 0, 1, Key.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(colorEClass, Color.class, "Color", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getColor_R(), theEcorePackage.getEInt(), "r", "0", 0, 1, Color.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColor_G(), theEcorePackage.getEInt(), "g", "0", 0, 1, Color.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColor_B(), theEcorePackage.getEInt(), "b", "0", 0, 1, Color.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(imageEEnum, Image.class, "Image");
		addEEnumLiteral(imageEEnum, Image.NONE);
		addEEnumLiteral(imageEEnum, Image.IMG_DEC_FIELD_ERROR);
		addEEnumLiteral(imageEEnum, Image.IMG_DEC_FIELD_WARNING);
		addEEnumLiteral(imageEEnum, Image.IMG_DEF_VIEW);
		addEEnumLiteral(imageEEnum, Image.IMG_ELCL_COLLAPSEALL);
		addEEnumLiteral(imageEEnum, Image.IMG_ELCL_COLLAPSEALL_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_ELCL_REMOVE);
		addEEnumLiteral(imageEEnum, Image.IMG_ELCL_REMOVE_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_ELCL_REMOVEALL);
		addEEnumLiteral(imageEEnum, Image.IMG_ELCL_REMOVEALL_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_ELCL_STOP);
		addEEnumLiteral(imageEEnum, Image.IMG_ELCL_STOP_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_ELCL_SYNCED);
		addEEnumLiteral(imageEEnum, Image.IMG_ELCL_SYNCED_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_ETOOL_CLEAR);
		addEEnumLiteral(imageEEnum, Image.IMG_ETOOL_CLEAR_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_ETOOL_DEF_PERSPECTIVE);
		addEEnumLiteral(imageEEnum, Image.IMG_ETOOL_DELETE);
		addEEnumLiteral(imageEEnum, Image.IMG_ETOOL_DELETE_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_ETOOL_HOME_NAV);
		addEEnumLiteral(imageEEnum, Image.IMG_ETOOL_HOME_NAV_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_ETOOL_PRINT_EDIT);
		addEEnumLiteral(imageEEnum, Image.IMG_ETOOL_PRINT_EDIT_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_ETOOL_SAVE_EDIT);
		addEEnumLiteral(imageEEnum, Image.IMG_ETOOL_SAVE_EDIT_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_ETOOL_SAVEALL_EDIT);
		addEEnumLiteral(imageEEnum, Image.IMG_ETOOL_SAVEALL_EDIT_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_ETOOL_SAVEAS_EDIT);
		addEEnumLiteral(imageEEnum, Image.IMG_ETOOL_SAVEAS_EDIT_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_LCL_LINKTO_HELP);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJ_ADD);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJ_ELEMENT);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJ_FILE);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJ_FOLDER);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJ_PROJECT);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJ_PROJECT_CLOSED);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_BKMRK_TSK);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_DND_BOTTOM_MASK);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_DND_BOTTOM_SOURCE);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_DND_INVALID_MASK);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_DND_INVALID_SOURCE);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_DND_LEFT_MASK);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_DND_LEFT_SOURCE);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_DND_OFFSCREEN_MASK);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_DND_OFFSCREEN_SOURCE);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_DND_RIGHT_MASK);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_DND_RIGHT_SOURCE);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_DND_STACK_MASK);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_DND_STACK_SOURCE);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_DND_TOFASTVIEW_MASK);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_DND_TOFASTVIEW_SOURCE);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_DND_TOP_MASK);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_DND_TOP_SOURCE);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_ERROR_TSK);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_INFO_TSK);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_TASK_TSK);
		addEEnumLiteral(imageEEnum, Image.IMG_OBJS_WARN_TSK);
		addEEnumLiteral(imageEEnum, Image.IMG_OPEN_MARKER);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_BACK);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_BACK_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_BACK_HOVER);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_COPY);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_COPY_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_COPY_HOVER);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_CUT);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_CUT_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_CUT_HOVER);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_DELETE);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_DELETE_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_DELETE_HOVER);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_FORWARD);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_FORWARD_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_FORWARD_HOVER);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_NEW_WIZARD);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_NEW_WIZARD_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_NEW_WIZARD_HOVER);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_PASTE);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_PASTE_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_PASTE_HOVER);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_REDO);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_REDO_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_REDO_HOVER);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_UNDO);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_UNDO_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_UNDO_HOVER);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_UP);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_UP_DISABLED);
		addEEnumLiteral(imageEEnum, Image.IMG_TOOL_UP_HOVER);

		// Create resource
		createResource(eNS_URI);
	}

} //ModelPackageImpl
