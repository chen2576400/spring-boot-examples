package ext.st.pmgt.indicator.test;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.inf.container.dao.OrgContainerDao;
import com.pisx.tundra.foundation.inf.container.model.OrgContainer;
import com.pisx.tundra.foundation.org.dao.GroupDao;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.plan.dao.PIPlanActivityDao;
import com.pisx.tundra.pmgt.plan.dao.PIPlanDao;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.dao.PIProjectDao;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.indicator.dao.*;
import ext.st.pmgt.indicator.model.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan
@EnableCaching
@EnableTransactionManagement
@EnableJpaAuditing
public class DataTest {

    @Autowired
    private DeviationDao deviationDao;
    @Autowired
    private DifficultyDao difficultyDao;
    @Autowired
    private DeliverableTypeDao deliverableTypeDao;
    @Autowired
    private PIProjectDao piProjectDao;
    @Autowired
    OrgContainerDao orgContainerDao;
    @Autowired
    ProjectInstanceOTIndicatorDao otIndicatorDao;
    @Autowired
    ProjectInstanceINIndicatorDao inIndicatorDao;
    @Autowired
    private ProCompetenceDao proCompetenceDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private PIPlanActivityDao piPlanActivityDao;
    @Autowired
    private PIPlanDao piPlanDao;
    @Autowired
    private RatingDao ratingDao;

    String filePath = "D:\\指标导入模板V4-20201120.xlsx";

    //偏差导入1
    @Test
    public void testSTDeviation() throws PIException {
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String, String>> list = null;
        String cellData = null;
        wb = readExcel(filePath);
        if (wb != null) {
            //用来存放表中数据
            //获取第一个sheet,偏差表
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            for (int i = 1; i < rownum; i++) {
                row = sheet.getRow(i);
                STDeviation deviation = STDeviation.newSTDeviation();
                if (row != null) {
                    for (int j = 0; j < colnum; j++) {
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        if (j == 0) {
                            deviation.setCode(cellData);
                        } else if (j == 1) {
                            double v = Double.parseDouble(cellData);
                            if (v < 0 || v > 1) {
                                break;
                            }
                            deviation.setValue(Double.parseDouble(cellData));
                        } else if (j == 2) {
                            deviation.setDescription(cellData);
                        }
                    }
                    deviationDao.save(deviation);
                }
            }
        }
    }

    //困难度导入2
    @Test
    public void testSTDifficulty() throws PIException {
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String, String>> list = null;
        String cellData = null;
        wb = readExcel(filePath);
        if (wb != null) {
            //获取第一个sheet,困难度表
            sheet = wb.getSheet("困难度STDifficulty");
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            STDifficulty difficulty = null;
            for (int i = 1; i < rownum; i++) {
                row = sheet.getRow(i);
                difficulty = STDifficulty.newSTDifficulty();
                Boolean m=true;
                if (row != null) {
                    for (int j = 0; j < colnum; j++) {
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        if (StringUtils.isEmpty(cellData)){
                            m=false;
                        }
                        if (j == 0) {  //指标编码
                            difficulty.setCode(cellData);
                        } else if (j == 1) {    //困难度
                            double v = Double.parseDouble(cellData);
                            if (v < 0 || v > 1) {
                                continue;
                            }
                            difficulty.setValue(Double.parseDouble(cellData));
                        } else if (j == 2) { //困难度描述
                            difficulty.setDescription(cellData);
                        }
                    }
                    if (m){
                        difficultyDao.save(difficulty);
                    }
                }
            }
        }
    }


