package ext.st.pmgt.indicator.processors;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultCreateFormProcessor;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STProjectInstanceINIndicator;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import ext.st.pmgt.indicator.model.STRating;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AddRatingProcessor
 * @Description:
 * @Author hma
 * @Date 2020/11/9
 * @Version V1.0
 **/
@Component
public class AddRatingProcessor extends DefaultObjectFormProcessor{
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        STProjectInstanceOTIndicator ot = null;
        STProjectInstanceINIndicator in = null;
        if (sourceObject instanceof STProjectInstanceOTIndicator){
            ot = (STProjectInstanceOTIndicator) sourceObject;
            in = STIndicatorHelper.service.getInByOT(ot);
        }
        if (in==null){
            return new ResponseWrapper(ResponseWrapper.FAILED, "", null);
        }
        Map<String, Object> layoutFields = params.getNfCommandBean().getLayoutFields();
        String otRating = (String) layoutFields.get("otRating");
        String description = (String) layoutFields.get("description");
        STRating stRating = STRating.newSTRating();
//        stRating.setOtRating(otRating);
        stRating.setDescription(description);
        stRating.setInIndicator(in);
        stRating.setReportTime(new Timestamp(System.currentTimeMillis()));
        PersistenceHelper.service.save(stRating);

        return new ResponseWrapper(ResponseWrapper.REGIONAL_FLUSH, "添加成功！", null);
    }
}