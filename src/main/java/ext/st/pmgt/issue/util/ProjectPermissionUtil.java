package ext.st.pmgt.issue.util;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.inf.container.model.PIContainer;
import com.pisx.tundra.foundation.inf.team.ContainerTeamHelper;
import com.pisx.tundra.foundation.inf.team.model.ContainerTeam;
import com.pisx.tundra.foundation.inf.team.model.ContainerTeamManaged;
import com.pisx.tundra.foundation.org.OrgHelper;
import com.pisx.tundra.foundation.org.model.*;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.ApplicationContextUtil;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.project.model.PIProject;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.util.*;

/**
 * @author: Mr.Chen
 * @create: 2021-01-18 10:18
 **/
public class ProjectPermissionUtil {
    private AutowireCapableBeanFactory beanFactory = ApplicationContextUtil.getApplicationContext().getAutowireCapableBeanFactory();

    /**
     * @param project 项目
     * @param piUser  当前和用户(默认为当前用户)
     * @param code    角色编码
     * @return    true
     * @throws PIException
     */
    public static Boolean isProjectRole(PIProject project, PIUser piUser, String code) throws PIException {
        if (piUser == null) {
            piUser = (PIUser) SessionHelper.service.getPrincipal();
        }
        Collection<Persistable> persistables = new HashSet<>();
        PIContainer piContainer = project.getContainer();
        ContainerTeam containerTeam = ContainerTeamHelper.service.getContainerTeam((ContainerTeamManaged) piContainer);
        PIRole role = OrgHelper.service.getRoleByCode(code, piContainer.getContainerReference());
        Enumeration teamPrincipalTarget = containerTeam.getPrincipalTarget(role);
        while (teamPrincipalTarget.hasMoreElements()) {
            PIPrincipalReference piPrincipalReference = (PIPrincipalReference) teamPrincipalTarget.nextElement();
            PIGroup group = (PIGroup) piPrincipalReference.getObject();
            Collection<Persistable> roleB = PersistenceHelper.service.navigate(group, "roleB", MembershipLink.class, true);
            persistables.addAll(roleB);
        }
        persistables.removeAll(Collections.singleton(null));
        List<PIUser> piUsers = new ArrayList<>(Arrays.asList(persistables.toArray(new PIUser[0])));
        PIUser finalPiUser = piUser;
        boolean m = piUsers.stream().anyMatch(user -> user.getName().equals(finalPiUser.getName()));
        return m;
    }


}
