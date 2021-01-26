package ext.st.pmgt.project.processors;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.org.model.PIPrincipal;
import com.pisx.tundra.foundation.org.model.PIPrincipalReference;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIMessage;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultUpdateFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.plan.PIPlanHelper;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.project.PIProjectHelper;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.project.resources.projectResource;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Component
public class EditSTProjectProcessor extends DefaultUpdateFormProcessor {
    private static final String RESOURCE = projectResource.class.getName();
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        try{
            PIPrincipal principal = SessionHelper.service.getPrincipal();
            PIPrincipalReference modifier= PIPrincipalReference.newPIPrincipalReference(principal);

            PIProject oldProject=(PIProject)params.getNfCommandBean().getSourceObject();
            Timestamp oldPlanStartDate=oldProject.getPlanStartDate();

            if(list.size()>0){
                PIProject project=(PIProject)list.get(0);
                String name = project.getProjectName().trim();
                String shortName = project.getProjectShortName().trim();
                String projectStr = project.toString();

                Locale locale = SessionHelper.service.getLocale();
                //check name value
//                if(!ProjectIXUtils.isValidContainerName(name)){
//                    String restricte = ProjectIXUtils.getRestrictedCharacterListInContainerName();
//                    String [] message = new String[] {name, restricte };
//                    throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
//                            "NAME_ILLEGAL_CHARACTERS_MESSAGE", message,localLocale));
//                }

                //Verify that the name is unique
                Collection projectsByName = PIProjectHelper.service.getProjectsByName(name);
                Iterator qr = projectsByName.iterator();
                while(qr.hasNext()) {
                    PIProject existProject=(PIProject)qr.next();
                    boolean isBaseline=PIProjectHelper.service.isBaselineProject(existProject);
                    if(isBaseline) {
                        continue;
                    }
                    String existProjectStr = existProject.toString();
                    if(!projectStr.equals(existProjectStr)){
                        throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
                                "PROIJECT_NAME_ERROR", null, locale));
                    }
                }

                //Verify that the number is unique
                Collection projectsByShortName = PIProjectHelper.service.getProjectsByShortName(shortName);
                qr = projectsByShortName.iterator();
                while(qr.hasNext()) {
                    PIProject existProject=(PIProject)qr.next();
                    String existProjectStr = existProject.toString();
                    if(!projectStr.equals(existProjectStr)){
                        throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
                                "SHORTNAME_ERROR", null, locale));
                    }
                }

//
//                Map<String,Object> combMap=params.getNfCommandBean().getLayoutFields();
//                Map<String,Object> textMap= params.getNfCommandBean().getText();
//
//                //get project calendar
//                List<String> calendarList=(ArrayList<String>)combMap.get(PIProject.CALENDAR_REFERENCE);
//                if(calendarList!=null&&calendarList.size()>0){
//                    String calendarOid=calendarList.get(0);
//                    PICalendar calendar=(PICalendar) CommonUtils.getPIObjectByOid(calendarOid);
//                    project.setCalendar(calendar);
//                }else{
//                    throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
//                            "NOT_CHOICE_PROJECT_CALENDAR_ERROR",null, locale));
//                }
//
//                //check plan date
//                CreateProjectProcessor.checkPlanDate(textMap);

                //get project group
//                ArrayList<String> groupList=(ArrayList<String>)combMap.get(PIProject.PROJECT_GROUP_REFERENCE);
//                if(groupList!=null&&groupList.size()>0){
//                    String groupOid=(String)groupList.get(0);
//                    PIGroup group=(PIGroup)CommonUtils.getPIObjectByOid(groupOid);
//                    project.setProjectGroup(group);
//                    PIGroup group = OrgHelper.service.getGroupById("383");
//                    project.setProjectGroup(group);
//                    PIObs obs=PIResourceHelper.service.getOBSOfOrgGroup(group);
//                    if(obs!=null)
//                        project.setObs(obs);
//                    else
//                        project.setObs(null);
//                }

                //change project and container name
                project=PIProjectHelper.service.changeProjectName(project, project.getProjectName());

                project.setModifier(modifier);
                project=(PIProject)PersistenceHelper.service.save(project);

                List<PIPlan> plans = PIPlanHelper.service.getPlans(project);
                for (PIPlan plan : plans ==null ?new ArrayList<PIPlan>():plans) {
                    plan.setCalendar(project.getCalendar());
                    PersistenceHelper.service.save(plan);
                }

            }else{
                throw new PIException(" get project object error,refresh the page and try again");
            }

        }catch(Exception e){
            e.printStackTrace();
            throw new PIException(e.toString());
        }

        return new ResponseWrapper(ResponseWrapper.PAGE_FLUSH, "", null);
    }
}
