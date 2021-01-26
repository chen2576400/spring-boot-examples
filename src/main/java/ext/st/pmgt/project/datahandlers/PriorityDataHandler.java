package ext.st.pmgt.project.datahandlers;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.select.SelectElement;
import com.pisx.tundra.netfactory.mvc.handler.DefaultDataHandler;
import com.pisx.tundra.netfactory.util.misc.Option;
import com.pisx.tundra.pmgt.project.model.PIProject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PriorityDataHandler extends DefaultDataHandler {
    @Override
    public Object getDataValue(String columnName, Object datum, ComponentParams params) throws PIException {
        List<Option> options = new ArrayList<>();
        for (int i = 1; i <=5 ; i++) {
            Option option=new Option(i,i+"");
            options.add(option);
        }
        SelectElement selectElement0= SelectElement.instance(columnName);
        selectElement0.setOptions(options);
        if(datum==null&&options.size()>0){
            selectElement0.setDefaultItem(options.get(0));
        }else {
            selectElement0.setDefaultItem(getDefaultOption(columnName,options,datum));
        }
        selectElement0.attribute(elementAttribute -> elementAttribute.addStyle("width:150px;"));
        return selectElement0;
    }

    private Option  getDefaultOption(String columnName,List<Option> options, Object datum){
            PIProject PIProject = (PIProject) datum;
        if (columnName.equals("priorityNum")){
            Integer priorityNum = PIProject.getPriorityNum();
            return options.stream().filter(filter-> filter.getValue().equals(priorityNum)).collect(Collectors.toList()).get(0);
        }else if (columnName.equals("strgyPriorityNum")){
            Integer strgyPriorityNum = PIProject.getStrgyPriorityNum();
            return options.stream().filter(filter-> filter.getValue().equals(strgyPriorityNum)).collect(Collectors.toList()).get(0);
        }
           return null;
    }
}
