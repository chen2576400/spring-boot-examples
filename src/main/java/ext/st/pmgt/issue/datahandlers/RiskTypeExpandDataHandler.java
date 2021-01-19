package ext.st.pmgt.issue.datahandlers;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.div.DivElement;
import com.pisx.tundra.netfactory.mvc.components.img.ImgElement;
import com.pisx.tundra.netfactory.mvc.components.input.InputElement;
import com.pisx.tundra.netfactory.util.misc.URLFactory;
import com.pisx.tundra.pmgt.risk.datahandlers.RiskTypeDataHandler;
import com.pisx.tundra.pmgt.risk.model.PIPmgtRiskType;
import ext.st.pmgt.issue.model.STProjectRisk;

/**
 * @author: Mr.Chen
 * @create: 2021-01-19 10:34
 **/
public class RiskTypeExpandDataHandler extends RiskTypeDataHandler {

    @Override
    public Object getDataValue(String columnName, Object datum, ComponentParams params) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        InputElement inputElement = InputElement.instance(columnName);
        if (datum != null) {
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

        //componentId 传给objpicker
        String url = URLFactory.getActionHref("pi-pmgt-enterprise", "riskTypePicker", params.getNfCommandBean().getSourceObject());
        rightImg.backFill(url, columnName);

        DivElement content = DivElement.instance();
        content.attribute(attr -> attr.setStyle("display:initial"));
        content.children(inputElement, rightImg);
        return content;

    }

    @Override
    public void getInputValue(Object datum, InputElement inputElement) {
        super.getInputValue(datum, inputElement);
        if (datum instanceof STProjectRisk) {
            STProjectRisk context = (STProjectRisk) datum;
            try {
                PIPmgtRiskType riskType = context.getRiskType();
                if (riskType != null) {
                    inputElement.setValue(riskType.getOid(), riskType.getRiskType());
                }
            } catch (PIException e) {
                e.printStackTrace();
            }
        }
    }
}
