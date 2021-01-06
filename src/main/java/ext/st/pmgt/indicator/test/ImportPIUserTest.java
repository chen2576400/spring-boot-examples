package ext.st.pmgt.indicator.test;

import com.pisx.tundra.foundation.auth.PasswordService;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.inf.container.dao.OrgContainerDao;
import com.pisx.tundra.foundation.inf.container.model.OrgContainer;
import com.pisx.tundra.foundation.org.OrgHelper;
import com.pisx.tundra.foundation.org.model.MembershipLink;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.org.model.PIOrganization;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIPropertyVetoException;
import com.pisx.tundra.pmgt.calendar.CalendarHelper;
import com.pisx.tundra.pmgt.calendar.model.PICalendar;
import com.pisx.tundra.pmgt.resource.model.PIResource;
import com.pisx.tundra.pmgt.resource.model.PIResourceType;
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

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan
@EnableCaching
@EnableTransactionManagement
@EnableJpaAuditing
public class ImportPIUserTest {

    @Autowired
    PasswordService passwordService;
    @Autowired
    OrgContainerDao orgContainerDao;

    String filePath = "D:\\用户资源.xlsx";

    //导入piuser
    @Test
    public void testPiuser() throws PIException, PIPropertyVetoException {
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String, String>> list = null;
        String cellData = null;
        wb = readExcel(filePath);
        if (wb != null) {
            sheet = wb.getSheet("用户表");
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            PIUser piUser = null;
            PIOrganization piOrganization=null;
            for (int i = 1; i < rownum; i++) {
                row = sheet.getRow(i);
                piUser=PIUser.newPIUser();
                for (int j = 0; j < colnum; j++) {
                    cellData = (String) getCellFormatValue(row.getCell(j));
                    switch (j) {
                        case 0://名称
                            PIUser userByUserName = OrgHelper.service.getUserByUserName(cellData);
                            if(userByUserName!=null){
                                piUser=userByUserName;
                            }else {
                                piUser.setName(cellData);
                            }
                            break;
                        case 1: //全名
                            piUser.setFullName(cellData);
                            break;
                        case 2: //密码
                            piUser.randomSalt();
                            piUser.setPassword(passwordService.encryptPassword(piUser.getName(), cellData, piUser.getSalt()));
                            break;
                        case 3: //昵称
                            piUser.setNickName(cellData);
                            break;
                        case 4:  //邮箱
                            piUser.setEmail(cellData);
                            break;
                        case 5: //电话号码

                            piUser.setTelephone(cellData);
                            break;
                        case 6: //性别 1男   2 女
                            if(cellData!=null) {
                                piUser.setSex((int) Double.parseDouble(cellData));
                            }
                            break;
                        case 7:  //组织容器
                            List<PIOrganization> organizationByName = OrgHelper.service.getOrganizationByName(cellData);
                            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(organizationByName)){
                                piOrganization = organizationByName.get(0);
                                piUser.setOrganization(piOrganization);
                            }
                            break;
                        case 8: //部门
                            List<OrgContainer> sinotruk = orgContainerDao.findByContainerInfoName("sinotruk");
                            PIGroup group = OrgHelper.service.getGroup(cellData, sinotruk.get(0));
                            if (isExist(piUser)){
                               System.out.println(piUser.getName()+"电话号码或者邮箱重复，请重试");
                               break;
                            }
                            if(group!=null) {
                                PersistenceHelper.service.save(piUser);
                                MembershipLink membershipLink = OrgHelper.service.addMember(group, piUser);
                            }else {
                                System.out.println(cellData+"部门不存在");
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    //导入资源
    @Test
    public void testPiresource() throws PIException {
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String, String>> list = null;
        String cellData = null;
        wb = readExcel(filePath);
        if (wb != null) {
            sheet = wb.getSheet("资源");
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            PIResource piResource = null;
            for (int i = 1; i < rownum; i++) {
                row = sheet.getRow(i);
                piResource = PIResource.newPIResource();
                for (int j = 0; j < colnum; j++) {
                    cellData = (String) getCellFormatValue(row.getCell(j));
                    switch (j) {
                        case 0://资源序号
                            piResource.setResourceSeqNum(Integer.valueOf(cellData));
                            break;
                        case 1: //启用状态（1.启用，0不启用）
                            if("1".equals(cellData)){
                            piResource.setActiveFlag(true);
                            }else {
                                piResource.setActiveFlag(false);
                            }
                            break;
                        case 2://时间表（1.启用，0不启用）
                            if("1".equals(cellData)){
                                piResource.setTimesheetFlag(true);
                            }else {
                                piResource.setTimesheetFlag(false);
                            }
                            break;
                        case 3://资源类型（labor，equipment，material）
                            if("labor".equals(cellData))
                            piResource.setResourceType(PIResourceType.LABOR);
                            if("equipment".equals(cellData))
                                piResource.setResourceType(PIResourceType.EQUIPMENT);
                            if("material".equals(cellData))
                                piResource.setResourceType(PIResourceType.MATERIAL);
                            break;
                        case 4://是否自动计算实际数量（1.是，0.否）
                            if("1".equals(cellData)){
                                piResource.setAutoComputeActualFlag(true);
                            }else {
                                piResource.setAutoComputeActualFlag(false);
                            }
                            break;
                        case 5://是否容许加班（1.是，0.否）
                            if("1".equals(cellData)){
                                piResource.setOvertimeFlag(true);
                            }else {
                                piResource.setOvertimeFlag(false);
                            }
                            break;
                        case 6://是否从数量计算费用（1.是，0.否）
                            if("1".equals(cellData)){
                                piResource.setDefaultCostQtyLinkFlag(true);
                            }else {
                                piResource.setDefaultCostQtyLinkFlag(false);
                            }
                            break;
                        case 7://资源代码
                            piResource.setResourceShortName(cellData);
                            break;
                        case 8://名称
                            piResource.setName(cellData);
                            break;
                        case 9://资源职称
                            piResource.setResourceTitleName(cellData);
                            break;
                        case 10://日历（5 DayWorkWeek）
                            PICalendar byCalendarName = CalendarHelper.service.findByCalendarName(cellData);
                            if(byCalendarName==null){
                                PICalendar piCalendar = CalendarHelper.service.findByCalendarName("5 DayWorkWeek");
                                piResource.setCalendar(piCalendar);
                            }else {
                                piResource.setCalendar(byCalendarName);
                            }
                            break;
                        case 11://用户名称
                            PIUser userByUserName = OrgHelper.service.getUserByUserName(cellData);
                            piResource.setUser(userByUserName);
                            List<OrgContainer> sinotruk = orgContainerDao.findByContainerInfoName("sinotruk");
                            piResource.setContainer(sinotruk.get(0));
                            PersistenceHelper.service.save(piResource);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

//判断邮箱电话号码是否重复
    boolean isExist(PIUser user){
        EntityManager em = PersistenceHelper.service.getEntityManager();
        boolean flag = false;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = cb.createQuery();
            Root root = criteriaQuery.from(PIUser.class);
            criteriaQuery.select(root);
            Path key1 = root.get("telephone");
            Path key2 = root.get("email");
            criteriaQuery.where(cb.or(cb.equal(key1,user.getTelephone()),cb.equal(key2,user.getEmail())));//升序
            TypedQuery query = em.createQuery(criteriaQuery);
            List result = query.getResultList();
            if(!CollectionUtils.isEmpty(result)){
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
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
                    DecimalFormat df = new DecimalFormat("#");
                    cellValue = String.valueOf(df.format(cell.getNumericCellValue())).trim();
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
