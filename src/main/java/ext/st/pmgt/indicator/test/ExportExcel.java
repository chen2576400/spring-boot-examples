package ext.st.pmgt.indicator.test;

import com.google.common.util.concurrent.*;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.httpgw.WebServerConfig;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.plan.dao.PIPlanDao;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.indicator.controller.ExportExcelController;
import ext.st.pmgt.indicator.dao.ProjectInstanceINIndicatorDao;
import ext.st.pmgt.indicator.dao.ProjectInstanceOTIndicatorDao;
import ext.st.pmgt.indicator.dao.RatingDao;
import ext.st.pmgt.indicator.model.STProCompetence;
import ext.st.pmgt.indicator.model.STProjectInstanceINIndicator;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import ext.st.pmgt.indicator.model.STRating;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;


public class ExportExcel {
    private static final Logger logger = LoggerFactory.getLogger(ExportExcelController.class);

    @Autowired
    private ProjectInstanceOTIndicatorDao otIndicatorDao;
    @Autowired
    private ProjectInstanceINIndicatorDao inIndicatorDao;
    @Autowired
    private RatingDao ratingDao;
    @Autowired
    WebServerConfig webServerConfig;
    @Autowired
    private PIPlanDao piPlanDao;

    public static ExecutorService executor() {
        return new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 20, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(500), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 基础字节数组输出
     */
    public static void outStream(InputStream is, OutputStream os) {
        try {
            byte[] buffer = new byte[10240];
            int length = -1;
            while ((length = is.read(buffer)) != -1) {
                os.write(buffer, 0, length);
                os.flush();
            }
        } catch (Exception e) {
            System.out.println("执行 outStream 发生了异常：" + e.getMessage());
        } finally {
            try {
                os.close();
            } catch (IOException e) {
            }
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }
//    @Async("taskExecutor")
//    public Future<Workbook> downloadTemplate(HttpServletResponse httpServletResponse) throws IOException {
//        List<STProjectInstanceOTIndicator> ots = otIndicatorDao.findAll();
//        List<STProjectInstanceINIndicator> ins = inIndicatorDao.findAll();
//        Workbook workbook = tasjJob(ots, ins);
//
//        return  new AsyncResult<Workbook>(workbook);
//
//
//    }


    public Result downloadTemplate(HttpServletResponse httpServletResponse, HttpServletRequest request, String name) throws PIException {
        ExecutorService executorService = ExportExcel.executor();
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
        List<STProjectInstanceOTIndicator> ots;
        List<STProjectInstanceINIndicator> ins;
        if (!StringUtils.isEmpty(name)) {
            PIPlan plan = null;
            plan = piPlanDao.findByNameEquals(name);
            if (plan == null) {
                return Result.error("该计划不存在");
            }

            ots = new ArrayList<>(otIndicatorDao.findByPlanReference(ObjectReference.newObjectReference(plan)));
            ins = new ArrayList<>(inIndicatorDao.findByPlanReference(ObjectReference.newObjectReference(plan)));
        } else {
            ots = otIndicatorDao.findAll();
            ins = inIndicatorDao.findAll();
        }
        final ListenableFuture<Workbook> submit = listeningExecutorService.submit(new Callable<Workbook>() {
            @Override
            public Workbook call() throws Exception {
                Workbook wb = tasjJob(ots, ins);
                return wb;
            }
        });


        Futures.addCallback(submit, new FutureCallback<Workbook>() {
            @SneakyThrows
            @Override
            public void onSuccess(Workbook workbook) {
                Workbook wb = submit.get();
                FileOutputStream fos = new FileOutputStream("D:\\" + "最新指标数据模板.xlsx");
                wb.write(fos);
                fos.close();
                listeningExecutorService.shutdown();
                executorService.shutdown();
            }

            @SneakyThrows
            @Override
            public void onFailure(Throwable throwable) {
                logger.error("错误信息为" + throwable);
                listeningExecutorService.shutdown();
                executorService.shutdown();

            }
        }, listeningExecutorService);

        StringBuffer buffer = new StringBuffer();
        buffer.append("正在上传文件稍后请到");
        buffer.append(webServerConfig.getHostAddress() + ":" + webServerConfig.getServerPort());
        buffer.append("/indicator/export/downloadExcel 下载");
        return Result.ok(buffer.toString());

    }


    public Workbook tasjJob(List<STProjectInstanceOTIndicator> ots, List<STProjectInstanceINIndicator> ins) {
        Workbook wb = null;
        try {
            wb = new XSSFWorkbook();

            Sheet sheet = wb.createSheet("OT指标ProjectInstanceOTIndicator");//创建一张表
            TitleByOt(sheet, ots);//OT title

            Sheet sheet1 = wb.createSheet("IN指标STProjectInstanceINIndicato");//创建一张表
            TitleByIN(sheet1, ins);//IN title
//            TitleByIN1(sheet1, ins);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wb;

    }


    public void TitleByOt(Sheet sheet, List<STProjectInstanceOTIndicator> ots) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Row titleRow = sheet.createRow(0);//创建第一行，起始为0
        titleRow.createCell(0).setCellValue("指标编码");//第一列
        titleRow.createCell(1).setCellValue("指标描述");
        titleRow.createCell(2).setCellValue("指标定义");
        titleRow.createCell(3).setCellValue("专业能力");
        titleRow.createCell(4).setCellValue("项目id");
        titleRow.createCell(5).setCellValue("任务id(WBS)");
        titleRow.createCell(6).setCellValue("标准偏差值");
        titleRow.createCell(7).setCellValue("标准困难度值");
        titleRow.createCell(8).setCellValue("广度");
        titleRow.createCell(9).setCellValue("关键度");
        titleRow.createCell(10).setCellValue("交付物类型编码");
        titleRow.createCell(11).setCellValue("偏差汇报");
        titleRow.createCell(12).setCellValue("困难度汇报");
        titleRow.createCell(13).setCellValue("汇报人");
        titleRow.createCell(14).setCellValue("指标源");
        titleRow.createCell(15).setCellValue("计划id");
        titleRow.createCell(16).setCellValue("完成状态(0,1,2)");
        titleRow.createCell(17).setCellValue("指标状态(停用，启用）");
        titleRow.createCell(18).setCellValue("汇报时间");
        int cell = 1;
        try {
            for (STProjectInstanceOTIndicator ot : ots) {
                STProCompetence stProCompetence = null;
                PIProject piProject = ot.getProject();
                ;
                PIPlan piPlan = ot.getPlan();
                PIPlanActivity planActivity = null;
                Object planActivityObject = ot.getPlanActivityReference() == null ? null : ot.getPlanActivityReference().getObject();
                Object competenObject = ot.getCompetenceReference() == null ? null : ot.getCompetenceReference().getObject();
                if (planActivityObject instanceof PIPlanActivity) planActivity = (PIPlanActivity) planActivityObject;
                if (competenObject instanceof STProCompetence) stProCompetence = (STProCompetence) competenObject;
                if (piPlan == null || piProject == null || planActivity == null || stProCompetence == null) continue;

                Row row = sheet.createRow(cell);//从第二行开始保存数据
                row.createCell(0).setCellValue(ot.getCode());//指标编码
                row.createCell(1).setCellValue(ot.getDescription());//指标描述
                row.createCell(2).setCellValue(ot.getDefinition());//指标定义
                row.createCell(3).setCellValue(stProCompetence.getName());//专业能力
                row.createCell(4).setCellValue(piProject.getProjectName());//项目id
                row.createCell(5).setCellValue(planActivity.getName());//任务id(WBS)
                row.createCell(6).setCellValue(ot.getStandardDeviationValue());//标准偏差值
                row.createCell(7).setCellValue(ot.getStandardDifficultyValue());//标准困难度值
                row.createCell(8).setCellValue(ot.getBreadth());//广度
                row.createCell(9).setCellValue(ot.getCriticality());//关键度
                row.createCell(10).setCellValue(ot.getDeliverableTypeCode());//交付物类型编码
                row.createCell(11).setCellValue(ot.getDeviationReport());//偏差汇报
                row.createCell(12).setCellValue(ot.getDifficultyReport());//困难度汇报
//                row.createCell(13).setCellValue(ot.getCode());//汇报人
//                row.createCell(14).setCellValue(ot.getCode());//指标源
                row.createCell(15).setCellValue(piPlan.getName());//计划id
                row.createCell(16).setCellValue(ot.getCompletionStatus());//完成状态(0,1,2)
//                row.createCell(17).setCellValue(ot.getCode());//指标状态(停用，启用）
                String time = format.format(ot.getReportTime());
                row.createCell(18).setCellValue(time);//汇报时间

                cell++;
            }
        } catch (Exception e) {
            StackTraceElement stackTraceElement = e.getStackTrace()[0];
            logger.error(String.format("OT创建表格出错,错误信息为%s,错误行数为%s", e, stackTraceElement.getLineNumber()));
        }
    }


    public void TitleByIN(Sheet sheet, List<STProjectInstanceINIndicator> ins) {//同一输入最新评定
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Row titleRow = sheet.createRow(0);//创建第一行，起始为0
        titleRow.createCell(0).setCellValue("任务id(WBS)");//第一列
        titleRow.createCell(1).setCellValue("计划id");
        titleRow.createCell(2).setCellValue("权重");
        titleRow.createCell(3).setCellValue("项目id(projectReference)");
        titleRow.createCell(4).setCellValue("指标编码（otCode）");
        titleRow.createCell(5).setCellValue("输出评定");
        titleRow.createCell(6).setCellValue("评定描述");
        titleRow.createCell(7).setCellValue("评定时间");
        int cell = 1;
        try {
            for (STProjectInstanceINIndicator in : ins) {
                Row row = sheet.createRow(cell);//从第二行开始保存数据
                PIProject piProject = null;
                PIPlan piPlan = null;
                PIPlanActivity planActivity = null;
                STRating stRating = null;
                Object object = in.getPlanReference() == null ? null : in.getPlanReference().getObject();
                piProject = in.getProject();
                Object o = in.getPlanActivityReference() == null ? null : in.getPlanActivityReference().getObject();
                List<STRating> stRatings = (List<STRating>) ratingDao.findByInIndicatorReference(ObjectReference.newObjectReference(in));
                if (object instanceof PIPlan) piPlan = (PIPlan) object;
                if (!CollectionUtils.isEmpty(stRatings)) stRating = stRatings.get(stRatings.size() - 1);
                if (o instanceof PIPlanActivity) planActivity = (PIPlanActivity) o;
                if (piPlan == null || piProject == null || planActivity == null || stRating == null) continue;
                row.createCell(0).setCellValue(planActivity.getName());//任务id(WBS)
                row.createCell(1).setCellValue(piPlan.getName());//计划id
                row.createCell(2).setCellValue(in.getWeights());//权重
                row.createCell(3).setCellValue(piProject.getProjectName());//项目ID
                row.createCell(4).setCellValue(in.getOtCode());//指标编码（otCode）
                row.createCell(5).setCellValue(stRating.getOtRating());//输出评定
                row.createCell(6).setCellValue(stRating.getDescription());//评定描述
                String time = format.format(stRating.getReportTime());
                row.createCell(7).setCellValue(time);//评定时间
                cell++;

            }
        } catch (PIException e) {
            StackTraceElement stackTraceElement = e.getStackTrace()[0];
            logger.error(String.format("IN创建表格出错,错误信息为%s,错误行数为%s", e, stackTraceElement.getLineNumber()));
        }

    }


    public void TitleByIN1(Sheet sheet, List<STProjectInstanceINIndicator> ins) {  //同一输入多个评定
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Row titleRow = sheet.createRow(0);//创建第一行，起始为0
        titleRow.createCell(0).setCellValue("任务id(WBS)");//第一列
        titleRow.createCell(1).setCellValue("计划id");
        titleRow.createCell(2).setCellValue("权重");
        titleRow.createCell(3).setCellValue("项目id(projectReference)");
        titleRow.createCell(4).setCellValue("指标编码（otCode）");
        titleRow.createCell(5).setCellValue("输出评定");
        titleRow.createCell(6).setCellValue("评定描述");
        titleRow.createCell(7).setCellValue("评定时间");
        int cell = 1;
        try {
            for (STProjectInstanceINIndicator in : ins) {
                PIProject piProject = null;
                PIPlan piPlan = null;
                PIPlanActivity planActivity = null;
                Object object = in.getPlanReference() == null ? null : in.getPlanReference().getObject();
                piProject = in.getProject();
                Object o = in.getPlanActivityReference() == null ? null : in.getPlanActivityReference().getObject();
                List<STRating> stRatings = (List<STRating>) ratingDao.findByInIndicatorReference(ObjectReference.newObjectReference(in));
                if (object instanceof PIPlan) piPlan = (PIPlan) object;
                if (o instanceof PIPlanActivity) planActivity = (PIPlanActivity) o;
                if (piPlan == null || piProject == null || planActivity == null || CollectionUtils.isEmpty(stRatings)) continue;
                for (STRating stRating : stRatings) {
                    Row row = sheet.createRow(cell);//从第二行开始保存数据
                    row.createCell(0).setCellValue(planActivity.getName());//任务id(WBS)
                    row.createCell(1).setCellValue(piPlan.getName());//计划id
                    row.createCell(2).setCellValue(in.getWeights());//权重
                    row.createCell(3).setCellValue(piProject.getProjectName());//项目ID
                    row.createCell(4).setCellValue(in.getOtCode());//指标编码（otCode）
                    row.createCell(5).setCellValue(stRating.getOtRating());//输出评定
                    row.createCell(6).setCellValue(stRating.getDescription());//评定描述
                    String time = format.format(stRating.getReportTime());
                    row.createCell(7).setCellValue(time);//评定时间
                    cell++;
                }


            }
        } catch (PIException e) {
            StackTraceElement stackTraceElement = e.getStackTrace()[0];
            logger.error(String.format("IN创建表格出错,错误信息为%s,错误行数为%s", e, stackTraceElement.getLineNumber()));
        }

    }

}



