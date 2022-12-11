package com.gmail.kulacholeg;

import com.gmail.kulacholeg.task_01.TrafficViolationsConverter;
import com.gmail.kulacholeg.task_02.entity.EntityClass;
import com.gmail.kulacholeg.task_02.util.UtilClass;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        //task_01
        //results can be found in 'task_01_results' in root
        long start = System.currentTimeMillis();
        TrafficViolationsConverter trafficViolationsConverter = new TrafficViolationsConverter();
        trafficViolationsConverter.readFiles();
        System.out.println(System.currentTimeMillis() - start);

        //task_02
        //also can be started from 'test'
        String fs = File.separator;
        Path path = Paths.get("src" + fs + "main" + fs + "resources" + fs + "task_02" + fs + "class.properties");
        System.out.println(UtilClass.loadFromProperties(EntityClass.class, path).toString());
    }
}
