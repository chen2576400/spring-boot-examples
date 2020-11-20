package ext.st.pmgt.indicator.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import ext.st.pmgt.indicator.model.STDeliverableType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @ClassName DeliverableTypeDao
 * @Description:
 * @Author hma
 * @Date 2020/10/9
 * @Version V1.0
 **/
public interface DeliverableTypeDao extends JpaRepository<STDeliverableType, ObjectIdentifier> {
    STDeliverableType findByName(String s);
    STDeliverableType findByNameEquals(String s);
    List<STDeliverableType> findByCodeEquals(String S);

}