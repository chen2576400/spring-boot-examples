package ext.st.pmgt.indicator.model;

import com.pisx.tundra.foundation.util.resource.PIListResourceBundle;
import com.pisx.tundra.foundation.util.resource.RBEntry;
import com.pisx.tundra.foundation.util.resource.RBUUID;

@RBUUID("com.st.pmgt.indicator.model.modelRB")
public class modelRB extends PIListResourceBundle {

    @RBEntry("指标编码")
    public static final String OTIndicator_01= "STProjectInstanceOTIndicator.code.value";
    @RBEntry("指标描述")
    public static final String OTIndicator_02= "STProjectInstanceOTIndicator.decription.value";
    @RBEntry("标准偏差")
    public static final String OTIndicator_03= "STProjectInstanceOTIndicator.standardDeviationValue.value";
    @RBEntry("标准困难度")
    public static final String OTIndicator_04= "STProjectInstanceOTIndicator.difficultyValue.value";

    @RBEntry("指标编码")
    public static final String INIndicator_01= "STProjectInstanceINIndicator.code.value";
    @RBEntry("指标描述")
    public static final String INIndicator_02= "STProjectInstanceINIndicator.decription.value";
    @RBEntry("权重")
    public static final String INIndicator_03= "STProjectInstanceINIndicator.inWeight.value";

}
