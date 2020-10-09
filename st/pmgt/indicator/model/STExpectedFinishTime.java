package com.st.pmgt.indicator.model;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.model.PIPmgtObject;
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


}