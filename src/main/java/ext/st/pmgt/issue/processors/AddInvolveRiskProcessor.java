package ext.st.pmgt.issue.processors;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import ext.st.pmgt.issue.STProjectIssueHelper;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectIssueInvolveRiskLink;
import ext.st.pmgt.issue.model.STProjectRisk;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@Component
public class AddInvolveRiskProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        Persistable persistable = params.getNfCommandBean().getSourceObject();
        List<Persistable> persistableList = params.getNfCommandBean().getSelectedObjects();
        STProjectIssue projectIssue = null;
        if (persistable instanceof STProjectIssue) {
            projectIssue = (STProjectIssue) persistable;
        }
        if (!CollectionUtils.isEmpty(persistableList)) {
            for (Persistable per : persistableList) {
                STProjectRisk risk = (STProjectRisk) per;
                if (!isExist(projectIssue, risk)) {
                    STProjectIssueInvolveRiskLink groupLink = STProjectIssueInvolveRiskLink.newSTProjectIssueInvolveRiskLink(projectIssue, risk);
                    PersistenceHelper.service.save(groupLink);
                }

            }

        }


        return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, "添加成功", null);

    }


    private Boolean isExist(STProjectIssue issue, STProjectRisk risk) throws PIException {
        Collection collection = STProjectIssueHelper.riskLinkService.findByRoleAObjectRefAndRoleBObjectRef(ObjectReference.newObjectReference(issue), ObjectReference.newObjectReference(risk));
        if (!CollectionUtils.isEmpty(collection)) {
            return true;
        }
        return false;

    }
}
