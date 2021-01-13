package ext.st.pmgt.issue.service;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.issue.model.STProjectRisk;

import java.util.Collection;
public interface STRiskService {

    Collection getProjectRisks(PIProject project) throws PIException;

//    void deleteAssociationLink(STProjectRisk stProjectRisk) throws PIException;


}
