import com.mongodb.*;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BsonField;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static com.mongodb.client.model.Aggregates.*;

import java.util.*;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoService {
    private final CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));
private  final MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
    private final List<MongoCollection<Document>> shopsCollection = new ArrayList<>();

    public void processInput() {
        MongoDatabase database = mongoClient.getDatabase("mongoShops");
        MongoCollection<Document> goods = database.getCollection("GoodsCollection");
        database.drop();
        do {
            Scanner scanner = new Scanner(System.in);
            String order = scanner.nextLine();
            switch (order) {
                case "ДОБАВИТЬ_МАГАЗИН":
                    System.out.println("Добавьте название магазина.");
                    String shopName = scanner.nextLine();
                    MongoCollection<Document> shop = database.getCollection(shopName);
                    shopsCollection.add(shop);
                    System.out.println("Введите команду из ряда: ДОБАВИТЬ_МАГАЗИН, ДОБАВИТЬ_ТОВАР, ВЫСТАВИТЬ_ТОВАР, СТАТИСТИКА_ТОВАРОВ.");
                    break;
                case "ДОБАВИТЬ_ТОВАР":
                    System.out.println("Добавьте название товара.");
                    String goodsName = scanner.nextLine();
                    Document d = new Document();
                    d.append("item", goodsName);
                    System.out.println("Добавьте цену товара.");
                    Integer goodsPrice = Integer.parseInt(scanner.nextLine());
                    d.append("price", goodsPrice);
                    goods.insertOne(d);
//                    goods.find().forEach((Consumer<Document>) doc -> System.out.println(doc.toString()));
                    System.out.println("Введите команду из ряда: ДОБАВИТЬ_МАГАЗИН, ДОБАВИТЬ_ТОВАР, ВЫСТАВИТЬ_ТОВАР, СТАТИСТИКА_ТОВАРОВ.");
                    break;
                case "ВЫСТАВИТЬ_ТОВАР":
                    System.out.println("Добавьте название товара.");
                    goodsName = scanner.nextLine();
                    Document doc = null;
                    BasicDBObject query = new BasicDBObject();
                    query.put("item", goodsName);
                    for (Document dc : goods.find(query)) {
                        if(dc.toString().contains(goodsName)) {
                            doc = dc;
                            break;
                        }
                    }
                    if (doc == null) {
                        System.out.println("The item " + goodsName + " is absent in the goods collection.");
                    } else {
                        System.out.println("Добавьте название магазина.");
                        shopName = scanner.nextLine();
                        MongoCollection<Document> shopChosen = null;
                        for (MongoCollection<Document> mc : shopsCollection) {
                            if (mc.getNamespace().toString().contains(shopName)) {
                                shopChosen = mc;
                                break;
                            }
                        }
                        if (shopChosen != null) {
                            shopChosen.insertOne(doc);
                        } else {
                            System.out.println("Such shop is absent.");
                        }
                    }
                    System.out.println("Введите команду из ряда: ДОБАВИТЬ_МАГАЗИН, ДОБАВИТЬ_ТОВАР, ВЫСТАВИТЬ_ТОВАР, СТАТИСТИКА_ТОВАРОВ.");
                    break;
                case "СТАТИСТИКА_ТОВАРОВ":
                   getStatistics(database, (ArrayList<MongoCollection<Document>>) shopsCollection);
                    break;
                default:
                    System.err.println("Wrong order input!");
            }
//            for (MongoCollection<Document> shop : shopsCollection) {
//                shop.find().forEach((Consumer<Document>) doc -> System.out.println(doc.toString()));
//            }
        } while (true);
    }

    private void getStatistics(MongoDatabase database, ArrayList<MongoCollection<Document>> listOfShops) {
        MongoCollection<Document> result = database.getCollection("Statistics");
        result = result.withCodecRegistry(pojoCodecRegistry);
        result.drop();
        for (MongoCollection<Document> shop : listOfShops) {
            Document document = new Document();
            //Находим общее количество наименований товаров в магазине
            document.append("shop", shop.getNamespace().toString()).append("countOfGoods", shop.countDocuments());
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
                document.append("item with max price", doc.toString());//.get("price"));
            }
            //Находим товар с миниимальной ценой в магазине
            Document d = cursor.sort(new BasicDBObject("price", 1)).limit(1).iterator().tryNext();
            if(d != null) {
                document.append("item with min price", d.toString());
            }
            //Находим среднюю цену товаров в магазине
            AggregateIterable<org.bson.Document> aggregate = shop.aggregate(Collections.singletonList(group("_id",
                    new BsonField("avgPrice", new BsonDocument("$avg", new BsonString("$price"))))));
            Document res = aggregate.first();
            if(res != null) {
                double avgPrice = res.getDouble("avgPrice");
                document.append("avgPrice", avgPrice);
            }
            result.insertOne(document);
        }
        result.find().forEach((Consumer<Document>) doc -> System.out.println(doc.toString()));
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
