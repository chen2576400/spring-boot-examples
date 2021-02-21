package ext.st.pmgt.issue.processors;

import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.inf.container.model.PIContainer;
import com.pisx.tundra.foundation.inf.team.ContainerTeamHelper;
import com.pisx.tundra.foundation.inf.team.model.ContainerTeam;
import com.pisx.tundra.foundation.inf.team.model.ContainerTeamManaged;
import com.pisx.tundra.foundation.org.OrgHelper;
import com.pisx.tundra.foundation.org.model.*;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.indicator.dingTalk.DingTalkUtils;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectMeasures;
import ext.st.pmgt.issue.model.STProjectRisk;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author: Mr.Chen
 * @create: 2021-01-18 15:56
 **/
@Component
public class ConfirmIssuesORRiskORMeasuresProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        String s = params.getAjaxData().getJSONObject("componentsData").getJSONObject("confirm_project_issue_wizard_step1").
                getJSONObject("confirm_project_issue_layout").getJSONObject("fieldMeta").
                getJSONObject("confirmStatus").get("value").toString();
        Boolean m = Boolean.valueOf(s);

        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        if (sourceObject instanceof STProjectIssue) {
            STProjectIssue projectIssue = (STProjectIssue) sourceObject;
            setSTProjectIssue(projectIssue, m);
            PersistenceHelper.service.save(projectIssue);
        } else if (sourceObject instanceof STProjectRisk) {
            STProjectRisk projectRisk = (STProjectRisk) sourceObject;
            setSTProjectRisk(projectRisk, m);
            PersistenceHelper.service.save(projectRisk);
        }else if (sourceObject instanceof  STProjectMeasures){
            STProjectMeasures projectMeasures = (STProjectMeasures) sourceObject;
            setSTProjectMeasures(projectMeasures,m);
            PersistenceHelper.service.save(projectMeasures);

            //确认措施后  发送钉钉消息到项目经理，风险提出人
            Set<PIUser> userSet =new HashSet<>();
            PIContainer piContainer = ((PIProject)projectMeasures.getProjectReference().getObject()).getContainer();
            ContainerTeam containerTeam = ContainerTeamHelper.service.getContainerTeam((ContainerTeamManaged) piContainer);
            PIRole role = OrgHelper.service.getRoleByCode("PM", piContainer.getContainerReference());
            Enumeration teamPrincipalTarget = containerTeam.getPrincipalTarget(role);
            while (teamPrincipalTarget.hasMoreElements()) {
                PIPrincipalReference piPrincipalReference = (PIPrincipalReference) teamPrincipalTarget.nextElement();
                PIGroup group = (PIGroup) piPrincipalReference.getObject();
                Collection<PIUser> roleB = (List)PersistenceHelper.service.navigate(group, "roleB", MembershipLink.class, true);
                userSet.addAll(roleB);
            }
            STProjectRisk stProjectRisk=((STProjectRisk)projectMeasures.getProjectRiskReference().getObject());
            userSet.add((PIUser)(stProjectRisk.getCreator().getObject()));
            //发送消息
            try {
                String useridList = DingTalkUtils.getUseridList(userSet);
                String message="风险："+stProjectRisk.getRiskName()+"，预防措施："+projectMeasures.getName()+"已由项目经理确认";
                PIUser piUser = (PIUser) SessionHelper.service.getPrincipalReference().getObject();
                List list1 = new ArrayList();
                OapiMessageCorpconversationAsyncsendV2Request.Form form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
                form.setKey("确认人:");
                form.setValue(piUser.getFullName());
                list1.add(form);
                DingTalkUtils.sendOAMessage(useridList, false, "措施确认", "措施确认",
                        list1, message, null, null, "重汽精细化管理平台");
            }catch (Exception e){
                e.printStackTrace();
            }


        }

        return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, null, null);
    }


    private STProjectIssue setSTProjectIssue(STProjectIssue projectIssue, Boolean m) throws PIException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        PIUser piUser = (PIUser) SessionHelper.service.getPrincipal();

        projectIssue.setProjectManagerUserReference(ObjectReference.newObjectReference(piUser));
        projectIssue.setConfirmStatus(m);
        if (!m) {
            projectIssue.setCloseStamp(timestamp);
        }
        return projectIssue;
    }

    private STProjectRisk setSTProjectRisk(STProjectRisk risk, Boolean m) throws PIException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        PIUser piUser = (PIUser) SessionHelper.service.getPrincipal();
        risk.setProjectManagerUserReference(ObjectReference.newObjectReference(piUser));
        risk.setConfirmStatus(m);
        if (!m) {
            risk.setCloseStamp(timestamp);
        }
        return risk;
    }

    private STProjectMeasures setSTProjectMeasures(STProjectMeasures measures, Boolean m) throws PIException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        PIUser piUser = (PIUser) SessionHelper.service.getPrincipal();
        measures.setProjectManagerUserReference(ObjectReference.newObjectReference(piUser));
        measures.setConfirmStatus(m);
        if (!m) {
            measures.setCloseStamp(timestamp);
        }
        return measures;
    }

}
