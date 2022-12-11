package com.gmail.kulacholeg.task_02.util;

import com.gmail.kulacholeg.task_02.entity.EntityClass;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class UtilClassTest {

    @Test
    public void loadFromPropertiesTest(){
        EntityClass expectedClass = new EntityClass();
        expectedClass.setStringProperty("value");
        expectedClass.setMyNumber(100);
        LocalDateTime localDateTime = LocalDateTime.parse("29.11.2022 18:30", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        Instant timeProperty = localDateTime.toInstant(OffsetDateTime.now().getOffset());
        expectedClass.setTimeProperty(timeProperty);

        String fs = File.separator;
        EntityClass actualClass = UtilClass.loadFromProperties(EntityClass.class, Paths.get("src" + fs + "main" + fs + "resources" + fs + "task_02" + fs + "class.properties"));

        assertEquals(expectedClass.getStringProperty(), actualClass.getStringProperty());
        assertEquals(expectedClass.getMyNumber(), actualClass.getMyNumber());
        assertEquals(expectedClass.getTimeProperty(), actualClass.getTimeProperty());
    }
}