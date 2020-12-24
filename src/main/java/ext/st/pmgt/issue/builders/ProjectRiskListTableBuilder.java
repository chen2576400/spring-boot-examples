package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.Row;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import ext.st.pmgt.issue.model.STProjectIssue;

import java.util.ArrayList;
import java.util.List;

/**
 * 该问题相关风险展示列表
 */
public class ProjectRiskListTableBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        Object contextObj = params.getNfCommandBean().getSourceObject();
        STProjectIssue stProjectIssue=null;
        List result = new ArrayList();
        if (contextObj instanceof STProjectIssue){
            stProjectIssue =(STProjectIssue)contextObj;
        }
        Row row = new Row();
        row.put("风险编号",stProjectIssue.getDescription());
        row.put("风险名称",stProjectIssue.getName());
        result.add(row);
       return result;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        if (componentData==null)  return null;

        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();

        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setEntities(componentData);
        ColumnConfig column1 = componentConfigFactory.newColumnConfig();
        column1.setName("风险编号");
        column1.setLabel("风险编号");
        tableConfig.addColumn(column1);

        ColumnConfig column2 = componentConfigFactory.newColumnConfig();
        column2.setName("风险名称");
        column2.setLabel("风险名称");
        tableConfig.addColumn(column2);
        return tableConfig;
    }
}
