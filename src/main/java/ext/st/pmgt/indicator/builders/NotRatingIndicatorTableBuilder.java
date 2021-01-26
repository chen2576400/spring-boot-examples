package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIMessage;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STProjectInstanceINIndicator;
import ext.st.pmgt.indicator.model.STRating;
import ext.st.pmgt.indicator.resources.indicatorResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName NotRatingIndicatorTableBuilder
 * @Description:
 * @Author hma
 * @Date 2021/1/25
 * @Version V1.0
 **/
public class NotRatingIndicatorTableBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        PIPlanActivity act = (PIPlanActivity) sourceObject;
        List<STProjectInstanceINIndicator> result = new ArrayList<>();
        List<STProjectInstanceINIndicator> ins = (List) STIndicatorHelper.service.findProjectINIndicatorByPlanActivity(act);
        for (STProjectInstanceINIndicator in : ins) {
            Collection ratings = STIndicatorHelper.service.findRatingByIN(in);
            if (ratings.isEmpty()){//说明指标未评定
                result.add(in);
            }
        }

        return result;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setEntities(componentData);
        tableConfig.setId("notRatingIndicatorTable");
        tableConfig.setPrimaryObjectType(STProjectInstanceINIndicator.class);
        tableConfig.setTableTitle(PIMessage.getLocalizedMessage(indicatorResource.class.getName(), "NOT_RATING_INDICATOR_TABLE", null, params.getLocale()));
        tableConfig.enableSelect();
        tableConfig.setPageSize(50);
        tableConfig.setRightMenuName("INTableMenu",params);

        ColumnConfig columnconfig = componentConfigFactory.newColumnConfig();
        columnconfig.setName("otCode");
        columnconfig.setLabel("指标编码");
        columnconfig.enableSort();
        columnconfig.enableFilter();
        tableConfig.addColumn(columnconfig);

        ColumnConfig column12 = componentConfigFactory.newColumnConfig();
        column12.setName("persistInfo.createStamp");
        column12.setLabel("创建时间");
        column12.enableSort();
        tableConfig.addColumn(column12);

        return tableConfig;
    }
}