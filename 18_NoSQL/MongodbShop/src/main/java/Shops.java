import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.*;
import java.util.function.Consumer;

public class Shops {
    private static final int NUMBER_OF_SHOPS = 5;
    private static final int NUMBER_OF_GOODS = 100;
    private static final int MIN_PRICE = 1;
    private static final int MAX_PRICE = 1000;

    public static void main(String[] args) {
        ShopsGenerator shopsGenerator = new ShopsGenerator(NUMBER_OF_SHOPS, NUMBER_OF_GOODS, MIN_PRICE, MAX_PRICE);
        ArrayList<MongoCollection<Document>> shopsCollection = shopsGenerator.getShopsCollection();
//        for (MongoCollection<Document> shop: shopsCollection) {
//            System.out.println(shop.getNamespace());
//            shop.find().forEach((Consumer<Document>) doc -> System.out.println(doc.toString()));
//        }

        MongoCollection<Document> statistics = shopsGenerator.getStatistics(shopsCollection);
        statistics.find().forEach((Consumer<Document>) doc -> System.out.println(doc.toString()));

        shopsGenerator.getMongoClient().close();
    }

}

