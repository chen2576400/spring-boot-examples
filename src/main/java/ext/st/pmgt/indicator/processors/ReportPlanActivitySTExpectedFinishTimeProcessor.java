package ext.st.pmgt.indicator.processors;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIPrincipal;
import com.pisx.tundra.foundation.org.model.PIPrincipalReference;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultCreateFormProcessor;
import com.pisx.tundra.netfactory.mvc.components.DefaultUpdateFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import ext.st.pmgt.indicator.model.STExpectedFinishTime;
import ext.st.pmgt.indicator.resources.indicatorResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Component
public class ReportPlanActivitySTExpectedFinishTimeProcessor extends DefaultCreateFormProcessor {
    private static final String RESOURCE = indicatorResource.class.getName();

    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        try {
            Object data = params.getNfCommandBean().getLayoutFields().get("expectedFinishTime");
            if (data == null || StringUtils.isBlank(data.toString()))
                return new ResponseWrapper(ResponseWrapper.FAILED, "", null);
            STExpectedFinishTime expectedFinishTime = (STExpectedFinishTime) list.get(0);

            Timestamp timestamp = Timestamp.valueOf(data.toString());
            expectedFinishTime.setExpectedFinishTime(timestamp);
            PIPlanActivity planActivity = (PIPlanActivity) params.getNfCommandBean().getSourceObject();
            expectedFinishTime.setPlanActivityReference(planActivity);
            expectedFinishTime.setPlanReference(planActivity.getRootReference());
            expectedFinishTime.setProjectReference(planActivity.getProjectReference());
            PIPrincipal principal = SessionHelper.service.getPrincipal();
            expectedFinishTime.setReporter(PIPrincipalReference.newPIPrincipalReference(principal));
            expectedFinishTime.setReportTime(new Timestamp(new Date().getTime()));
            STExpectedFinishTime finishTime = PersistenceHelper.service.save(expectedFinishTime);
        } catch (PIException e) {
            e.printStackTrace();
        }
        return new ResponseWrapper(ResponseWrapper.REGIONAL_FLUSH, "", null);
    }

}
