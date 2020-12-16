package ext.st.pmgt.indicator.controller;

import com.pisx.tundra.foundation.util.PIException;
import ext.st.pmgt.indicator.test.ExportExcel;
import ext.st.pmgt.indicator.test.LimitTime;
import ext.st.pmgt.indicator.test.Result;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping("indicator/export")
@Log
public class ExportExcelController {

    @Autowired
    private AutowireCapableBeanFactory factory;


    @RequestMapping(value = "generateExcel", produces = "application/json; charset=utf-8")
    @LimitTime(time = 1, timeout = 1000 * 10)
    public Result downloadTemplate(HttpServletResponse httpServletResponse,HttpServletRequest request,String projectName) throws IOException, PIException {
        ExportExcel exportExcel = new ExportExcel();
        factory.autowireBean(exportExcel);
        return exportExcel.downloadTemplate(httpServletResponse,request, projectName);
//        Future<Workbook> resultFuture = exportExcel.downloadTemplate(httpServletResponse);


//        return Result.ok("生产文件路径为D:最新指标数据模板.xlsx文件");
    }

    @RequestMapping(value = "downloadExcel", produces = "application/json; charset=utf-8")
    public Result downloadExcel(HttpServletResponse httpServletResponse, HttpServletRequest request) throws IOException {
        File file = new File("C:\\最新指标数据模板.xlsx");
        if(file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            httpServletResponse.setHeader("content-Type", "application/vnd.ms-excel");
            httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("最新指标数据模板.xlsx", "utf-8"));

            // 输出
            ExportExcel.outStream(fileInputStream, httpServletResponse.getOutputStream());
            return Result.ok("已下载成功");
        }
        return Result.error("该文件不存在");
    }




}

