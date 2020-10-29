package ext.st.pmgt.indicator.datahandlers;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.actionmodel.NfAction;
import com.pisx.tundra.netfactory.mvc.components.div.DivElement;
import com.pisx.tundra.netfactory.mvc.components.img.ImgElement;
import com.pisx.tundra.netfactory.mvc.components.input.InputElement;
import com.pisx.tundra.netfactory.mvc.handler.DefaultDataHandler;
import com.pisx.tundra.netfactory.util.action.NfActionHelper;
import com.pisx.tundra.netfactory.util.misc.URLFactory;

public class DeliverablePickerHandler extends DefaultDataHandler {

    @Override
    public Object getDataValue(String columnName, Object datum, ComponentParams params) throws PIException {
        InputElement inputElement = InputElement.instance(columnName);
        inputElement.attribute(elementAttribute -> {
            elementAttribute.addStyle("width:150px;");
        });

        ImgElement rightImg = ImgElement.instance();
        rightImg.attribute(imgAttribute -> {
            imgAttribute.setSrc("/img/foundation/find.gif");
            imgAttribute.setTitle("选择");
            imgAttribute.setStyle("cursor: pointer;margin:3px;");
        });

        //componentId 传给objpicker
        NfAction action = NfActionHelper.service.getActionByActionsgroupAndActionName("st-pmgt-act", "documentPicker");
        String url = URLFactory.getHREFContainContainerOid(action,params.getNfCommandBean().getSourceObject());
        rightImg.backFill(url, columnName);

        DivElement content = DivElement.instance();
        content.attribute(attr -> attr.setStyle("display:initial"));
        content.children(inputElement, rightImg);
        return content;
    }
}