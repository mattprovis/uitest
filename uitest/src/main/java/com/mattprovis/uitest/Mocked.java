package com.mattprovis.uitest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks fields in a UI Test class (ie. a subclass of AbstractUITestBase) that are to be populated
 * with mocks from the server's context.
 * These mocks must be defined in an instance of MocksDefinition, as either beans or POJO instances reachable from a mocked bean.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Mocked {
}
