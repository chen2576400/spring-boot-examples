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
import ext.st.pmgt.issue.model.STProjectIssue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
@Component("deleteProjectIssuesProcessorCopy")
public class DeleteProjectIssuesProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        List<Persistable> selectedObjects = params.getNfCommandBean().getSelectedObjects();

        if (sourceObject instanceof STProjectIssue && selectedObjects == null) {//右键删除
            PersistenceHelper.service.delete(sourceObject);
            ContentHolder contentHolder=(STProjectIssue)sourceObject;
            List<ContentHolder> contentHolderList=new ArrayList<>();
            contentHolderList.add(contentHolder);
            PIProjectChangeHelper.service.deleteAttachmentData(contentHolderList);
            return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, "删除成功！", null);

        }

        if (sourceObject instanceof PIProject && selectedObjects != null && selectedObjects.size() > 0) {//checkbox选中删除
            PIHashSet deleteItem = new PIHashSet();
            deleteItem.addAll(selectedObjects);
            PersistenceHelper.service.delete(deleteItem);
            PIProjectChangeHelper.service.deleteAttachmentData(new ArrayList(new HashSet<>(selectedObjects)));
            return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, "删除成功！", null);
        }

        return new ResponseWrapper<>(ResponseWrapper.FAILED, "删除失败！", null);
    }
}
