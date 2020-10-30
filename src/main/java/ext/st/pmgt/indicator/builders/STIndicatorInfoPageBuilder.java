package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.tab.TabConfig;
import com.pisx.tundra.pmgt.risk.model.PIProjectRisk;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * @ClassName STIndicatorInfoPageBuilder
 * @Description:
 * @Author hma
 * @Date 2020/10/29
 * @Version V1.0
 **/
@Component("ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator-InfoPage")
public class STIndicatorInfoPageBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return params.getNfCommandBean().getPagePrimaryObject();
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        TabConfig tabConfig = componentConfigFactory.newTabConfig(params);
        tabConfig.setId("otIndicatorInfoPageTabSet");
        return tabConfig;
    }
}