package com.st.pmgt.indicator.model;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.pmgt.model.PIPmgtObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName STProCompetence
 * @Description:专业能力
 * @Author hma
 * @Date 2020/10/9
 * @Version V1.0
 **/
@Entity
@Table
public class STProCompetence extends PIPmgtObject implements Serializable {
    /**
     * 专业能力
     */
    @Column(nullable = true, unique = false)
    private String name;

    /**
     * 职能部门
     */
    @Embedded   //引入该实体
    @AttributeOverrides({
            @AttributeOverride(name = "key.id", column = @Column(name = "planRefId", nullable = true)),
            @AttributeOverride(name = "key.classname", column = @Column(name = "planRefClass", nullable = true))
    })
    ObjectReference aaaReference;//todo
}