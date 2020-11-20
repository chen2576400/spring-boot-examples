package ext.st.pmgt.indicator.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import ext.st.pmgt.indicator.model.STRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingDao extends JpaRepository<STRating, ObjectIdentifier> {
}
