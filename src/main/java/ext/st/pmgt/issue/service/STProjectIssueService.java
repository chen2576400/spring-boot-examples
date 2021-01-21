package ext.st.pmgt.issue.service;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectRisk;

import java.util.Collection;

public interface STProjectIssueService {

    Collection getAllProjectIssues(PIProject project) throws PIException;


    Collection getAllProjectIssues(PIPlan piPlan) throws PIException;

    Collection getProjectIssues(PIPlanActivity act) throws PIException;


//     void deleteAssociastionLink(STProjectIssue sourceObject) throws PIException;
}
