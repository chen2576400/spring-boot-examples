package com.st.pmgt.indicator.model;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.org.model.PIPrincipalReference;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.model.PIPmgtObject;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.model.PIProject;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @ClassName STFinishTime
 * @Description:
 * @Author hma
 * @Date 2020/10/9
 * @Version V1.0
 **/
@Entity
@Table
public class STExpectedFinishTime extends PIPmgtObject implements Serializable {

    /*
     * 项目
     * */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "projectRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "projectRefClass", nullable = true))
    })
    ObjectReference projectReference;

    /*
     * 计划
     * */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "planRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "planRefClass", nullable = true))
    })
    ObjectReference planReference;

    /**
     * 任务
     */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "planActivityRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "planActivityRefClass", nullable = true))
    })
    ObjectReference planActivityReference;

    /**
     * 填报时间
     */
    @Column(nullable = true, unique = false)
    private Timestamp reportTime;

    /**
     * 填报者
     */
    @Embedded   //引入该实体
    @AttributeOverrides({   //罗列出所有需要重新命名的属性
            @AttributeOverride(name = "key.id", column = @Column(name = "reporterRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "reporterRefClass", nullable = true))
    })
    PIPrincipalReference reporter;

    /**
     * 预计完成时间
     */
    @Column(nullable = true, unique = false)
    private Timestamp expectedFinishTime;

    public Timestamp getReportTime() {
        return reportTime;
    }

    public void setReportTime(Timestamp reportTime) {
        this.reportTime = reportTime;
    }

    public Timestamp getExpectedFinishTime() {
        return expectedFinishTime;
    }

    public void setExpectedFinishTime(Timestamp expectedFinishTime) {
        this.expectedFinishTime = expectedFinishTime;
    }

    public ObjectReference getProjectReference() {
        return projectReference;
    }

    public void setProjectReference(ObjectReference projectReference) {
        this.projectReference = projectReference;
    }

    public PIProject getProject() {
        return (PIProject) projectReference.getObject();
    }

    public void setProject(PIProject project) throws PIException {
        this.projectReference = ObjectReference.newObjectReference(project);
    }

    public ObjectReference getPlanActivityReference() {
        return planActivityReference;
    }

    public void setPlanActivityReference(ObjectReference planActivityReference) {
        this.planActivityReference = planActivityReference;
    }

    public PIPlanActivity getPlanActivitye() {
        return (PIPlanActivity) planActivityReference.getObject();
    }

    public void setPlanActivityReference(PIPlanActivity planActivity) throws PIException {
        this.planActivityReference = ObjectReference.newObjectReference(planActivity);
    }

    public ObjectReference getPlanReference() {
        return planReference;
    }

    public void setPlanReference(ObjectReference planReference) {
        this.planReference = planReference;
    }

    public PIPlan getPlan() {
        return (PIPlan) planReference.getObject();
    }

    public void setPlanReference(PIPlan plan) throws PIException {
        this.planReference = ObjectReference.newObjectReference(plan);
    }

    public PIPrincipalReference getReporter() {
        return reporter;
    }

    public void setReporter(PIPrincipalReference reporter) {
        reporter = reporter;
    }


}