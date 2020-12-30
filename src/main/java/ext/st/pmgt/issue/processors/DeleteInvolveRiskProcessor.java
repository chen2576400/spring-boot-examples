package ext.st.pmgt.issue.processors;

import com.alibaba.fastjson.JSONObject;
import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.workflow.util.WorkflowUtil;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import ext.st.pmgt.issue.STProjectIssueHelper;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectRisk;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DeleteInvolveRiskProcessor  extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        final JSONObject jsonObject = params.getAjaxData().getJSONObject("componentsData").getJSONObject("component0")
                .getJSONObject("project_risk_list_table_builder");
        String sourceOid = (String) jsonObject.get("sourceOid");
        Persistable persistable = WorkflowUtil.getObjectByOid(sourceOid);
        STProjectIssue stProjectIssue = null;
        if (persistable instanceof STProjectIssue) {
            stProjectIssue = (STProjectIssue) persistable;
        }
        List<Map<String, Object>> rows = (List<Map<String, Object>>) jsonObject.get("selectedRows");
        for (Map<String, Object> row : rows) {
            Integer id = Integer.valueOf((String) row.get("pi_row_id"));
            ObjectIdentifier objectIdentifier = new ObjectIdentifier(STProjectRisk.class, id.longValue());
            ObjectReference objectReference = ObjectReference.newObjectReference(objectIdentifier);
            STProjectIssueHelper.riskLinkService.deleteByRoleAObjectRefAndRoleBObjectRef(ObjectReference.newObjectReference(stProjectIssue),objectReference);
        }
        return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, "删除成功！", null);
    }
}
