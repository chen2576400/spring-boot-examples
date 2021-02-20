package ext.st.pmgt.issue.processors;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIRole;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.Option;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pdm.container.model.ProductContainer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Mr.Chen
 * @create: 2021-01-25 16:38
 **/
@Component
public class STObejctPickerProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> process(ComponentParams params) throws PIException {
        List<Persistable> selectedObjects = params.getNfCommandBean().getSelectedObjects();
        Persistable selectedObject = selectedObjects.get(0);
        String pickerObjOid = null;
        String displayVaule = null;

        if (selectedObject instanceof PIUser) {
            PIUser user = (PIUser) selectedObject;
            pickerObjOid = user.getOid();
            displayVaule = user.getFullName();
        }
        if (selectedObject instanceof PIRole) {
            PIRole role = (PIRole) selectedObject;

            pickerObjOid = role.getOid();
            displayVaule = role.getRoleName();
        }

        if (selectedObject instanceof ProductContainer) {
            ProductContainer productContainer = (ProductContainer) selectedObject;

            pickerObjOid = productContainer.getOid();
            displayVaule = productContainer.getName();
        }

        //将需要回填展示的字段和对象oid传给父页面
        Option load = new Option(pickerObjOid,displayVaule);

        return new ResponseWrapper(ResponseWrapper.BACK_FILL, null, load);

    }
}
