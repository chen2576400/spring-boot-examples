package ext.st.pmgt.indicator.service;

import com.pisx.tundra.foundation.fc.collections.PICollection;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.model.PIProject;

import java.sql.Timestamp;


public interface STIndicatorService {

    PICollection findProjectINIndicatorByProject(PIProject project) throws PIException;

    PICollection findProjectINIndicatorByPlan(PIPlan plan) throws PIException;

    PICollection findProjectINIndicatorByPlanActivity(PIPlanActivity planActivity) throws PIException;

    PICollection findProjectOTIndicatorByProject(PIProject project) throws PIException;

    PICollection findProjectOTIndicatorByPlan(PIPlan plan) throws PIException;

    PICollection findProjectOTIndicatorByPlanActivity(PIPlanActivity planActivity) throws PIException;

    PICollection findProjectOTIndicatorByPlanDeliverable(PIPlanDeliverable planDeliverable) throws PIException;

    //api1
    Object api1(String planid) throws PIException;

    //api2
    Object api2(String planid) throws PIException;

    //api3
    Object api3(String userId, Timestamp actualStartDate, Timestamp actualEndDate) throws PIException;

    //api4
    Object api4(String planid) throws PIException;
}
