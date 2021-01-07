package ext.st.pmgt.issue.datahandlers;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.div.DivElement;
import com.pisx.tundra.netfactory.mvc.components.img.ImgElement;
import com.pisx.tundra.netfactory.mvc.components.input.InputElement;
import com.pisx.tundra.netfactory.util.misc.URLFactory;
import com.pisx.tundra.pmgt.resource.model.PIResource;
import com.pisx.tundra.pmgt.risk.datahandlers.ResourceDataHandler;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectRisk;

public class ResourceDataExpandHandler extends ResourceDataHandler {
    @Override
    public Object getDataValue(String columnName, Object datum, ComponentParams params) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        InputElement inputElement = InputElement.instance(columnName);
        if (datum != null) {//编辑页面是需要回填到input框
            getInputValue(datum, inputElement);
        }
        inputElement.attribute(elementAttribute -> {
            elementAttribute.addStyle("width:150px;");
            //datum为空的时候，说明是创建，不为空时为编辑或者更新
        });


        ImgElement rightImg = ImgElement.instance();
        rightImg.attribute(imgAttribute -> {
            imgAttribute.setSrc("/img/foundation/find.gif");
            imgAttribute.setTitle("选择");
            imgAttribute.setStyle("cursor: pointer;margin:3px;");
        });

        //context传给objpicker
        String url = URLFactory.getActionHref("pi-pmgt-enterprise", "resourcePicker", getParentPageObject(sourceObject));

        rightImg.backFill(url, columnName);

        DivElement content = DivElement.instance();
        content.attribute(attr -> attr.setStyle("display:initial"));
        content.children(inputElement, rightImg);
        return content;
    }

    public void getInputValue(Object datum, InputElement inputElement) {
        super.getInputValue(datum, inputElement);
        if (datum instanceof STProjectRisk) {
            STProjectRisk context = (STProjectRisk) datum;
            try {
                PIResource rsrc = context.getRsrc();
                if (rsrc != null) {
                    inputElement.setValue(rsrc.getOid(), rsrc.getName());
                }
            } catch (PIException e) {
                e.printStackTrace();
            }
        } else if (datum instanceof STProjectIssue) {
            STProjectIssue context = (STProjectIssue) datum;
            try {
                PIResource rsrc = context.getRsrc();
                if (rsrc != null) {
                    inputElement.setValue(rsrc.getOid(), rsrc.getName());
                }
            } catch (PIException e) {
                e.printStackTrace();
            }
        }
    }
}
