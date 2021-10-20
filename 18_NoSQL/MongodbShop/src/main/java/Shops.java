import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.*;

public class Shops {
    private static Integer NUMBER_OF_SHOPS = 5;
    private static Integer NUMBER_OF_GOODS = 100;
    private static Integer MIN_PRICE = 1;
    private static Integer MAX_PRICE = 1000;

    public static void main(String[] args) {
        ShopsGenerator shopsGenerator = new ShopsGenerator(NUMBER_OF_SHOPS, NUMBER_OF_GOODS, MIN_PRICE, MAX_PRICE);
        Collection<MongoCollection<Document>> shopsCollection = shopsGenerator.getShopsCollection();
        System.out.println("shopsCollection size: " + shopsCollection.size());
        for (MongoCollection<Document> shops: shopsCollection) {
            System.out.println("Number of goods: " + shops.countDocuments());
            for (Document shopDoc: shops.find()) {
                System.out.println(shopDoc);
            }
        }
    }
}

