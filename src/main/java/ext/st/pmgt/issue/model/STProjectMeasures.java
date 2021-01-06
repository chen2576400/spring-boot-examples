package ext.st.pmgt.issue.model;

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
import com.pisx.tundra.foundation.lifecycle.model.LifeCycleTemplateReference;
import com.pisx.tundra.foundation.meta.type.model.LTDTypeDefinition;
import com.pisx.tundra.foundation.meta.type.model.LTDTyped;
import com.pisx.tundra.foundation.meta.type.model.TypeDefinitionReference;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIPropertyVetoException;
import com.pisx.tundra.pmgt.model.PIPmgtObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Vector;

@Entity
@Table
public class STProjectMeasures extends PIPmgtObject  implements LTDTyped, ContentHolder, PIContained, PolicyAccessControlled, LifeCycleManaged, Serializable {
    static final String CLASSNAME = STProjectMeasures.class.getName();

    @Override
    public String getConceptualClassname() {
        return CLASSNAME;
    }

    /**
     * 措施名称
     */
    @Column(nullable = false, unique = false, length = 200)
    private String name;


    /**
     * 所属项目
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "projectRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "projectRefClass", nullable = true))
    })
    ObjectReference projectReference;


    /**
     * 涉及部门
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "involveGroupReferenceId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "involveGroupReferenceClass", nullable = true))
    })
    ObjectReference involveGroupReference;

    /**
     * 预防措施
     */
    @Column(nullable = true, unique = false)
    private String precaution;


    /**
     * 项目经理确认(0未确认、1确认)
     */
    @Column(nullable = true, unique = false)
    private Boolean confirmStatus = Boolean.FALSE;

    /**
     * 涉及部门确认(0未确认、1确认)
     */
    @Column(nullable = true, unique = false)
    private Boolean involveGroupStatus = Boolean.FALSE;


