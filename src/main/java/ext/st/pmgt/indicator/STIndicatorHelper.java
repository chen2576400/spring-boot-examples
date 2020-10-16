package ext.st.pmgt.indicator;

import com.TundraApplication;
import com.pisx.tundra.foundation.util.ApplicationContextUtil;
import ext.st.pmgt.indicator.service.STIndicatorService;

import java.io.Serializable;

/**
 * @ClassName STHelper
 * @Description:
 * @Author hma
 * @Date 2020/9/29
 * @Version V1.0
 **/
public class STIndicatorHelper implements Serializable {
    public static STIndicatorService service;

    static {
        service = (STIndicatorService) ApplicationContextUtil.getApplicationContext().getBean(STIndicatorService.class);
    }

    public STIndicatorHelper() {
    }
}