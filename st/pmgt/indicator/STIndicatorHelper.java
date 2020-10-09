package com.st.pmgt.indicator;

import com.TundraApplication;
import com.st.pmgt.indicator.service.STIndicatorService;
import com.st.pmgt.indicator.service.STIndicatorServiceImpl;

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
        service = (STIndicatorService) TundraApplication.getApplicationContext().getBean(STIndicatorService.class);
    }

    public STIndicatorHelper() {
    }
}