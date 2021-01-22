package ext.st.pmgt.indicator.processors;

import com.pisx.tundra.foundation.doc.model.PIDocument;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.PIObject;
import com.pisx.tundra.foundation.meta.type.TypeHelper;
import com.pisx.tundra.foundation.meta.type.model.LTDTypeDefinition;
import com.pisx.tundra.foundation.org.model.PIPrincipalReference;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultCreateFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.assignment.model.PIResourceAssignment;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverableLink;
import com.pisx.tundra.pmgt.deliverable.model.deliverableResource;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CreateDeliverableProcessor extends DefaultCreateFormProcessor {

    private static final String RESOURCE = deliverableResource.class.getName();

    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        Object object=params.getNfCommandBean().getSourceObject();
        PIPlanActivity act=null;
        if(object instanceof PIPlanActivity){
            act=(PIPlanActivity)object;
        }else if(object instanceof PIResourceAssignment){
            PIResourceAssignment assignment=(PIResourceAssignment)object;
            act=(PIPlanActivity) assignment.getPlannable();
        }else{
            throw new PIException(" primary object type must be PIPlanActivity or PIResourceAssignment");
        }
        if (list.size()>0){
            PIPlanDeliverable deliverable = (PIPlanDeliverable) list.get(0);
            Map<String,Object> combMap=params.getNfCommandBean().getLayoutFields();

//            String subjectOid = (String) combMap.get("subjectReference");
//            String  typeName=(String)textbMap.get(CommonConstants.CONS_PICKER_TYPE_HIDDEN_ID);
//            ArrayList<String> template=(ArrayList<String>)combMap.get("deliverableTemplateReference");
            PIObject subObject=null;
//            if(subjectOid!=null&&subjectOid.length()>0) {
//                NfOid groupOid = NfOid.parse(subjectOid);
//                subObject =groupOid!=null? PIPlanDeliverableHelper.service.getPlanDeliverablesById(groupOid.getId()) : null;
//            }
            try {
//                deliverable.setSubject(subObject);

                // set deliverable type
//                if(typeName!=null&&typeName.trim().length()>0){
//                    TypeIdentifier ti=TypedUtility.getTypeIdentifier(typeName);
//                    TypeDefinitionReadView tv = TypeDefinitionServiceHelper.service.getTypeDefView(ti);
//                    ObjectIdentifier oid= tv.getOid();
//                    ReferenceFactory rf= new ReferenceFactory();
//                    deliverable.setDeliverableTypeReference((ObjectReference)rf.getReference(oid.toString()));
//                }

                //set template
//                if(template!=null&&template.size()>0){
//                    String templateOid=(String)template.get(0);
//                    if(templateOid!=null&&templateOid.trim().length()>0){
//                        PIObject tempObject= CommonUtils.getPIObjectByOid(templateOid);
//                        deliverable.setDeliverableTemplate(tempObject);
//                    }
//                }
                PIPrincipalReference principalReference = PIPrincipalReference.newPIPrincipalReference(SessionHelper.service.getPrincipal());
                deliverable.setProject(act.getProject());
                deliverable.setParent(act);
                deliverable.setRoot(act.getRoot());
                deliverable.setCreator(principalReference);
                deliverable.setModifier(principalReference);

                //todo 重汽暂时交付物类型都为文档类型
                LTDTypeDefinition ltdTypeDefinition = TypeHelper.service.findType(PIDocument.class.getName());
                deliverable.setDeliverableTypeReference(ObjectReference.newObjectReference(ltdTypeDefinition));


                //save deliverable
                deliverable= PersistenceHelper.service.save(deliverable);

                //create  deliverable link
                PIPlanDeliverableLink link= PIPlanDeliverableLink.newPIPlanDeliverableLink(act, deliverable);
                link= PersistenceHelper.service.save(link);

                //set modifier
                act.setModifier(principalReference);

                //set boolean value of plan activity
                act.setHasDeliverable(Boolean.TRUE);
                act= PersistenceHelper.service.save(act);
            } catch (PIException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new PIException(e);
            }
        }else{
            throw new PIException(" get deliverable object error,refresh the page and try again");
        }

        return new ResponseWrapper(ResponseWrapper.REGIONAL_FLUSH, "", null);
    }
}
