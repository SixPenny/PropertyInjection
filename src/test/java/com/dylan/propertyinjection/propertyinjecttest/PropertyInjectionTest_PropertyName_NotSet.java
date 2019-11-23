package com.dylan.propertyinjection.propertyinjecttest;

import com.dylan.propertyinjection.PropertyInjection;
import com.dylan.propertyinjection.annotate.SourcePropertyName;
import com.dylan.propertyinjection.exception.PropertyInjectionException;
import org.junit.Test;

public class PropertyInjectionTest_PropertyName_NotSet {
    private PropertyInjection propertyInjection = new PropertyInjection();

    @Test(expected = PropertyInjectionException.class)
    public void testInject_PropertyName_NotSet() {
        SourceClass sourceClass = new SourceClass();
        TargetClass targetClass = new TargetClass();
        propertyInjection.inject(sourceClass, targetClass);
    }

    public static class SourceClass {
        private String name;
        private long id;
    }

    public static class TargetClass {
        @SourcePropertyName
        private String aa;
        private long bb;
    }
}
