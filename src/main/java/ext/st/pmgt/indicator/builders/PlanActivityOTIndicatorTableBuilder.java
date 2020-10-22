package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.fc.collections.PICollection;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIMessage;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.pmgt.assignment.PIAssignmentHelper;
import com.pisx.tundra.pmgt.assignment.model.PIResourceAssignment;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.resource.PIResourceHelper;
import com.pisx.tundra.pmgt.resource.model.PIObs;
import com.pisx.tundra.pmgt.resource.model.PIResource;
import com.pisx.tundra.pmgt.resource.service.PIResourceUpAndDownService;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import ext.st.pmgt.indicator.resources.indicatorResource;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PlanActivityOTIndicatorTableBuilder
 * @Description:
 * @Author hma
 * @Date 2020/10/13
 * @Version V1.0
 **/
public class PlanActivityOTIndicatorTableBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        PIPlanActivity piPlanActivity = (PIPlanActivity) params.getNfCommandBean().getSourceObject();
        return STIndicatorHelper.service.findProjectOTIndicatorByPlanActivity(piPlanActivity);
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setEntities(componentData);
//        tableConfig.setToolbarActionModel("allUsersToolbarSet");
        tableConfig.setId("planActivityOTIndicator");
        tableConfig.setPrimaryObjectType(STProjectInstanceOTIndicator.class);
        tableConfig.setTableTitle(PIMessage.getLocalizedMessage(indicatorResource.class.getName(),"OT_INDICATOR_TABLE",null,params.getLocale()));
        tableConfig.enableSelect();
//        tableConfig.setToolbarActionModel("deliverablesForPlanToolBarSet");


        ColumnConfig columnconfig = componentConfigFactory.newColumnConfig();
        columnconfig.setName("code");
//        columnconfig.haveInfoPageLink();
        tableConfig.addColumn(columnconfig);

        ColumnConfig columnconfig3 = componentConfigFactory.newColumnConfig();
        columnconfig3.setName("description");
        tableConfig.addColumn(columnconfig3);

        ColumnConfig columnconfig32 = componentConfigFactory.newColumnConfig();
        columnconfig32.setName("standardDeviationValue");
        tableConfig.addColumn(columnconfig32);

        ColumnConfig columnconfig33 = componentConfigFactory.newColumnConfig();
        columnconfig33.setName("standardDifficultyValue");
        tableConfig.addColumn(columnconfig33);

        ColumnConfig columnconfig4 = componentConfigFactory.newColumnConfig();
        columnconfig4.setName("deviationReport");
        tableConfig.addColumn(columnconfig4);

        ColumnConfig columnconfig5 = componentConfigFactory.newColumnConfig();
        columnconfig5.setName("difficultyReport");
        tableConfig.addColumn(columnconfig5);

        return tableConfig;
    }
}