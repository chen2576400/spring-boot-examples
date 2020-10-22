package ext.st.pmgt.indicator.model;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.inf.container.model.PIContained;
import com.pisx.tundra.foundation.inf.container.model.PIContainer;
import com.pisx.tundra.foundation.inf.container.model.PIContainerRef;
import com.pisx.tundra.foundation.org.model.PIPrincipalReference;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.model.PIPmgtObject;
import com.pisx.tundra.pmgt.plan.model.PIPlannable;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.project.model.PIProjectContainer;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @ClassName ProjectInstanceOTIndicator
 * @Description:
 * @Author hma
 * @Date 2020/9/28
 * @Version V1.0
 **/
@Entity
@Table
public class STProjectInstanceOTIndicator extends PIPmgtObject implements Serializable, PIContained {
    static final String CLASSNAME = STProjectInstanceOTIndicator.class.getName();

    @Override
    public String getConceptualClassname() {
        return CLASSNAME;
    }



    /**
     * 指标编码
     */
    @Column(nullable = true, unique = false)
    private String code;

    /**
     * 指标描述
     */
    @Column(nullable = true, unique = false)
    private String decription;

    /**
     * 指标定义
     */
    @Column(nullable = true, unique = false)
    private String definition;

    /**
     * 专业能力
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "competenceRefId", nullable = false)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "competenceRefClass", nullable = false))
    })
    ObjectReference competenceReference;

    /**
     * 项目计划
     */
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
     * 标准偏差值
     */
    @Column(nullable = true, unique = false)
    private Double standardDeviationValue;

    /**
     * 标准困难度值
     */
    @Column(nullable = true, unique = false)
    private Double standardDifficultyValue;


    /**
     * 广度
     */
    @Column(nullable = true)
    private Integer breadth;


    /**
     * 关键度
     */
    @Column(nullable = true, unique = false)
    private Double criticality = 0D;


