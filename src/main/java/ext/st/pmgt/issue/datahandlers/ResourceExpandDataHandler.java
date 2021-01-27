package ext.st.pmgt.issue.datahandlers;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.div.DivElement;
import com.pisx.tundra.netfactory.mvc.components.img.ImgElement;
import com.pisx.tundra.netfactory.mvc.components.input.InputElement;
import com.pisx.tundra.netfactory.util.misc.URLFactory;
import com.pisx.tundra.pmgt.change.model.PIProjectChangeRequest;
import com.pisx.tundra.pmgt.change.model.PIProjectIssue;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.PIProjectHelper;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.project.model.PIProjectContainer;
import com.pisx.tundra.pmgt.resource.model.PIResource;
import com.pisx.tundra.pmgt.risk.datahandlers.ResourceDataHandler;
import com.pisx.tundra.pmgt.risk.model.PIProjectRisk;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectRisk;

public class ResourceExpandDataHandler extends ResourceDataHandler {
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
        String url = URLFactory.getActionHref("pi-pmgt-enterprise", "resourcePicker", this.getParentPageObject(sourceObject));

        rightImg.backFill(url, columnName);

        DivElement content = DivElement.instance();
        content.attribute(attr -> attr.setStyle("display:initial"));
        content.children(inputElement, rightImg);
        return content;
    }

    @Override
    public Object getParentPageObject(Object sourceObject){
        Object pageObject = null;
        pageObject=super.getParentPageObject(sourceObject);
        if (sourceObject instanceof STProjectIssue){
            pageObject = ((STProjectIssue) sourceObject).getProject();
        }
        else if (sourceObject instanceof STProjectRisk){
            pageObject = ((STProjectRisk) sourceObject).getProject();
        }else if (sourceObject instanceof PIPlanActivity){
            pageObject = ((PIPlanActivity) sourceObject).getProject();
        }else  if (sourceObject instanceof PIProjectContainer) {
            try {
                pageObject = PIProjectHelper.service.getProjectFromContainer((PIProjectContainer) sourceObject);
            } catch (PIException e) {
               return null;
            }
        }
        return pageObject;
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