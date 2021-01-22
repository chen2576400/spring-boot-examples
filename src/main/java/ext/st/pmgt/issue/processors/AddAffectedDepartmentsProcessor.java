package ext.st.pmgt.issue.processors;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.collections.PICollection;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import ext.st.pmgt.issue.STProjectIssueHelper;
import ext.st.pmgt.issue.STRiskHelper;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectRisk;
import ext.st.pmgt.issue.model.STProjectRiskAffectedGroupLink;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@Component("addAffectedDepartmentsProcessor")
public class AddAffectedDepartmentsProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        Persistable persistable = params.getNfCommandBean().getSourceObject();
        List<Persistable> persistableList = params.getNfCommandBean().getSelectedObjects();
        STProjectRisk projectRisk = null;
        if (persistable instanceof STProjectRisk) {
            projectRisk = (STProjectRisk) persistable;
        }
        if (!CollectionUtils.isEmpty(persistableList)) {
            for (Persistable per : persistableList) {
                PIGroup group = (PIGroup) per;
                if (isExist(projectRisk, group)) {
                    STProjectRiskAffectedGroupLink groupLink = STProjectRiskAffectedGroupLink.newSTProjectRiskAffectedGroupLink(projectRisk, group);
                    PersistenceHelper.service.save(groupLink);
                }
            }

        }
        return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, "添加成功", null);

    }


    private Boolean isExist(STProjectRisk risk, PIGroup group) throws PIException {
        Collection collection = STRiskHelper.linkService.findByRoleAObjectRefAndRoleBObjectRef(ObjectReference.newObjectReference(risk), ObjectReference.newObjectReference(group));
        if (!CollectionUtils.isEmpty(collection)) {
            return true;
        }
        return false;

    }
}
