package ext.st.pmgt.indicator.processors;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.plan.PIPlanHelper;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.PIProjectHelper;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName ImportOTIndicatorProcessor
 * @Description:
 * @Author hma
 * @Date 2020/10/22
 * @Version V1.0
 **/
@Component
public class ImportOTIndicatorProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        Persistable pagePrimaryObject = params.getNfCommandBean().getPagePrimaryObject();
        PIPlanActivity act = null;
        if (pagePrimaryObject instanceof PIPlanActivity) {
            act = (PIPlanActivity) pagePrimaryObject;
        }


        STProjectInstanceOTIndicator stProjectInstanceOTIndicator = STProjectInstanceOTIndicator.newSTProjectInstanceOTIndicator();

//        stProjectInstanceOTIndicator.setCompetenceReference(STIndicatorHelper.service.getProContenceByName("产品规划"));//专业能力
        stProjectInstanceOTIndicator.setContainerReference(act.getContainerReference());
//        stProjectInstanceOTIndicator.setDeliverableTypeReference(ObjectReference.newObjectReference(STIndicatorHelper.service.findDeliverableTypeByCode("D-P01")));//交付物类型编码
        stProjectInstanceOTIndicator.setPlanReference(act.getRootReference());
        stProjectInstanceOTIndicator.setPlanActivityReference(ObjectReference.newObjectReference(act));
        PersistenceHelper.service.save(stProjectInstanceOTIndicator);


        return new ResponseWrapper(ResponseWrapper.REGIONAL_FLUSH, "", null);
    }
}