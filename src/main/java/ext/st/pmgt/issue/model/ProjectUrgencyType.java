package ext.st.pmgt.issue.model;

import com.pisx.tundra.foundation.fc.model.EnumeratedType;

import javax.persistence.*;
import java.util.Hashtable;
import java.util.Locale;

@Embeddable
@AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "urgencyType", nullable = false))
})
public class ProjectUrgencyType extends EnumeratedType {

    static final long serialVersionUID = 1L;
    static final String CLASS_RESOURCE = ProjectUrgencyTypeRB.class.getName();

    @Transient
    static Hashtable localeSets;
    @Transient
    private static volatile EnumeratedType[] valueSet;

    public static final ProjectUrgencyType IMPORTANT = toProjectUrgencyType("urgent");
    public static final ProjectUrgencyType ORDINARY = toProjectUrgencyType("ordinary");


    static EnumeratedType[] _valueSet() {
        if (valueSet == null) {
            synchronized (ProjectUrgencyType.class) {
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


    public static ProjectUrgencyType newProjectUrgencyType(int secretHandshake) throws IllegalAccessException {
        validateFriendship(secretHandshake);
        return new ProjectUrgencyType();
    }

    public static ProjectUrgencyType toProjectUrgencyType(String internal_value){
        if (valueSet == null) {
            try {
                valueSet = initializeLocaleSet(null);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return (ProjectUrgencyType) toEnumeratedType(internal_value, _valueSet());
    }


    public static ProjectUrgencyType getProjectUrgencyTypeDefault() {
        return (ProjectUrgencyType) defaultEnumeratedType(_valueSet());
    }


    public static ProjectUrgencyType[] getProjectUrgencyTypeSet() {
        ProjectUrgencyType[] set = new ProjectUrgencyType[_valueSet().length];
        System.arraycopy(valueSet, 0, set, 0, valueSet.length);
        return set;
    }

    public static EnumeratedType[] getProjectUrgencyTypeSet(Locale locale) {
        EnumeratedType[] enumeratedTypes = null;
        if (ProjectUrgencyType.localeSets == null) {
            ProjectUrgencyType.localeSets = new Hashtable<>();
        } else {
            enumeratedTypes = (EnumeratedType[]) ProjectUrgencyType.localeSets.get(locale);
        }

        if (enumeratedTypes == null) {
            try {
                enumeratedTypes = ProjectUrgencyType.initializeLocaleSet(locale);
            } catch (Throwable ignored) {
            }

            if (enumeratedTypes != null) {
                ProjectUrgencyType.localeSets.put(locale, enumeratedTypes);
            }
        }
        return enumeratedTypes;
    }


    @Override
    public EnumeratedType[] getValueSet() {
        return getProjectUrgencyTypeSet();
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
        return instantiateSet(ProjectUrgencyType.class.getMethod("newProjectUrgencyType", Integer.TYPE), CLASS_RESOURCE, locale);
    }
}