    //职能部门导入3
    @Test
    public void testSTProCompetence() throws PIException {
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String, String>> list = null;
        String cellData = null;
        wb = readExcel(filePath);
        if (wb != null) {
            //用来存放表中数据
            //获取第一个sheet,职能部门表
            sheet = wb.getSheet("专业能力库");
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            STProCompetence proCompetence = null;
            for (int i = 1; i < rownum; i++) {
                row = sheet.getRow(i);
                proCompetence = STProCompetence.newSTProCompetence();
                if (row != null) {
                    for (int j = 0; j < colnum; j++) {
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        if (j == 0) {   //专业能力
                            proCompetence.setName(cellData);
                        } else if (j == 1) {   //职能部门
                            try {
                                cellData = (String) getCellFormatValue(row.getCell(j));
                                List<OrgContainer> pisx = orgContainerDao.findByContainerInfoName("sinotruk");
                                if (pisx != null && pisx.size() > 0) {
                                    OrgContainer orgContainer = pisx.get(0);
                                    PIGroup piGroup = PIGroup.newPIGroup(cellData, orgContainer);
//                                如果部门已经存在，会导致save为null
                                    PIGroup group = groupDao.save(piGroup);
                                    proCompetence.setDepartmentReference(ObjectReference.newObjectReference(group));
                                }
                            } catch (PIException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    proCompetenceDao.save(proCompetence);

                } else {
                    break;
                }

            }
        }
    }


    //OT測試4
    @Test
    public void testOT() throws PIException {
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        String cellData = null;
        wb = readExcel(filePath);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (wb != null) {
            sheet = wb.getSheet("OT指标ProjectInstanceOTIndicator");
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            STProjectInstanceOTIndicator stProjectInstanceOTIndicator = null;
            for (int i = 1; i < rownum; i++) {
                row = sheet.getRow(i);
                stProjectInstanceOTIndicator = STProjectInstanceOTIndicator.newSTProjectInstanceOTIndicator();
                if (row != null) {
                    Boolean m=true;
                    PIPlan plan = piPlanDao.findByNameEquals("计划1");//
                    if (plan == null) {
                        break;
                    }

                    PIProject project = piProjectDao.findByProjectName("重汽");
                    if (project == null) {
                        break;
                    }
                    for (int j = 0; j < colnum; j++) {
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        switch (j) {
                            case 0://指标编码
                                stProjectInstanceOTIndicator.setCode(cellData);
                                break;
                            case 1://指标描述
                                stProjectInstanceOTIndicator.setDescription(cellData);
                                break;
                            case 2:    //指标定义
                                stProjectInstanceOTIndicator.setDefinition(cellData);
                                break;
                            case 3://专业能力
                                stProjectInstanceOTIndicator.setDefinition(cellData);
                                STProCompetence stProCompetence = proCompetenceDao.findByNameEquals(cellData);
                                if (stProCompetence == null) {
                                    m=false;
                                    break;
                                }
                                stProjectInstanceOTIndicator.setCompetenceReference(ObjectReference.newObjectReference(stProCompetence));
                                break;
                            case 4:  //项目ID  PIProject

                                stProjectInstanceOTIndicator.setContainerReference(project.getContainerReference());
                                stProjectInstanceOTIndicator.setProjectReference(ObjectReference.newObjectReference(project));
                                break;
                            case 5: //任务ID  PIPlanActivity
                                List<PIPlanActivity> piPlanActivity = piPlanActivityDao.findByRootReferenceAndName(ObjectReference.newObjectReference(plan),cellData);
                                if (piPlanActivity == null) {
                                    m=false;
                                    break;
                                }
                                stProjectInstanceOTIndicator.setPlanActivityReference(ObjectReference.newObjectReference(piPlanActivity.get(0)));
                                break;
                            case 6:   //标准偏差值
                                stProjectInstanceOTIndicator.setStandardDeviationValue(Double.parseDouble(cellData));
                                break;
                            case 7:   //标准困难度值
                                stProjectInstanceOTIndicator.setStandardDifficultyValue(Double.parseDouble(cellData));
                                break;
                            case 8://广度
                                stProjectInstanceOTIndicator.setBreadth(Double.parseDouble(cellData));
                                break;
                            case 9://关键度
                                stProjectInstanceOTIndicator.setCriticality(Double.parseDouble(cellData));
                                break;
                            case 10://交付物类型编码
//                                List<STDeliverableType> stDeliverableType = deliverableTypeDao.findByCodeEquals(cellData);
//                                if (stDeliverableType == null) {
//                                    break;
//                                }
//                                stProjectInstanceOTIndicator.setDeliverableTypeReference(ObjectReference.newObjectReference(stDeliverableType.get(0)));
                                stProjectInstanceOTIndicator.setDeliverableTypeCode(cellData);
                                break;
                            case 11: //偏差汇报
                                stProjectInstanceOTIndicator.setDeviationReport(cellData.equals("") ? 0L : Double.valueOf(cellData));
                                break;
                            case 12: //困难度汇报
                                stProjectInstanceOTIndicator.setDifficultyReport(cellData.equals("") ? 0L : Double.valueOf(cellData));
                                break;
                            case 13: //汇报人
                                break;
                            case 14: //指标源
                                break;
                            case 15://计划ID  PIPLAN
                                stProjectInstanceOTIndicator.setPlanReference(ObjectReference.newObjectReference(plan));
                                break;
                            case 16:  //完成状态 0.0
                                stProjectInstanceOTIndicator.setCompletionStatus(0);
                                break;
                            case 17://指标状态
                                break;
                            case 18: //汇报时间
                                stProjectInstanceOTIndicator.setReportTime(timestamp);
                                break;

                            default:
                                break;
                        }
                    }
                    if (m){
                        otIndicatorDao.save(stProjectInstanceOTIndicator);
                    }
                }
            }
        }

    }


    //in指标的导入5
    @Test
    public void testSTProjectInstanceINIndicato() throws PIException {
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String, String>> list = null;
        String cellData = null;
        wb = readExcel(filePath);
        if (wb != null) {
            sheet = wb.getSheet("IN指标STProjectInstanceINIndicato");
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            STProjectInstanceINIndicator instanceINIndicator=null;
            STRating  stRating=null;
            for (int i = 1; i < rownum; i++) {
                row = sheet.getRow(i);
                instanceINIndicator= STProjectInstanceINIndicator.newSTProjectInstanceINIndicator();
                stRating = STRating.newSTRating();
                if (row != null) {
                    Boolean m=true;
                    PIPlan plan = piPlanDao.findByNameEquals("计划1");//
                    if (plan == null) {
                        break;
                    }

                    PIProject piProject = piProjectDao.findByProjectAbbreviation("sinotruk");
                    if (piProject == null) {
                        break;
                    }
                    for (int j = 0; j < colnum; j++) {
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        switch (j) {
                            case 0://任务ID
                                List<PIPlanActivity> piPlanActivitys = piPlanActivityDao.findByRootReferenceAndName(ObjectReference.newObjectReference(plan),cellData);
                                if (CollectionUtils.isEmpty(piPlanActivitys)) {
                                    m=false;
                                    break;
                                }
                                PIPlanActivity piPlanActivity = piPlanActivitys.get(0);
                                instanceINIndicator.setPlanActivityReference(ObjectReference.newObjectReference(piPlanActivity));
                                break;
                            case 1: //计划ID(目前一个计划)
                                instanceINIndicator.setPlanReference(ObjectReference.newObjectReference(plan));
                                instanceINIndicator.setProjectPlanInstanceRef(ObjectReference.newObjectReference(plan));
                                break;
                            case 2://权重
                                instanceINIndicator.setWeights(Double.valueOf(cellData));
                                break;
                            case 3: //项目ID
                                instanceINIndicator.setProjectReference(ObjectReference.newObjectReference(piProject));
                                instanceINIndicator.setContainerReference(piProject.getContainerReference());
                                break;
                            case 4://OT编码
//                                STProjectInstanceOTIndicator byCode = otIndicatorDao.findByCode(cellData);
//                                if (byCode == null) {
//                                    break;
//                                }
                                instanceINIndicator.setOtCode(cellData);
                                break;

                            case 5://输出评定stating关联IN
                                stRating.setOtRating(Double.valueOf(cellData));

                            default:
                                break;
                        }

                    }
                }
                STProjectInstanceINIndicator inIndicator = inIndicatorDao.save(instanceINIndicator);
                stRating.setInIndicatorReference(ObjectReference.newObjectReference(inIndicator));
                ratingDao.save(stRating);
            }
        }
    }




    //交付物类型导入6
    @Test
    public void testSTDeliverableType() throws PIException {
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        String cellData = null;
        wb = readExcel(filePath);
        if (wb != null) {
            //用来存放表中数据
            //获取第一个sheet,交付物类型表
            sheet = wb.getSheet("交付物类型");
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            STDeliverableType deliverableType = null;
            for (int i = 1; i < rownum; i++) {
                row = sheet.getRow(i);
                deliverableType = STDeliverableType.newSTDeliverableType();
                if (row != null) {
                    for (int j = 0; j < colnum; j++) {
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        switch (j) {
                            case 0://名称
                                deliverableType.setName(cellData);
                                break;

                            case 1: //交付物类型编码
                                deliverableType.setCode(cellData);
                                break;
//                            case 2: //指标编码   STProjectIndicator

//
                            default:
                                break;
                        }

                    }
                    deliverableTypeDao.save(deliverableType);
                }

            }
        }
    }



    //读取excel
    public static Workbook readExcel(String filePath) {
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                return wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wb;
    }

    public static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            //判断cell类型
            switch (cell.getCellType()) {
                case NUMERIC: {
                    cellValue = String.valueOf(cell.getNumericCellValue()).trim();
                    break;
                }
                case FORMULA: {
                    //判断cell是否为日期格式
                    if (DateUtil.isCellDateFormatted(cell)) {
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    } else {
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

}
