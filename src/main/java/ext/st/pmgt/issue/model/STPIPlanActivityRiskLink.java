
package ext.st.pmgt.issue.model;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.ObjectToObjectLink;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.plan.model.AbstractPIPlanActivity;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanRootable;
import com.pisx.tundra.pmgt.project.model.PIProject;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="STPIPlanActivityRiskLink",  uniqueConstraints = {
        @UniqueConstraint(name = "STPIPlanActivityRiskLinkUFields1", columnNames = {"roleAObjectId", "roleAObjectClass", "roleBObjectId","roleBObjectClass"})
})
public class STPIPlanActivityRiskLink extends ObjectToObjectLink implements Serializable {
    static final long serialVersionUID = 1L;
    static final String CLASSNAME = STPIPlanActivityRiskLink.class.getName();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "rootRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "rootRefClass", nullable = true))
    })
    ObjectReference rootReference;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "projectRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "projectRefClass", nullable = true))
    })
    ObjectReference projectReference;

    public STPIPlanActivityRiskLink() {
    }

    public PIPlanRootable getRoot() {
        return this.rootReference != null ? (PIPlanRootable)this.rootReference.getObject() : null;
    }

    public ObjectReference getRootReference() {
        return this.rootReference;
    }

    public void setRoot(PIPlanRootable the_root) throws PIException {
        this.setRootReference(the_root == null ? null : ObjectReference.newObjectReference(the_root));
    }

    public void setRootReference(ObjectReference the_rootReference) throws PIException {
        this.rootReferenceValidate(the_rootReference);
        this.rootReference = the_rootReference;
    }

    void rootReferenceValidate(ObjectReference the_rootReference) throws PIException {
        if (the_rootReference != null && the_rootReference.getReferencedClass() != null && !PIPlanRootable.class.isAssignableFrom(the_rootReference.getReferencedClass())) {
            String msg= "The value assigned to rootReference must be of the type 'ObjectReference'.";
            throw new PIException(msg);
        }
    }

    public PIProject getProject() {
        return this.projectReference != null ? (PIProject)this.projectReference.getObject() : null;
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
        if (the_projectReference != null && the_projectReference.getReferencedClass() != null && !PIProject.class.isAssignableFrom(the_projectReference.getReferencedClass())) {
            String msg= "The value assigned to projectReference must be of the type 'ObjectReference'.";
            throw new PIException(msg);
        }
    }

    public AbstractPIPlanActivity getPlanActivity() {
        return (AbstractPIPlanActivity)this.getRoleAObject();
    }

    public void setPlanActivity(AbstractPIPlanActivity the_planActivity) {
        this.setRoleAObject(the_planActivity);
    }

    public STProjectRisk getRisk() {
        return (STProjectRisk)this.getRoleBObject();
    }

    public void setRisk(STProjectRisk the_risk) {
        this.setRoleBObject(the_risk);
    }

    @Override
    public String getConceptualClassname() {
        return CLASSNAME;
    }

    public static STPIPlanActivityRiskLink newPIPlanActivityRiskLink(AbstractPIPlanActivity act, STProjectRisk  risk) throws PIException {
        STPIPlanActivityRiskLink link = new STPIPlanActivityRiskLink();
        link.initialize(act, risk);
        return link;
    }

    public static STPIPlanActivityRiskLink newPIPlanActivityRiskLink() throws PIException {
        STPIPlanActivityRiskLink link = new STPIPlanActivityRiskLink();
        link.initialize();
        return link;
    }


    public  void initialize(AbstractPIPlanActivity act, STProjectRisk  risk) throws PIException {
        super.initialize(act,risk);
        this.setProjectReference(act.getProjectReference());
        if(act instanceof PIPlan) {
            this.setRoot((PIPlan)act);
        } else {
            this.setRootReference(act.getRootReference());
        }

    }

    @Override
    public AbstractPIPlanActivity getRoleAObject()
    {
        return (AbstractPIPlanActivity)super.getRoleAObject();
    }

    @Override
    public STProjectRisk getRoleBObject()
    {
        return (STProjectRisk)super.getRoleBObject();
    }
}
