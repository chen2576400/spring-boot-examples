package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.tab.TabConfig;
import com.pisx.tundra.netfactory.util.misc.ComponentException;
import org.springframework.stereotype.Component;

@Component("ext.st.pmgt.issue.model.STProjectRisk-InfoPage")
public class RiskInfoPageBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws ComponentException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        TabConfig tabConfig = componentConfigFactory.newTabConfig(params);
        tabConfig.setId("riskInfopageTabSet");
        return tabConfig;
    }
}
