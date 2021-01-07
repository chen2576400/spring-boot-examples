package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.tab.TabConfig;

/**
 * @ClassName IndicatorRatingTabBuilder
 * @Description:
 * @Author hma
 * @Date 2020/12/10
 * @Version V1.0
 **/
public class IndicatorRatingTabBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        TabConfig tabConfig = componentConfigFactory.newTabConfig(params);
        tabConfig.setId("indicatorRatingTabSet");
        return tabConfig;

    }
}