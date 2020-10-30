package ext.st.pmgt.indicator.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import ext.st.pmgt.indicator.model.STDeviation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * @ClassName DeviationDao
 * @Description:
 * @Author hma
 * @Date 2020/10/9
 * @Version V1.0
 **/
public interface DeviationDao extends JpaRepository<STDeviation, ObjectIdentifier> {
        Collection findByCodeEquals(String code);
}