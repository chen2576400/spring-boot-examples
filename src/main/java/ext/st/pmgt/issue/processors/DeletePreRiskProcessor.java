package ext.st.pmgt.issue.processors;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import ext.st.pmgt.issue.STRiskHelper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeletePreRiskProcessor  extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        ObjectIdentifier objectIdentifier1 = sourceObject.getObjectIdentifier();
        ObjectReference  objectReference1= ObjectReference.newObjectReference(objectIdentifier1);
        List<Persistable> persistableList = params.getNfCommandBean().getSelectedObjects();
        for (Persistable persistable:persistableList){
            ObjectIdentifier objectIdentifier2 = persistable.getObjectIdentifier();
            ObjectReference objectReference2 = ObjectReference.newObjectReference(objectIdentifier2);
            STRiskHelper.preRiskLinkService.deleteByRoleAObjectRefAndRoleBObjectRef(objectReference1,objectReference2);
        }

        return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, "删除成功！", null);
    }
}
