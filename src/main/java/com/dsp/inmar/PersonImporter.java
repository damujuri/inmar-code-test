package com.dsp.inmar;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PersonImporter {
    public static void main(String[] args) {
        //get the file from command line argument
        if (args.length == 0) {
            throw new InMarClientException("No file select; please provide file name");
        } else if (args.length > 2) {
            throw new InMarClientException("Invalid arguments. It should accept only parameter as file path");
        } else {
            File file = new File(args[0]);
            if (file.exists()) {
                String path = file.getAbsolutePath();
                String fileType = FilenameUtils.getExtension(path);
                switch (fileType) {
                    case InMarConstants.JSON -> readJSONFile(path);
                    case InMarConstants.CSV, InMarConstants.TXT -> readCSVFile(path);
                    default -> throw new InMarClientException("Invalid file format");
                }
            } else {
                throw new InMarClientException("Invalid File");
            }
        }
    }

    /*
     * read CSV file
     */
    public static void readCSVFile(String path) {
        String line;
        List<Person> personsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            PersonPublisher publisher = new PersonPublisher();
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",", -1);
                Person p = createPerson(row);
                personsList.add(p);
            }
            publisher.publishMessage(personsList);

        } catch (IOException e) {
            throw new InMarClientException(e.getMessage());
        }
    }

    private static Person createPerson(String[] row) {
        Person person = new Person();
        person.setFirstName(row[0]);
        person.setLastName(row[1]);
        person.setUid(UUID.randomUUID());
        return person;
    }

    /*
     * read JSON file
     */
    public static void readJSONFile(String file) {
        PersonPublisher publisher = new PersonPublisher();
        JSONParser jsonParser = new JSONParser();
        Object object;
        List<Person> personsList = new ArrayList<>();

        try (FileReader fileReader = new FileReader(file)) {
            object = jsonParser.parse(fileReader);
            JSONObject personObj = (JSONObject) object;
            Person p;
            JSONArray personArray = (JSONArray) personObj.get("persons");
            for (Object o : personArray) {
                JSONObject personJSONObj = (JSONObject) o;
                p = new Person();
                p.setFirstName((String) personJSONObj.get("firstName"));
                p.setLastName((String) personJSONObj.get("lastName"));
                personsList.add(p);
            }
            publisher.publishMessage(personsList);
        } catch (IOException | ParseException e) {
            throw new InMarClientException(e.getMessage());
        }
    }
}
