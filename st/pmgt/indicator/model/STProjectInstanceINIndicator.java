package com.st.pmgt.indicator.model;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.inf.container.model.PIContained;
import com.pisx.tundra.foundation.inf.container.model.PIContainer;
import com.pisx.tundra.foundation.inf.container.model.PIContainerRef;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.model.PIPmgtObject;
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
     * 项目计划
     */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "planRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "planRefClass", nullable = true))
    })
    ObjectReference planReference;


    /**
     * OT项目计划实例
     */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "planInstanceRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "planInstanceRefClass", nullable = true))
    })
    ObjectReference planInstanceReference;

    /**
     * IN权重
     */
    @Column(nullable = true, unique = false)
    private Double inWeight;

    /**
     * 指标源
     */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "sourceIndicatorRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "sourceIndicatorRefClass", nullable = true))
    })
    ObjectReference sourceIndicatorReference;


    /**
     * 项目实例OT指标
     */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "projectInstanceOTIndicatorRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "projectInstanceOTIndicatorRefClass", nullable = true))
    })
    ObjectReference projectInstanceOTIndicatorRef;

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

    public ObjectReference getPlanInstanceReference() {
        return planInstanceReference;
    }

    public void setPlanInstanceReference(ObjectReference planInstanceReference) {
        this.planInstanceReference = planInstanceReference;
    }

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

    public Double getInWeight() {
        return inWeight;
    }

    public void setInWeight(Double inWeight) {
        this.inWeight = inWeight;
    }

    public ObjectReference getSourceIndicatorReference() {
        return sourceIndicatorReference;
    }

    public void setSourceIndicatorReference(ObjectReference sourceIndicatorReference) {
        this.sourceIndicatorReference = sourceIndicatorReference;
    }

    public PIProject getSourceIndicator() {
        return (PIProject) sourceIndicatorReference.getObject();
    }

    public void setSourceIndicator(PIProject project) throws PIException {
        this.sourceIndicatorReference = ObjectReference.newObjectReference(project);
    }

    public ObjectReference getProjectInstanceOTIndicatorRef() {
        return projectInstanceOTIndicatorRef;
    }

    public void setProjectInstanceOTIndicatorRef(ObjectReference projectInstanceOTIndicatorRef) {
        this.projectInstanceOTIndicatorRef = projectInstanceOTIndicatorRef;
    }

    public STProjectInstanceOTIndicator getProjectInstanceOTIndicator() {
        return (STProjectInstanceOTIndicator) projectInstanceOTIndicatorRef.getObject();
    }

    public void setProjectInstanceOTIndicatorRef(STProjectInstanceOTIndicator projectInstanceOTIndicator) throws PIException {
        this.projectInstanceOTIndicatorRef = ObjectReference.newObjectReference(projectInstanceOTIndicator);
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
    public void setContainerReference(PIContainerRef containerReference) throws PIException {
        if (containerReference.getObject() instanceof PIProjectContainer) {
            this.containerReference = containerReference;
        } else {
            String msg = "the container is not PIProjectContainer, not allowed to set.";
            throw new PIException(msg);
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

}