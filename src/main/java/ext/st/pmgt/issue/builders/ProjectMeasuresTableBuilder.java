package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIMessage;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.pmgt.change.resources.changeResource;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.issue.STProjectMeasuresHelper;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectMeasures;
import ext.st.pmgt.issue.model.STProjectRisk;
import ext.st.pmgt.issue.util.ProjectPermissionUtil;

public class ProjectMeasuresTableBuilder extends AbstractComponentBuilder {
    PIProject project = null;
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        Persistable persistable = params.getNfCommandBean().getSourceObject();
        STProjectRisk risk = null;
        if (persistable instanceof STProjectRisk) {
            risk = (STProjectRisk) persistable;
            project = risk.getProject();
            return STProjectMeasuresHelper.measuresService.findByProjectRiskReference(ObjectReference.newObjectReference(risk));
        }
        return null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();

        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setEntities(componentData);
        tableConfig.setId("projectRiskMeasuresTable");
        tableConfig.setPrimaryObjectType(STProjectMeasures.class);
        tableConfig.enableSearch();
        tableConfig.enableSelect();
        if (isSelect(params)) {
            tableConfig.setToolbarActionModel("projectRiskMeasuresToolBar", params);
            if (isManager(project)) {
                tableConfig.setRightMenuName("measuresMenusByManager", params);
            } else {
                tableConfig.setRightMenuName("measuresMenus", params);
            }
        }

        ColumnConfig column1 = componentConfigFactory.newColumnConfig();
        column1.setName("name");
        column1.haveInfoPageLink();
        tableConfig.addColumn(column1);

        ColumnConfig column2 = componentConfigFactory.newColumnConfig();
        column2.setName("precaution");
        tableConfig.addColumn(column2);

        ColumnConfig column8 = componentConfigFactory.newColumnConfig();
        column8.setName("projectManagerUser.fullName");
        column8.setLabel("项目经理");
        tableConfig.addColumn(column8);

        ColumnConfig column6 = componentConfigFactory.newColumnConfig();
        column6.setName("confirmStatus");
        tableConfig.addColumn(column6);

        ColumnConfig column3 = componentConfigFactory.newColumnConfig();
        column3.setName("involveGroup.name");
        column3.setLabel("涉及部门");
        column3.haveInfoPageLink();
        tableConfig.addColumn(column3);

        ColumnConfig column4 = componentConfigFactory.newColumnConfig();
        column4.setName("dutyUser.fullName");
        column4.haveInfoPageLink();
        column4.setLabel("责任人");
        tableConfig.addColumn(column4);

        ColumnConfig column5 = componentConfigFactory.newColumnConfig();
        column5.setName("persistInfo.createStamp");
        column5.setLabel("创建时间");
        column5.enableSort();


        ColumnConfig column7 = componentConfigFactory.newColumnConfig();
        column7.setName("closeStamp");
        tableConfig.addColumn(column7);

        return tableConfig;


    }

    private boolean isSelect(ComponentParams params) throws PIException {
        Persistable persistable = params.getNfCommandBean().getSourceObject();
        if (persistable instanceof STProjectRisk) {
            return true;
        }
        return false;
    }

    private Boolean isManager(PIProject project) throws PIException {
        return ProjectPermissionUtil.isProjectRole(project, null, "PM");
    }
}