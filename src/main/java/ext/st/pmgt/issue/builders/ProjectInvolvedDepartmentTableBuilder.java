package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import ext.st.pmgt.issue.model.STProjectIssue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * 相关涉及部门展示
 */
public class ProjectInvolvedDepartmentTableBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        STProjectIssue stProjectIssue=null;
        List result = new ArrayList();
        if (sourceObject instanceof STProjectIssue){
            stProjectIssue =(STProjectIssue)sourceObject;
        }
        if (stProjectIssue!=null){
          return Arrays.asList(stProjectIssue.getDutyGroup());
        }
        return  null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();

        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setEntities(componentData);
        tableConfig.setId("projectInvolvedDepartmentTable");
        tableConfig.setPrimaryObjectType(PIGroup.class);
        tableConfig.setTableTitle("标题");


        tableConfig.setToolbarActionModel("departmentDesignToolbarSet");//操作按钮


        ColumnConfig column1 = componentConfigFactory.newColumnConfig();
        column1.setName("name");
        column1.setLabel("名称");
        tableConfig.addColumn(column1);

//        ColumnConfig column2 = componentConfigFactory.newColumnConfig();
//        column2.setName("group");
//        column2.setLabel("涉及部门");
//        tableConfig.addColumn(column2);
        return tableConfig;
    }
}
