package ext.st.pmgt.indicator.processors;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
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
        HashMap<String, String> map = new HashMap<>();
        map.put("url","http://www.baidu.com");
        map.put("width","900px");
        map.put("height","750px");
        return new ResponseWrapper(ResponseWrapper.DIRECT, "", "http://www.baidu.com");
    }
}