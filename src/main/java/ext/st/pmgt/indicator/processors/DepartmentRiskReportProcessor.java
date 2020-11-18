package ext.st.pmgt.indicator.processors;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName PERTProcessor
 * @Description:
 * @Author hma
 * @Date 2020/10/21
 * @Version V1.0
 **/
@Component
public class DepartmentRiskReportProcessor extends DefaultObjectFormProcessor {

    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        List<Persistable> selectedObjects = params.getNfCommandBean().getOpenerSelectedObjects();
        if (selectedObjects!=null&&selectedObjects.size()>0){
            String startTime = (String) params.getNfCommandBean().getLayoutFields().get("startTime");
            String endTime = (String) params.getNfCommandBean().getLayoutFields().get("endTime");
            String ids = builderID(selectedObjects);
            String url = "http://192.168.1.124:8088/report/departmentRisk?userIds="+ids+"&startTime="+startTime+"&endTime="+endTime;
            return new ResponseWrapper(ResponseWrapper.DIRECT, "", url);
        }else {
            return new ResponseWrapper(ResponseWrapper.FAILED, "未找到勾选用户！", null);
        }

    }

    private String builderID(List list) {
        String ids = "";
        for (int i = 0; i < list.size(); i++) {
            Persistable persistable = (Persistable) list.get(i);
            if (i == list.size() - 1) {
                ids = ids + persistable.getObjectIdentifier().getId();
            } else {
                ids = ids + persistable.getObjectIdentifier().getId() + ",";
            }
        }
        return ids;
    }
}