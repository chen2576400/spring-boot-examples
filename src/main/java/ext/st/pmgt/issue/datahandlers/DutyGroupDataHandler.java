package ext.st.pmgt.issue.datahandlers;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.div.DivElement;
import com.pisx.tundra.netfactory.mvc.components.img.ImgElement;
import com.pisx.tundra.netfactory.mvc.components.input.InputElement;
import com.pisx.tundra.netfactory.mvc.handler.DefaultDataHandler;
import com.pisx.tundra.netfactory.util.misc.URLFactory;
import ext.st.pmgt.issue.model.STProjectIssue;

public class DutyGroupDataHandler extends DefaultDataHandler {
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
            imgAttribute.setTitle("选择部门");
            imgAttribute.setStyle("cursor: pointer;margin:3px;");
        });

        //componentId 传给objpicker
        String url = URLFactory.getActionHref("pi-pmgt-project-copy", "departmentListPicker", params.getNfCommandBean().getSourceObject());

        rightImg.backFill(url, columnName);

        DivElement content = DivElement.instance();
        content.attribute(attr -> attr.setStyle("display:initial"));
        content.children(inputElement, rightImg);
        return content;
    }

    private void getInputValue(Object datum, InputElement inputElement) throws PIException {
        if (datum instanceof STProjectIssue) {
            STProjectIssue context = (STProjectIssue) datum;
            PIGroup piGroup = null;
            if (context.getDutyGroupReference() != null) {
                piGroup = (PIGroup) context.getDutyGroupReference().getObject();
                if (piGroup != null) {
                    inputElement.setValue(piGroup.getOid(), piGroup.getName());
                }
            }
        }
    }
}

