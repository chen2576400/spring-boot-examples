package ext.st.pmgt.issue.service;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.issue.STProjectIssueHelper;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectRisk;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
@Service
public class STProjectIssueServiceImpl implements  STProjectIssueService{
    @Override
    public Collection getAllProjectIssues(PIProject project) throws PIException {
        Collection result = new ArrayList();
        EntityManager em = PersistenceHelper.service.getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = cb.createQuery();
            Root root = criteriaQuery.from(STProjectIssue.class);
            Path key1 = root.get("projectReference").get("key");
            criteriaQuery.select(root).where(cb.equal(key1, project.getObjectIdentifier()));
            TypedQuery query = em.createQuery(criteriaQuery);
            result.addAll(query.getResultList());
        } catch (Exception e) {
            throw new PIException(e);
        } finally {
            //em.close();
        }
        return result;
    }

//    /**
//     * 删除问题时  删除风险列表和部门列表
//     * @param projectIssue
//     */
//    @Override
//    public void deleteAssociastionLink(STProjectIssue projectIssue) throws PIException {
//            STProjectIssueHelper.linkService.deleteByRoleAObjectRef(ObjectReference.newObjectReference(projectIssue));
//            STProjectIssueHelper.riskLinkService.deleteByRoleAObjectRef(ObjectReference.newObjectReference(projectIssue));
//        }
}
