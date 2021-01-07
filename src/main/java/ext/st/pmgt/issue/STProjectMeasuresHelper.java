package ext.st.pmgt.issue;

import com.pisx.tundra.foundation.util.ApplicationContextUtil;
import ext.st.pmgt.issue.service.STProjectMeasuresService;

public class STProjectMeasuresHelper {
    public  static STProjectMeasuresService measuresService;
    static {
        measuresService = (STProjectMeasuresService) ApplicationContextUtil.getApplicationContext().getBean(STProjectMeasuresService.class);
    }
}
