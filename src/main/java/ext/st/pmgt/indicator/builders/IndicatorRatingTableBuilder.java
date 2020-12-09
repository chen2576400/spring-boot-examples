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
import com.pisx.tundra.netfactory.util.misc.Collections;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STProjectInstanceINIndicator;
import ext.st.pmgt.indicator.model.STRating;
import ext.st.pmgt.indicator.resources.indicatorResource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName IndicatorRatingTableBuilder
 * @Description:
 * @Author hma
 * @Date 2020/12/8
 * @Version V1.0
 **/
public class IndicatorRatingTableBuilder extends AbstractComponentBuilder {

    //获取最新的评定
    private STRating getLatestRating(List<STRating> ratings) {
        ratings = ratings.stream().sorted(Comparator.comparing(STRating::getReportTime).reversed()).collect(Collectors.toList());
        return ratings.get(0);
    }

    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        PIPlanActivity act = (PIPlanActivity) sourceObject;
        List<STRating> ratings = new ArrayList<>();

        List<STProjectInstanceINIndicator> ins = (List) STIndicatorHelper.service.findProjectINIndicatorByPlanActivity(act);
        if (Collections.isNotEmpty(ins)) {
            for (STProjectInstanceINIndicator in : ins) {
                List<STRating> ratings1 = (List) STIndicatorHelper.service.findRatingByIN(in);
                if (ratings1.size() > 0) {
                    ratings.add(getLatestRating(ratings1));
                }
            }

        }
        return ratings;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setEntities(componentData);
        tableConfig.setId("indicatorRatingTable");
        tableConfig.setPrimaryObjectType(STRating.class);
        tableConfig.setTableTitle(PIMessage.getLocalizedMessage(indicatorResource.class.getName(), "INDICATOR_RATING_TABLE", null, params.getLocale()));
        tableConfig.enableSelect();
        tableConfig.setPageSize(50);
//        tableConfig.setToolbarActionModel("deliverablesForPlanToolBarSet");


        ColumnConfig columnconfig = componentConfigFactory.newColumnConfig();
        columnconfig.setName("inIndicator.otCode");
        columnconfig.setLabel("指标编码");
        columnconfig.enableSort();
        tableConfig.addColumn(columnconfig);

        ColumnConfig columnconfig2 = componentConfigFactory.newColumnConfig();
        columnconfig2.setName("otRating");
        columnconfig2.enableSort();
        tableConfig.addColumn(columnconfig2);


        ColumnConfig columnconfig3 = componentConfigFactory.newColumnConfig();
        columnconfig3.setName("description");
        columnconfig3.enableSort();
        tableConfig.addColumn(columnconfig3);

        ColumnConfig columnconfig4 = componentConfigFactory.newColumnConfig();
        columnconfig4.setName("reportTime");
        columnconfig4.enableSort();
        tableConfig.addColumn(columnconfig4);

        return tableConfig;
    }
}