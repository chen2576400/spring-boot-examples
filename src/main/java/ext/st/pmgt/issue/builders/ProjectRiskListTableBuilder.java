package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.workflow.util.WorkflowUtil;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.Row;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.pmgt.project.PIProjectHelper;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.project.model.PIProjectContainer;
import ext.st.pmgt.issue.STRiskHelper;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectRisk;

import java.util.ArrayList;
import java.util.List;

/**
 * 该问题相关风险展示列表
 */
public class ProjectRiskListTableBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        PIProject project = null;
        Object contextObj = params.getNfCommandBean().getSourceObject();
        if (contextObj instanceof STProjectIssue) {
            project = ((STProjectIssue) contextObj).getProject();
        } else if (contextObj instanceof PIProject) {
            project = (PIProject) contextObj;
        }
        return STRiskHelper.service.getProjectRisks(project);
    }
    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        if (!isSelect(params)){
            return  null;
        }
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();

        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setEntities(componentData);
        tableConfig.setId("projectRiskListTableBuilder");
        tableConfig.setPrimaryObjectType(STProjectRisk.class);
        tableConfig.setTableTitle("风险列表");

        ColumnConfig column1 = componentConfigFactory.newColumnConfig();
        column1.setName("riskCode");
//        column1.setLabel("风险编号");
        tableConfig.addColumn(column1);


        ColumnConfig column2 = componentConfigFactory.newColumnConfig();
        column2.setName("riskName");
        column2.haveInfoPageLink();
//        column2.setLabel("风险名称");
        tableConfig.addColumn(column2);
        return tableConfig;
    }

    private  boolean  isSelect(ComponentParams params) throws PIException {
        String sourceOid =  params.getNfCommandBean().getSourceOid().toString();
        Persistable persistable = WorkflowUtil.getObjectByOid(sourceOid);
        if (persistable instanceof STProjectIssue){
            return true;
        }
        return  false;
    }
}
