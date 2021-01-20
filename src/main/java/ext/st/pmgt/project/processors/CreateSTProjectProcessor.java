package ext.st.pmgt.project.processors;

import com.alibaba.fastjson.JSONObject;
import com.pisx.tundra.foundation.admin.AdministrativeDomainHelper;
import com.pisx.tundra.foundation.admin.model.AdminDomainRef;
import com.pisx.tundra.foundation.admin.model.AdministrativeDomain;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.inf.container.PIContainerHelper;
import com.pisx.tundra.foundation.inf.container.model.OrgContainer;
import com.pisx.tundra.foundation.inf.container.model.PIContainer;
import com.pisx.tundra.foundation.inf.container.model.PIContainerRef;
import com.pisx.tundra.foundation.lifecycle.LifeCycleHelper;
import com.pisx.tundra.foundation.lifecycle.model.LcState;
import com.pisx.tundra.foundation.lifecycle.model.LifeCycleState;
import com.pisx.tundra.foundation.meta.type.TypeHelper;
import com.pisx.tundra.foundation.meta.type.model.LTDTypeDefinition;
import com.pisx.tundra.foundation.meta.type.model.TypeDefinitionReference;
import com.pisx.tundra.foundation.org.model.PIOrganization;
import com.pisx.tundra.foundation.org.model.PIPrincipal;
import com.pisx.tundra.foundation.org.model.PIPrincipalReference;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.ownership.model.Ownership;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIMessage;
import com.pisx.tundra.foundation.util.PIPropertyVetoException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultCreateFormProcessor;
import com.pisx.tundra.netfactory.mvc.components.WebUIData;
import com.pisx.tundra.netfactory.util.beans.NfCommandBean;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.access.PmgtAccessHelper;
import com.pisx.tundra.pmgt.assignment.PIAssignmentHelper;
import com.pisx.tundra.pmgt.common.constants.CommonConstants;
import com.pisx.tundra.pmgt.common.util.CommonUtils;
import com.pisx.tundra.pmgt.plan.PIPlanHelper;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.project.PIProjectHelper;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.project.model.PIProjectContainer;
import com.pisx.tundra.pmgt.project.projectResource;
import com.pisx.tundra.pmgt.resource.PIResourceHelper;
import com.pisx.tundra.pmgt.resource.model.PIResource;
import com.pisx.tundra.pmgt.resource.model.PIResourceProjectLink;
import com.pisx.tundra.pmgt.template.AbstractWorklistDrivenCopier;
import com.pisx.tundra.pmgt.template.ix.ExpImpForPIProjectHandler;
import com.pisx.tundra.pmgt.template.ix.PlanCopyHelper;
import com.pisx.tundra.pmgt.template.model.ProjectCopyWorklist;
import com.pisx.tundra.pmgt.template.model.ProjectIXUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CreateSTProjectProcessor extends DefaultCreateFormProcessor {

    private static final String RESOURCE = projectResource.class.getName();

    //@Autowired
    //AdministrativeDomainDao administrativeDomainDao;

    private static Logger LOGGER = LoggerFactory.getLogger(CreateSTProjectProcessor.class);

    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {

        try {
            PIPrincipal principal = SessionHelper.service.getPrincipal();
            PIResource resource = PIResourceHelper.service.getResouceForUser((PIUser) principal);
            if (resource == null) {
                String error = PIMessage.getLocalizedMessage(RESOURCE, "RESOURCE_NOT_EXIST", null, null);
                throw new PIException(error);
//                return new ResponseWrapper<>(ResponseWrapper.FAILED, error, null);
            }

            //List<ExchangeContainer> exs = PIContainerHelper.service.getAllExchangeContainers();
            //ExchangeContainer topContainer = exs.get(0);
            //ExchangeContainer topContainer = PIContainerHelper.service.getExchangeContainerByName("Tundra Default Site");
            //获取到OrgContainer
            //List<OrgContainer> orgs = PIContainerHelper.service.getOrgContainers(topContainer);
            //OrgContainer orgContainer = PIContainerHelper.service.getOrgContainer(topContainer, "PISX");
            //OrgContainer orgContainer = orgs.get(0);

            //get user organization
            PIOrganization organization = ((PIUser) principal).getOrganization();
            OrgContainer orgContainer = PIContainerHelper.service.getOrgContainer(organization);
            if (list.size() > 0) {
                PIProject project = (PIProject) list.get(0);
                Ownership ownership = Ownership.newOwnership(principal);
                project.setOwnership(ownership);
                PIContainerRef topContainerRef = PIContainerHelper.service.getTopContainer(orgContainer);
                LcState lcState = LifeCycleHelper.service.getLcState("INWORK", topContainerRef);
                LifeCycleState state = LifeCycleState.newLifeCycleState(lcState);
                project.setState(state);
                //project.setPlanStartDate(Timestamp.valueOf("2020-03-01 12:00:00"));
                //project.setPlanEndDate(new Timestamp(System.currentTimeMillis()));
                //project.setScheduledEndDate(new Timestamp(System.currentTimeMillis()));


                project.setIsTemplate(false);
                project.setProjectAbbreviation(project.getProjectAbbreviation().trim());

                //check project number and name and calendar
                checkProjectBaseInfo(params.getNfCommandBean(), project);

                Map<String,Object> textMap = params.getNfCommandBean().getLayoutFields();
                //是否创建createContainer 复选框
                // String createContainerValue = getTextValue(params, "isCreateContainer");
                Boolean bool = (Boolean) textMap.get("isCreateContainer");
                //todo 重汽项目 默认全部创建项目容器
                bool = true;

                String createContainerValue = "";
                if (bool){
                    createContainerValue = "true";
                }
                //HashMap<String,Object> combMap=nmcommandbean.getComboBox();
                //HashMap<String,String> textMap= nmcommandbean.getText();
                //String isCreateContainer= nmcommandbean.getTextParameter("isCreateContainer");
                //LOGGER.debug(" ### Create project isCreateContainer: "+isCreateContainer);

                //check plan date
                checkPlanDate(textMap);

                //get project calendar
                //ArrayList<String> calendarList=(ArrayList<String>)combMap.get(PIProject.CALENDAR_REFERENCE);
                //String calendarOid=calendarList.get(0);
                //PICalendar calendar=(PICalendar)CommonUtils.getPIObjectByOid(calendarOid);
                //project.setCalendar(calendar);

                //get project template
                //PIProject tempProject=null;
                //WTContainer projectContainer=null;
                //ArrayList<String> templateList=(ArrayList<String>)combMap.get(PIProject.PROJECT_TEMPLATE_REFERENCE);
                //if(templateList!=null&&templateList.size()>0){
                //    String templateOid=(String)templateList.get(0);
                //    if(templateOid!=null&&!"".equals(templateOid)){
                //        tempProject=(PIProject)CommonUtils.getPIObjectByOid(templateOid);
                //        projectContainer=tempProject.getContainer();
                //    }
                //}

                //get project group
                //ArrayList<String> groupList=(ArrayList<String>)combMap.get(PIProject.PROJECT_GROUP_REFERENCE);
                //if(groupList!=null&&groupList.size()>0){
                //    String groupOid=(String)groupList.get(0);
                //    WTGroup group=(WTGroup)CommonUtils.getPIObjectByOid(groupOid);

                //
                //    PIObs obs=PIResourceHelper.service.getOBSOfOrgGroup(group);
                //    if(obs!=null)
                //        project.setObs(obs);
                //}

                //get project type
                JSONObject jsonObject = (JSONObject) textMap.get("createProjectType");
                String type= jsonObject.getString("value");
                //String type=CommonConstants.TYPE_PIPROJECT;
                //ArrayList<String> typeList=(ArrayList<String>)combMap.get("createProjectType");
                //if(typeList!=null&&typeList.size()>0){
                //    type=(String)typeList.get(0);
                //}
                //TypeIdentifier identifier=TypedUtilityServiceHelper.service.getTypeIdentifier(type);
                //TypeDefinitionReference typeDef=ClientTypedUtility.
                //        getTypeDefinitionReference(identifier.getTypename());
                //project.setTypeDefinitionReference(typeDef);
                LTDTypeDefinition optionalLTDTypeDefinition = TypeHelper.service.findType(type);
                if(optionalLTDTypeDefinition!=null){
                    project.setLTDTypeDefinitionReference(TypeDefinitionReference.newTypeDefinitionReference((optionalLTDTypeDefinition)));
                }
                project.setCreator(PIPrincipalReference.newPIPrincipalReference(principal));
                project.setModifier(PIPrincipalReference.newPIPrincipalReference(principal));

                //Locale locale = SessionHelper.service.getLocale();
                //PIContainerRef orgRef = PIContainerRef.newPIContainerRef(orgContainer);
                //AdministrativeDomain orgDefaultDomain = administrativeDomainDao.findByNameAndContainerReference(AdministrativeDomainHelper.DEFAULT_DOMAIN, orgRef).get(0);
                //AdministrativeDomain orgPrivateDomain = administrativeDomainDao.findByNameAndContainerReference(AdministrativeDomainHelper.PRIVATE_DOMAIN, orgRef).get(0);
                //AdminDomainRef orgDefaultDomainRef = AdminDomainRef.newAdminDomainRef(orgDefaultDomain);
                //AdminDomainRef orgPrivateDomainRef = AdminDomainRef.newAdminDomainRef(orgPrivateDomain);
                //AdministrativeDomain domain = null;
                //List lists1 = administrativeDomainDao.findByNameAndContainerReferenceAndDomainRef("PIProject", orgRef, orgDefaultDomainRef);
                //if (lists1 != null && lists1.size() > 0) {
                //    domain = (AdministrativeDomain) lists1.get(0);
                //}
                //if (domain == null) {
                //    throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
                //            "PIPROJECT_DOMAIN_IS_NOT_EXIST", null, locale));
                //}
                //AdministrativeDomain privateDomain = null;
                //List lists2 = administrativeDomainDao.findByNameAndContainerReferenceAndDomainRef("PIProject", orgRef, orgPrivateDomainRef);
                //if (lists2 != null && lists2.size() > 0) {
                //    privateDomain = (AdministrativeDomain) lists2.get(0);
                //}
                //if (privateDomain == null) {
                //    throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
                //            "PIPROJECT_PRIVATE_DOMAIN_IS_NOT_EXIST", null, locale));
                //}
                //get org domain
                Hashtable<String, AdministrativeDomain> table = new CommonUtils().getPIProjectDomain(orgContainer);
                AdministrativeDomain domain = table.get(CommonConstants.CONS_DOMAIN_PIPROJECT_DEFAULT);
                AdministrativeDomain privateDomain = table.get(CommonConstants.CONS_DOMAIN_PIPROJECT_PRIVATE);
                AdminDomainRef domainRef = AdminDomainRef.newAdminDomainRef(domain);

                //create project container
                PIContainerRef ref = null;
                PIProject tempProject = project.getProjectTemplate();
                PIContainer projectContainer =null;
                if(tempProject!=null){
                    projectContainer = tempProject.getContainer();
                }
                if ("true".equals(createContainerValue)) { // 创建项目容器
                    //add by myang
                    project.setContainer(orgContainer);
                    //end by myang
                    PIProjectContainer targetContainer = null;
                    if (projectContainer instanceof PIProjectContainer) {
                        //new target container
                        targetContainer = createProjectContainer(project, projectContainer);
                        project.setContainer(targetContainer);


                        //create team and folder and domain
					ProjectCopyWorklist workList=new ProjectCopyWorklist();
					workList.setDoFolderStructure(true);
					workList.setDoPolicyRules(true);
					workList.setDoTeamMembers(true);
					workList.setDoTeamRoles(true);
					workList.setDoProjectPlan(false);
					AbstractWorklistDrivenCopier copier =new AbstractWorklistDrivenCopier();
					PIContainerRef sourceContainer=PIContainerRef.newPIContainerRef(projectContainer);
					copier.initializeScript(sourceContainer,"Default");
					ref= PIContainerRef.newPIContainerRef(targetContainer);
					copier.copyObjects(sourceContainer,ref,workList);

					//remove template project manager user
					//ContainerTeamManaged teamManaged=(ContainerTeamManaged)targetContainer;
					//ContainerTeam containerTeam= ContainerTeamHelper.service.getContainerTeam(teamManaged);
					//PIRole role= PIContainerHelper.getAdminRole(targetContainer.getClass());
					//ArrayList arrayList=containerTeam.getAllPrincipalsForTarget(role);
					//if(arrayList.size()>1){
					//	for (int i = 0; i < arrayList.size(); i++) {
					//		PIPrincipalReference principalReference = ((PIPrincipalReference) arrayList.get(i));
					//		PIPrincipal prin=principalReference.getPrincipal();
					//		if(prin!=principal){
					//			containerTeam.deletePrincipalTarget(role, principalReference.getPrincipal());
					//		}
					//	}
					//}

                    } else if (projectContainer == null) {
                        targetContainer = createProjectContainer(project, orgContainer);
                        ref = PIContainerRef.newPIContainerRef(targetContainer);
                        project.setContainerReference(ref);
                    } else {
                        throw new PIException(PIMessage.getLocalizedMessage(RESOURCE, "CREATE_PROJECTCONTAINER_ERROR", null, null));
                    }

                } else {
                    ref = PIContainerRef.newPIContainerRef(orgContainer);
                    project.setContainerReference(ref);
                }

                if (ref.getObject().getContainer() instanceof PIProjectContainer) {
                    PIProjectContainer container = (PIProjectContainer) ref.getObject().getContainer();
                    //todo：change domain
                    new CommonUtils().changePIProjectContainerDomain(container, domain, privateDomain);
				    AdministrativeDomain domain1= AdministrativeDomainHelper.service.getDomain(CommonConstants.CONS_DOMAIN_DEFAULT, ref);
                    //AdministrativeDomain domain1= administrativeDomainDao.findByNameAndContainerReference("Default", ref).get(0);
				    domainRef=AdminDomainRef.newAdminDomainRef(domain1);
                }

                project.setDomainRef(domainRef);
                project.setCheckoutFlag(false);
                project = (PIProject) PersistenceHelper.service.save(project);
                if (tempProject != null) {
                    project = (PIProject) PersistenceHelper.service.save(project);
					ExpImpForPIProjectHandler.createRoleProjectProfile(tempProject, project);
                    PmgtAccessHelper.service.createAdminGroupRoleProfiles(project);
                }
                if (project.getContainer() instanceof PIProjectContainer) {
                    PIProjectContainer pjcontianer = (PIProjectContainer) project.getContainer();
                    ExpImpForPIProjectHandler.createResourceProjectLink(project, pjcontianer);
                }

                if (resource != null) {
                    boolean flag1 = PIResourceHelper.service.checkResourceInProject(project, resource);
                    if (!flag1) {
                        PIResourceProjectLink link = PIResourceProjectLink.
                                newPIResourceProjectLink(resource, project);
                        PersistenceHelper.service.save(link);
                    }
                }

                //create project plan and deliverable
                if (tempProject != null) {
                    PlanCopyHelper planCoper = new PlanCopyHelper();
                    planCoper.setSourceContainer(tempProject.getContainerReference());
                    planCoper.setTargetContainer(project.getContainerReference());
                    planCoper.setTargetProject(project);
                    planCoper.copyAddedObjects();
                }

                //Calculate project schedule time
                PIPlanHelper.service.calculateProjectScheduleTime(project);

                //calculate resource assignments
                PIAssignmentHelper.service.calculateAssignmentScheduleTime(project);
                //检查plan的目标完成时间是否在项目的必须完成时间之前,项目模板不做检查
                Boolean isTemplate = project.getIsTemplate();
                if (!isTemplate) {
                    List<PIPlan> qResult = PIPlanHelper.service.getPlans(project);
                    if (qResult.size() > 0) {
                        for (PIPlan object : qResult) {
                            if (object instanceof PIPlan) {
                                PIPlan piPlan = (PIPlan) object;
                                PIPlanHelper.service.checkPlanFinishDate(piPlan);
                            }
                        }
                    }
                }
            } else {
                throw new PIException(" get project object error,refresh the page and try again");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PIException(e.toString());
            //new ResponseWrapper<>(ResponseWrapper.FAILED, e.getLocalizedMessage(), null);
        }

        return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, "创建项目成功", null);
    }

    private String getTextValue(ComponentParams params, String textId) {
        JSONObject ajaxData = params.getAjaxData();
        Object element = null;
        for (Object value : ajaxData.values()) {
            JSONObject step = (JSONObject) value;
            for (Object o : step.values()) {
                JSONObject componentData = (JSONObject) o;
                Collection<Object> collection = componentData.values();
                Iterator iterator = collection.iterator();
                while (iterator.hasNext()){
                    JSONObject jsonObject = (JSONObject) iterator.next();
                    Map<String, Object> fieldMeta = jsonObject.getJSONObject(WebUIData.FIELD_META);
                    for (Map.Entry<String, Object> entry : fieldMeta.entrySet()) {
                        String key = entry.getKey();
                        if (key.equals(textId)) {
                            element = entry.getValue();
                            if (element != null) {
                                Boolean element1 = (Boolean) element;
                                if (element1){
                                    return "true";
                                }else {
                                    return "false";
                                }
                            }else {
                                return "false";
                            }
                        }
                    }
                }

            }
        }
        return null;
    }

    public PIProjectContainer createProjectContainer(PIProject project, PIContainer container) throws PIException, PIPropertyVetoException {
        PIProjectContainer targetContainer = null;
        //try {
        //    targetContainer = PIProjectContainer.newPIProjectContainer(project.getProjectName(), container);
        //} catch (PIPropertyVetoException e) {
        //    e.printStackTrace();
        //}
        //targetContainer.setIsTemplate(false);
        //PIPrincipal principal = SessionHelper.service.getPrincipal();
        //targetContainer.setCreator(principal);
        //PIContainerRef orgContainerRef = project.getContainerReference();
        //AdministrativeDomain orgDefaultDomain = AdministrativeDomainHelper.service.getDomain(CommonConstants.CONS_DOMAIN_DEFAULT, orgContainerRef);
        //targetContainer.setDomainRef(AdminDomainRef.newAdminDomainRef(orgDefaultDomain));
        //if (container instanceof PIProjectContainer) {
        //    targetContainer.setContainer(container.getContainer());
        //}
        targetContainer = PIProjectHelper.service.createProjectContainer(project);
        //targetContainer = PersistenceHelper.service.save(targetContainer);
        return targetContainer;
    }

    public String checkProjectBaseInfo(NfCommandBean nmcommandbean, PIProject project) throws PIException {
        Locale locale = SessionHelper.service.getLocale();

		String restricte = ProjectIXUtils.getRestrictedCharacterListInContainerName();
		//check name value
        String name = project.getProjectName().trim();
		if(!ProjectIXUtils.isValidContainerName(name)){
			String [] message = new String[] {name, restricte };
            throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
                    "NAME_ILLEGAL_CHARACTERS_MESSAGE", message, locale));
		}

        //Verify that the name is unique
        PIProject existProject = PIProjectHelper.service.getProjectByName(name);
        if (existProject != null) {
            throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
                    "PROIJECT_NAME_ERROR", null, locale));
        }

        //check number value
        String number = project.getProjectShortName();
		if(number!=null&&number.trim().length()>0){
			if(!ProjectIXUtils.isValidContainerName(number)){
				String [] message = new String[] {number, restricte };
				throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
						"SHORTNAME_ILLEGAL_CHARACTERS_MESSAGE", message, locale));
			}
			//check number uniqueness
			PIProject oldProject=PIProjectHelper.service.getProjectByShortName(number.trim());
			if(oldProject!=null){
				String [] message = new String[] {number};
				throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
						"SHORTNAME_ERROR", message, locale));
			}
		}

        //check project calendar