    /**
     * 交付物类型编码
     */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "deliverableTypeRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "deliverableTypeClass", nullable = true))
    })
    ObjectReference deliverableTypeReference;


    /**
     * 交付物
     */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "planDeliverableRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "planDeliverableClass", nullable = true))
    })
    ObjectReference planDeliverableReference;


    /**
     * 偏差汇报
     */
    @Column(nullable = true, unique = false)
    private Double deviationReport;

    /**
     * 困难度汇报
     */
    @Column(nullable = true, unique = false)
    private Double difficultyReport;

    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "projectRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "projectRefClass", nullable = true))
    })
    ObjectReference projectReference;

    /**
     * 完成状态（0,1,2）-->发布次数
     */
    @Column(nullable = true, unique = true)
    private Integer completionStatus;

    /**
     * 汇报时间
     */
    @Column(nullable = true)
    private Timestamp reportTime;

    /**
     * 汇报人
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "reporterRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "reporterRefClass", nullable = true))
    })
    PIPrincipalReference reporter;

    @Embedded   //引入该实体
    @AttributeOverrides({   //罗列出所有需要重新命名的属性
            @AttributeOverride(name = "key.id", column = @Column(name = "containerRefId", nullable = false)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "containerRefClass", nullable = false))
    })
    PIContainerRef containerReference;  //ProjectContainerRef


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public ObjectReference getPlanReference() {
        return planReference;
    }

    public void setPlanReference(ObjectReference planReference) {
        this.planReference = planReference;
    }

    public Double getStandardDeviationValue() {
        return standardDeviationValue;
    }

    public void setStandardDeviationValue(Double standardDeviationValue) {
        this.standardDeviationValue = standardDeviationValue;
    }

    public Double getCriticality() {
        return criticality;
    }

    public void setCriticality(Double criticality) {
        this.criticality = criticality;
    }

    public ObjectReference getDeliverableTypeReference() {
        return deliverableTypeReference;
    }

    public void setDeliverableTypeReference(ObjectReference deliverableTypeReference) {
        this.deliverableTypeReference = deliverableTypeReference;
    }

    public String getDeliverableTypeCode() {
        STDeliverableType object = (STDeliverableType) deliverableTypeReference.getObject();
        return object.getCode();
    }

    public ObjectReference getPlanDeliverableReference() {
        return planDeliverableReference;
    }

    public void setPlanDeliverableReference(ObjectReference planDeliverableReference) {
        this.planDeliverableReference = planDeliverableReference;
    }

    public Double getStandardDifficultyValue() {
        return standardDifficultyValue;
    }

    public void setStandardDifficultyValue(Double standardDifficultyValue) {
        this.standardDifficultyValue = standardDifficultyValue;
    }

    public Double getDeviationReport() {
        return deviationReport;
    }

    public void setDeviationReport(Double deviationReport) {
        this.deviationReport = deviationReport;
    }

    public Double getDifficultyReport() {
        return difficultyReport;
    }

    public void setDifficultyReport(Double difficultyReport) {
        this.difficultyReport = difficultyReport;
    }

    public Integer getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(Integer completionStatus) {
        this.completionStatus = completionStatus;
    }

    public Timestamp getReportTime() {
        return reportTime;
    }

    public void setReportTime(Timestamp reportTime) {
        this.reportTime = reportTime;
    }

    public PIPlannable getPlanActivity() {
        return (planActivityReference != null) ? (PIPlannable) planActivityReference.getObject() : null;
    }

    public ObjectReference getPlanActivityReference() {
        return planActivityReference;
    }

    public void setPlanActivity(PIPlannable the_planActivity) throws PIException {
        setPlanActivityReference(the_planActivity == null ? null : ObjectReference.newObjectReference((PIPlannable) the_planActivity));
    }

    public void setPlanActivityReference(ObjectReference the_planActivityReference) throws PIException {
        planActivityReferenceValidate(the_planActivityReference);
        planActivityReference = (ObjectReference) the_planActivityReference;
    }

    void planActivityReferenceValidate(ObjectReference the_planActivityReference) throws PIException {
        if (the_planActivityReference != null && the_planActivityReference.getReferencedClass() != null &&
                !PIPlannable.class.isAssignableFrom(the_planActivityReference.getReferencedClass())) {
            String msg = "The value assigned to planActivityReference must be of the type 'ObjectReference'.";
            throw new PIException(msg);
        }
    }

    public PIPlanDeliverable getPlanDeliverable() {
        return (PIPlanDeliverable) planDeliverableReference.getObject();
    }

    @Override
    public PIContainerRef getContainerReference() {
        return containerReference;
    }

    @Override
    public void setContainer(PIContainer container) throws PIException {
        if (container instanceof PIProjectContainer) {
            containerReference = PIContainerRef.newPIContainerRef(container);
        } else {
            String msg = "the container is not PIProjectContainer, not allowed to set.";
            throw new PIException(msg);
        }
    }

    @Override
    public void setContainerReference(PIContainerRef containerReference)  {
        if (containerReference.getObject() instanceof PIProjectContainer) {
            this.containerReference = containerReference;
        } else {
            String msg = "the container is not PIProjectContainer, not allowed to set.";
            try {
                throw new PIException(msg);
            } catch (PIException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getContainerName() {
        return containerReference.getName();
    }

    @Override
    public PIContainer getContainer() {
        return (PIContainer) containerReference.getObject();
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

    public void setProjectReference(PIProject project) throws PIException {
        this.projectReference = ObjectReference.newObjectReference(planActivityReference);
    }

    public PIUser getReporter() {
        return (PIUser) reporter.getObject();
    }

    public void setReporter(PIUser reporter) {
        this.reporter = PIPrincipalReference.newPIPrincipalReference(reporter);
    }

    public ObjectReference getCompetenceReference() {
        return competenceReference;
    }

    public void setCompetenceReference(ObjectReference competenceReference) {
        this.competenceReference = competenceReference;
    }

    public STProCompetence getCompetence() {
        return (STProCompetence) competenceReference.getObject();
    }

    public void setCompetenceReference(STProCompetence competence) throws PIException {
        this.competenceReference = ObjectReference.newObjectReference(competence);
    }

    public Integer getBreadth() {
        return breadth;
    }

    public void setBreadth(Integer breadth) {
        this.breadth = breadth;
    }

    public void setReporter(PIPrincipalReference reporter) {
        this.reporter = reporter;
    }

    public static STProjectInstanceOTIndicator newSTProjectInstanceOTIndicator() throws PIException {
        STProjectInstanceOTIndicator obj = new STProjectInstanceOTIndicator();
        obj.initialize();
        return obj;
    }
}