package ext.st.pmgt.indicator.processors;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ImportIndicatorProcessor
 * @Description:
 * @Author hma
 * @Date 2020/10/27
 * @Version V1.0
 **/
@Component
public class ImportIndicatorProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        Map<String, List<Part>> files = params.getFiles();
        Part primary = files.get("primaryFile").get(0);//主文件

        return new ResponseWrapper(ResponseWrapper.REGIONAL_FLUSH, "", null);
    }
}