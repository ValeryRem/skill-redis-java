package com.skillbox.mongodemo;

import au.com.bytecode.opencsv.CSVReader;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Collation;
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
import static com.mongodb.client.model.Filters.*;

public class Test {

    public static void main(String[] args) throws IOException {
        MongoClient mongoClient = new MongoClient( "127.0.0.1" , 27017 );

        MongoDatabase database = mongoClient.getDatabase("mongo-students");

        // Создаем коллекцию
        MongoCollection<Document> collection = database.getCollection("students");

        // Удалим из нее все документы
        collection.drop();

        try {
            CSVReader reader = new CSVReader(new FileReader("mongo.csv"), ',' , '"' , 0);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                    Document document = new Document()
                            .append("Name", nextLine[0].trim())
                            .append("Age", nextLine[1].trim())
                            .append("Courses", nextLine[2].trim());
                    collection.insertOne(document);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Count of students in the collection: " + collection.countDocuments());
//        int i = 0;
//        for (Document d : collection.find()) {
//            ++i;
//            System.out.println(i + ". " + d.toJson());
//        }
//        BasicDBObject query = new BasicDBObject();
//        var query = new BasicDBObject("Age", new BasicDBObject("$gt", 40));
//        collection.find(query).forEach((Consumer<Document>) System.out::println);
        System.out.println("\nStudents of Age over 40: ");
        int i = 0;
        for (Document d :  collection.find(and(gt("age", 40)))) {
            ++i;
            System.out.println(i + ". " + d);
        }
        System.out.println("Count of students over 40: " + i);
        collection.find(and(gt("Age", 40))).forEach((Consumer<Document>) System.out::println);


    }
}
