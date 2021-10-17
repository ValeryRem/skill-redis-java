package com.skillbox.mongodemo;

import au.com.bytecode.opencsv.CSVReader;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
                            .append("Age", Integer.parseInt(nextLine[1].trim()))
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

        int k = 0;
        for (Document d : collection.find(and(gt("Age", 40)))) {
            k++;
        }
        System.out.println("Count of students over 40: " + k);
        System.out.println("\nStudents of Age over 40: ");
        collection.find(and(gt("Age", 40))).forEach((Consumer<Document>) System.out::println);

        FindIterable<Document> cursor = collection.find();
        Document document = cursor.sort(new BasicDBObject("Age", 1)).limit(1).iterator().tryNext();
        if (document != null) {
            System.out.println("\nA student of the yangest Age: " + document.get("Name"));
        }

        if (document != null) {
            System.out.print("\nCourses of the yangest student: " + document.get("Courses"));
        }
    }
}
