package ext.st.pmgt.indicator.processors;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
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
public class PERTReportProcessor extends DefaultObjectFormProcessor {

    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        String id = null;
        if (sourceObject!=null){
            PIPlanActivity act = (PIPlanActivity) sourceObject;
            id = act.getObjectIdentifier().getId().toString();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("url","http://192.168.111.198:8088/report/pertReport?id="+id);
        map.put("width","1600px");
        map.put("height","900px");
        return new ResponseWrapper(ResponseWrapper.OPEN_WINDOW, "", map);
    }
}