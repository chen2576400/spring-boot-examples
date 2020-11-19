package ext.st.pmgt.indicator.model;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.model.PIPmgtObject;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @ClassName STRating
 * @Description:评定
 * @Author hma
 * @Date 2020/11/10
 * @Version V1.0
 **/
@Entity
@Table
public class STRating extends PIPmgtObject implements Serializable {
    static final String CLASSNAME = STRating.class.getName();
    @Override
    public String getConceptualClassname() {
        return CLASSNAME;
    }

    /**
     * 输出评定
     */
    @Column(nullable = true, unique = false)
    private Double otRating = 0D;

    /**
     * 评定描述
     */
    @Column(nullable = true, unique = false)
    private String description;

    /*
     * in指标
     * */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "inIndicatorRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "inIndicatorRefClass", nullable = true))
    })
    ObjectReference inIndicatorReference;

    /**
     * 汇报时间
     */
    @Column(nullable = true)
    private Timestamp reportTime;

    public static String getCLASSNAME() {
        return CLASSNAME;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ObjectReference getInIndicatorReference() {
        return inIndicatorReference;
    }

    public void setInIndicatorReference(ObjectReference inIndicatorReference) {
        this.inIndicatorReference = inIndicatorReference;
    }

    public STProjectInstanceINIndicator getInIndicator() {
        return (STProjectInstanceINIndicator) inIndicatorReference.getObject();
    }

    public void setInIndicator(STProjectInstanceINIndicator inIndicator) throws PIException {
        this.inIndicatorReference = ObjectReference.newObjectReference(inIndicator);
    }

    public Timestamp getReportTime() {
        return reportTime;
    }

    public void setReportTime(Timestamp reportTime) {
        this.reportTime = reportTime;
    }

    public static STRating newSTRating() throws PIException {
        STRating obj = new STRating();
        obj.initialize();
        return obj;
    }

    public Double getOtRating() {
        return otRating;
    }

    public void setOtRating(Double otRating) {
        this.otRating = otRating;
    }
}