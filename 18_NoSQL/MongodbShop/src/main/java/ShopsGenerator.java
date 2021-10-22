import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import java.util.*;
import static com.mongodb.client.model.Filters.*;

public class ShopsGenerator {

    private final int NUMBER_OF_SHOPS;
    private final int NUMBER_OF_GOODS;
    private final int MAX_PRICE;
    private final int MIN_PRICE;

    public ShopsGenerator(Integer NUMBER_OF_SHOPS, Integer NUMBER_OF_GOODS, Integer MIN_PRICE, Integer MAX_PRICE) {
        this.NUMBER_OF_SHOPS = NUMBER_OF_SHOPS;
        this.NUMBER_OF_GOODS = NUMBER_OF_GOODS;
        this.MIN_PRICE = MIN_PRICE;
        this.MAX_PRICE = MAX_PRICE;
    }

    private final MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
    private final MongoDatabase database = mongoClient.getDatabase("mongo-shops");

    // Создаем базовую коллекцию тоаров GoodsCollection
    private MongoCollection<Document> goods = database.getCollection("GoodsCollection");
    // Метод создания сети магазинов с запасами товаров
    public ArrayList<MongoCollection<Document>> getShopsCollection() {
        ArrayList<MongoCollection<Document>> shopsCollection = new ArrayList<>();

        // Наполняем базовую коллекцию goods тоарами в кол-ве NUMBER_OF_GOODS
        getGoods(NUMBER_OF_GOODS, MIN_PRICE, MAX_PRICE);
        for (int i = 1; i <= NUMBER_OF_SHOPS; i++) {
            //Создаем новый магазин
            MongoCollection<Document> shop = database.getCollection("Shop_" + i);
            shop.drop();
            int numberOfGoodsHere = (int) (Math.random() * NUMBER_OF_GOODS);
            // Определем набор товаров для данного магазина - первые numberOfGoodsHere из базовой коллекции товаров
            FindIterable<Document> iterableGoods = goods.find().limit(numberOfGoodsHere);
            for (Document d: iterableGoods) {
                shop.insertOne(d);
            }
            shopsCollection.add(shop);
        }
        return shopsCollection;
    }
    // Метод наполнения коллекции goods тоарами в кол-ве NUMBER_OF_GOODS со случайными ценами
    private void getGoods (int NUMBER_OF_GOODS, int MIN_PRICE, int MAX_PRICE){
        int price;
        String name;
        for (int k = 1; k <= NUMBER_OF_GOODS; k++) {
            price = (int) (MIN_PRICE + (MAX_PRICE - MIN_PRICE) * Math.random());
            name = "Item_" + k;
            Document document = new Document();
            document.append("name", name).append("price", price);
            goods.insertOne(document);
        }
    }

    public MongoCollection<Document> getStatistics(ArrayList<MongoCollection<Document>> listOfShops) throws NoSuchFieldException {
        MongoCollection<Document> result = database.getCollection("Statistics");
        result.drop();
        for (MongoCollection<Document> shop : listOfShops) {
            Document document = new Document();
            //Находим общее количество наименований товаров в магазине
            document.append("shop", shop.getNamespace()).append("countOfGoods", shop.countDocuments());
            //Находим количество товаров дешевле 100 рублей в магазине.
            int countOfLess100 = 0;
            for (Document d : shop.find(and(lt("price", 100)))) {
                countOfLess100++;
            }
            document.append("count of prices less 100", countOfLess100);
            result.insertOne(document);
        }
        return result;
    }
}
