package ext.st.pmgt.issue.processors;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectMeasures;
import ext.st.pmgt.issue.model.STProjectRisk;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: Mr.Chen
 * @create: 2021-01-18 15:56
 **/
@Component
public class ConfirmIssuesORRiskORMeasuresProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        String s = params.getAjaxData().getJSONObject("componentsData").getJSONObject("confirm_project_issue_wizard_step1").
                getJSONObject("confirm_project_issue_layout").getJSONObject("fieldMeta").
                getJSONObject("confirmStatus").get("value").toString();
        Boolean m = Boolean.valueOf(s);

        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        if (sourceObject instanceof STProjectIssue) {
            STProjectIssue projectIssue = (STProjectIssue) sourceObject;
            setSTProjectIssue(projectIssue, m);
            PersistenceHelper.service.save(projectIssue);
        } else if (sourceObject instanceof STProjectRisk) {
            STProjectRisk projectRisk = (STProjectRisk) sourceObject;
            setSTProjectRisk(projectRisk, m);
            PersistenceHelper.service.save(projectRisk);
        }else if (sourceObject instanceof  STProjectMeasures){
            STProjectMeasures projectMeasures = (STProjectMeasures) sourceObject;
            setSTProjectMeasures(projectMeasures,m);
            PersistenceHelper.service.save(projectMeasures);
        }

        return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, null, null);
    }


    private STProjectIssue setSTProjectIssue(STProjectIssue projectIssue, Boolean m) throws PIException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        PIUser piUser = (PIUser) SessionHelper.service.getPrincipal();

        projectIssue.setProjectManagerUserReference(ObjectReference.newObjectReference(piUser));
        projectIssue.setConfirmStatus(m);
        if (!m) {
            projectIssue.setCloseStamp(timestamp);
        }
        return projectIssue;
    }

    private STProjectRisk setSTProjectRisk(STProjectRisk risk, Boolean m) throws PIException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        PIUser piUser = (PIUser) SessionHelper.service.getPrincipal();
        risk.setProjectManagerUserReference(ObjectReference.newObjectReference(piUser));
        risk.setConfirmStatus(m);
        if (!m) {
            risk.setCloseStamp(timestamp);
        }
        return risk;
    }

    private STProjectMeasures setSTProjectMeasures(STProjectMeasures measures, Boolean m) throws PIException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        PIUser piUser = (PIUser) SessionHelper.service.getPrincipal();
        measures.setProjectManagerUserReference(ObjectReference.newObjectReference(piUser));
        measures.setConfirmStatus(m);
        if (!m) {
            measures.setCloseStamp(timestamp);
        }
        return measures;
    }

}
