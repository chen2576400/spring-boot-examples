package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.model.PIPmgtBaselineType;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.dao.ProjectInstanceINIndicatorDao;
import ext.st.pmgt.indicator.model.STProjectInstanceINIndicator;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import ext.st.pmgt.indicator.model.STRating;

/**
 * @ClassName AddRatingWizardBuilder
 * @Description:
 * @Author hma
 * @Date 2020/11/9
 * @Version V1.0
 **/
public class AddRatingWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        STProjectInstanceOTIndicator ot = null;
        STProjectInstanceINIndicator in = null;
        if (sourceObject instanceof STProjectInstanceOTIndicator){
            ot = (STProjectInstanceOTIndicator) sourceObject;
            ObjectReference actRef = ot.getPlanActivityReference();
            in = STIndicatorHelper.service.getINByActRef(actRef);
        }
        return in;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("addRatingWizard");

        try {
            StepConfig step = wizardConfig.newStep();
            step.setId("addRatingStep");
            LayoutConfig layout = componentConfigFactory.newLayoutConfig(params);
            layout.setPrimaryClassName(STRating.class);
            layout.setId("addRatingLayout1");
            layout.addField("otRating")
                    .addField("description");
            step.addLayout(layout);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wizardConfig;
    }
}