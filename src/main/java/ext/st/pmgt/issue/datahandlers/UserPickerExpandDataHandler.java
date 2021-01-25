package ext.st.pmgt.issue.datahandlers;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.actionmodel.NfAction;
import com.pisx.tundra.netfactory.mvc.components.div.DivElement;
import com.pisx.tundra.netfactory.mvc.components.img.ImgElement;
import com.pisx.tundra.netfactory.mvc.components.input.InputElement;
import com.pisx.tundra.netfactory.util.action.NfActionHelper;
import com.pisx.tundra.netfactory.util.misc.URLFactory;
import com.pisx.tundra.pmgt.resource.datahandlers.UserPickerDataHandler;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectMeasures;
import ext.st.pmgt.issue.model.STProjectRisk;

public class UserPickerExpandDataHandler extends UserPickerDataHandler {
    @Override
    public Object getDataValue(String columnName, Object datum, ComponentParams params) throws PIException {
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
            imgAttribute.setTitle("选择用户");
            imgAttribute.setStyle("cursor: pointer;margin:3px;");
        });

        //componentId 传给objpicker
        NfAction action = NfActionHelper.service.getActionByActionsgroupAndActionName("st-pmgt-commonPickers", "stUserPicker");
        String url = URLFactory.getActionHREF(action);
        rightImg.backFill(url, columnName);

        DivElement content = DivElement.instance();
        content.attribute(attr -> attr.setStyle("display:initial"));
        content.children(inputElement, rightImg);
        return content;
    }

    public void getInputValue(Object datum, InputElement inputElement) {
        super.getInputValue(datum, inputElement);
        if (datum instanceof STProjectIssue) {
            STProjectIssue context = (STProjectIssue) datum;
            try {
                PIUser responsibleUser = context.getResponsibleUser();
                if (responsibleUser != null) {
                    inputElement.setValue(responsibleUser.getOid(), responsibleUser.getFullName());
                }
            } catch (PIException e) {
                e.printStackTrace();
            }
        } else if (datum instanceof STProjectRisk) {
            STProjectRisk context = (STProjectRisk) datum;
            try {
                PIUser identifiedBy = context.getIdentifiedBy();
                if (identifiedBy != null) {
                    inputElement.setValue(identifiedBy.getOid(), identifiedBy.getFullName());
                }
            } catch (PIException e) {
                e.printStackTrace();
            }
        } else if (datum instanceof STProjectMeasures) {
            STProjectMeasures context = (STProjectMeasures) datum;
            try {
                PIUser identifiedBy = context.getDutyUser();
                if (identifiedBy != null) {
                    inputElement.setValue(identifiedBy.getOid(), identifiedBy.getFullName());
                }
            } catch (PIException e) {
                e.printStackTrace();
            }
        }
    }
}
