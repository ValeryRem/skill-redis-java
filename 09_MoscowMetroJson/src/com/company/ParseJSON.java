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
        Gson g = new Gson();
        Type type = new TypeToken<Map<String, List<String>>>(){}.getType();
        Map<String, List<String>> myMap = g.fromJson(parsedJSON, type);
        myMap.forEach((x,y)-> System.out.println("key : " + x + " , value : " + y));

//        String string1 = parsedJSON.substring(25, parsedJSON.indexOf('}') );
//        String string2 = string1.replaceAll("[{}:\"]", "").replaceAll("[0]", " ");
//       String[] array = string2.split(" ");
//        String key;
//        int value = 0;
//        for (int i = 0; i < array.length; i++) {
//           String st = array[i].trim();
//            if (st.matches("\\d+")) {
//                key = st;
//            } else {
//                value += value;
//            }
//
//        }
//
    }
}
//            //Convert JSON to JsonElement, and later to String
//            /** JsonElement json = gson.fromJson(reader, JsonElement.class);
//             String jsonInString = gson.toJson(json);
//             System.out.println(jsonInString);** /
//             } catch (IOException e) {
//             e.printStackTrace();
//             }
//
//        return
//    }
//
//    private static void parseLines (JSONArray linesArray)
//    {
//        linesArray.forEach(lineObject -> {
//            JSONObject lineJsonObject = (JSONObject) lineObject;
//            Line line = new Line(
//                    ((Long) lineJsonObject.get("number")).intValue(),
//                    (String) lineJsonObject.get("name")
//            );
//            stationIndex.addLine(line);
//        });
//    }
