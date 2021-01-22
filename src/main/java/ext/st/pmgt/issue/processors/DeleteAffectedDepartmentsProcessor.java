package ext.st.pmgt.issue.processors;

import com.alibaba.fastjson.JSONObject;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.collections.PICollection;
import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import ext.st.pmgt.issue.STProjectIssueHelper;
import ext.st.pmgt.issue.STRiskHelper;
import ext.st.pmgt.issue.model.STProjectRisk;
import ext.st.pmgt.issue.model.STProjectRiskAffectedGroupLink;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DeleteAffectedDepartmentsProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        final JSONObject jsonObject = params.getAjaxData().getJSONObject("componentsData").getJSONObject("component0")
                .getJSONObject("project_risk_affected_department_table");
        Persistable persistable = params.getNfCommandBean().getSourceObject();
        STProjectRisk  projectRisk = null;
        if (persistable instanceof STProjectRisk) {
            projectRisk = (STProjectRisk) persistable;
        }
        List<Map<String, Object>> rows = (List<Map<String, Object>>) jsonObject.get("selectedRows");
        for (Map<String, Object> row : rows) {
            Integer id = Integer.valueOf((String) row.get("pi_row_id"));
            ObjectIdentifier objectIdentifier = new ObjectIdentifier(PIGroup.class, id.longValue());
            ObjectReference objectReference = ObjectReference.newObjectReference(objectIdentifier);
            PICollection collection = STRiskHelper.linkService.findByRoleAObjectRefAndRoleBObjectRef(ObjectReference.newObjectReference(projectRisk), objectReference);
            PersistenceHelper.service.delete(collection);
        }

        return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, "删除成功！", null);
    }
}
