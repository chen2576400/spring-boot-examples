package ext.st.pmgt.indicator;

import com.pisx.tundra.foundation.util.ApplicationContextUtil;
import ext.st.pmgt.indicator.service.STRiskService;
import ext.st.pmgt.indicator.service.STRiskServiceImpl;

import java.io.Serializable;

public class STRiskHelper implements Serializable {

    public static STRiskService service;

    static {
        service = (STRiskService) ApplicationContextUtil.getApplicationContext().getBean(STRiskServiceImpl.class);
    }
    public STRiskHelper() {
    }
}