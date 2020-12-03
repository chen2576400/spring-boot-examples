package ext.st.pmgt.indicator.controller;


import ext.st.pmgt.indicator.dao.ProjectInstanceINIndicatorDao;
import ext.st.pmgt.indicator.dao.ProjectInstanceOTIndicatorDao;
import ext.st.pmgt.indicator.dao.RatingDao;
import ext.st.pmgt.indicator.test.ExportExcel;
import ext.st.pmgt.indicator.test.LimitTime;
import ext.st.pmgt.indicator.test.Result;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

@RestController
@RequestMapping("mv")
@Log
public class ExportExcelController {
    private static final Logger logger = LoggerFactory.getLogger(ExportExcelController.class);

    @Autowired
    private ProjectInstanceOTIndicatorDao otIndicatorDao;
    @Autowired
    private ProjectInstanceINIndicatorDao inIndicatorDao;
    @Autowired
    private RatingDao ratingDao;

    @Autowired
    private AutowireCapableBeanFactory factory;


    public static ExecutorService executor() {
        return new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 20, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(500), new ThreadPoolExecutor.CallerRunsPolicy());
    }


    @RequestMapping(value = "sb", produces = "application/json; charset=utf-8")
    @LimitTime(time = 1, timeout = 1000 * 10)
    public Result downloadTemplate() {
        ExportExcel exportExcel = new ExportExcel();
        factory.autowireBean(exportExcel);
        return exportExcel.downloadTemplate();
    }



}
