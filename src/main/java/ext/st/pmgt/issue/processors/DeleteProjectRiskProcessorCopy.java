package ext.st.pmgt.issue.processors;

import com.pisx.tundra.foundation.content.model.ContentHolder;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.collections.PIHashSet;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.change.PIProjectChangeHelper;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.issue.STProjectIssueHelper;
import ext.st.pmgt.issue.STRiskHelper;
import ext.st.pmgt.issue.model.STProjectRisk;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
@Component
public class DeleteProjectRiskProcessorCopy extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        List<Persistable> selectedObjects = params.getNfCommandBean().getSelectedObjects();
        if (selectedObjects==null&&sourceObject instanceof STProjectRisk){//右键删除
            PersistenceHelper.service.delete(sourceObject);

            ContentHolder contentHolder=(STProjectRisk)sourceObject;
            List<ContentHolder> contentHolderList=new ArrayList<>();
            contentHolderList.add(contentHolder);
            PIProjectChangeHelper.service.deleteAttachmentData(contentHolderList);

//            STRiskHelper.service.deleteAssociationLink((STProjectRisk)sourceObject);
        }
        if (selectedObjects!=null&&selectedObjects.size()>0&&sourceObject instanceof PIProject) {//checkbox删除
            PIHashSet deleteItem = new PIHashSet();
            deleteItem.addAll(selectedObjects);
            PersistenceHelper.service.delete(deleteItem);
            PIProjectChangeHelper.service.deleteAttachmentData(new ArrayList(new HashSet(selectedObjects)));

        }
        return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, null, null);
    }

}
