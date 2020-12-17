package ext.st.pmgt.indicator.processors;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIPrincipal;
import com.pisx.tundra.foundation.org.model.PIPrincipalReference;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultCreateFormProcessor;
import com.pisx.tundra.netfactory.mvc.components.DefaultUpdateFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.assignment.model.PIResourceAssignment;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.plan.model.PIPlannable;
import ext.st.pmgt.indicator.model.STExpectedFinishTime;
import ext.st.pmgt.indicator.resources.indicatorResource;
import io.micrometer.core.instrument.util.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ReportPlanActivitySTExpectedFinishTimeProcessor extends DefaultCreateFormProcessor {
    private static final String RESOURCE = indicatorResource.class.getName();

    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        try {
            List<PIResourceAssignment> assignments = (List) params.getNfCommandBean().getOpenerSelectedObjects();
            Set<PIPlannable> activities = assignments.stream().map(item -> item.getPlannable()).collect(Collectors.toSet());
            for (PIPlannable activity : activities) {
                String time = (String) params.getNfCommandBean().getLayoutFields().get("expectedFinishTime");
                if (time == null || StringUtils.isBlank(time)) {
                    return new ResponseWrapper(ResponseWrapper.FAILED, "", null);
                }
                STExpectedFinishTime expectedFinishTime = STExpectedFinishTime.newSTExpectedFinishTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = simpleDateFormat.parse(time);
                Timestamp timestamp = new Timestamp(date.getTime());
                expectedFinishTime.setExpectedFinishTime(timestamp);
                PIPlanActivity planActivity = (PIPlanActivity) params.getNfCommandBean().getSourceObject();
                expectedFinishTime.setPlanActivityReference(planActivity);

                PIPrincipal principal = SessionHelper.service.getPrincipal();
                expectedFinishTime.setReporter(PIPrincipalReference.newPIPrincipalReference(principal));
                expectedFinishTime.setReportTime(new Timestamp(new Date().getTime()));


                expectedFinishTime.setPlanActivityReference(ObjectReference.newObjectReference(activity));
                expectedFinishTime.setPlanReference(activity.getRootReference());
                expectedFinishTime.setProjectReference(activity.getProjectReference());
                PersistenceHelper.service.save(expectedFinishTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ResponseWrapper(ResponseWrapper.REGIONAL_FLUSH, "", null);
    }

}
