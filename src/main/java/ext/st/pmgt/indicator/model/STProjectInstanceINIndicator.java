package ext.st.pmgt.indicator.model;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.inf.container.model.PIContained;
import com.pisx.tundra.foundation.inf.container.model.PIContainer;
import com.pisx.tundra.foundation.inf.container.model.PIContainerRef;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.model.PIPmgtObject;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlannable;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.project.model.PIProjectContainer;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName ProjectInstanceINIndicator
 * @Description:
 * @Author hma
 * @Date 2020/9/28
 * @Version V1.0
 **/
@Entity
@Table
public class STProjectInstanceINIndicator extends PIPmgtObject implements Serializable, PIContained {

    static final String CLASSNAME = STProjectInstanceINIndicator.class.getName();

    @Override
    public String getConceptualClassname() {
        return CLASSNAME;
    }
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
     * 计划
     */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "planRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "planRefClass", nullable = true))
    })
    ObjectReference planReference;


    /**
     * IN权重
     */
    @Column(nullable = true, unique = false)
    private Double weights = 0D;


    /**
     * ot项目计划实例-->PIPlan(暂时与planReference相同)
     */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "projectPlanInstanceRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "projectPlanInstanceRefClass", nullable = true))
    })
    ObjectReference projectPlanInstanceRef;

    /**
     * 指标源（固定为项目）
     */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "indicatorSourceRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "indicatorSourceRefClass", nullable = true))
    })
    ObjectReference IndicatorSourceRef;

    /**
     * 项目实例OT指标编码
     */
    @Column
    private String otCode;

    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "projectRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "projectRefClass", nullable = true))
    })
    ObjectReference projectReference;


    @Embedded   //引入该实体
    @AttributeOverrides({   //罗列出所有需要重新命名的属性
            @AttributeOverride(name = "key.id", column = @Column(name = "containerRefId", nullable = false)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "containerRefClass", nullable = false))
    })
    PIContainerRef containerReference;  //ProjectContainerRef

    public ObjectReference getProjectReference() {
        return projectReference;
    }

    public void setProjectReference(ObjectReference projectReference) {
        this.projectReference = projectReference;
    }


    public ObjectReference getPlanActivityReference() {
        return planActivityReference;
    }

    public void setPlanActivityReference(ObjectReference planActivityReference) {
        this.planActivityReference = planActivityReference;
    }

    public ObjectReference getPlanReference() {
        return planReference;
    }

    public void setPlanReference(ObjectReference planReference) {
        this.planReference = planReference;
    }

    public String getOtCode() {
        return otCode;
    }

    public void setOtCode(String otCode) {
        this.otCode = otCode;
    }

    public PIPlannable getPlanActivity() {
        return (planActivityReference != null) ? (PIPlannable) planActivityReference.getObject() : null;
    }


    void planActivityReferenceValidate(ObjectReference the_planActivityReference) throws PIException {
        if (the_planActivityReference != null && the_planActivityReference.getReferencedClass() != null &&
                !PIPlannable.class.isAssignableFrom(the_planActivityReference.getReferencedClass())) {
            String msg = "The value assigned to planActivityReference must be of the type 'ObjectReference'.";
            throw new PIException(msg);
        }
    }


    public PIProject getProject() {
        return (projectReference != null) ? (PIProject) projectReference.getObject() : getPlanActivity().getProject();
    }

    public Double getWeights() {
        return weights;
    }

    public void setWeights(Double weights) {
        this.weights = weights;
    }

    public ObjectReference getProjectPlanInstanceRef() {
        return projectPlanInstanceRef;
    }

    public void setProjectPlanInstanceRef(ObjectReference projectPlanInstanceRef) {
        this.projectPlanInstanceRef = projectPlanInstanceRef;
    }

    public PIPlan getProjectPlanInstance() {
        return (PIPlan) projectPlanInstanceRef.getObject();
    }

    public void setProjectPlanInstance(PIPlan plan) throws PIException {
        this.projectPlanInstanceRef = ObjectReference.newObjectReference(plan);
    }

    public ObjectReference getIndicatorSourceRef() {
        return IndicatorSourceRef;
    }

    public void setIndicatorSourceRef(ObjectReference indicatorSourceRef) {
        IndicatorSourceRef = indicatorSourceRef;
    }

    public PIProject getIndicatorSource() {
        return (PIProject) IndicatorSourceRef.getObject();
    }

    public void setIndicatorSourceRef(PIProject project) throws PIException {
        IndicatorSourceRef = ObjectReference.newObjectReference(project);
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
    public void setContainerReference(PIContainerRef containerReference) {
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

    public static STProjectInstanceINIndicator newSTProjectInstanceINIndicator() throws PIException {
        STProjectInstanceINIndicator obj = new STProjectInstanceINIndicator();
        obj.initialize();
        return obj;
    }
}