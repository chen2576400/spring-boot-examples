package ext.st.pmgt.issue.processors;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import ext.st.pmgt.issue.model.STProjectMeasures;
import ext.st.pmgt.issue.model.STProjectRisk;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeleteRiskMeasuresProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        List<STProjectMeasures> measures=(List)params.getNfCommandBean().getSelectedObjects();
        STProjectRisk risk = (STProjectRisk)params.getNfCommandBean().getSourceObject();
        for (STProjectMeasures projectMeasures:measures){
            PersistenceHelper.service.delete(projectMeasures);
        }
        return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, "删除成功！", null);
    }

}
