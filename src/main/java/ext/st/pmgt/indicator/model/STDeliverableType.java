package ext.st.pmgt.indicator.model;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.model.PIPmgtObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName STDeliverable
 * @Description:交付物类型
 * @Author hma
 * @Date 2020/10/9
 * @Version V1.0
 **/
@Entity
@Table
public class STDeliverableType extends PIPmgtObject implements Serializable {
    static final String CLASSNAME = STDeliverableType.class.getName();

    @Override
    public String getConceptualClassname() {
        return CLASSNAME;
    }
    /**
     * 名称
     */
    @Column(nullable = true, unique = false)
    private String name;

    /**
     * 编码
     */
    @Column(nullable = true, unique = true)
    private String code;


    /*
     * 指标
     * */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "indicatorRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "indicatorRefClass", nullable = true))
    })
    ObjectReference indicatorReference;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static STDeliverableType newSTDeliverableType() throws PIException {
        STDeliverableType obj = new STDeliverableType();
        obj.initialize();
        return obj;
    }

    public ObjectReference getIndicatorReference() {
        return indicatorReference;
    }

    public void setIndicatorReference(ObjectReference indicatorReference) {
        this.indicatorReference = indicatorReference;
    }
}