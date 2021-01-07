package ext.st.pmgt.issue.model;

/*

solution

issueNumber
project
responsibleUser
priorityType
issueType
description
state---
name
thresh---
trackView---
root
planActivity
rsrc
thresh_parm_id----
baselineProject
workspace---
issueValue---
lowerParmValue----
higherParmValue---
addedBy
expectedSolutionDate
resolvedDate
addDate
contentHolder-----

*/

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
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.team.model.TeamReference;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIPropertyVetoException;
import com.pisx.tundra.pmgt.change.model.ProjectIssuePriorityType;
import com.pisx.tundra.pmgt.change.model.ProjectIssueType;
import com.pisx.tundra.pmgt.model.PIPmgtObject;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.plan.model.PIPlanRootable;
import com.pisx.tundra.pmgt.plan.model.PIPlannable;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.resource.model.PIResource;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Vector;

/**
 * the class for plan issue objects..
 */
// wt.workflow.notebook.SubjectOfNotebook, wt.notify.Notifiable, wt.type.Typed,wt.workflow.forum.SubjectOfForum,
@Entity
@Table
public class STProjectIssue extends PIPmgtObject implements LTDTyped, ContentHolder, PIContained, PolicyAccessControlled, LifeCycleManaged, Serializable {
    static final long serialVersionUID = 1;
    static final String CLASSNAME = STProjectIssue.class.getName();


    @Column(nullable = false, unique = true, length = 200)
    private String issueNumber;

    @Column(nullable = false, unique = false, length = 200)
    private String name;

