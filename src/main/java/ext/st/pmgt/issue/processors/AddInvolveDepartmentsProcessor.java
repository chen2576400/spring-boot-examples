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
import ext.st.pmgt.issue.model.STProjectIssueInvolveGroupLink;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Component
public class AddInvolveDepartmentsProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        Persistable persistable = params.getNfCommandBean().getSourceObject();
        List<Persistable> persistableList = params.getNfCommandBean().getSelectedObjects();
        STProjectIssue projectIssue = null;
        if (persistable instanceof STProjectIssue) {
            projectIssue = (STProjectIssue) persistable;
        }
        if (!CollectionUtils.isEmpty(persistableList)) {

            //添加之前将之前选中的该问题下所有涉及部门删除
            STProjectIssueHelper.linkService.deleteByRoleAObjectRef(ObjectReference.newObjectReference(projectIssue));


            for (Persistable per : persistableList) {
                PIGroup group = (PIGroup) per;
                STProjectIssueInvolveGroupLink groupLink = STProjectIssueInvolveGroupLink.newSTProjectIssueInvolveGroupLink(projectIssue, group);
                PersistenceHelper.service.save(groupLink);
            }

        }

        return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, "添加成功", null);

    }
}
