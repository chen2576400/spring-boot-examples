package ext.st.pmgt.issue.datahandlers;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.div.DivElement;
import com.pisx.tundra.netfactory.mvc.components.img.ImgElement;
import com.pisx.tundra.netfactory.mvc.components.input.InputElement;
import com.pisx.tundra.netfactory.util.misc.URLFactory;
import com.pisx.tundra.pmgt.change.datahandlers.PlanActivityDataHandler;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.plan.model.PIPlannable;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectRisk;

public class PlanActivityExpandDataHandler extends PlanActivityDataHandler {
    @Override
    public Object getDataValue(String columnName, Object datum, ComponentParams params) throws PIException {

        Persistable sourceObject = params.getNfCommandBean().getSourceObject();

        InputElement inputElement = InputElement.instance(columnName);
        if (datum!=null){//编辑页面是需要回填到input框
            getInputValue(datum,inputElement);
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
        String url = URLFactory.getActionHref("pi-pmgt-enterprise", "requestActivityPicker",getContent(sourceObject));

        rightImg.backFill(url, columnName);

        DivElement content = DivElement.instance();
        content.attribute(attr -> attr.setStyle("display:initial"));
        content.children(inputElement, rightImg);
        return content;
    }

    private Object getContent(Persistable sourceObject){
        if (sourceObject instanceof PIProject){
            return (PIProject) sourceObject;
        }else if (sourceObject instanceof STProjectIssue){
            return ((STProjectIssue) sourceObject).getProject();
        }else if (sourceObject instanceof PIPlanActivity){
            return ((PIPlanActivity) sourceObject).getProject();
        }
        return null;
    }


    public void getInputValue(Object datum, InputElement inputElement){
        super.getInputValue(datum,inputElement);
        if (datum instanceof STProjectIssue) {
            STProjectIssue context = (STProjectIssue) datum;
            try {
                PIPlannable planActivity = context.getPlanActivity();
                if(planActivity!=null){
                    inputElement.setValue(planActivity.getOid(), planActivity.getName());
                }
            } catch (PIException e) {
                e.printStackTrace();
            }
        }


    }
}
