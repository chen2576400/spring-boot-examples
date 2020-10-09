package com.st.pmgt.indicator.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.st.pmgt.indicator.model.STExpectedFinishTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpectedFinishTimeDao extends JpaRepository<STExpectedFinishTime, ObjectIdentifier> {
}
