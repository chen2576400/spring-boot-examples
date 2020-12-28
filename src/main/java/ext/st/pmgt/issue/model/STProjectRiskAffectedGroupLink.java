package ext.st.pmgt.issue.model;

import com.pisx.tundra.foundation.fc.model.ObjectToObjectLink;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

@Entity
@Table(name = "STProjectRiskAffectedGroupLink", uniqueConstraints = {
        @UniqueConstraint(name = "STProjectRiskAffectedGroupLinkUFields1", columnNames = {"roleAObjectId", "roleAObjectClass", "roleBObjectId", "roleBObjectClass"})
})
public class STProjectRiskAffectedGroupLink extends ObjectToObjectLink implements Serializable {
    static final String CLASSNAME = STProjectIssueInvolveGroupLink.class.getName();
    public STProjectRiskAffectedGroupLink()
    {
    }

    public static STProjectRiskAffectedGroupLink newSTProjectRiskAffectedGroupLink(STProjectRisk stProjectRisk, PIGroup group) throws PIException
    {
        STProjectRiskAffectedGroupLink link= new STProjectRiskAffectedGroupLink();
        link.initialize(stProjectRisk, group);
        return link;
    }



    public PIGroup getPIGroup()
    {
        return getRoleBObject();
    }
    public void setPIGroup(PIGroup piGroup) throws PIException {
        setRoleBObject(piGroup);
    }


    public void setSTProjectRisk(STProjectRisk stProjectRisk) throws PIException {
        setRoleAObject(stProjectRisk);
    }
    public STProjectRisk getstProjectRisk()
    {
        return getRoleAObject();
    }




    public STProjectRisk getRoleAObject()
    {
        return (STProjectRisk)super.getRoleAObject();
    }

    public PIGroup getRoleBObject()
    {
        return (PIGroup)super.getRoleBObject();
    }



    @Override
    public String getConceptualClassname() {
        return CLASSNAME;
    }
}
