package ext.st.pmgt.indicator.model;


import com.pisx.tundra.foundation.access.model.PolicyAccessControlled;
import com.pisx.tundra.foundation.admin.model.AdminDomainRef;
import com.pisx.tundra.foundation.content.model.ContentHolder;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.inf.container.model.PIContained;
import com.pisx.tundra.foundation.inf.container.model.PIContainer;
import com.pisx.tundra.foundation.inf.container.model.PIContainerRef;
import com.pisx.tundra.foundation.lifecycle.model.LcState;
import com.pisx.tundra.foundation.lifecycle.model.LifeCycleManaged;
import com.pisx.tundra.foundation.lifecycle.model.LifeCycleState;
import com.pisx.tundra.foundation.meta.type.model.LTDTypeDefinition;
import com.pisx.tundra.foundation.meta.type.model.LTDTyped;
import com.pisx.tundra.foundation.meta.type.model.TypeDefinitionReference;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIPropertyVetoException;
import com.pisx.tundra.pmgt.model.PIPmgtObject;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.plan.model.PIPlannable;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.resource.model.PIResource;
import com.pisx.tundra.pmgt.risk.model.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Vector;


/*
project
baselineProject
addDate
riskName
riskType
rsrc
riskToType
identifiedBy
responseType
responseText
preRspProbability
preRspSchdProbability
preRspCostProbability
postRspProbability
postRspSchdProbability
postRspCostProbability
riskCause
riskEffect
notes
resolvedDate
riskCode
riskDescription

*/

/**
 * the class for project risk objects..
 */

