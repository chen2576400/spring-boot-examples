package ext.st.pmgt.issue.scheduled;

import ext.st.pmgt.issue.dao.STProjectMeasuresDao;
import ext.st.pmgt.issue.model.STProjectMeasures;
import ext.st.pmgt.issue.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author nchen
 * @version 1.0
 * @date 2021/2/22 10:41
 */
@Component
public class ScheduledTaskJob {
    private Logger log = LoggerFactory.getLogger(ScheduledTaskJob.class);
    @Autowired
    private STProjectMeasuresDao measuresDao;

    //@Scheduled(cron = "*/5 * * * * ?")
    @Async(value = "taskPoolExecutor")
    public void test() {
        List<STProjectMeasures> allMeasures = measuresDao.getNotRecognizedMeasures();
        Date now = new Date();
        for (STProjectMeasures measures : allMeasures) {
            Date date = measures.getCreateTimestamp();
            double distanceTime = DateUtils.getDistanceTime(date, now);
            if (distanceTime > 24 && distanceTime <= 48) {
                System.out.println("情况一" + measures.getObjectIdentifier().getId() + "相差" + distanceTime + "小时");
            } else if (distanceTime > 48 && distanceTime <= 72) {
                System.out.println("情况二" + measures.getObjectIdentifier().getId() + "相差" + distanceTime + "小时");
            } else if (distanceTime > 72) {
                System.out.println("情况三" + measures.getObjectIdentifier().getId() + "相差" + distanceTime + "小时");
            }
        }
    }

}
