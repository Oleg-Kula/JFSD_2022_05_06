package com.gmail.kulacholeg.task_02.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormatProperty {
    String format() default "dd.MM.yyyy HH:mm";
}
