package ext.st.pmgt.issue.service;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.plan.model.AbstractPIPlanActivity;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.project.model.PIProject;
import java.util.Collection;

public interface STRiskService {

    Collection getProjectRisks(PIProject project) throws PIException;

    Collection getProjectRisks(AbstractPIPlanActivity act) throws PIException;

    Collection getAllProjectRisks(PIPlan plan) throws PIException;
    //    void deleteAssociationLink(STProjectRisk stProjectRisk) throws PIException;



}
