package ext.st.pmgt.issue.processors;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.inf.container.model.OrgContainer;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.mvc.components.table.Row;
import com.pisx.tundra.netfactory.util.misc.Option;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectDutyDepartmentProcessor extends DefaultObjectFormProcessor
{
    @Override
    protected ResponseWrapper<?> process(ComponentParams params) throws Exception {
        List<Persistable> selectedObjects = params.getNfCommandBean().getSelectedObjects();
        String key="";
        String value="";
//        List<Row> rowList=new ArrayList<>();
//        if(!CollectionUtils.isEmpty(selectedObjects)){
//            for(Persistable persistable:selectedObjects ){
//                if(persistable instanceof PIGroup){
//                    PIGroup  group=(PIGroup)persistable;
//                    Row row=new Row();
//                    row.setRowSingleEntity(group);
//                    row.put("name",group.getName());
//                    row.put("containerReference",((OrgContainer)group.getContainerReference().getObject()).getOrganizationName());
//                    rowList.add(row);
//                }
//            }
//        }

        if (!CollectionUtils.isEmpty(selectedObjects)){
            Persistable persistable=selectedObjects.get(0);
            if(persistable instanceof PIGroup){
                PIGroup  group=(PIGroup)persistable;
                key=group.getOid();
                value=group.getName();
            }
        }
        Option load = new Option(key,value);
        return new ResponseWrapper<>(ResponseWrapper.BACK_FILL,null,load);
    }



}
