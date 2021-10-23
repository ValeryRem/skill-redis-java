import com.mongodb.*;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Projections;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static com.mongodb.client.model.Aggregates.*;

import java.util.*;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

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
    private final CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));

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

    public MongoCollection<Document> getStatistics(ArrayList<MongoCollection<Document>> listOfShops) {
        MongoCollection<Document> result = database.getCollection("Statistics");
        result = result.withCodecRegistry(pojoCodecRegistry);
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
            FindIterable<Document> cursor = shop.find();
            //Находим товар с максимальной ценой в магазине
            Document doc = cursor.sort(new BasicDBObject("price", -1)).limit(1).iterator().tryNext();
            if(doc != null) {
                document.append("max price", doc.get("price"));
            }
            //Находим товар с миниимальной ценой в магазине
            Document d = cursor.sort(new BasicDBObject("price", 1)).limit(1).iterator().tryNext();
            if(d != null) {
                document.append("min price", d.get("price"));
            }
            //Находим среднюю цену товаров в агазине
//            BsonField field = avg("avgOfPrice", "$shop.price");
//            document.append(field.getName(), field.getValue());

            AggregateIterable<org.bson.Document> aggregate = shop.aggregate(Collections.singletonList(group("_id",
                    new BsonField("avgPrice", new BsonDocument("$avg", new BsonString("$price"))))));
            Document res = aggregate.first();
            if(res != null) {
                double avgPrice = res.getDouble("avgPrice");
                document.append("avgPrice", avgPrice);
            }
            result.insertOne(document);
        }
        return result;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
