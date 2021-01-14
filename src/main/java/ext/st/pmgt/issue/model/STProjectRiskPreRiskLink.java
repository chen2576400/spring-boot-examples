package ext.st.pmgt.issue.model;

import com.pisx.tundra.foundation.fc.model.ObjectToObjectLink;
import com.pisx.tundra.foundation.util.PIException;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

@Entity
@Table(name = "STProjectRiskPreRiskLink", uniqueConstraints = {
        @UniqueConstraint(name = "STProjectRiskPreRiskLinkUFields1", columnNames = {"roleAObjectId", "roleAObjectClass", "roleBObjectId", "roleBObjectClass"})
})
public class STProjectRiskPreRiskLink extends ObjectToObjectLink implements Serializable {
    static final String CLASSNAME = STProjectRiskPreRiskLink.class.getName();

    public STProjectRiskPreRiskLink() {
    }

    public static STProjectRiskPreRiskLink newSTProjectRiskPreRiskLink(STProjectRisk stProjectRisk, STProjectRisk stProjectPreRisk) throws PIException
    {
        STProjectRiskPreRiskLink link= new STProjectRiskPreRiskLink();
        link.initialize(stProjectRisk, stProjectPreRisk);
        return link;
    }

    public static STProjectRiskPreRiskLink newSTProjectRiskPreRiskLink() throws PIException
    {
        STProjectRiskPreRiskLink link= new STProjectRiskPreRiskLink();
        link.initialize();
        return link;
    }


    public STProjectRisk getSTProjectPreRisk()
    {
        return getRoleBObject();
    }
    public void setSTProjectPreRisk(STProjectRisk stProjectPreRisk) throws PIException {
        setRoleBObject(stProjectPreRisk);
    }


    public void setSTProjectRisk(STProjectRisk stProjectRisk) throws PIException {
        setRoleAObject(stProjectRisk);
    }
    public STProjectRisk getStProjectRisk()
    {
        return getRoleAObject();
    }


    public STProjectRisk getRoleAObject()
    {
        return (STProjectRisk)super.getRoleAObject();
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
