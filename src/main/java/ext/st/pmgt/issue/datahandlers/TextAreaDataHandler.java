package ext.st.pmgt.issue.datahandlers;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.div.DivElement;
import com.pisx.tundra.netfactory.mvc.components.textarea.TextAreaElement;
import com.pisx.tundra.netfactory.mvc.handler.DefaultDataHandler;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectMeasures;
import ext.st.pmgt.issue.model.STProjectRisk;

/**
 * @author: Mr.Chen
 * @create: 2021-01-19 17:56
 **/
public class TextAreaDataHandler extends DefaultDataHandler {
    @Override
    public Object getDataValue(String columnName, Object datum, ComponentParams params) throws PIException {
        TextAreaElement textAreaElement = TextAreaElement.instance(columnName);

        textAreaElement.attribute(elementAttribute -> {
            elementAttribute.addStyle("width:300px;height:150px");
        });
        if (datum != null) {
            setDefaultValue(textAreaElement, datum);
        }
        DivElement content = DivElement.instance();
        content.attribute(attr -> attr.setStyle("display:initial"));
        content.children(textAreaElement);
        return content;

    }


    public void setDefaultValue(TextAreaElement textAreaElement, Object datum) {
        if (datum instanceof STProjectIssue) {
            STProjectIssue issue = (STProjectIssue) datum;
            String treatmentPlan = issue.getTreatmentPlan();
            if (treatmentPlan != null) {
                textAreaElement.setValue(treatmentPlan);
            }
        } else if (datum instanceof STProjectMeasures) {
            STProjectMeasures measures = (STProjectMeasures) datum;
            String precaution = measures.getPrecaution();
            if (precaution != null) {
                textAreaElement.setValue(precaution);
            }
        }else if (datum instanceof STProjectRisk){
            STProjectRisk risk = (STProjectRisk) datum;
            String riskDescription = risk.getRiskDescription();
            if (riskDescription != null) {
                textAreaElement.setValue(riskDescription);
            }
        }
    }
}
