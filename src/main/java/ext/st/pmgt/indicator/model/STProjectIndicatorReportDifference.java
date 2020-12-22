package ext.st.pmgt.indicator.model;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.org.model.PIPrincipalReference;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.model.PIPmgtObject;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlannable;
import com.pisx.tundra.pmgt.project.model.PIProject;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table
public class STProjectIndicatorReportDifference extends PIPmgtObject implements Serializable {
    static final String CLASSNAME = STProjectIndicatorReportDifference.class.getName();

    @Embedded   //项目id
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "projectRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "projectRefClass", nullable = true))
    })
    ObjectReference projectReference;

    /**
     * 项目计划
     */
    @Embedded   //计划id
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "planRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "planRefClass", nullable = true))
    })
    ObjectReference planReference;

    /**
     * 指标编码
     */
    @Column(nullable = true, unique = false)
    private String code;

    /**
     * OT任务
     */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "otplanActivityRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "otplanActivityRefClass", nullable = true))
    })
    ObjectReference otplanActivityReference;

    /**
     * ot指标任务描述
     */
    @Column(nullable = true, unique = false)
    private String otplanActivitydescription;

    /**
     * 偏差汇报
     */
    @Column(nullable = true, unique = false)
    private Double deviationReport = 0D;

    /**
     * 偏差汇报人
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "reporterRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "reporterRefClass", nullable = true))
    })
    PIPrincipalReference reporter;

    /**
     * 汇报时间
     */
    @Column(nullable = true)
    private Timestamp reportTime;

    /**
     * in指标任务
     */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "otplanActivityRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "otplanActivityRefClass", nullable = true))
    })
    ObjectReference inplanActivityReference;

    /**
     * in指标任务描述
     */
    @Column(nullable = true, unique = false)
    private String inplanActivitydescription;

    /**
     * 偏差评定
     */
    @Column(nullable = true, unique = false)
    private Double otRating = 0D;

    /**
     * 偏差评定人
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "raterRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "raterRefClass", nullable = true))
    })
    PIPrincipalReference rater;

    /**
     * 评定描述
     */
    @Column(nullable = true, unique = false)
    private String inRatedescription;

    /**
     * 评定时间
     */
    @Column(nullable = true)
    private Timestamp rateTime;


    public ObjectReference getProjectReference() {
        return projectReference;
    }

    public void setProjectReference(ObjectReference projectReference) {
        this.projectReference = projectReference;
    }
    public PIProject getProject() {
        return projectReference!=null?(PIProject) projectReference.getObject():null;
    }

    public void setProjectReference(PIProject project) throws PIException {
        this.projectReference = ObjectReference.newObjectReference(project);
    }
    public ObjectReference getPlanReference() {
        return planReference;
    }

    public void setPlanReference(ObjectReference planReference) {
        this.planReference = planReference;
    }
    public PIPlan getPlan() {
        return (planReference != null) ? (PIPlan) planReference.getObject() : null;

    }

    public void setPlan(PIPlan plan) throws PIException {
        this.planReference = ObjectReference.newObjectReference(plan);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ObjectReference getOtplanActivityReference() {
        return otplanActivityReference;
    }

    public void setOtplanActivityReference(ObjectReference otplanActivityReference) {
        this.otplanActivityReference = otplanActivityReference;
    }
    public PIPlannable getotPlanActivity() {
        return (otplanActivityReference != null) ? (PIPlannable) otplanActivityReference.getObject() : null;
    }
    public void setOtplanActivity(PIPlannable otplanActivity) throws PIException {
        setOtplanActivityReference(otplanActivity == null ? null : ObjectReference.newObjectReference(otplanActivity));
    }


    public String getOtplanActivitydescription() {
        return otplanActivitydescription;
    }

    public void setOtplanActivitydescription(String otplanActivitydescription) {
        this.otplanActivitydescription = otplanActivitydescription;
    }

    public Double getDeviationReport() {
        return deviationReport;
    }

    public void setDeviationReport(Double deviationReport) {
        this.deviationReport = deviationReport;
    }


    public void setReporter(PIPrincipalReference reporter) {
        this.reporter = reporter;
    }

    public PIUser getReporter() {
        return reporter!=null?(PIUser) reporter.getObject():null;
    }

    public void setReporter(PIUser reporter) {
        this.reporter = PIPrincipalReference.newPIPrincipalReference(reporter);
    }

    public Timestamp getReportTime() {
        return reportTime;
    }

    public void setReportTime(Timestamp reportTime) {
        this.reportTime = reportTime;
    }

    public ObjectReference getInplanActivityReference() {
        return inplanActivityReference;
    }

    public void setInplanActivityReference(ObjectReference inplanActivityReference) {
        this.inplanActivityReference = inplanActivityReference;
    }
    public PIPlannable getInPlanActivity() {
        return (inplanActivityReference != null) ? (PIPlannable) inplanActivityReference.getObject() : null;
    }
    public void setInplanActivity(PIPlannable inplanActivity) throws PIException {
        setOtplanActivityReference(inplanActivity == null ? null : ObjectReference.newObjectReference(inplanActivity));
    }

    public String getInplanActivitydescription() {
        return inplanActivitydescription;
    }

    public void setInplanActivitydescription(String inplanActivitydescription) {
        this.inplanActivitydescription = inplanActivitydescription;
    }

    public Double getOtRating() {
        return otRating;
    }

    public void setOtRating(Double otRating) {
        this.otRating = otRating;
    }

    public PIUser getRater() {
        return rater!=null?(PIUser) rater.getObject():null;
    }

    public void setRater(PIPrincipalReference rater) {
        this.rater = rater;
    }
    public void setRater(PIUser rater) {
        this.rater = PIPrincipalReference.newPIPrincipalReference(rater);
    }
    public String getInRatedescription() {
        return inRatedescription;
    }

    public void setInRatedescription(String inRatedescription) {
        this.inRatedescription = inRatedescription;
    }

    public Timestamp getRateTime() {
        return rateTime;
    }

    public void setRateTime(Timestamp rateTime) {
        this.rateTime = rateTime;
    }


    public static STProjectIndicatorReportDifference newSTProjectIndicatorReportDifference() throws PIException {
        STProjectIndicatorReportDifference obj = new STProjectIndicatorReportDifference();
        obj.initialize();
        return obj;
    }
}
