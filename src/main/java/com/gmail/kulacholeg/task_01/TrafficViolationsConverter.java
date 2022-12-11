package com.gmail.kulacholeg.task_01;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.gmail.kulacholeg.task_01.Entity.TrafficViolation;
import com.gmail.kulacholeg.task_01.Entity.TrafficViolationAmounts;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class TrafficViolationsConverter {
    private static final String FS = File.separator;
    private static final String PATH = "src" + FS + "main" + FS + "resources" + FS + "task_01" + FS;
    private final File inputDirectory = new File(PATH + "data_input");
    private final File outputFile = new File(PATH + "data_output.xml");
    private Map<String, Double> result = new HashMap<>();
    private final CopyOnWriteArrayList<TrafficViolation> violations = new CopyOnWriteArrayList<>();


    public void readFiles(){
        ObjectMapper mapper = new ObjectMapper();
        //creating thread-safe arraylist with files
        CopyOnWriteArrayList<File> files =
                new CopyOnWriteArrayList<>(Objects.requireNonNull(inputDirectory.listFiles((pathname) -> pathname.getName().endsWith(".json"))));

        //creating pool with fixed number of threads
        ExecutorService executorService = Executors.newFixedThreadPool(8); //2, 4, 8

        //creating threads and reading files
        for(File file : files){
            CompletableFuture.supplyAsync(() -> {
                try {
                    violations.addAll(mapper.readValue(file, new TypeReference<List<TrafficViolation>>() {}));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }, executorService);
        }

        //waiting until all threads done execution
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        //calling method to write result
        writeAmounts();
    }

    private void writeAmounts() {
        try {
            //Write needed data to Map
            for (TrafficViolation tv : violations) {
                result.merge(tv.getType(), tv.getFineAmount(), Double::sum);
            }
            //Sort map from max to min amounts
            result = result.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldVal, newVal) -> oldVal, LinkedHashMap::new
                    ));
            //Write output xml-file
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            xmlMapper.writeValue(outputFile, new TrafficViolationAmounts(result));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
