package com.skillbox.mongodemo;

import au.com.bytecode.opencsv.CSVReader;
import com.mongodb.MongoClient;
import com.mongodb.client.ListDatabasesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class Test {

    public static void main(String[] args) throws IOException {
        MongoClient mongoClient = new MongoClient( "127.0.0.1" , 27017 );

        MongoDatabase database = mongoClient.getDatabase("mongo-students");

        // Создаем коллекцию
        MongoCollection<Document> collection = database.getCollection("students");

        // Удалим из нее все документы
        collection.drop();
        System.out.println(collection.countDocuments());
//        try {
//            CSVReader reader = new CSVReader(new FileReader("mongo.csv"), ',' , '"' , 0);
//            String[] nextLine;
//            while ((nextLine = reader.readNext()) != null) {
//                    //Verifying the read data here
////                    System.out.println(Arrays.toString(nextLine));
//                    Document document = new Document()
//                            .append("Name", nextLine[0])
//                            .append("Age", nextLine[1])
//                            .append("Courses", nextLine[2]);
//                    collection.insertOne(document);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        collection.find().forEach((Consumer<Document>) System.out::println);

//        // Создадим первый документ
//        Document firstDocument = new Document()
//                .append("Type", 1)
//                .append("Description", "Это наш первый документ в MongoDB")
//                .append("Author", "Я")
//                .append("Time", new SimpleDateFormat().format(new Date()));
//
//
//        // Вложенный объект
//        Document nestedObject = new Document()
//                .append("Course", "NoSQL Базы Данных")
//                .append("Author", "Mike Ovchinnikov");
//
//        firstDocument.append("Skillbox", nestedObject);
//
//
//        // Вставляем документ в коллекцию
//        collection.insertOne(firstDocument);
//
//        collection.find().forEach((Consumer<Document>) document -> {
//            System.out.println("Наш первый документ:\n" + document);
//        });
//
//        // Используем JSON-синтаксис для создания объекта
//        Document secondDocument = Document.parse(
//                "{Type: 2, Description:\"Мы создали и нашли этот документ с помощью JSON-синтаксиса\"}"
//        );
//        collection.insertOne(secondDocument);
//
//        // Используем JSON-синтаксис для написания запроса (выбираем документы с Type=2)
//        BsonDocument query = BsonDocument.parse("{Type: {$eq: 2}}");
//        collection.find(query).forEach((Consumer<Document>) document -> {
//            System.out.println("Наш второй документ:\n" + document);
//        });
    }
}
