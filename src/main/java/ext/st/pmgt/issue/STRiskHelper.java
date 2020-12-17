package ext.st.pmgt.issue;

import com.pisx.tundra.foundation.util.ApplicationContextUtil;
import ext.st.pmgt.issue.service.STRiskService;
import ext.st.pmgt.issue.service.STRiskServiceImpl;

import java.io.Serializable;

public class STRiskHelper implements Serializable {

    public static STRiskService service;

    static {
        service = (STRiskService) ApplicationContextUtil.getApplicationContext().getBean(STRiskServiceImpl.class);
    }
    public STRiskHelper() {
    }
}