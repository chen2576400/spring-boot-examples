package ext.st.pmgt.issue.model;

import com.pisx.tundra.foundation.fc.model.ObjectToObjectLink;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

@Entity
@Table(name = "STProjectIssueInvolveRiskLink", uniqueConstraints = {
        @UniqueConstraint(name = "STProjectIssueInvolveRiskLinkUFields1", columnNames = {"roleAObjectId", "roleAObjectClass", "roleBObjectId", "roleBObjectClass"})
})
public class STProjectIssueInvolveRiskLink extends ObjectToObjectLink implements Serializable {
    static final String CLASSNAME = STProjectIssueInvolveRiskLink.class.getName();
    public STProjectIssueInvolveRiskLink()
    {
    }

    public static STProjectIssueInvolveRiskLink newSTProjectIssueInvolveRiskLink(STProjectIssue issue, STProjectRisk risk) throws PIException
    {
        STProjectIssueInvolveRiskLink link= new STProjectIssueInvolveRiskLink();
        link.initialize(issue, risk);
        return link;
    }


    public STProjectRisk getSTProjectRisk()
    {
        return getRoleBObject();
    }
    public void setSTProjectRisk(STProjectRisk stProjectRisk) throws PIException {
        setRoleBObject(stProjectRisk);
    }


    public void setSTProjectIssue(STProjectIssue stProjectIssue) throws PIException {
        setRoleAObject(stProjectIssue);
    }
    public STProjectIssue getSTProjectIssue()
    {
        return getRoleAObject();
    }


    public STProjectIssue getRoleAObject()
    {
        return (STProjectIssue)super.getRoleAObject();
    }

    public STProjectRisk getRoleBObject()
    {
        return (STProjectRisk)super.getRoleBObject();
    }



    @Override
    public String getConceptualClassname() {
        return CLASSNAME;
    }
}
