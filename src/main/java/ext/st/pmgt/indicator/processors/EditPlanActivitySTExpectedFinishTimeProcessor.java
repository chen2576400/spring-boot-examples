package ext.st.pmgt.indicator.processors;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIPrincipal;
import com.pisx.tundra.foundation.org.model.PIPrincipalReference;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultUpdateFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import ext.st.pmgt.indicator.model.STExpectedFinishTime;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Component("editPlanActivitySTExpectedFinishTimeProcessor")
public class EditPlanActivitySTExpectedFinishTimeProcessor extends DefaultUpdateFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        STExpectedFinishTime expectedFinishTime = (STExpectedFinishTime)list.get(0);
        PIPlanActivity planActivity = (PIPlanActivity)params.getNfCommandBean().getSourceObject();
        expectedFinishTime.setPlanActivityReference(planActivity);
        expectedFinishTime.setPlanReference(planActivity.getRootReference());
        expectedFinishTime.setProjectReference(planActivity.getProjectReference());
        PIPrincipal principal = SessionHelper.service.getPrincipal();
        expectedFinishTime.setReporter(PIPrincipalReference.newPIPrincipalReference(principal));
        expectedFinishTime.setReportTime(new Timestamp(new Date().getTime()));
        STExpectedFinishTime finishTime = PersistenceHelper.service.save(expectedFinishTime);
        return new ResponseWrapper(ResponseWrapper.REGIONAL_FLUSH, "", null);
    }

}
