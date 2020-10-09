package com.st.pmgt.indicator.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.st.pmgt.indicator.model.STDeliverableType;
import com.st.pmgt.indicator.model.STDeviation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName DeviationDao
 * @Description:
 * @Author hma
 * @Date 2020/10/9
 * @Version V1.0
 **/
public interface DeviationDao extends JpaRepository<STDeviation, ObjectIdentifier> {
}