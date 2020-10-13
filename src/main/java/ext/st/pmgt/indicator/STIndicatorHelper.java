package ext.st.pmgt.indicator;

import com.TundraApplication;
import ext.st.pmgt.indicator.service.STIndicatorService;
import ext.st.pmgt.indicator.service.STIndicatorServiceImpl;

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
        service = (STIndicatorService) TundraApplication.getApplicationContext().getBean(STIndicatorServiceImpl.class);
    }

    public STIndicatorHelper() {
    }
}