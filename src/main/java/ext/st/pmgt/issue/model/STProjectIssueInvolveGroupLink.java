package ext.st.pmgt.issue.model;

import com.pisx.tundra.foundation.fc.model.ObjectToObjectLink;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "STProjectIssueInvolveGroupLink", uniqueConstraints = {
        @UniqueConstraint(name = "STProjectIssueInvolveGroupLinkUFields1", columnNames = {"roleAObjectId", "roleAObjectClass", "roleBObjectId", "roleBObjectClass"})
})
public class STProjectIssueInvolveGroupLink extends ObjectToObjectLink implements Serializable {
    static final String CLASSNAME = STProjectIssueInvolveGroupLink.class.getName();
    public STProjectIssueInvolveGroupLink()
    {
    }

    public static STProjectIssueInvolveGroupLink newSTProjectIssueInvolveGroupLink(STProjectIssue stProjectIssue, PIGroup group) throws PIException
    {
        STProjectIssueInvolveGroupLink link= new STProjectIssueInvolveGroupLink();
        link.initialize(stProjectIssue, group);
        return link;
    }


    public PIGroup getPIGroup()
    {
        return getRoleBObject();
    }
    public void setPIGroup(PIGroup piGroup) throws PIException {
        setRoleBObject(piGroup);
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

    public PIGroup getRoleBObject()
    {
        return (PIGroup)super.getRoleBObject();
    }


    @Override
    public String getConceptualClassname() {
        return CLASSNAME;
    }
}
