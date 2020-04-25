package com.company;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ParseJSON {

    public ParseJSON() {
    }

//    public void parseJSON(String pathToJson) {
//        Gson gson = new Gson();
//        try (Reader reader = new FileReader(pathToJson)) {
//            StationIndex jsonMap = gson.fromJson(reader, StationIndex.class);
//            String jsonInString = gson.toJson(jsonMap);
//            System.out.println(jsonInString);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public String parseJsonFile(String pathToJson) {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(pathToJson));
            lines.forEach(builder::append);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }

    public void presentResult (String parsedJSON) {
//        Gson g = new Gson();
//        Type type = new TypeToken<Map<String, List<String>>>(){}.getType();
//        Map<String, List<String>> myMap = g.fromJson(parsedJSON, type);
//        myMap.forEach((x,y)-> System.out.println("key : " + x + " , value : " + y));
        Map<String, Integer> myMap = new TreeMap<>();
                String string1 = parsedJSON.substring(25, parsedJSON.indexOf('}') );
        String[] array = string1.split("]");
        String key;
        int value;
        for (int i = 0; i < array.length - 1; i++) {
            String[] pref = array[i].split(":");
            String[] pref2 = pref[0].split("\"");
                    key = pref2[pref2.length - 1].replace("\\", "");
                    value = pref[1].split(",").length;
            myMap.put(key, value);
        }
        myMap.forEach((x,y)-> System.out.println("line: " + x + ", number of stations: " + y));
    }
}
