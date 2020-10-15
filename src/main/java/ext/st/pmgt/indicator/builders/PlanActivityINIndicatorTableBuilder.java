package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIMessage;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STProjectInstanceINIndicator;
import ext.st.pmgt.indicator.resources.indicatorResource;

import java.util.ArrayList;

/**
 * @ClassName PlanActivityINIndicatorTableBuilder
 * @Description:
 * @Author hma
 * @Date 2020/10/13
 * @Version V1.0
 **/
public class PlanActivityINIndicatorTableBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        PIPlanActivity piPlanActivity = (PIPlanActivity) params.getNfCommandBean().getSourceObject();
        return STIndicatorHelper.service.findProjectINIndicatorByPlanActivity(piPlanActivity);
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setEntities(componentData);
//        tableConfig.setToolbarActionModel("allUsersToolbarSet");
        tableConfig.setId("planActivityINIndicator");
        tableConfig.setObjectType(STProjectInstanceINIndicator.class);
        tableConfig.setTableTitle(PIMessage.getLocalizedMessage(indicatorResource.class.getName(),"IN_INDICATOR_TABLE",null,params.getLocale()));
        tableConfig.enableSelect();
//        tableConfig.setToolbarActionModel("deliverablesForPlanToolBarSet");
//        tableConfig.setRightMenuName("deliverablesForPlanToolBarSet",params);

        ColumnConfig columnconfig = componentConfigFactory.newColumnConfig();
        columnconfig.setName("code");
        columnconfig.haveInfoPageLink();
        tableConfig.addColumn(columnconfig);

        ColumnConfig columnconfig3 = componentConfigFactory.newColumnConfig();
        columnconfig3.setName("description");
        tableConfig.addColumn(columnconfig3);


        ColumnConfig columnconfig33 = componentConfigFactory.newColumnConfig();
        columnconfig33.setName("inWeight");
        tableConfig.addColumn(columnconfig33);

        return tableConfig;
    }
}