//		SubjectOfForum,
//			SubjectOfNotebook,
//			Notifiable,
//			Typed,
@Entity
@Table
public class STProjectRisk extends PIPmgtObject
        implements
        ContentHolder,
        PIContained,
        PolicyAccessControlled,
        LifeCycleManaged,
        Serializable,
        LTDTyped {
    static final long serialVersionUID = 1L;
    static final String CLASSNAME = STProjectRisk.class.getName();

    @Column(nullable = false, unique = false)
    private String riskCode;
    @Column(nullable = false, unique = false)
    private String riskName;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "riskToType", nullable = false))
    })
    RiskToType riskToType = RiskToType.getRiskToTypeDefault();
    @Embedded
    RiskResponseType responseType;

    @Column(nullable = true, unique = false)
    private String responseText;
    @Column(nullable = false, unique = false)
    private Timestamp addDate;
    @Embedded
    RiskRspProbability preRspProbability = RiskRspProbability.getRiskRspProbabilityDefault();
    @Embedded
    RiskRspSchdProbability preRspSchdProbability = RiskRspSchdProbability.getRiskRspSchdProbabilityDefault();
    @Embedded
    RiskRspCostProbability preRspCostProbability = RiskRspCostProbability.getRiskRspCostProbabilityDefault();
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "postRspProbability", nullable = true))
    })
    RiskRspProbability postRspProbability = RiskRspProbability.getRiskRspProbabilityDefault();
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "postRspSchdProbability", nullable = true))
    })
    RiskRspSchdProbability postRspSchdProbability = RiskRspSchdProbability.getRiskRspSchdProbabilityDefault();
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "postRspCostProbability", nullable = true))
    })
    RiskRspCostProbability postRspCostProbability = RiskRspCostProbability.getRiskRspCostProbabilityDefault();

    @Column(nullable = true, unique = false)
    private String riskCause;
    @Column(nullable = true, unique = false)
    private String riskEffect;
    @Column(nullable = true, unique = false)
    private String riskDescription;
    @Column(nullable = true, unique = false)
    private String notes;
    @Column(nullable = true, unique = false)
    private Timestamp resolvedDate;

    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "riskTypeRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "riskTypeRefClass", nullable = true))
    })
    ObjectReference riskTypeReference;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "identifiedByRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "identifiedByRefClass", nullable = true))
    })
    ObjectReference identifiedByReference;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "projectRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "projectRefClass", nullable = true))
    })
    ObjectReference projectReference;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "rsrcRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "rsrcRefClass", nullable = true))
    })
    ObjectReference rsrcReference;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "baselineProjectRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "baselineProjectRefClass", nullable = true))
    })
    ObjectReference baselineProjectReference;

    @Transient
    Vector contentVector;
    @Transient
    boolean hasContents;
    //HttpContentOperation operation;
    @Transient
    Vector httpVector;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "containerRefId", nullable = false)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "containerRefClass", nullable = false))
    })
    PIContainerRef containerReference;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "lTDTypeDefinitionRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "lTDTypeDefinitionRefClass", nullable = true))
    })
    TypeDefinitionReference lTDTypeDefinitionReference;

    //WTStringSet eventSet;
    //TypeDefinitionReference typeDefinitionReference;
    //AttributeContainer theAttributeContainer;
    @Transient
    boolean inheritedDomain;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "domainRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "domainRefClass", nullable = true))
    })
    AdminDomainRef domainRef;
    //AdministrativeLock administrativeLock;
    @Embedded
    LifeCycleState state;
    //AclEntrySet entrySet;
    //TeamTemplateReference teamTemplateId;
    //TeamReference teamId;

    public String getRiskCode() {
        return this.riskCode;
    }

    public void setRiskCode(String riskCode) {
        this.riskCodeValidate(riskCode);
        this.riskCode = riskCode;
    }

    void riskCodeValidate(String riskCode) {
    }

    public String getRiskName() {
        return this.riskName;
    }

    public void setRiskName(String riskName) {
        this.riskNameValidate(riskName);
        this.riskName = riskName;
    }

    void riskNameValidate(String riskName) {
    }

    public RiskToType getRiskToType() {
        return this.riskToType;
    }

    public void setRiskToType(RiskToType riskToType) {
        this.riskToTypeValidate(riskToType);
        this.riskToType = riskToType;
    }

    void riskToTypeValidate(RiskToType riskToType) {
    }

    public RiskResponseType getResponseType() {
        return this.responseType;
    }

    public void setResponseType(RiskResponseType responseType) {
        this.responseTypeValidate(responseType);
        this.responseType = responseType;
    }

    void responseTypeValidate(RiskResponseType responseType) {
    }

    public String getResponseText() {
        return this.responseText;
    }

    public void setResponseText(String responseText) {
        this.responseTextValidate(responseText);
        this.responseText = responseText;
    }

    void responseTextValidate(String responseText) {
    }

    public Timestamp getAddDate() {
        return this.addDate;
    }

    public void setAddDate(Timestamp addDate) {
        this.addDateValidate(addDate);
        this.addDate = addDate;
    }

    void addDateValidate(Timestamp addDate) {
    }

    public RiskRspProbability getPreRspProbability() {
        return this.preRspProbability;
    }

    public void setPreRspProbability(RiskRspProbability preRspProbability) {
        this.preRspProbabilityValidate(preRspProbability);
        this.preRspProbability = preRspProbability;
    }

    void preRspProbabilityValidate(RiskRspProbability preRspProbability) {
    }

    public RiskRspSchdProbability getPreRspSchdProbability() {
        return this.preRspSchdProbability;
    }

    public void setPreRspSchdProbability(RiskRspSchdProbability preRspSchdProbability) {
        this.preRspSchdProbabilityValidate(preRspSchdProbability);
        this.preRspSchdProbability = preRspSchdProbability;
    }

    void preRspSchdProbabilityValidate(RiskRspSchdProbability preRspSchdProbability) {
    }

    public RiskRspCostProbability getPreRspCostProbability() {
        return this.preRspCostProbability;
    }

    public void setPreRspCostProbability(RiskRspCostProbability preRspCostProbability) {
        this.preRspCostProbabilityValidate(preRspCostProbability);
        this.preRspCostProbability = preRspCostProbability;
    }

    void preRspCostProbabilityValidate(RiskRspCostProbability preRspCostProbability) {
    }

    public RiskRspProbability getPostRspProbability() {
        return this.postRspProbability;
    }

    public void setPostRspProbability(RiskRspProbability postRspProbability) {
        this.postRspProbabilityValidate(postRspProbability);
        this.postRspProbability = postRspProbability;
    }

    void postRspProbabilityValidate(RiskRspProbability postRspProbability) {
    }

    public RiskRspSchdProbability getPostRspSchdProbability() {
        return this.postRspSchdProbability;
    }

    public void setPostRspSchdProbability(RiskRspSchdProbability postRspSchdProbability) {
        this.postRspSchdProbabilityValidate(postRspSchdProbability);
        this.postRspSchdProbability = postRspSchdProbability;
    }

    void postRspSchdProbabilityValidate(RiskRspSchdProbability postRspSchdProbability) {
    }

    public RiskRspCostProbability getPostRspCostProbability() {
        return this.postRspCostProbability;
    }

    public void setPostRspCostProbability(RiskRspCostProbability postRspCostProbability) {
        this.postRspCostProbabilityValidate(postRspCostProbability);
        this.postRspCostProbability = postRspCostProbability;
    }

    void postRspCostProbabilityValidate(RiskRspCostProbability postRspCostProbability) {
    }

    public String getRiskCause() {
        return this.riskCause;
    }

    public void setRiskCause(String riskCause) {
        this.riskCauseValidate(riskCause);
        this.riskCause = riskCause;
    }

    void riskCauseValidate(String riskCause) {
    }

    public String getRiskEffect() {
        return this.riskEffect;
    }

    public void setRiskEffect(String riskEffect) {
        this.riskEffectValidate(riskEffect);
        this.riskEffect = riskEffect;
    }

    void riskEffectValidate(String riskEffect) {
    }

    public String getRiskDescription() {
        return this.riskDescription;
    }

    public void setRiskDescription(String riskDescription) {
        this.riskDescriptionValidate(riskDescription);
        this.riskDescription = riskDescription;
    }

    void riskDescriptionValidate(String riskDescription) {
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notesValidate(notes);
        this.notes = notes;
    }

    void notesValidate(String notes) {
    }

    public Timestamp getResolvedDate() {
        return this.resolvedDate;
    }

    public void setResolvedDate(Timestamp resolvedDate) {
        this.resolvedDateValidate(resolvedDate);
        this.resolvedDate = resolvedDate;
    }

    void resolvedDateValidate(Timestamp resolvedDate) {
    }

    public PIPmgtRiskType getRiskType() {
        return this.riskTypeReference != null ? (PIPmgtRiskType) this.riskTypeReference.getObject() : null;
    }

    public ObjectReference getRiskTypeReference() {
        return this.riskTypeReference;
    }

    public void setRiskType(PIPmgtRiskType the_riskType) throws PIException {
        this.setRiskTypeReference(the_riskType == null ? null : ObjectReference.newObjectReference(the_riskType));
    }

    public void setRiskTypeReference(ObjectReference the_riskTypeReference) throws PIException {
        this.riskTypeReferenceValidate(the_riskTypeReference);
        this.riskTypeReference = the_riskTypeReference;
    }

    void riskTypeReferenceValidate(ObjectReference the_riskTypeReference) throws PIException {
        if (the_riskTypeReference != null && the_riskTypeReference.getReferencedClass() != null
                && !PIPmgtRiskType.class.isAssignableFrom(the_riskTypeReference.getReferencedClass())) {
            String msg = "The value assigned to riskTypeReference must be of the type 'ObjectReference'.";
            throw new PIException(msg);
        }
    }

    public PIUser getIdentifiedBy() {
        return this.identifiedByReference != null ? (PIUser) this.identifiedByReference.getObject() : null;
    }

    public ObjectReference getIdentifiedByReference() {
        return this.identifiedByReference;
    }

    public void setIdentifiedBy(PIUser the_identifiedBy) throws PIException {
        this.setIdentifiedByReference(
                the_identifiedBy == null ? null : ObjectReference.newObjectReference(the_identifiedBy));
    }

    public void setIdentifiedByReference(ObjectReference the_identifiedByReference) throws PIException {
        this.identifiedByReferenceValidate(the_identifiedByReference);
        this.identifiedByReference = the_identifiedByReference;
    }

    void identifiedByReferenceValidate(ObjectReference the_identifiedByReference) throws PIException {
        if (the_identifiedByReference != null && the_identifiedByReference.getReferencedClass() != null
                && !PIUser.class.isAssignableFrom(the_identifiedByReference.getReferencedClass())) {
            String msg = "The value assigned to identifiedByReference must be of the type 'ObjectReference'.";
            throw new PIException(msg);
        }
    }

    public PIProject getProject() {
        return this.projectReference != null ? (PIProject) this.projectReference.getObject() : null;
    }

    public ObjectReference getProjectReference() {
        return this.projectReference;
    }

    public void setProject(PIProject the_project) throws PIException {
        this.setProjectReference(the_project == null ? null : ObjectReference.newObjectReference(the_project));
    }

    public void setProjectReference(ObjectReference the_projectReference) throws PIException {
        this.projectReferenceValidate(the_projectReference);
        this.projectReference = the_projectReference;
    }

    void projectReferenceValidate(ObjectReference the_projectReference) throws PIException {
        if (the_projectReference != null && the_projectReference.getReferencedClass() != null
                && !PIProject.class.isAssignableFrom(the_projectReference.getReferencedClass())) {
            String msg = "The value assigned to projectReference must be of the type 'ObjectReference'.";
            throw new PIException(msg);
        }
    }

    public PIResource getRsrc() {
        return this.rsrcReference != null ? (PIResource) this.rsrcReference.getObject() : null;
    }

    public ObjectReference getRsrcReference() {
        return this.rsrcReference;
    }

    public void setRsrc(PIResource the_rsrc) throws PIException {
        this.setRsrcReference(the_rsrc == null ? null : ObjectReference.newObjectReference(the_rsrc));
    }

    public void setRsrcReference(ObjectReference the_rsrcReference) throws PIException {
        this.rsrcReferenceValidate(the_rsrcReference);
        this.rsrcReference = the_rsrcReference;
    }

    void rsrcReferenceValidate(ObjectReference the_rsrcReference) throws PIException {
        if (the_rsrcReference != null && the_rsrcReference.getReferencedClass() != null
                && !PIResource.class.isAssignableFrom(the_rsrcReference.getReferencedClass())) {
            String msg = "The value assigned to rsrcReference must be of the type 'ObjectReference'.";
            throw new PIException(msg);
        }
    }

    public PIProject getBaselineProject() {
        return this.baselineProjectReference != null ? (PIProject) this.baselineProjectReference.getObject() : null;
    }

    public ObjectReference getBaselineProjectReference() {
        return this.baselineProjectReference;
    }

    public void setBaselineProject(PIProject the_baselineProject) throws PIException {
        this.setBaselineProjectReference(
                the_baselineProject == null ? null : ObjectReference.newObjectReference(the_baselineProject));
    }

    public void setBaselineProjectReference(ObjectReference the_baselineProjectReference) throws PIException {
        this.baselineProjectReferenceValidate(the_baselineProjectReference);
        this.baselineProjectReference = the_baselineProjectReference;
    }

    void baselineProjectReferenceValidate(ObjectReference the_baselineProjectReference) throws PIException {
        if (the_baselineProjectReference != null && the_baselineProjectReference.getReferencedClass() != null
                && !PIProject.class.isAssignableFrom(the_baselineProjectReference.getReferencedClass())) {
            String msg = "The value assigned to baselineProjectReference must be of the type 'ObjectReference'.";
            throw new PIException(msg);
        }
    }

    @Override
    public Vector getContentVector() {
        return this.contentVector;
    }

    @Override
    public void setContentVector(Vector contentVector) {
        this.contentVectorValidate(contentVector);
        this.contentVector = contentVector;
    }

    void contentVectorValidate(Vector contentVector) {
    }

    @Override
    public boolean isHasContents() {
        return this.hasContents;
    }

    @Override
    public void setHasContents(boolean hasContents) {
        this.hasContentsValidate(hasContents);
        this.hasContents = hasContents;
    }

    void hasContentsValidate(boolean hasContents) {
    }

    //public HttpContentOperation getOperation() {
    //	return this.operation;
    //}

    //public void setOperation(HttpContentOperation operation) throws PIPropertyVetoException {
    //	this.operationValidate(operation);
    //	this.operation = operation;
    //}

    //void operationValidate(HttpContentOperation operation) throws PIPropertyVetoException {
    //}

    @Override
    public Vector getHttpVector() {
        return this.httpVector;
    }

    @Override
    public void setHttpVector(Vector httpVector) {
        this.httpVectorValidate(httpVector);
        this.httpVector = httpVector;
    }

    void httpVectorValidate(Vector httpVector) {
    }

    @Override
    public String getContainerName() {
        try {
            return this.getContainerReference().getName();
        } catch (NullPointerException var2) {
            return null;
        }
    }

    @Override
    public PIContainer getContainer() {
        return this.containerReference != null ? (PIContainer) this.containerReference.getObject() : null;
    }

    @Override
    public PIContainerRef getContainerReference() {
        return this.containerReference;
    }

    @Override
    public void setContainer(PIContainer the_container) throws PIException {
        this.setContainerReference(the_container == null ? null : PIContainerRef.newPIContainerRef(the_container));
    }

    @Override
    public void setContainerReference(PIContainerRef the_containerReference) {
        try {
            containerReferenceValidate(the_containerReference);
        } catch (PIException e) {
            e.printStackTrace();
        }
        this.containerReference = the_containerReference;
    }

    void containerReferenceValidate(PIContainerRef the_containerReference) throws PIException {
        if (the_containerReference != null && the_containerReference.getReferencedClass() != null) {
            if (the_containerReference != null && the_containerReference.getReferencedClass() != null
                    && !PIContainer.class.isAssignableFrom(the_containerReference.getReferencedClass())) {
                String msg = "The value assigned to containerReference must be of the type 'ObjectReference'.";
                throw new PIException(msg);
            }
        }
    }

    //public WTStringSet getEventSet() {
    //	return this.eventSet;
    //}

    //public void setEventSet(WTStringSet eventSet) {
    //	this.eventSet = eventSet;
    //}

    //public TypeDefinitionReference getTypeDefinitionReference() {
    //	return this.typeDefinitionReference;
    //}

    //public void setTypeDefinitionReference(TypeDefinitionReference typeDefinitionReference)
    //		throws PIPropertyVetoException {
    //	this.typeDefinitionReferenceValidate(typeDefinitionReference);
    //	this.typeDefinitionReference = typeDefinitionReference;
    //}

    //void typeDefinitionReferenceValidate(TypeDefinitionReference typeDefinitionReference)
    //		throws PIPropertyVetoException {
    //	if (typeDefinitionReference == null) {
    //		throw new PIPropertyVetoException("wt.fc.fcResource", "22",
    //				new Object[]{new PropertyDisplayName(CLASSNAME, "typeDefinitionReference")},
    //				new PropertyChangeEvent(this, "typeDefinitionReference", this.typeDefinitionReference,
    //						typeDefinitionReference));
    //	}
    //}

    //public AttributeContainer getAttributeContainer() {
    //	return this.theAttributeContainer;
    //}

    //public void setAttributeContainer(AttributeContainer theAttributeContainer) {
    //	this.theAttributeContainer = theAttributeContainer;
    //}

    public boolean isInheritedDomain() {
        return this.inheritedDomain;
    }

    @Override
    public void setInheritedDomain(Boolean flag) throws PIPropertyVetoException {

    }

    public void setInheritedDomain(boolean inheritedDomain) {
        this.inheritedDomainValidate(inheritedDomain);
        this.inheritedDomain = inheritedDomain;
    }

    void inheritedDomainValidate(boolean inheritedDomain) {
    }

    public AdminDomainRef getDomainRef() {
        return this.domainRef;
    }

    public void setDomainRef(AdminDomainRef domainRef) {
        this.domainRef = domainRef;
    }

    //public AdministrativeLock getAdministrativeLock() {
    //	return this.administrativeLock;
    //}

    //public void setAdministrativeLock(AdministrativeLock administrativeLock) throws PIPropertyVetoException {
    //	this.administrativeLockValidate(administrativeLock);
    //	this.administrativeLock = administrativeLock;
    //}

    //void administrativeLockValidate(AdministrativeLock administrativeLock) throws PIPropertyVetoException {
    //}

    @Override
    public LifeCycleState getState() {
        return this.state;
    }

    @Override
    public void setState(LifeCycleState state) {
        try {
            this.stateValidate(state);
        } catch (PIException e) {
            e.printStackTrace();
        }
        this.state = state;
    }

    void stateValidate(LifeCycleState state) throws PIException {
        if (state == null) {
            String msg = "The value of state cannot be set to null, since it is a required attribute.";
            throw new PIException(msg);
        }
    }

    //public String getLifeCycleName() {
    //	try {
    //		return this.getState().getLifeCycleId().getName();
    //	} catch (NullPointerException var2) {
    //		return null;
    //	}
    //}

    @Override
    public LcState getLifeCycleState() {
        try {
            return this.getState().getState();
        } catch (NullPointerException var2) {
            return null;
        }
    }

    @Override
    public boolean isLifeCycleAtGate() {
        try {
            return this.getState().isAtGate();
        } catch (NullPointerException var2) {
            return false;
        }
    }

    //public LifeCycleTemplateReference getLifeCycleTemplate() {
    //	try {
    //		return this.getState().getLifeCycleId();
    //	} catch (NullPointerException var2) {
    //		return null;
    //	}
    //}

    //public boolean isLifeCycleBasic() {
    //	try {
    //		return this.getState().getLifeCycleId().isBasic();
    //	} catch (NullPointerException var2) {
    //		return false;
    //	}
    //}

    //public AclEntrySet getEntrySet() {
    //	return this.entrySet;
    //}

    //public void setEntrySet(AclEntrySet entrySet) {
    //	this.entrySet = entrySet;
    //}

    //public TeamTemplateReference getTeamTemplateId() {
    //	return this.teamTemplateId;
    //}

    //public void setTeamTemplateId(TeamTemplateReference teamTemplateId) throws PIPropertyVetoException {
    //	this.teamTemplateIdValidate(teamTemplateId);
    //	this.teamTemplateId = teamTemplateId;
    //}

    //void teamTemplateIdValidate(TeamTemplateReference teamTemplateId) throws PIPropertyVetoException {
    //}

    //public TeamReference getTeamId() {
    //	return this.teamId;
    //}

    //public void setTeamId(TeamReference teamId) throws PIPropertyVetoException {
    //	this.teamIdValidate(teamId);
    //	this.teamId = teamId;
    //}

    //void teamIdValidate(TeamReference teamId) throws PIPropertyVetoException {
    //}

    //public String getTeamName() {
    //	try {
    //		return this.getTeamId().getName();
    //	} catch (NullPointerException var2) {
    //		return null;
    //	}
    //}

    //public String getTeamIdentity() {
    //	try {
    //		return this.getTeamId().getIdentity();
    //	} catch (NullPointerException var2) {
    //		return null;
    //	}
    //}

    //public String getTeamTemplateName() {
    //	try {
    //		return this.getTeamTemplateId().getName();
    //	} catch (NullPointerException var2) {
    //		return null;
    //	}
    //}

    //public String getTeamTemplateIdentity() {
    //	try {
    //		return this.getTeamTemplateId().getIdentity();
    //	} catch (NullPointerException var2) {
    //		return null;
    //	}
    //}

    @Override
    public LTDTypeDefinition getLTDTypeDefinition() {
        return lTDTypeDefinitionReference != null ? (LTDTypeDefinition) lTDTypeDefinitionReference.getObject() : null;
    }

    @Override
    public void setLTDTypeDefinition(LTDTypeDefinition typeDefinition) throws PIPropertyVetoException, PIException {
        this.setLTDTypeDefinitionReference(typeDefinition == null ? null : TypeDefinitionReference.newTypeDefinitionReference(typeDefinition));
    }


    public TypeDefinitionReference getLTDTypeDefinitionReference() {
        return lTDTypeDefinitionReference;
    }

    public void setLTDTypeDefinitionReference(TypeDefinitionReference lTDTypeDefinitionReference) {
        this.lTDTypeDefinitionReference = lTDTypeDefinitionReference;
    }

    @Override
    public String getConceptualClassname() {
        return CLASSNAME;
    }

    public static STProjectRisk newPIProjectRisk(PIPlannable plannable) throws PIException {
        STProjectRisk risk = new STProjectRisk();
        if (((plannable instanceof PIPlan)) || ((plannable instanceof PIPlanActivity))) {
            risk.initialize(plannable);
            return risk;
        } else {
            return risk;
        }
    }

    public static STProjectRisk newPIProjectRisk(PIProject project) throws PIException {
        STProjectRisk risk = new STProjectRisk();
        risk.initialize(project);
        return risk;
    }
    public static STProjectRisk newPIProjectRisk() throws PIException {
        STProjectRisk risk = new STProjectRisk();
        risk.initialize();
        return risk;
    }

    protected void initialize(PIPlannable plannable) throws PIException {
        super.initialize();
        setContainerReference(plannable.getContainerReference());
        setProjectReference(plannable.getProjectReference());
        //this.setDomainRef(((PolicyAccessControlled)plannable).getDomainRef());

    }

    protected void initialize(PIProject project) throws PIException {
        super.initialize();
        setContainerReference(project.getContainerReference());
        setProject(project);
        //setDomainRef(((PolicyAccessControlled)project).getDomainRef());
    }

    //public TypeDefinitionInfo getTypeDefinitionInfo()
    //{
    //	return null;
    //}

    public Object getValue() {
        return null;
    }

    public void setValue(String s, String s1) {
    }

    public String getFlexTypeIdPath() {
        return null;
    }
}