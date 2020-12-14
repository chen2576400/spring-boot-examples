package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIMessage;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.pmgt.project.PIProjectHelper;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.project.model.PIProjectContainer;
import com.pisx.tundra.pmgt.risk.resources.riskResource;
import ext.st.pmgt.indicator.STRiskHelper;
import ext.st.pmgt.indicator.model.STProjectRisk;

public class ProjectRisksForProjectTableBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        PIProject project = null;
        Object contextObj = params.getNfCommandBean().getSourceObject();
        if (contextObj instanceof PIProjectContainer) {
            project = PIProjectHelper.service.getProjectFromContainer((PIProjectContainer) contextObj);
        } else if (contextObj instanceof PIProject) {
            project = (PIProject) contextObj;
        }
        return STRiskHelper.service.getProjectRisks(project);
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setComponentParams(params);
        tableConfig.haveStripe();
        tableConfig.setEntities(componentData);
        tableConfig.setId("projectRisksForProjectTable");//表格id
        tableConfig.setPrimaryObjectType(STProjectRisk.class);
        tableConfig.setTableTitle(PIMessage.getLocalizedMessage(riskResource.class.getName(),"PROJECT_RISKS_FOR_PROJECT_TABLE_TITLE",null, params.getLocale()));
        tableConfig.enableSelect();//设置单选多选
        tableConfig.setToolbarActionModel("projectRisksTableToolbarSet");
        tableConfig.setRightMenuName("projectRisksMenus", params);



        ColumnConfig column1 = componentConfigFactory.newColumnConfig();
        column1.setName("riskCode");
        column1.haveInfoPageLink();
        tableConfig.addColumn(column1);

        ColumnConfig column2 = componentConfigFactory.newColumnConfig();
        column2.setName("riskName");
        tableConfig.addColumn(column2);

        ColumnConfig column4 = componentConfigFactory.newColumnConfig();
        column4.setName("riskToType");
        tableConfig.addColumn(column4);

        ColumnConfig column5 = componentConfigFactory.newColumnConfig();
        column5.setName("riskDescription");
        tableConfig.addColumn(column5);

        ColumnConfig column6 = componentConfigFactory.newColumnConfig();
        column6.setName("state.state.stateName");
        column6.setLabel("状态");
        tableConfig.addColumn(column6);

        ColumnConfig column7 = componentConfigFactory.newColumnConfig();
        column7.setName("addDate");
        tableConfig.addColumn(column7);

        ColumnConfig column8 = componentConfigFactory.newColumnConfig();
        column8.setName("identifiedBy.name");
        column8.setLabel("识别人");
        tableConfig.addColumn(column8);

        ColumnConfig column9 = componentConfigFactory.newColumnConfig();
        column9.setName("rsrc.name");
        column9.setLabel("资源");
        tableConfig.addColumn(column9);

        ColumnConfig column10 = componentConfigFactory.newColumnConfig();
        column10.setName("responseType");
        tableConfig.addColumn(column10);

        ColumnConfig column11 = componentConfigFactory.newColumnConfig();
        column11.setName("responseText");
        tableConfig.addColumn(column11);

        ColumnConfig column12 = componentConfigFactory.newColumnConfig();
        column12.setName("resolvedDate");
        tableConfig.addColumn(column12);

        ColumnConfig column13 = componentConfigFactory.newColumnConfig();
        column13.setName("creator.name");
        column13.setLabel("创建者");
        tableConfig.addColumn(column13);

        ColumnConfig column14 = componentConfigFactory.newColumnConfig();
        column14.setName("persistInfo.createStamp");
        tableConfig.addColumn(column14);

        return tableConfig;
    }
}
