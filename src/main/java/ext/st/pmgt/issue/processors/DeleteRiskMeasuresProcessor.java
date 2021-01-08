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
import ext.st.pmgt.issue.model.STProjectMeasures;
import ext.st.pmgt.issue.model.STProjectRisk;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class DeleteRiskMeasuresProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        List<STProjectMeasures> measures=(List)params.getNfCommandBean().getSelectedObjects();
        STProjectRisk risk = (STProjectRisk)params.getNfCommandBean().getSourceObject();
        for (STProjectMeasures projectMeasures:measures){
            PersistenceHelper.service.delete(projectMeasures);
            ContentHolder contentHolder=projectMeasures;
            List<ContentHolder> contentHolderList=new ArrayList<>();
            contentHolderList.add(contentHolder);
            PIProjectChangeHelper.service.deleteAttachmentData(contentHolderList);
        }

//        if (selectedObjects!=null&&selectedObjects.size()>0&&sourceObject instanceof PIProject) {
//            PIHashSet deleteItem = new PIHashSet();
//            deleteItem.addAll(selectedObjects);
//            PersistenceHelper.service.delete(deleteItem);
//            PIProjectChangeHelper.service.deleteAttachmentData(new ArrayList(new HashSet(selectedObjects)));
//        }

        return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, "删除成功！", null);
    }

}
