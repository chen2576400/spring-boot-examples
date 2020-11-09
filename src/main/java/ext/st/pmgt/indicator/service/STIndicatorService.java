package ext.st.pmgt.indicator.service;

import com.pisx.tundra.foundation.fc.collections.PICollection;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.indicator.model.STDeliverableType;
import ext.st.pmgt.indicator.model.STProCompetence;
import ext.st.pmgt.indicator.model.STProjectInstanceINIndicator;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;


public interface STIndicatorService {

    Collection findProjectINIndicatorByProject(PIProject project) throws PIException;

    Collection findProjectINIndicatorByPlan(PIPlan plan) throws PIException;

    Collection findProjectINIndicatorByPlanActivity(PIPlanActivity planActivity) throws PIException;

    Collection findProjectOTIndicatorByProject(PIProject project) throws PIException;

    Collection findProjectOTIndicatorByPlan(PIPlan plan) throws PIException;

    Collection findProjectOTIndicatorByPlanActivity(PIPlanActivity planActivity) throws PIException;

    Collection findProjectOTIndicatorByPlanDeliverable(PIPlanDeliverable planDeliverable) throws PIException;

    Collection findProjectOTIndicatorByPlanActivityAndPlan(PIPlanActivity planActivity,PIPlan plan) throws PIException;
    //api1

    Object api1(String planid) throws PIException;
    //api2

    Object api2(String planid) throws PIException;
    //api3

    Object api3(String userId, Timestamp actualStartDate, Timestamp actualEndDate) throws PIException;
    //api4

    Object api4(String activityId) throws PIException;

    Collection getAllDeliverableType();

    Collection getDeliverableTypeByAct(PIPlanActivity act) throws PIException;

    STProCompetence getProContenceByName(String name);

    STDeliverableType findDeliverableTypeByCode(String s);

    STProjectInstanceOTIndicator getOTByCode(String s);

    Collection getExpextTimeByActivity(PIPlanActivity act) throws PIException;

    Collection getOTByDeliverableType(STDeliverableType deliverableType) throws PIException;

    Collection getPlanDeliverablesByOT(STProjectInstanceOTIndicator ot) throws PIException;


    Collection getDeviationByOTCode(String code);

    Collection getDifficultyByOTCode(String code);

    Collection getOTByIN(STProjectInstanceINIndicator in) throws PIException;

    STProjectInstanceINIndicator getINByActRef(ObjectReference actRef);

    STProjectInstanceINIndicator getInByOT(STProjectInstanceOTIndicator ot);
}
