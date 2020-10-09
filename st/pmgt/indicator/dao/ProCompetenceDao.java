package com.st.pmgt.indicator.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.st.pmgt.indicator.model.STProCompetence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProCompetenceDao extends JpaRepository<STProCompetence, ObjectIdentifier> {
}
