//package ext.st.pmgt.issue.processors;
//
//
//import com.pisx.tundra.foundation.fc.model.Persistable;
//import com.pisx.tundra.foundation.org.model.PIGroup;
//import com.pisx.tundra.foundation.org.model.PIUser;
//import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
//import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
//import com.pisx.tundra.netfactory.util.misc.Option;
//import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.util.List;
//@Component
//public class ProjectManagerUserProcessor  extends DefaultObjectFormProcessor{
//    @Override
//    protected ResponseWrapper<?> process(ComponentParams params) throws Exception {
//        List<Persistable> selectedObjects = params.getNfCommandBean().getSelectedObjects();
//        String key="";
//        String value="";
//        if (!CollectionUtils.isEmpty(selectedObjects)){
//            Persistable persistable=selectedObjects.get(0);
//            if(persistable instanceof PIUser){
//                PIUser piUser=(PIUser)persistable;
//                key=piUser.getOid();
//                value=piUser.getFullName();
//            }
//        }
//        Option load = new Option(key,value);
//        return new ResponseWrapper<>(ResponseWrapper.BACK_FILL,null,load);
//    }
//
//}
