package ext.st.pmgt.indicator.processors;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.service.ReferenceFactory;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SaveOTIndicatorProcessor
 * @Description:
 * @Author hma
 * @Date 2020/10/22
 * @Version V1.0
 **/
@Component
public class SaveOTIndicatorProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        List<Map<String, Object>> tableRows = params.getNfCommandBean().getTableRows();
        for (Map<String, Object> tableRow : tableRows) {
            ReferenceFactory factory = new ReferenceFactory();
            STProjectInstanceOTIndicator ot = (STProjectInstanceOTIndicator) factory.getReference(tableRow.get("pi_row_key").toString()).getObject();
            String newReprot = tableRow.get("deviationReport").toString();
            if (!ot.getDeviationReport().equals(Double.valueOf(newReprot))){
                ot.setDeviationReport(Double.valueOf(newReprot));
                PersistenceHelper.service.save(ot);
            }
        }
        return new ResponseWrapper(ResponseWrapper.SUCCESS, "", null);
    }
}