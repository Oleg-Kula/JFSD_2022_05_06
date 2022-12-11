package com.gmail.kulacholeg.task_02.util;

import com.gmail.kulacholeg.task_02.entity.EntityClass;
import com.gmail.kulacholeg.task_02.annotation.FormatProperty;
import com.gmail.kulacholeg.task_02.annotation.NameProperty;
import com.gmail.kulacholeg.task_02.exception.NoSetterException;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class UtilClass {

    public static EntityClass loadFromProperties(Class<EntityClass> cls, Path path) {
        //get properties from file
        Properties classProperties = new Properties();
        try {
            classProperties.load(new FileInputStream(String.valueOf(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //create variables from properties file
        String stringProperty = classProperties.getProperty("stringProperty");
        int numberProperty = Integer.parseInt(classProperties.getProperty("numberProperty"));
        String stringDate = classProperties.getProperty("timeProperty");
        String pattern = "";
        //get date&time pattern from annotation
        for (Field field : cls.getDeclaredFields()) {
            if (field.isAnnotationPresent(FormatProperty.class)) {
                pattern = field.getAnnotation(FormatProperty.class).format();
            }
        }
        LocalDateTime localDateTime = LocalDateTime.parse(stringDate, DateTimeFormatter.ofPattern(pattern));
        Instant timeProperty = localDateTime.toInstant(OffsetDateTime.now().getOffset());

        //create instance and set fields
        try {
            String fieldName = "";
            EntityClass entityClass = cls.getDeclaredConstructor().newInstance();
            Field[] fields = entityClass.getClass().getDeclaredFields();
            for (Field field : fields) {
                //if field has annotation use it instead of field name
                if (field.isAnnotationPresent(NameProperty.class)) {
                    fieldName = field.getAnnotation(NameProperty.class).name();
                } else {
                    fieldName = field.getName();
                }
                //set fields or throw new exception if there are no fields with necessary names
                switch (fieldName) {
                    case ("stringProperty"):
                        entityClass.setStringProperty(stringProperty);
                        break;

                    case ("numberProperty"):
                        entityClass.setMyNumber(numberProperty);
                        break;

                    case ("timeProperty"):
                        entityClass.setTimeProperty(timeProperty);
                        break;

                    default:
                        throw new NoSetterException("No setter found");
                }
            }
            return entityClass;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
