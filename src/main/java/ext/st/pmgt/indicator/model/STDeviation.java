package ext.st.pmgt.indicator.model;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.model.PIPmgtObject;
import com.pisx.tundra.pmgt.plan.model.AbstractPIPlanActivity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @ClassName STDeviation
 * @Description:偏差
 * @Author hma
 * @Date 2020/10/9
 * @Version V1.0
 **/
@Entity
@Table
public class STDeviation extends PIPmgtObject implements Serializable {
    static final String CLASSNAME = STDeviation.class.getName();

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
     * 标准偏差值
     */
    @Column(nullable = true, unique = false)
    private Double value = 0D;

    /**
     * 偏差描述
     */
    @Column(nullable = true, unique = true)
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static STDeviation newSTDeviation() throws PIException {
        STDeviation obj = new STDeviation();
        obj.initialize();
        return obj;
    }
}