package com.dylan.propertyinjection.propertyinjecttest;

import com.dylan.propertyinjection.PropertyInjection;
import com.dylan.propertyinjection.annotate.SourcePropertyName;
import org.junit.Assert;
import org.junit.Test;

public class PropertyInjectionTest_HappyCase {
    private static final String TEST_NAME = "testName";
    private static final long TEST_ID = 34345;
    private PropertyInjection propertyInjection = new PropertyInjection();

    @Test
    public void testInject_HappyCase() {
        SourceClass sourceClass = new SourceClass();
        sourceClass.setName(TEST_NAME);
        sourceClass.setId(TEST_ID);
        TargetClass targetClass = new TargetClass();
        propertyInjection.inject(sourceClass, targetClass);

        Assert.assertEquals(sourceClass.getName(), targetClass.getAa());
        Assert.assertEquals(sourceClass.getId(), targetClass.getId());
        Assert.assertNull(targetClass.getOther());
    }

    public static class SourceClass {
        private String name;
        private String other;
        private long id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }
    }

    public static class TargetClass {
        @SourcePropertyName(value = "name")
        private String aa;
        private String other;
        private long id;

        public String getAa() {
            return aa;
        }

        public void setAa(String aa) {
            this.aa = aa;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }
    }
}
