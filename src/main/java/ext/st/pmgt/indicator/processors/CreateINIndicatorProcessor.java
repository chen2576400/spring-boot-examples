package ext.st.pmgt.indicator.processors;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.netfactory.util.misc.StringUtils;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STProjectInstanceINIndicator;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("createINIndicatorProcessor")
public class CreateINIndicatorProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        //得到权重
        String weights = (String) params.getNfCommandBean().getClassification().get("creatinindicatorwizardstep1").get("creatinindicatorlayout").getLayoutFields().get("weights");

        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        PIPlanActivity piPlanActivity = null;
        PIProject project = null;
        PIPlan piPlan = null;
        if (sourceObject instanceof PIPlanActivity) {
            piPlanActivity = (PIPlanActivity) sourceObject;
            //        判断任务是否放分配，进度是否为0%
//          if(piPlanActivity.getPhysicalCompletePercent()==0)){
//              return new ResponseWrapper(ResponseWrapper.FAILED,"该任务不允许添加指标",null);
//          }
            piPlan = (PIPlan) piPlanActivity.getRootReference().getObject();
            project = piPlanActivity.getProject();
        }
//        得到被选中的指标
        List<Persistable> selectedObjects = params.getNfCommandBean().getSelectedObjects();
        if (CollectionUtils.isNotEmpty(selectedObjects)) {
            for (Persistable selectedObject : selectedObjects) {
                if (selectedObject instanceof STProjectInstanceOTIndicator) {
                    STProjectInstanceINIndicator inIndicator = STProjectInstanceINIndicator.newSTProjectInstanceINIndicator();
                    inIndicator.setContainerReference(piPlanActivity.getContainerReference());
                    inIndicator.setProjectReference(ObjectReference.newObjectReference(project));
                    inIndicator.setPlanActivityReference(ObjectReference.newObjectReference(piPlanActivity));
                    inIndicator.setProjectPlanInstance(piPlan);
                    inIndicator.setPlanReference(ObjectReference.newObjectReference(piPlan));
                    if(StringUtils.isNotEmpty(weights)) {
                        inIndicator.setWeights(Double.parseDouble(weights));
                    }
                    inIndicator.setOtCode(((STProjectInstanceOTIndicator) selectedObject).getCode());
                    PersistenceHelper.service.save(inIndicator);
                    //        更新广度关键度
                    STIndicatorHelper.service.updateBreadthAndCriticality(((STProjectInstanceOTIndicator) selectedObject).getCode(), piPlan);
                }
            }
        } else {
           return new ResponseWrapper(ResponseWrapper.FAILED, "必须选择一个指标！", null);
        }
        return new ResponseWrapper(ResponseWrapper.Code.REGIONAL_FLUSH, "添加成功！", null);
    }
}
