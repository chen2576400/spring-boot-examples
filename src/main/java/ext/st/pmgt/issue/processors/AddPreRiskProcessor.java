package ext.st.pmgt.issue.processors;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import ext.st.pmgt.issue.STRiskHelper;
import ext.st.pmgt.issue.model.STProjectIssueInvolveRiskLink;
import ext.st.pmgt.issue.model.STProjectRisk;
import ext.st.pmgt.issue.model.STProjectRiskPreRiskLink;
import ext.st.pmgt.issue.service.STProjectRiskPreRiskLinkService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@Component
public class AddPreRiskProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        Persistable persistable = params.getNfCommandBean().getSourceObject();
        List<Persistable> persistableList = params.getNfCommandBean().getSelectedObjects();
        STProjectRisk stProjectRisk = null;
        if (persistable instanceof STProjectRisk) {
            stProjectRisk = (STProjectRisk) persistable;
        }

        if (!CollectionUtils.isEmpty(persistableList)) {
            for (Persistable per : persistableList) {
                STProjectRisk preRisk = (STProjectRisk) per;
                if (!isExist(stProjectRisk, preRisk)) {
                    STProjectRiskPreRiskLink link = STProjectRiskPreRiskLink.newSTProjectRiskPreRiskLink(stProjectRisk, preRisk);
                    PersistenceHelper.service.save(link);
                }
            }

        }

        return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, "添加成功", null);
    }

    private Boolean isExist(STProjectRisk risk, STProjectRisk preRisk) throws PIException {
        Collection collection = STRiskHelper.preRiskLinkService.findByRoleAObjectRefAndRoleBObjectRef(ObjectReference.newObjectReference(risk), ObjectReference.newObjectReference(preRisk));
        if (!CollectionUtils.isEmpty(collection)) {
            return true;
        }
        return false;

    }
}
