package com.st.pmgt.indicator.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.st.pmgt.indicator.model.STDeliverableType;
import com.st.pmgt.indicator.model.STDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName DifficultyDao
 * @Description:
 * @Author hma
 * @Date 2020/10/9
 * @Version V1.0
 **/
public interface DifficultyDao extends JpaRepository<STDifficulty, ObjectIdentifier> {
}