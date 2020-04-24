package com.company;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ParseJSON {

    public ParseJSON() {
    }

//    public void parseJSON(String pathToJson) {
//        Gson gson = new Gson();
//        try (Reader reader = new FileReader(pathToJson)) {
//            JsonElement jsonMap = gson.fromJson(reader, JsonElement.class);
//            String jsonInString = gson.toJson(jsonMap);
//            System.out.println(jsonInString);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    public String getJsonFile (String pathToJson)
    {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(pathToJson));
            lines.forEach(line -> builder.append(line));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
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
