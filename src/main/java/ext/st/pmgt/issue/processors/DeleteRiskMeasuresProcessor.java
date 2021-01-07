package ext.st.pmgt.issue.processors;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeleteRiskMeasuresProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        return null;
    }

}
