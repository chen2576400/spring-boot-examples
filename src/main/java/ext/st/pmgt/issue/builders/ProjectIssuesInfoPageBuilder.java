package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.tab.TabConfig;
import com.pisx.tundra.netfactory.util.misc.ComponentException;
import ext.st.pmgt.issue.model.STProjectIssue;
import org.springframework.stereotype.Component;

@Component("ext.st.pmgt.issue.model.STProjectIssue-InfoPage")
public class ProjectIssuesInfoPageBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws ComponentException, PIException {
        return params.getNfCommandBean().getPagePrimaryObject();
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws ComponentException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        TabConfig tabConfig = componentConfigFactory.newTabConfig(params);
        tabConfig.setId("projectIssueInfoPageTabSetCopy");

        tabConfig.setContextObjectIcon("img/pmgt/issues.png");
        String contextObjectIdentifier = getContextObjectIdentifier((STProjectIssue) componentData);//设置上下文对象
        tabConfig.setContextObjectIdentifier(" 问题 - "+contextObjectIdentifier);
        return tabConfig;
    }

    /**
     * 上下文对象标识
     * @return
     */
    private String getContextObjectIdentifier(STProjectIssue stProjectIssue){
        String contextObjectIdentifier ="";
        //todo 空指针
//        String viewName = part.getViewName();
        contextObjectIdentifier = stProjectIssue.getName();
        //ReferenceFactory rf=new ReferenceFactory();
        //PIReference reference= rf.getReference(part);

        return contextObjectIdentifier;
    }
}
