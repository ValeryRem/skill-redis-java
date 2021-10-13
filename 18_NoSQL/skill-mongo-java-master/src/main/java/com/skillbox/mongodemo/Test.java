package com.skillbox.mongodemo;

import au.com.bytecode.opencsv.CSVReader;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.ListDatabasesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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

public class Test {

    public static void main(String[] args) throws IOException {
        MongoClient mongoClient = new MongoClient( "127.0.0.1" , 27017 );

        MongoDatabase database = mongoClient.getDatabase("mongo-students");

        // Создаем коллекцию
        MongoCollection<Document> collection = database.getCollection("students");

        // Удалим из нее все документы
        collection.drop();
//        System.out.println("Cunt: " + collection.countDocuments());

        try {
            CSVReader reader = new CSVReader(new FileReader("mongo.csv"), ',' , '"' , 0);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                    Document document = new Document()
                            .append("Name", nextLine[0])
                            .append("Age", nextLine[1])
                            .append("Courses", nextLine[2]);
                    collection.insertOne(document);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Count of students in the collection: " + collection.countDocuments());

        BasicDBObject query = new BasicDBObject();
        query.put("Age", new BasicDBObject("$gt", 40));
        FindIterable<Document> iterable = collection.find(query);
        MongoCollection<Document> collectionOver40 = database.getCollection("studentsOver40");
        while (iterable.iterator().hasNext()) {
            Document studentOver40 = iterable.iterator().next();
            collectionOver40.insertOne(studentOver40);
        }
        System.out.println("Count of students over40: " + collectionOver40.countDocuments());
    }
}
