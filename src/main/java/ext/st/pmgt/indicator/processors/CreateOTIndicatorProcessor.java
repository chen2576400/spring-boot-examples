package ext.st.pmgt.indicator.processors;

import com.alibaba.fastjson.JSONObject;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.fc.service.ReferenceFactory;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STProjectIndicator;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Component
public class CreateOTIndicatorProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {

        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        STProjectIndicator stProjectIndicator = (STProjectIndicator) sourceObject;
        String parentPageOid = params.getParamMap().get("parentPageOid")[0];
        PIPlanActivity piPlanActivity = (PIPlanActivity) new ReferenceFactory().getReference(parentPageOid).getObject();

        JSONObject JSONObject = (JSONObject) params.getNfCommandBean().getClassification().get("deviation_value_and_difficulty_value_wizard_step").get("adeviation_value_and_difficulty_value_layout1").getLayoutFields().get("standardDeviationValue");
        String standardDeviationValue = JSONObject.get("value").toString();
        JSONObject JSONObject2 = (JSONObject) params.getNfCommandBean().getClassification().get("deviation_value_and_difficulty_value_wizard_step").get("adeviation_value_and_difficulty_value_layout1").getLayoutFields().get("standardDifficultyValue");
        String standardDifficultyValue =JSONObject2.get("value").toString();

        PIPlan piPlan = (PIPlan) piPlanActivity.getRoot();
        PIProject project = piPlanActivity.getProject();


        STProjectInstanceOTIndicator stProjectInstanceOTIndicator = STProjectInstanceOTIndicator.newSTProjectInstanceOTIndicator();
//        设置基本属性
        stProjectInstanceOTIndicator.setContainer(piPlanActivity.getContainer());
        stProjectInstanceOTIndicator.setProjectReference(project);
        stProjectInstanceOTIndicator.setPlan(piPlan);
        stProjectInstanceOTIndicator.setPlanActivityReference(ObjectReference.newObjectReference(piPlanActivity));
        stProjectInstanceOTIndicator.setStandardDeviationValue(Double.parseDouble(standardDeviationValue));
        stProjectInstanceOTIndicator.setStandardDifficultyValue(Double.parseDouble(standardDifficultyValue));
        stProjectInstanceOTIndicator.setCode(stProjectIndicator.getCode());
        stProjectInstanceOTIndicator.setDecription(stProjectIndicator.getDecription());
        stProjectInstanceOTIndicator.setDefinition(stProjectIndicator.getDefinition());
        stProjectInstanceOTIndicator.setReportTime(new Timestamp(new Date(0).getTime()));
        stProjectInstanceOTIndicator.setCompetenceReference(stProjectIndicator.getCompetenceReference());
        stProjectInstanceOTIndicator.setDeliverableTypeCode(stProjectIndicator.getDeliverableTypeCode());
        PersistenceHelper.service.save(stProjectInstanceOTIndicator);
        STIndicatorHelper.service.updateBreadthAndCriticality(stProjectIndicator.getCode(), piPlan);

        return new ResponseWrapper(ResponseWrapper.Code.PAGE_FLUSH, "", null);

    }
}