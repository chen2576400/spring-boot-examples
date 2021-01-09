package ext.st.pmgt.indicator.service;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.indicator.model.*;

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

    Object getDataByPlanId(String planid) throws PIException;
    //api2

    Object getDataByProjectId(String planid) throws PIException;
    //api3

    Object getDataByUserIdAndTime(String userId, Timestamp actualStartDate, Timestamp actualEndDate) throws PIException;
    //api4

    Object getPERTData(String activityId) throws PIException;

    Collection getAllDeliverableType();

    Collection getDeliverableTypeByAct(PIPlanActivity act) throws PIException;

    STProCompetence getProContenceByName(String name);

    STDeliverableType findDeliverableTypeByCode(String s);

    Collection getOTByCode(String s);

    Collection getExpextTimeByActivity(PIPlanActivity act) throws PIException;

    Collection getOTByDeliverableTypeCodeAndPlanActivity(String code,PIPlanActivity activity) throws PIException;

    Collection getPlanDeliverablesByOT(STProjectInstanceOTIndicator ot) throws PIException;


    Collection getDeviationByOTCode(String code);

    Collection getDifficultyByOTCode(String code);

    Collection getOTByIN(STProjectInstanceINIndicator in) throws PIException;

    STProjectInstanceINIndicator getINByActRef(ObjectReference actRef);

    STProjectInstanceINIndicator getInByOT(STProjectInstanceOTIndicator ot,PIPlanActivity activity);

    Object getDataByProjectIdAndUserId(String projectId, String userId) throws PIException;

    List<STProjectInstanceOTIndicator> getLatestOt(List<STProjectInstanceOTIndicator> ots);

    Collection findRatingByIN(STProjectInstanceINIndicator in) throws PIException;

    void updateBreadthAndCriticality(String otCode,PIPlan piPlan) throws PIException;

    public Collection getAllIndicatorByCompetence(PIGroup piGroup, Boolean enable) throws PIException;

    public Collection findINIndicatorByOtCode(String otCode,ObjectReference planReference) throws PIException;
    public void saveSTProjectIndicatorReportDifference(STProjectInstanceOTIndicator otIndicator) throws PIException;
    public void saveSTProjectIndicatorReportDifference(STProjectInstanceINIndicator stProjectInstanceINIndicator, STRating stRating) throws PIException;
}
