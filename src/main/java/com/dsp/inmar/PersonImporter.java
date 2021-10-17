package com.dsp.inmar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class PersonImporter {
    public static void main(String[] args) {
        File file = null;
        //get the file from command line argument
        if (args.length == 1) {
            file = new File(args[0]);
            String path = file.getAbsolutePath();
            String fileType = path.substring(path.lastIndexOf(".") + 1);

            if ("json".equalsIgnoreCase(fileType)) {
                //read JSON file
                readJSONFile(path);
            } else if ("csv".equalsIgnoreCase(fileType) || "txt".equalsIgnoreCase(fileType)) {
                //Read the file either csv, txt
                readCSVFile(path);
            } else {
                throw new InMarClientException("Invalid file format");
            }
        } else {
            throw new InMarClientException("File not found Exception");
        }
    }

    /*
     * read CSV file
     */
    private static void readCSVFile(String path) {
        String line = null;
        PersonPublisher publisher;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                publisher = new PersonPublisher();
                Person p = new Person();
                p.setFirstName(row[0]);
                p.setLastName(row[1]);
                publisher.publishMessage(p);
            }
        } catch (IOException e) {
            throw new InMarClientException(e.getMessage());
        }
    }

    /*
     * read JSON file
     */
    private static void readJSONFile(String file) {
        PersonPublisher publisher = new PersonPublisher();
        JSONParser jsonParser = new JSONParser();
        Object object = null;

        try (FileReader fileReader = new FileReader(file)) {
            object = jsonParser.parse(fileReader);
            JSONObject personObj = (JSONObject) object;
            Person p = null;
            JSONArray personArray = (JSONArray) personObj.get("persons");
            for (Object o : personArray) {
                JSONObject personJSONObj = (JSONObject) o;
                p = new Person();
                p.setFirstName((String) personJSONObj.get("firstName"));
                p.setLastName((String) personJSONObj.get("lastName"));
                publisher.publishMessage(p);
            }
        } catch (IOException | ParseException e) {
            throw new InMarClientException(e.getMessage());
        }
    }
}
