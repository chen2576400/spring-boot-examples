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
import ext.st.pmgt.issue.STProjectMeasuresHelper;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectMeasures;
import ext.st.pmgt.issue.model.STProjectRisk;

public class ProjectMeasuresTableBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {

        Persistable persistable = params.getNfCommandBean().getSourceObject();
        STProjectRisk risk = null;
        if (persistable instanceof STProjectRisk) {
            risk = (STProjectRisk) persistable;
            return STProjectMeasuresHelper.measuresService.findByProjectRiskReference(ObjectReference.newObjectReference(risk));
        }
        return  null;
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
        if (isSelect(params)){
            tableConfig.setToolbarActionModel("projectRiskMeasuresToolBar",params);
        }

        ColumnConfig column1 = componentConfigFactory.newColumnConfig();
        column1.setName("name");
        tableConfig.addColumn(column1);

        ColumnConfig column2 = componentConfigFactory.newColumnConfig();
        column2.setName("precaution");
        tableConfig.addColumn(column2);

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
        return tableConfig;


    }

    private boolean isSelect(ComponentParams params) throws PIException {
//        String sourceOid = params.getNfCommandBean().getSourceOid().toString();
//        Persistable persistable = WorkflowUtil.getObjectByOid(sourceOid);
        Persistable persistable = params.getNfCommandBean().getSourceObject();
        if (persistable instanceof STProjectRisk) {
            return true;
        }
        return false;
    }
}