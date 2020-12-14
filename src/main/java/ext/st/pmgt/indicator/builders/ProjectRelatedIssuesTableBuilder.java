package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIMessage;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.pmgt.change.PIProjectChangeHelper;
import com.pisx.tundra.pmgt.change.model.PIProjectIssue;
import com.pisx.tundra.pmgt.change.resources.changeResource;
import com.pisx.tundra.pmgt.project.PIProjectHelper;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.project.model.PIProjectContainer;
import ext.st.pmgt.indicator.STProjectIssueHelper;
import ext.st.pmgt.indicator.model.STProjectIssue;

public class ProjectRelatedIssuesTableBuilder extends AbstractComponentBuilder {

    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        PIProject project = null;
        Object contextObj = params.getNfCommandBean().getSourceObject();
        if (contextObj instanceof PIProjectContainer) {
            project = PIProjectHelper.service.getProjectFromContainer((PIProjectContainer) contextObj);

        } else if (contextObj instanceof PIProject) {
            project = (PIProject) contextObj;
        }
//        return PIProjectChangeHelper.service.getAllProjectIssues(project);
        return STProjectIssueHelper.service.getAllProjectIssues(project);
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setEntities(componentData);
        tableConfig.setId("projectRelatedIssuesTable");
        tableConfig.setPrimaryObjectType(STProjectIssue.class);
        tableConfig.setTableTitle(PIMessage.getLocalizedMessage(changeResource.class.getName(),"PROJECT_RELATED_ISSUES_TABLE_TITLE",null, params.getLocale()));
        tableConfig.enableSearch();
        tableConfig.enableSelect();
        tableConfig.setToolbarActionModel("projectRelatedIssuesToolBar",params);
        tableConfig.setRightMenuName("projectRelatedIssuesMenus",params);

        //定义每一个column及其属性
        ColumnConfig column1 = componentConfigFactory.newColumnConfig();
        column1.setName("issueNumber");
        tableConfig.addColumn(column1);

        //定义每一个column及其属性
        ColumnConfig column2 = componentConfigFactory.newColumnConfig();
        column2.setName("name");
        tableConfig.addColumn(column2);

        //定义每一个column及其属性
        ColumnConfig column3 = componentConfigFactory.newColumnConfig();
        column3.setName("issueType");
        tableConfig.addColumn(column3);

        ColumnConfig column31 = componentConfigFactory.newColumnConfig();
        column31.setName("project.projectName");
        column31.setLabel("项目");
        tableConfig.addColumn(column31);

        ColumnConfig column30 = componentConfigFactory.newColumnConfig();
        column30.setName("planActivity.name");
        column30.setLabel("活动");
        tableConfig.addColumn(column30);

        //定义每一个column及其属性
        ColumnConfig column4 = componentConfigFactory.newColumnConfig();
        column4.setName("state.state.stateName");
        column4.setLabel("状态");
        tableConfig.addColumn(column4);

        //定义每一个column及其属性
        ColumnConfig column5 = componentConfigFactory.newColumnConfig();
        column5.setName("addDate");
        tableConfig.addColumn(column5);

//        //定义每一个column及其属性
//        ColumnConfig column6 = componentConfigFactory.newColumnConfig();
//        column6.setName("addedBy");
//        tableConfig.addColumn(column6);

        //定义每一个column及其属性
        ColumnConfig column7 = componentConfigFactory.newColumnConfig();
        column7.setName("responsibleUser.name");
        column7.setLabel("负责人");
        tableConfig.addColumn(column7);

        ColumnConfig column71 = componentConfigFactory.newColumnConfig();
        column71.setName("description");
        tableConfig.addColumn(column71);

        ColumnConfig column72 = componentConfigFactory.newColumnConfig();
        column72.setName("priorityType");
        tableConfig.addColumn(column72);

        //定义每一个column及其属性
        ColumnConfig column8 = componentConfigFactory.newColumnConfig();
        column8.setName("expectedSolutionDate");
        tableConfig.addColumn(column8);

        ColumnConfig column9 = componentConfigFactory.newColumnConfig();
        column9.setName("resolvedDate");
        tableConfig.addColumn(column9);

        ColumnConfig column10 = componentConfigFactory.newColumnConfig();
        column10.setName("creator.name");
        column10.setLabel("创建者");
        tableConfig.addColumn(column10);

        ColumnConfig column11 = componentConfigFactory.newColumnConfig();
        column11.setName("persistInfo.createStamp");
        tableConfig.addColumn(column11);

        return tableConfig;
    }
}
