package ext.st.pmgt.indicator.model;

import com.pisx.tundra.foundation.util.resource.PIListResourceBundle;
import com.pisx.tundra.foundation.util.resource.RBEntry;
import com.pisx.tundra.foundation.util.resource.RBUUID;

@RBUUID("ext.st.pmgt.indicator.model.modelRB_zh_CN")
public class modelRB extends PIListResourceBundle {

    @RBEntry("指标编码")
    public static final String OTIndicator_01= "STProjectInstanceOTIndicator.code.value";
    @RBEntry("指标描述")
    public static final String OTIndicator_02= "STProjectInstanceOTIndicator.description.value";
    @RBEntry("标准偏差")
    public static final String OTIndicator_03= "STProjectInstanceOTIndicator.standardDeviationValue.value";
    @RBEntry("标准困难度")
    public static final String OTIndicator_04= "STProjectInstanceOTIndicator.standardDifficultyValue.value";
    @RBEntry("汇报偏差")
    public static final String OTIndicator_05= "STProjectInstanceOTIndicator.deviationReport.value";
    @RBEntry("汇报困难度")
    public static final String OTIndicator_06= "STProjectInstanceOTIndicator.difficultyReport.value";
    @RBEntry("汇报时间")
    public static final String OTIndicator_07= "STProjectInstanceOTIndicator.reportTime.value";



    @RBEntry("权重")
    public static final String INIndicator_03= "STProjectInstanceINIndicator.inWeight.value";

    @RBEntry("预计完成时间")
    public static final String STExpectedFinishTime_01= "STExpectedFinishTime.expectedFinishTime.value";


    @RBEntry("名称")
    public static final String STDeliverableType_01= "STDeliverableType.name.value";
    @RBEntry("交付物编码")
    public static final String STDeliverableType_02= "STDeliverableType.code.value";

}
