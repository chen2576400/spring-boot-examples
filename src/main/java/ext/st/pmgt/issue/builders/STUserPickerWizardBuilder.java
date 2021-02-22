package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.resource.model.PIResource;
import com.pisx.tundra.pmgt.resource.model.PIResourceProjectLink;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectMeasures;
import ext.st.pmgt.issue.model.STProjectRisk;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author: Mr.Chen
 * @create: 2021-01-25 16:36
 **/
public class STUserPickerWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        PIProject piProject = null;
        if (sourceObject instanceof STProjectRisk) {
            piProject = ((STProjectRisk) sourceObject).getProject();
        } else if (sourceObject instanceof STProjectMeasures) {
            piProject = ((STProjectMeasures) sourceObject).getProject();
        }else if (sourceObject instanceof PIProject){
            piProject=((PIProject) sourceObject);
        }else if (sourceObject instanceof STProjectIssue){
            piProject=((STProjectIssue)sourceObject).getProject();
        }
        Collection qr = PersistenceHelper.service.navigate(piProject, "roleA", PIResourceProjectLink.class, true);
        if (!CollectionUtils.isEmpty(qr)) {
            List<PIUser> projectResourceUser = getProjectResourceUser(qr);
            return projectResourceUser;
        }
        return null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("projectManagerUserTableWizard");
        wizardConfig.setTitle("负责人");
        StepConfig stepConfig = wizardConfig.newStep();
        stepConfig.children(getTableConfig(componentData, params, componentConfigFactory));
        return wizardConfig;

    }


    private TableConfig getTableConfig(Object componentData, ComponentParams params, ComponentConfigFactory componentConfigFactory) {
        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setId("projectManagerUserTable");
        tableConfig.setTableTitle("项目成员");
        tableConfig.setEntities(componentData);
        tableConfig.enableSearch();
        tableConfig.enableSelect();
        tableConfig.disablePaginate();
        tableConfig.setSingleSelect(true);//单选
        tableConfig.setPrimaryObjectType(PIUser.class);

        ColumnConfig column1 = componentConfigFactory.newColumnConfig();
        column1.setName("fullName");
        column1.enableSort();
        tableConfig.addColumn(column1);

        ColumnConfig column2 = componentConfigFactory.newColumnConfig();
        column2.setName("name");
        column2.enableSort();
        tableConfig.addColumn(column2);

        ColumnConfig column3 = componentConfigFactory.newColumnConfig();
        column3.setName("email");
        column3.enableSort();
        tableConfig.addColumn(column3);

        return tableConfig;

    }


    private List<PIUser> getProjectResourceUser(Collection collection) {
//        List<Persistable> persistables = ((List<PIResource>) collection)
//                .stream().map(piResource -> piResource.getUserReference().getObject())
//                .collect(Collectors.toList());
//        if (CollectionUtils.isNotEmpty(persistables)) {
//            List<PIUser> piUsers=new ArrayList<>(Arrays.asList(persistables.toArray(new PIUser[0])));
//            return piUsers;
//        }
//        return null;
        List<PIUser> piUsers = ((List<PIResource>) collection)
                .stream().map(piResource -> piResource.getUser())
                .collect(Collectors.toList())
                .stream()
                .filter(distinctByKey(PIUser::getName)).collect(Collectors.toList());
        return piUsers;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
