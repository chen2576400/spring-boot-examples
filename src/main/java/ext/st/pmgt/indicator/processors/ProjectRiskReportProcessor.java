package ext.st.pmgt.indicator.processors;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.project.model.PIProject;
import org.springframework.stereotype.Component;

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
public class ProjectRiskReportProcessor extends DefaultObjectFormProcessor {

    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        List<Persistable> selectedObjects = params.getNfCommandBean().getSelectedObjects();
        String ids = "";
        if(selectedObjects.size()>0){
            for (int i = 0; i < selectedObjects.size(); i++) {
                PIProject project = (PIProject) selectedObjects.get(i);
                if (i<selectedObjects.size()-1) {
                    ids = ids + project.getObjectIdentifier().getId()+ ",";
                }else {
                    ids = ids + project.getObjectIdentifier().getId() ;
                }
            }
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("url","http://192.168.111.198:8088/report/projectRisk?ids="+ids);
        map.put("width","900px");
        map.put("height","750px");
        return new ResponseWrapper(ResponseWrapper.OPEN_WINDOW, "", map);
    }
}