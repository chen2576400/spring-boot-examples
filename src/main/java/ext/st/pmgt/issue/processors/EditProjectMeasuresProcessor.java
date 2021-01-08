package ext.st.pmgt.issue.processors;

import com.alibaba.fastjson.JSONObject;
import com.pisx.tundra.foundation.content.ContentHelper;
import com.pisx.tundra.foundation.content.model.ApplicationData;
import com.pisx.tundra.foundation.content.model.ContentHolder;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIPrincipalReference;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultUpdateFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.change.PIProjectChangeHelper;
import ext.st.pmgt.issue.model.STProjectMeasures;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EditProjectMeasuresProcessor extends DefaultUpdateFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        STProjectMeasures measures = (STProjectMeasures) list.get(0);
        PIPrincipalReference piPrincipalReference = SessionHelper.service.getPrincipalReference();

        Map<String, List<Part>> files = params.getFiles();
        List<Part> second = files.get("secondFile");//有数据都是新增的
        JSONObject ajaxData = params.getAjaxData();
        JSONObject componentsData = ajaxData.getJSONObject("componentsData");
        JSONObject createProjectmMasureStep2 = componentsData.getJSONObject("create_project_measure_step2");
        JSONObject contextHolderAttachmentsTable = createProjectmMasureStep2.getJSONObject("createoreditattachmentstable1");
        String  rows = contextHolderAttachmentsTable.getJSONArray("rows").toJSONString();


        PersistenceHelper.service.save(measures);
        ContentHolder contentHolder=measures;
        deleteAttachmentData(contentHolder,params);
        List  updatedObjects = params.getNfCommandBean().getUpdatedObjects();
        List<ApplicationData> updateApplicationDataList=null;
        if(!CollectionUtils.isEmpty(updatedObjects)){
            updateApplicationDataList = (List<ApplicationData>) updatedObjects.stream().filter(item -> item instanceof ApplicationData).collect(Collectors.toList());
        }
        PIProjectChangeHelper.service.addAndUpdateSecondData(contentHolder,piPrincipalReference, second,rows,updateApplicationDataList);
        return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, null, null);
    }
    /**
     * 排它 删除 的附件信息统一物理删除
     *
     * @param contentHolder
     * @param params
     * @throws PIException
     */
    public void deleteAttachmentData(ContentHolder contentHolder, ComponentParams params) throws PIException {
        List<ApplicationData> deleteData = new ArrayList<>();
        List<Persistable> excludedObjects = params.getNfCommandBean().getExcludedObjects();
        List<Persistable> removedObjects = params.getNfCommandBean().getRemovedObjects();
        if (excludedObjects != null) {
            excludedObjects.forEach(item -> deleteData.add((ApplicationData) item));
        }
        if (removedObjects != null) {
            removedObjects.forEach(item -> deleteData.add((ApplicationData) item));
        }
        if (deleteData.size() != 0) {
            ContentHelper.service.deleteSecondData(deleteData, contentHolder);
        }
    }
}
