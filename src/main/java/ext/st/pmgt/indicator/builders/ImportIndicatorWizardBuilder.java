package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.content.datahandlers.UploadFileDataHandler;
import com.pisx.tundra.foundation.doc.builders.DocumentContainerDataHandler;
import com.pisx.tundra.foundation.doc.model.PIDocument;
import com.pisx.tundra.foundation.folder.builders.FolderPickerDataHandler;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.upload.UploadConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;

/**
 * @ClassName ImportIndicatorWizardBuilder
 * @Description:
 * @Author hma
 * @Date 2020/10/27
 * @Version V1.0
 **/
public class ImportIndicatorWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        try {
            wizardConfig.setId("importDocumentWizard");
            //第一步
            StepConfig stepConfig1 = wizardConfig.newStep();
            stepConfig1.setId("import_document_step1");
            stepConfig1.setTitle("上传文档");
            LayoutConfig layout1 = componentConfigFactory.newLayoutConfig(params);
            layout1.setPrimaryClassName(PIDocument.class);
            layout1.setId("primary_layout");
            layout1.addField(UploadConfig.PRIMARY_FILE, "文档", new UploadFileDataHandler());
            stepConfig1.children(layout1);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return wizardConfig;
    }
}