    /**
     * 责任人
     */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "dutyUserRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "dutyUserRefClass", nullable = true))
    })
    ObjectReference dutyUserReference;


    /**
     * 关联风险
     */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "projectRiskRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "projectRiskRefClass", nullable = true))
    })
    ObjectReference projectRiskReference;

    @Transient
    boolean inheritedDomain;
    @Transient
    Vector contentVector;
    @Transient
    boolean hasContents;

    @Transient
    Vector httpVector;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "domainRefId", nullable = false)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "domainRefClass", nullable = false))
    })
    AdminDomainRef domainRef;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "containerRefId", nullable = false)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "containerRefClass", nullable = false))
    })
    PIContainerRef containerReference;

    @Embedded
    LifeCycleState state;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "lTDTypeDefinitionRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "lTDTypeDefinitionRefClass", nullable = true))
    })
    TypeDefinitionReference lTDTypeDefinitionReference;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectReference getProjectRiskReference() {
        return projectRiskReference;
    }

    public void setProjectRiskReference(ObjectReference projectRiskReference) {
        this.projectRiskReference = projectRiskReference;
    }

    public ObjectReference getProjectReference() {
        return projectReference;
    }

    public void setProjectReference(ObjectReference projectReference) {
        this.projectReference = projectReference;
    }

    public ObjectReference getInvolveGroupReference() {
        return involveGroupReference;
    }

    public void setInvolveGroupReference(ObjectReference involveGroupReference) {
        this.involveGroupReference = involveGroupReference;
    }

    public PIGroup getInvolveGroup() {
        return involveGroupReference!=null?(PIGroup) involveGroupReference.getObject():null;
    }

    public void setInvolveGroup(PIGroup piGroup) throws PIException {
        this.involveGroupReference = ObjectReference.newObjectReference(piGroup);;
    }


    public String getPrecaution() {
        return precaution;
    }

    public void setPrecaution(String precaution) {
        this.precaution = precaution;
    }

    public Boolean getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Boolean confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public Boolean getInvolveGroupStatus() {
        return involveGroupStatus;
    }

    public void setInvolveGroupStatus(Boolean involveGroupStatus) {
        this.involveGroupStatus = involveGroupStatus;
    }

    public ObjectReference getDutyUserReference() {
        return dutyUserReference;
    }

    public void setDutyUserReference(ObjectReference dutyUserReference) {
        this.dutyUserReference = dutyUserReference;
    }





    public TypeDefinitionReference getlTDTypeDefinitionReference() {
        return lTDTypeDefinitionReference;
    }

    public void setlTDTypeDefinitionReference(TypeDefinitionReference lTDTypeDefinitionReference) {
        this.lTDTypeDefinitionReference = lTDTypeDefinitionReference;
    }

    public boolean isInheritedDomain() {
        return inheritedDomain;
    }

    @Override
    public void setInheritedDomain(Boolean flag) throws PIPropertyVetoException {

    }

    public void setInheritedDomain(boolean inheritedDomain) {
        inheritedDomainValidate(inheritedDomain);
        this.inheritedDomain = inheritedDomain;
    }

    void inheritedDomainValidate(boolean inheritedDomain) {
    }



    public AdminDomainRef getDomainRef() {
        return domainRef;
    }

    public void setDomainRef(AdminDomainRef domainRef) {
        this.domainRef = domainRef;
    }

    @Override
    public Vector getContentVector() {
        return contentVector;
    }

    @Override
    public void setContentVector(Vector contentVector) {
        contentVectorValidate(contentVector);
        this.contentVector = contentVector;
    }

    void contentVectorValidate(Vector contentVector) {
    }

    @Override
    public boolean isHasContents() {
        return hasContents;
    }


    @Override
    public void setHasContents(boolean hasContents) {
        hasContentsValidate(hasContents);
        this.hasContents = hasContents;
    }

    void hasContentsValidate(boolean hasContents) {
    }



    @Override
    public Vector getHttpVector() {
        return httpVector;
    }


    @Override
    public void setHttpVector(Vector httpVector) {
        httpVectorValidate(httpVector);
        this.httpVector = httpVector;
    }

    void httpVectorValidate(Vector httpVector) {
    }


    @Override
    public void setContainerReference(PIContainerRef the_containerReference) {
        try {
            containerReferenceValidate(the_containerReference);
        } catch (PIException e) {
            e.printStackTrace();
        }
        containerReference = (PIContainerRef) the_containerReference;
    }
    void containerReferenceValidate(PIContainerRef the_containerReference) throws PIException {
        if (the_containerReference != null && the_containerReference.getReferencedClass() != null &&
                !PIContainer.class.isAssignableFrom(the_containerReference.getReferencedClass())) {
            String msg = "The value assigned to containerReference must be of the type 'ObjectReference'.";
            throw new PIException(msg);
        }
    }

    @Override
    public PIContainerRef getContainerReference() {
        return containerReference;
    }

    @Override
    public String getContainerName() {
        try {
            return (String) ((PIContainerRef) getContainerReference()).getName();
        } catch (java.lang.NullPointerException npe) {
            return null;
        }
    }

    @Override
    public PIContainer getContainer() {
        return (containerReference != null) ? (PIContainer) containerReference.getObject() : null;
    }


    @Override
    public void setContainer(PIContainer the_container) throws PIException, PIPropertyVetoException {
        setContainerReference(the_container == null ? null : PIContainerRef.newPIContainerRef((PIContainer) the_container));

    }

    @Override
    public LifeCycleState getState() {
        return state;
    }

    @Override
    public void setState(LifeCycleState state) throws PIPropertyVetoException {
        try {
            stateValidate(state);
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
    @Override
    public LcState getLifeCycleState() {
        try {
            return (LcState) ((LifeCycleState) getState()).getState();
        } catch (java.lang.NullPointerException npe) {
            return null;
        }
    }

    @Override
    public boolean isLifeCycleAtGate() {
        try {
            return (boolean) ((LifeCycleState) getState()).isAtGate();
        } catch (java.lang.NullPointerException npe) {
            return false;
        }
    }

    @Override
    public LifeCycleTemplateReference getLifeCycleTemplate() {
        return null;
    }

    @Override
    public LTDTypeDefinition getLTDTypeDefinition() {
        return lTDTypeDefinitionReference != null ? (LTDTypeDefinition) lTDTypeDefinitionReference.getObject() : null;
    }

    @Override
    public TypeDefinitionReference getLTDTypeDefinitionReference() {
        return lTDTypeDefinitionReference;
    }

    @Override
    public void setLTDTypeDefinition(LTDTypeDefinition typeDefinition) throws PIPropertyVetoException, PIException {
        this.setLTDTypeDefinitionReference(typeDefinition == null ? null : TypeDefinitionReference.newTypeDefinitionReference(typeDefinition));

    }

    @Override
    public void setLTDTypeDefinitionReference(TypeDefinitionReference typeReference) throws PIPropertyVetoException {
        this.lTDTypeDefinitionReference = lTDTypeDefinitionReference;

    }




    public static STProjectMeasures newSTProjectMeasures() throws PIException {
        STProjectMeasures obj = new STProjectMeasures();
        obj.initialize();
        return obj;
    }
}