package ext.st.pmgt.indicator.model;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.PersistInfo;
import com.pisx.tundra.foundation.fc.util.InvalidAttributeException;
import com.pisx.tundra.foundation.inf.container.model.OrgContainer;
import com.pisx.tundra.foundation.inf.container.model.PIContained;
import com.pisx.tundra.foundation.inf.container.model.PIContainer;
import com.pisx.tundra.foundation.inf.container.model.PIContainerRef;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.model.PIPmgtObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName ProjectIndicator
 * @Description:
 * @Author hma
 * @Date 2020/9/28
 * @Version V1.0
 **/
@Entity
@Table
public class STProjectIndicator extends PIPmgtObject implements Serializable, PIContained {
    static final String CLASSNAME = STProjectIndicator.class.getName();

    @Override
    public String getConceptualClassname() {
        return CLASSNAME;
    }
    /**
     * 指标编码
     */
    @Column(name = "code", nullable = true, unique = true)
    private String code;

    /**
     * 指标描述
     */
    @Column(name = "decription", nullable = true, unique = false)
    private String decription;

    /**
     * 指标定义
     */
    @Column(name = "definition", nullable = true, unique = false)
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
     * 交付物类型编码
     */
    @Column(name = "deliverableTypeCode", nullable = true, unique = false)
    private String deliverableTypeCode;


    @Column
    Boolean enabled = false;

    @Embedded   //引入该实体
    @AttributeOverrides({   //罗列出所有需要重新命名的属性
            @AttributeOverride(name = "key.id", column = @Column(name = "containerRefId", nullable = false)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "containerRefClass", nullable = false))
    })
    PIContainerRef containerReference;  //OrgContainer



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

    public String getDeliverableTypeCode() {
        return deliverableTypeCode;
    }

    public void setDeliverableTypeCode(String deliverableTypeCode) {
        this.deliverableTypeCode = deliverableTypeCode;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public PIContainerRef getContainerReference() {
        return containerReference;
    }


    public void setContainer(PIContainer container) throws PIException {
        if (container instanceof OrgContainer) {
            containerReference = PIContainerRef.newPIContainerRef(container);
        }else {
            String msg= "the container is not Site container or Organization Container, not allowed to set.";
            throw new PIException(msg);
        }
    }

    public void setContainerReference(PIContainerRef containerReference)  {
        if (containerReference.getObject() instanceof OrgContainer) {
            this.containerReference = containerReference;
        }else {
            String msg= "the container is not Organization Container, not allowed to set.";
            try {
                throw new PIException(msg);
            } catch (PIException e) {
                e.printStackTrace();
            }
        }
    }

    public String getContainerName() {
        try {
            return (String) ((PIContainerRef) getContainerReference()).getName();
        } catch (NullPointerException npe) {
            return null;
        }
    }

    public PIContainer getContainer() {
        return (containerReference != null) ? (PIContainer) containerReference.getObject() : null;
    }

    public ObjectReference getCompetenceReference() {
        return competenceReference;
    }

    public void setCompetenceReference(ObjectReference competenceReference) {
        this.competenceReference = competenceReference;
    }

    public STProCompetence getCompetence() {
        return competenceReference!=null?(STProCompetence) competenceReference.getObject():null;
    }

    public void setCompetence(STProCompetence competence) throws PIException {
        setCompetenceReference(competence!=null?ObjectReference.newObjectReference(competence):null);
    }

    @Override
    public ObjectIdentifier getObjectIdentifier() {
        return this.getObjectIdentifier();
    }

    @Override
    public void setObjectIdentifier(ObjectIdentifier oid) {
        this.setObjectIdentifier(oid);
    }

    @Override
    public PersistInfo getPersistInfo() {
        return this.getPersistInfo();
    }

    @Override
    public void setPersistInfo(PersistInfo persistInfo) {
        this.setPersistInfo(persistInfo);
    }

    @Override
    public void checkAttributes() throws InvalidAttributeException {

    }

    @Override
    public String getOid() throws PIException {
        return null;
    }


    public static STProjectIndicator newSTProjectIndicator() throws PIException {
        STProjectIndicator obj = new STProjectIndicator();
        obj.initialize();
        return obj;
    }
}