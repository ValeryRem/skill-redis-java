import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.*;

public class Shops {
    private static final int NUMBER_OF_SHOPS = 5;
    private static final int NUMBER_OF_GOODS = 100;
    private static final int MIN_PRICE = 1;
    private static final int MAX_PRICE = 1000;
//    private static ShopsGenerator shopsGenerator;

    public static void main(String[] args) {
        ShopsGenerator shopsGenerator = new ShopsGenerator(NUMBER_OF_SHOPS, NUMBER_OF_GOODS, MIN_PRICE, MAX_PRICE);
        ArrayList<MongoCollection<Document>> shopsCollection = shopsGenerator.getShopsCollection();
//        for (MongoCollection<Document> shop: shopsCollection) {
//            System.out.println(shop.getNamespace());
//            for (Document shopDoc: shop.find()) {
//                System.out.println(shopDoc);
//            }
//        }

        MongoCollection<Document> statistics;
        try {
            statistics = shopsGenerator.getStatistics(shopsCollection);
            for (Document shopStat: statistics.find()) {
                System.out.println(shopStat);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }
}

