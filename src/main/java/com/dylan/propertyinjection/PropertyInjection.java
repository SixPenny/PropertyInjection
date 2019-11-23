package com.dylan.propertyinjection;

import com.dylan.propertyinjection.annotate.SourcePropertyName;
import com.dylan.propertyinjection.exception.PropertyInjectionException;
import com.dylan.propertyinjection.utils.Strings;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Inject Property from a bean into another
 * <p>
 * So many times we have to copy the value from one class into another.
 * Transform a database entity into DTO.
 * Transform a service object into a web object.
 * <p>
 * Use this class to specify how you wanna inject your property.
 * Let it do this job for you.
 */
public class PropertyInjection {
    private static final String SOURCE_PROPERTY_NAME_NOT_SPECIFIED_EXCEPTION_MSG =
            "value of @SourcePropertyName cannot be empty";
    private static final String TYPE_NOT_MATCH_EXCEPTION_MSG = "source[type=%s] and target[type=%s] type not match";
    private static final String FIELD_ACCESS_EXCEPTION_MSG = "field %s access denied";

    public void inject(Object source, Object target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("source or target is null");
        }

        // todo inject parent class field value into target
        Field[] sourceFields = source.getClass().getDeclaredFields();

        Field[] targetFields = target.getClass().getDeclaredFields();

        //todo type convert
        //now only accept same type injection
        Map<String, Field> sourceFieldMap = new HashMap<>();
        for (Field field : sourceFields) {
            sourceFieldMap.put(field.getName(), field);
        }

        for (Field field : targetFields) {
            String name = field.getName();
            //todo move to a handler
            if (field.isAnnotationPresent(SourcePropertyName.class)) {
                name = field.getAnnotation(SourcePropertyName.class).value();

                if (Strings.isEmpty(name)) {
                    throw new PropertyInjectionException(SOURCE_PROPERTY_NAME_NOT_SPECIFIED_EXCEPTION_MSG);
                }
            }

            Field sourceField = sourceFieldMap.get(name);
            //todo need a policy
            if (sourceField == null) {
                continue;
            }

            sourceField.setAccessible(true);
            if (!field.getType().isAssignableFrom(sourceField.getType())) {
                throw new PropertyInjectionException(String.format(TYPE_NOT_MATCH_EXCEPTION_MSG,
                        sourceField.getType().getCanonicalName(),
                        field.getType().getCanonicalName()));
            }

            field.setAccessible(true);
            try {
                field.set(target, sourceField.get(source));
            } catch (Exception e) {
                throw new PropertyInjectionException(String.format(FIELD_ACCESS_EXCEPTION_MSG,
                        field.getDeclaringClass().getCanonicalName()));
            }
        }
    }
}