//		HashMap<String,Object> combMap=nmcommandbean.getComboBox();
//		ArrayList<String> calendarList=(ArrayList<String>)combMap.get(PIProject.CALENDAR_REFERENCE);
//		if(calendarList==null||calendarList.size()<=0){
//			throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
//					"NOT_CHOICE_PROJECT_CALENDAR_ERROR",null, locale));
//		}
        return name;
    }

    public static void checkPlanDate(Map<String,Object> textMap) throws PIException {
        String startDate = null;
        String endDate = null;
        String latestDate = null;
        String foreCastDate = null;
        Iterator<String> iter = textMap.keySet().iterator();
		while(iter.hasNext()){
			String key=iter.next();
			if(key.contains(PIProject.PLAN_START_DATE)){
				startDate= (String) textMap.get(key);
			}else if(key.contains(PIProject.PLAN_END_DATE)){
				endDate= (String) textMap.get(key);
			}else if(key.contains(PIProject.SCHEDULED_END_DATE)){
				latestDate= (String) textMap.get(key);
			}else if(key.contains(PIProject.FORECAST_START_DATE)){
				foreCastDate= (String) textMap.get(key);
			}else{

			}
		}
        LOGGER.debug("  ### project startDate: " + startDate + "  endDate: " + endDate + "  latestDate: " + latestDate);

        Locale locale = SessionHelper.service.getLocale();
        if (startDate == null) {
            throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
                    "STARTDATE_NOT_NULL", null, locale));
        }
        if (endDate == null) {
            throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
                    "ENDDATE_NOT_NULL", null, locale));
        }
        if (latestDate == null) {
            throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
                    "SCHEDULEDENDDATE_NOT_NULL", null, locale));
        }

        if (startDate.compareTo(endDate) > 0) {
            String[] message = new String[]{endDate, startDate};
            throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
                    "ENDDAATE_GREATER_THAN_STARTEND", message, locale));
        }

        if (latestDate.compareTo(endDate) < 0) {
            String[] message = new String[]{latestDate, endDate};
            throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
                    "LATESTDATE_GREATER_THAN_ENDDATE", message, locale));
        }

        if (foreCastDate != null) {
            if (foreCastDate.compareTo(endDate) > 0) {
                String[] message = new String[]{foreCastDate, endDate};
                throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
                        "FORCATS_DATA_TOO_EARLY_TARGET_ENDDATE", message, locale));
            }

            if (foreCastDate.compareTo(latestDate) > 0) {
                String[] message = new String[]{foreCastDate, latestDate};
                throw new PIException(PIMessage.getLocalizedMessage(RESOURCE,
                        "FORCATS_DATA_TOO_EARLY_MUST_ENDDATE", message, locale));
            }
        }
    }

}