    @Column(nullable = true, unique = false, length = 200)
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "issueType", nullable = false))
    })
    ProjectIssueType issueType = ProjectIssueType.getProjectIssueTypeDefault();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "priorityType", nullable = false))
    })
    ProjectIssuePriorityType priorityType = ProjectIssuePriorityType.getProjectIssuePriorityTypeDefault();

    @Column(nullable = false, unique = false)
    private Timestamp addDate;

    @Column(nullable = true, unique = false, length = 200)
    private String addedBy;

    @Column(nullable = true, unique = false)
    private Timestamp expectedSolutionDate;

    @Column(nullable = true, unique = false)
    private Timestamp resolvedDate;

    @Column(nullable = true, unique = false, length = 1000)
    private String solution;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "responsibleUserRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "responsibleUserRefClass", nullable = true))
    })
    ObjectReference responsibleUserReference;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "projectRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "projectRefClass", nullable = true))
    })
    ObjectReference projectReference;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "rootRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "rootRefClass", nullable = true))
    })
    ObjectReference rootReference;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "planActivityRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "planActivityRefClass", nullable = true))
    })
    ObjectReference planActivityReference;

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

    @Transient
    Vector httpVector;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "containerRefId", nullable = false)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "containerRefClass", nullable = false))
    })
    PIContainerRef containerReference;

    @Transient
    boolean inheritedDomain;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "domainRefId", nullable = false)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "domainRefClass", nullable = false))
    })
    AdminDomainRef domainRef;

    @Embedded
    LifeCycleState state;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "lTDTypeDefinitionRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "lTDTypeDefinitionRefClass", nullable = true))
    })
    TypeDefinitionReference lTDTypeDefinitionReference;

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        issueNumberValidate(issueNumber);
        this.issueNumber = issueNumber;
    }

    void issueNumberValidate(String issueNumber) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        nameValidate(name);
        this.name = name;
    }

    void nameValidate(String name) {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        descriptionValidate(description);
        this.description = description;
    }

    void descriptionValidate(String description) {
    }

    public ProjectIssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(ProjectIssueType issueType) {
        issueTypeValidate(issueType);
        this.issueType = issueType;
    }

    void issueTypeValidate(ProjectIssueType issueType) {
    }

    public ProjectIssuePriorityType getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(ProjectIssuePriorityType priorityType) {
        priorityTypeValidate(priorityType);
        this.priorityType = priorityType;
    }

    void priorityTypeValidate(ProjectIssuePriorityType priorityType) {
    }

    public Timestamp getAddDate() {
        return addDate;
    }

    public void setAddDate(Timestamp addDate) {
        addDateValidate(addDate);
        this.addDate = addDate;
    }

    void addDateValidate(Timestamp addDate) {
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        addedByValidate(addedBy);
        this.addedBy = addedBy;
    }

    void addedByValidate(String addedBy) {
    }

    public Timestamp getExpectedSolutionDate() {
        return expectedSolutionDate;
    }

    public void setExpectedSolutionDate(Timestamp expectedSolutionDate) {
        expectedSolutionDateValidate(expectedSolutionDate);
        this.expectedSolutionDate = expectedSolutionDate;
    }

    void expectedSolutionDateValidate(Timestamp expectedSolutionDate) {
    }

    public Timestamp getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(Timestamp resolvedDate) {
        resolvedDateValidate(resolvedDate);
        this.resolvedDate = resolvedDate;
    }

    void resolvedDateValidate(Timestamp resolvedDate) {
    }


    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        solutionValidate(solution);
        this.solution = solution;
    }

    void solutionValidate(String solution) {
    }

    public PIUser getResponsibleUser() {
        return (responsibleUserReference != null) ? (PIUser) responsibleUserReference.getObject() : null;
    }

    public ObjectReference getResponsibleUserReference() {
        return responsibleUserReference;
    }

    public void setResponsibleUser(PIUser the_responsibleUser) throws PIException {
        setResponsibleUserReference(the_responsibleUser == null ? null : ObjectReference.newObjectReference((PIUser) the_responsibleUser));
    }

    public void setResponsibleUserReference(ObjectReference the_responsibleUserReference) throws PIException {
        responsibleUserReferenceValidate(the_responsibleUserReference);
        responsibleUserReference = (ObjectReference) the_responsibleUserReference;
    }

    void responsibleUserReferenceValidate(ObjectReference the_responsibleUserReference) throws PIException {
        if (the_responsibleUserReference != null && the_responsibleUserReference.getReferencedClass() != null &&
                !PIUser.class.isAssignableFrom(the_responsibleUserReference.getReferencedClass())) {
            String msg = "The value assigned to responsibleUserReference must be of the type 'ObjectReference'.";
            throw new PIException(msg);
        }
    }

    public PIProject getProject() {
        return (projectReference != null) ? (PIProject) projectReference.getObject() : null;
    }

    public ObjectReference getProjectReference() {
        return projectReference;
    }

    public void setProject(PIProject the_project) throws PIException {
        setProjectReference(the_project == null ? null : ObjectReference.newObjectReference((PIProject) the_project));
    }

    public void setProjectReference(ObjectReference the_projectReference) throws PIException {
        projectReferenceValidate(the_projectReference);
        projectReference = (ObjectReference) the_projectReference;
    }

    void projectReferenceValidate(ObjectReference the_projectReference) throws PIException {
        if (the_projectReference != null && the_projectReference.getReferencedClass() != null &&
                !PIProject.class.isAssignableFrom(the_projectReference.getReferencedClass())) {
            String msg = "The value assigned to projectReference must be of the type 'ObjectReference'.";
            throw new PIException(msg);
        }
    }

    public PIPlanRootable getRoot() {
        return (rootReference != null) ? (PIPlanRootable) rootReference.getObject() : null;
    }

    public ObjectReference getRootReference() {
        return rootReference;
    }

    public void setRoot(PIPlanRootable the_root) throws PIException {
        setRootReference(the_root == null ? null : ObjectReference.newObjectReference((PIPlanRootable) the_root));
    }

    public void setRootReference(ObjectReference the_rootReference) throws PIException {
        rootReferenceValidate(the_rootReference);
        rootReference = (ObjectReference) the_rootReference;
    }

    void rootReferenceValidate(ObjectReference the_rootReference) throws PIException {
        if (the_rootReference != null && the_rootReference.getReferencedClass() != null &&
                !PIPlanRootable.class.isAssignableFrom(the_rootReference.getReferencedClass())) {
            String msg = "The value assigned to rootReference must be of the type 'ObjectReference'.";
            throw new PIException(msg);
        }
    }


    public PIPlannable getPlanActivity() {
        return (planActivityReference != null) ? (PIPlannable) planActivityReference.getObject() : null;
    }

    public ObjectReference getPlanActivityReference() {
        return planActivityReference;
    }

    public void setPlanActivity(PIPlannable the_planActivity) throws PIException {
        setPlanActivityReference(the_planActivity == null ? null : ObjectReference.newObjectReference((PIPlannable) the_planActivity));
    }

    public void setPlanActivityReference(ObjectReference the_planActivityReference) throws PIException {
        planActivityReferenceValidate(the_planActivityReference);
        planActivityReference = (ObjectReference) the_planActivityReference;
    }

    void planActivityReferenceValidate(ObjectReference the_planActivityReference) throws PIException {
        if (the_planActivityReference != null && the_planActivityReference.getReferencedClass() != null &&
                !PIPlannable.class.isAssignableFrom(the_planActivityReference.getReferencedClass())) {
            String msg = "The value assigned to planActivityReference must be of the type 'ObjectReference'.";
            throw new PIException(msg);
        }
    }

    public PIResource getRsrc() {
        return (rsrcReference != null) ? (PIResource) rsrcReference.getObject() : null;
    }

    public ObjectReference getRsrcReference() {
        return rsrcReference;
    }

    public void setRsrc(PIResource the_rsrc) throws PIException {
        setRsrcReference(the_rsrc == null ? null : ObjectReference.newObjectReference((PIResource) the_rsrc));
    }

    public void setRsrcReference(ObjectReference the_rsrcReference) throws PIException {
        rsrcReferenceValidate(the_rsrcReference);
        rsrcReference = (ObjectReference) the_rsrcReference;
    }

    void rsrcReferenceValidate(ObjectReference the_rsrcReference) throws PIException {
        if (the_rsrcReference != null && the_rsrcReference.getReferencedClass() != null &&
                !PIResource.class.isAssignableFrom(the_rsrcReference.getReferencedClass())) {
            String msg = "The value assigned to rsrcReference must be of the type 'ObjectReference'.";
            throw new PIException(msg);
        }
    }

    public PIProject getBaselineProject() {
        return (baselineProjectReference != null) ? (PIProject) baselineProjectReference.getObject() : null;
    }

    public ObjectReference getBaselineProjectReference() {
        return baselineProjectReference;
    }

    public void setBaselineProject(PIProject the_baselineProject) throws PIException {
        setBaselineProjectReference(the_baselineProject == null ? null : ObjectReference.newObjectReference((PIProject) the_baselineProject));
    }

    public void setBaselineProjectReference(ObjectReference the_baselineProjectReference) throws PIException {
        baselineProjectReferenceValidate(the_baselineProjectReference);
        baselineProjectReference = (ObjectReference) the_baselineProjectReference;
    }

    void baselineProjectReferenceValidate(ObjectReference the_baselineProjectReference) throws PIException {
        if (the_baselineProjectReference != null && the_baselineProjectReference.getReferencedClass() != null &&
                !PIProject.class.isAssignableFrom(the_baselineProjectReference.getReferencedClass())) {
            String msg = "The value assigned to baselineProjectReference must be of the type 'ObjectReference'.";
            throw new PIException(msg);
        }
    }

    /**
     * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
     *
     * @see ContentHolder
     */
    @Override
    public Vector getContentVector() {
        return contentVector;
    }

    /**
     * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
     *
     * @see ContentHolder
     */
    @Override
    public void setContentVector(Vector contentVector) {
        contentVectorValidate(contentVector);
        this.contentVector = contentVector;
    }

    void contentVectorValidate(Vector contentVector) {
    }

    /**
     * This is a non-persistent variable that is used to pass information from server to client.  Should not be directly accessed by the client
     *
     * @see ContentHolder
     */
    @Override
    public boolean isHasContents() {
        return hasContents;
    }

    /**
     * This is a non-persistent variable that is used to pass information from server to client.  Should not be directly accessed by the client
     *
     * @see ContentHolder
     */
    @Override
    public void setHasContents(boolean hasContents) {
        hasContentsValidate(hasContents);
        this.hasContents = hasContents;
    }

    void hasContentsValidate(boolean hasContents) {
    }

    //HttpContentOperation operation;
    /**
     * @see ContentHolder
     */
    //public wt.content.HttpContentOperation getOperation() {
    //   return operation;
    //}
    /**
     * @see ContentHolder
     */
    //public void setOperation(wt.content.HttpContentOperation operation){
    //   operationValidate(operation);
    //   this.operation = operation;
    //}
    //void operationValidate(wt.content.HttpContentOperation operation){
    //}

    /**
     * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
     *
     * @see ContentHolder
     */
    @Override
    public Vector getHttpVector() {
        return httpVector;
    }

    /**
     * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
     *
     * @see ContentHolder
     */
    @Override
    public void setHttpVector(Vector httpVector) {
        httpVectorValidate(httpVector);
        this.httpVector = httpVector;
    }

    void httpVectorValidate(Vector httpVector) {
    }

    /**
     * Derived from {@link PIContainerRef#getName()}
     *
     * @see PIContained
     */
    @Override
    public String getContainerName() {
        try {
            return (String) ((PIContainerRef) getContainerReference()).getName();
        } catch (java.lang.NullPointerException npe) {
            return null;
        }
    }

    /**
     * @see PIContained
     */
    @Override
    public PIContainer getContainer() {
        return (containerReference != null) ? (PIContainer) containerReference.getObject() : null;
    }

    /**
     * @see PIContained
     */
    @Override
    public PIContainerRef getContainerReference() {
        return containerReference;
    }

    /**
     * @see PIContained
     */
    @Override
    public void setContainer(PIContainer the_container) throws PIException {
        setContainerReference(the_container == null ? null : PIContainerRef.newPIContainerRef((PIContainer) the_container));
    }

    /**
     * @see PIContained
     */
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

    //wt.fc.WTStringSet eventSet;
    /**
     * Sets of events for which the object has a subscriber.
     *
     * @see wt.notify.Notifiable
     */
    //public wt.fc.WTStringSet getEventSet() {
    //   return eventSet;
    //}
    /**
     * Sets of events for which the object has a subscriber.
     *
     * @see wt.notify.Notifiable
     */
    //public void setEventSet(wt.fc.WTStringSet eventSet) {
    //   this.eventSet = eventSet;
    //}

    //wt.type.TypeDefinitionReference typeDefinitionReference;
    /**
     * @see wt.type.Typed
     */
    //public wt.type.TypeDefinitionReference getTypeDefinitionReference() {
    //   return typeDefinitionReference;
    //}
    /**
     * @see wt.type.Typed
     */
    //public void setTypeDefinitionReference(wt.type.TypeDefinitionReference typeDefinitionReference){
    //   typeDefinitionReferenceValidate(typeDefinitionReference);
    //   this.typeDefinitionReference = typeDefinitionReference;
    //}
    //void typeDefinitionReferenceValidate(wt.type.TypeDefinitionReference typeDefinitionReference){
    //   if (typeDefinitionReference == null)
    //      throw new wt.util.PIPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
    //            new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "typeDefinitionReference") },
    //            new java.beans.PropertyChangeEvent(this, "typeDefinitionReference", this.typeDefinitionReference, typeDefinitionReference));
    //}

    //wt.iba.value.AttributeContainer theAttributeContainer;
    /**
     * @see wt.iba.value.IBAHolder
     */
    //public wt.iba.value.AttributeContainer getAttributeContainer() {
    //   return theAttributeContainer;
    //}
    /**
     * @see //wt.iba.value.IBAHolder
     */
    //public void setAttributeContainer(wt.iba.value.AttributeContainer theAttributeContainer) {
    //   this.theAttributeContainer = theAttributeContainer;
    //}

    /**
     * A boolean indicating whether the administrative domain is inherited. If the value is true, the domain is inherited from a parent object.
     *
     * @see //wt.admin.DomainAdministered
     */
    public boolean isInheritedDomain() {
        return inheritedDomain;
    }

    @Override
    public void setInheritedDomain(Boolean flag) throws PIPropertyVetoException {

    }

    /**
     * A boolean indicating whether the administrative domain is inherited. If the value is true, the domain is inherited from a parent object.
     *
     * @see //wt.admin.DomainAdministered
     */
    public void setInheritedDomain(boolean inheritedDomain) {
        inheritedDomainValidate(inheritedDomain);
        this.inheritedDomain = inheritedDomain;
    }

    void inheritedDomainValidate(boolean inheritedDomain) {
    }

    /**
     * @see //wt.admin.DomainAdministered
     */
    public AdminDomainRef getDomainRef() {
        return domainRef;
    }

    /**
     * @see //wt.admin.DomainAdministered
     */
    public void setDomainRef(AdminDomainRef domainRef) {
        this.domainRef = domainRef;
    }

    //wt.fc.adminlock.AdministrativeLock administrativeLock;
    /**
     * An administrative lock. An administrative lock requires some form of validation to determine whether an authenticated user is authorized to perform actions on an instance.<P><B>Note:</B>This property is only to be modified by the <code>wt.fc.adminlock</code> package. Other code should use the methods provided by the <code>wt.fc.adminlock.AdministrativeLockHelper</code> and <code>wt.fc.adminlock.AdministrativeLockServerHelper</code> to get or set the administrative lock.
     *
     * @see wt.fc.adminlock.AdministrativelyLockable
     */
    //public wt.fc.adminlock.AdministrativeLock getAdministrativeLock() {
    //   return administrativeLock;
    //}
    /**
     * An administrative lock. An administrative lock requires some form of validation to determine whether an authenticated user is authorized to perform actions on an instance.<P><B>Note:</B>This property is only to be modified by the <code>wt.fc.adminlock</code> package. Other code should use the methods provided by the <code>wt.fc.adminlock.AdministrativeLockHelper</code> and <code>wt.fc.adminlock.AdministrativeLockServerHelper</code> to get or set the administrative lock.
     *
     * @see //wt.fc.adminlock.AdministrativelyLockable
     */
    //public void setAdministrativeLock(wt.fc.adminlock.AdministrativeLock administrativeLock){
    //   administrativeLockValidate(administrativeLock);
    //   this.administrativeLock = administrativeLock;
    //}
    //void administrativeLockValidate(wt.fc.adminlock.AdministrativeLock administrativeLock){
    //}

    /**
     * <b>Supported API: </b>true
     *
     * @see LifeCycleManaged
     */
    @Override
    public LifeCycleState getState() {
        return state;
    }

    /**
     * @see LifeCycleManaged
     */
    @Override
    public void setState(LifeCycleState state) {
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

    /**
     * Derived from {@link wt.lifecycle.LifeCycleTemplateReference#getName()}
     *
     * @see LifeCycleManaged
     */
    //public String getLifeCycleName() {
    //   try { return (String) ((LifeCycleTemplateReference) ((LifeCycleState) getState()).getLifeCycleId()).getName(); }
    //   catch (java.lang.NullPointerException npe) { return null; }
    //}

    /**
     * Derived from {@link LifeCycleState#getState()}
     * <p>
     * <b>Supported API: </b>true
     *
     * @see LifeCycleManaged
     */
    @Override
    public LcState getLifeCycleState() {
        try {
            return (LcState) ((LifeCycleState) getState()).getState();
        } catch (java.lang.NullPointerException npe) {
            return null;
        }
    }

    /**
     * Derived from {@link LifeCycleState#isAtGate()}
     *
     * @see LifeCycleManaged
     */
    @Override
    public boolean isLifeCycleAtGate() {
        try {
            return (boolean) ((LifeCycleState) getState()).isAtGate();
        } catch (java.lang.NullPointerException npe) {
            return false;
        }
    }

    /**
     * Derived from {@link LifeCycleState#getLifeCycleId()}
     *
     * @see LifeCycleManaged
     */
    //public wt.lifecycle.LifeCycleTemplateReference getLifeCycleTemplate() {
    //   try { return (wt.lifecycle.LifeCycleTemplateReference) ((LifeCycleState) getState()).getLifeCycleId(); }
    //   catch (java.lang.NullPointerException npe) { return null; }
    //}

    /**
     * Derived from {@link wt.lifecycle.LifeCycleTemplateReference#isBasic()}
     *
     * @see LifeCycleManaged
     */
    //public boolean isLifeCycleBasic() {
    //   try { return (boolean) ((wt.lifecycle.LifeCycleTemplateReference) ((LifeCycleState) getState()).getLifeCycleId()).isBasic(); }
    //   catch (java.lang.NullPointerException npe) { return false; }
    //}

    //wt.access.AclEntrySet entrySet;
    /**
     * @see wt.access.AdHocControlled
     */
    //public wt.access.AclEntrySet getEntrySet() {
    //   return entrySet;
    //}
    /**
     * @see wt.access.AdHocControlled
     */
    //public void setEntrySet(wt.access.AclEntrySet entrySet) {
    //   this.entrySet = entrySet;
    //}

    //wt.team.TeamTemplateReference teamTemplateId;
    /**
     * <b>Supported API: </b>true
     *
     * @see wt.team.TeamManaged
     */
    //public wt.team.TeamTemplateReference getTeamTemplateId() {
    //   return teamTemplateId;
    //}
    /**
     * @see wt.team.TeamManaged
     */
    //public void setTeamTemplateId(wt.team.TeamTemplateReference teamTemplateId){
    //   teamTemplateIdValidate(teamTemplateId);
    //   this.teamTemplateId = teamTemplateId;
    //}
    //void teamTemplateIdValidate(wt.team.TeamTemplateReference teamTemplateId){
    //}

    //wt.team.TeamReference teamId;
    /**
     * <b>Supported API: </b>true
     *
     * @see wt.team.TeamManaged
     */
    //public wt.team.TeamReference getTeamId() {
    //   return teamId;
    //}
    /**
     * @see wt.team.TeamManaged
     */
    //public void setTeamId(wt.team.TeamReference teamId){
    //   teamIdValidate(teamId);
    //   this.teamId = teamId;
    //}
    //void teamIdValidate(wt.team.TeamReference teamId){
    //}

    /**
     * Derived from {@link wt.team.TeamReference#getName()}
     *
     * @see wt.team.TeamManaged
     */
    //public String getTeamName() {
    //   try { return (String) ((wt.team.TeamReference) getTeamId()).getName(); }
    //   catch (java.lang.NullPointerException npe) { return null; }
    //}

    /**
     * Derived from {@link wt.team.TeamReference#getIdentity()}
     *
     * @see wt.team.TeamManaged
     */
    //public String getTeamIdentity() {
    //   try { return (String) ((wt.team.TeamReference) getTeamId()).getIdentity(); }
    //   catch (java.lang.NullPointerException npe) { return null; }
    //}

    /**
     * Derived from {@link wt.team.TeamTemplateReference#getName()}
     *
     * @see wt.team.TeamManaged
     */
    //public String getTeamTemplateName() {
    //   try { return (String) ((wt.team.TeamTemplateReference) getTeamTemplateId()).getName(); }
    //   catch (java.lang.NullPointerException npe) { return null; }
    //}

    /**
     * Derived from {@link //wt.team.TeamTemplateReference#getIdentity()}
     *
     * @see //wt.team.TeamManaged
     */
    //public String getTeamTemplateIdentity() {
    //   try { return (String) ((wt.team.TeamTemplateReference) getTeamTemplateId()).getIdentity(); }
    //   catch (java.lang.NullPointerException npe) { return null; }
    //}
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

    @Override
    public String getConceptualClassname() {
        return CLASSNAME;
    }

    //public void setName(String paramString)
    //{
    //   if (paramString != null) {
    //      paramString = paramString.trim();
    //   }
    //   nameValidate(paramString);
    //   this.name = paramString;
    //}

    public static STProjectIssue newSTProjectIssue(PIPlannable plannable) throws PIException, PIPropertyVetoException {
        STProjectIssue issue = new STProjectIssue();
        if (((plannable instanceof PIPlan)) || ((plannable instanceof PIPlanActivity))) {

            issue.initialize(plannable);
            return issue;
        } else {
            return issue;
        }
    }

    public static STProjectIssue newSTProjectIssue() throws PIException, PIPropertyVetoException {
        STProjectIssue issue = new STProjectIssue();
        issue.initialize();
        return issue;

    }

    protected void initialize(PIPlannable plannable) throws PIException, PIPropertyVetoException {
        super.initialize();
        setContainerReference(plannable.getContainerReference());
        setProjectReference(plannable.getProjectReference());
        setPlanActivity(plannable);
        if (plannable instanceof PIPlan) {
            setRoot((PIPlan) plannable);
        } else {
            setRootReference(plannable.getRootReference());
        }
        //this.setDomainRef(((PolicyAccessControlled)plannable).getDomainRef());
    }

    //public TypeDefinitionInfo getTypeDefinitionInfo()
    //{
    //   return null;
    //}

    public Object getValue() {
        return null;
    }

    public void setValue(String s, String s1) {
    }

    public String getFlexTypeIdPath() {
        return null;
    }


    /**
     * 风险关联
     */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "projectRiskRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "projectRiskRefClass", nullable = true))
    })
    ObjectReference projectRiskReference;


    /**
     * 项目经理确认(0未确认、1确认)
     */
    @Column(nullable = true, unique = false)
    private Boolean confirmStatus = Boolean.FALSE;


    /**
     * 处理方案
     */
    @Column(nullable = true, unique = false)
    private String treatmentPlan;


    /**
     * 责任部门
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "dutyGroupReferenceId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "dutyGroupReferenceClass", nullable = true))
    })
    ObjectReference dutyGroupReference;

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
     * 关闭时间
     */
    @Column(updatable = true, nullable = true)
    Timestamp closeStamp;


    /**
     * 有效性（0有效，1无效）
     */
    @Column(nullable = true, unique = false)
    private Integer validStatus;


    public ObjectReference getProjectRiskReference() {
        return projectRiskReference;
    }

    public void setProjectRiskReference(ObjectReference projectRiskReference) {
        this.projectRiskReference = projectRiskReference;
    }


    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public TypeDefinitionReference getlTDTypeDefinitionReference() {
        return lTDTypeDefinitionReference;
    }

    public void setlTDTypeDefinitionReference(TypeDefinitionReference lTDTypeDefinitionReference) {
        this.lTDTypeDefinitionReference = lTDTypeDefinitionReference;
    }

    public ObjectReference getDutyGroupReference() {
        return dutyGroupReference;
    }

    public void setDutyGroupReference(ObjectReference dutyGroupReference) {
        this.dutyGroupReference = dutyGroupReference;
    }
    public PIGroup getDutyGroup() {
        return dutyGroupReference!=null?(PIGroup) dutyGroupReference.getObject():null;
    }

    public void setPIGroup(PIGroup piGroup) throws PIException {
        this.dutyGroupReference = ObjectReference.newObjectReference(piGroup);;
    }

    public ObjectReference getInvolveGroupReference() {
        return involveGroupReference;
    }

    public void setInvolveGroupReference(ObjectReference involveGroupReference) {
        this.involveGroupReference = involveGroupReference;
    }

    public Timestamp getCloseStamp() {
        return closeStamp;
    }

    public void setCloseStamp(Timestamp closeStamp) {
        this.closeStamp = closeStamp;
    }


    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public Boolean getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Boolean confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    @Override
    public LifeCycleTemplateReference getLifeCycleTemplate() {
        return null;
    }

    @Override
    public boolean isLifeCycleBasic() {
        return false;
    }

    @Override
    public TeamReference getTeamReference() {
        return null;
    }

    @Override
    public void setTeamReference(TeamReference paramTeamReference) {

    }

    @Override
    public String getTeamName() {
        return null;
    }
}
