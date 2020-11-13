package ext.st.pmgt.indicator.datahandlers;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.div.DivElement;
import com.pisx.tundra.netfactory.mvc.components.textarea.TextAreaElement;
import com.pisx.tundra.netfactory.mvc.handler.DefaultDataHandler;
import com.pisx.tundra.pmgt.resource.model.PIResource;

/**
 * @ClassName NotesDataHanlder
 * @Description:
 * @Author hma
 * @Date 2020/8/26
 * @Version V1.0
 **/
public class TextAreaDataHandler extends DefaultDataHandler {
    @Override
    public Object getDataValue(String columnName, Object datum, ComponentParams params) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        TextAreaElement textAreaElement = TextAreaElement.instance(columnName);

        textAreaElement.attribute(elementAttribute -> {
            elementAttribute.addStyle("width:300px;height:150px");
        });

        DivElement content = DivElement.instance();
        content.attribute(attr -> attr.setStyle("display:initial"));
        content.children(textAreaElement);
        return content;

    }
}