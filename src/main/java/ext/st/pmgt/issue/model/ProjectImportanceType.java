package ext.st.pmgt.issue.model;

import com.pisx.tundra.foundation.fc.model.EnumeratedType;
import javax.persistence.*;
import java.util.Hashtable;
import java.util.Locale;


@Embeddable
@AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "importanceType", nullable = false))
})
public class ProjectImportanceType extends EnumeratedType {

    static final long serialVersionUID = 1L;
    static final String CLASS_RESOURCE = ProjectImportanceTypeRB.class.getName();

    @Transient
    static Hashtable localeSets;
    @Transient
    private static volatile EnumeratedType[] valueSet;

    public static final ProjectImportanceType IMPORTANT = toProjectImportanceType("important");
    public static final ProjectImportanceType ORDINARY = toProjectImportanceType("ordinary");

    static EnumeratedType[] _valueSet() {
        if (valueSet == null) {
            synchronized (ProjectImportanceType.class) {
                try {
                    if (valueSet == null) {
                        valueSet = initializeLocaleSet(null);
                    }
                } catch (Throwable t) {
                    throw new ExceptionInInitializerError(t);
                }
            }
        }
        return valueSet;
    }


    public static ProjectImportanceType newProjectImportanceType(int secretHandshake) throws IllegalAccessException {
        validateFriendship(secretHandshake);
        return new ProjectImportanceType();
    }

    public static ProjectImportanceType toProjectImportanceType(String internal_value){
        if (valueSet == null) {
            try {
                valueSet = initializeLocaleSet(null);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return (ProjectImportanceType) toEnumeratedType(internal_value, _valueSet());
    }



    public static ProjectImportanceType getProjectImportanceTypeDefault() {
        return (ProjectImportanceType) defaultEnumeratedType(_valueSet());
    }

    public static ProjectImportanceType[] getProjectImportanceTypeSet() {
        ProjectImportanceType[] set = new ProjectImportanceType[_valueSet().length];
        System.arraycopy(valueSet, 0, set, 0, valueSet.length);
        return set;
    }


    public static EnumeratedType[] getProjectImportanceTypeSet(Locale locale) {
        EnumeratedType[] enumeratedTypes = null;
        if (ProjectImportanceType.localeSets == null) {
            ProjectImportanceType.localeSets = new Hashtable<>();
        } else {
            enumeratedTypes = (EnumeratedType[]) ProjectImportanceType.localeSets.get(locale);
        }

        if (enumeratedTypes == null) {
            try {
                enumeratedTypes = ProjectImportanceType.initializeLocaleSet(locale);
            } catch (Throwable ignored) {
            }

            if (enumeratedTypes != null) {
                ProjectImportanceType.localeSets.put(locale, enumeratedTypes);
            }
        }
        return enumeratedTypes;
    }



    @Override
    public EnumeratedType[] getValueSet() {
        return getProjectImportanceTypeSet();
    }

    @Override
    protected EnumeratedType[] valueSet() {
        return _valueSet();
    }

    @Override
    public EnumeratedType[] getLocaleSet(Locale locale) {
        EnumeratedType[] request = null;
        if (localeSets == null) {
            localeSets = new Hashtable();
        } else {
            request = (EnumeratedType[]) localeSets.get(locale);
        }

        if (request == null) {
            try {
                request = initializeLocaleSet(locale);
            } catch (Throwable var4) {
                ;
            }

            localeSets.put(locale, request);
        }

        return request;
    }


    static EnumeratedType[] initializeLocaleSet(Locale locale) throws Throwable {
        return instantiateSet(ProjectImportanceType.class.getMethod("newProjectImportanceType", Integer.TYPE), CLASS_RESOURCE, locale);
    }
}
