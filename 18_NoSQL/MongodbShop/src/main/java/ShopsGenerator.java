import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class ShopsGenerator {

    private Integer NUMBER_OF_SHOPS;
    private Integer NUMBER_OF_GOODS;
    private Integer MAX_PRICE;
    private Integer MIN_PRICE;

    public ShopsGenerator(Integer NUMBER_OF_SHOPS, Integer NUMBER_OF_GOODS, Integer MIN_PRICE, Integer MAX_PRICE) {
        this.NUMBER_OF_SHOPS = NUMBER_OF_SHOPS;
        this.NUMBER_OF_GOODS = NUMBER_OF_GOODS;
        this.MIN_PRICE = MIN_PRICE;
        this.MAX_PRICE = MAX_PRICE;
    }

    private final MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
    private final MongoDatabase database = mongoClient.getDatabase("mongo-shops");

    // Метод создния коллекции МАГАЗИН
    private MongoCollection<Document> addShopCollection(String shopName) {
        return database.getCollection(shopName);
    }
    // Метод создния коллекции ТОВАРЫ
    private MongoCollection<Document> addGoodsCollection(String goods) {
        return database.getCollection(goods);
    }
    // Метод добавления 1 товара в МАГАЗИН
    void addGoodsToShop(MongoCollection<Document> shopName, Document doc) {
        shopName.insertOne(doc);
    }
    // Метод создания сети магазинов с запасами товаров
    GoodsGenerator goodsGenerator = new GoodsGenerator();
    Map<String, Integer> products = goodsGenerator.getProductsMap(NUMBER_OF_GOODS, MIN_PRICE, MAX_PRICE);
    MongoCollection<Document> goods = addGoodsCollection("goodsCollection");

    public Collection<MongoCollection<Document>> getShopsCollection() {
        Collection<String> productNames = products.keySet();
        Collection<MongoCollection<Document>> shopsCollection = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_SHOPS; i++) {
            MongoCollection<Document> shop = addShopCollection("Shop_" + i);
            int numberOfGoodsHere = (int) (Math.random() * NUMBER_OF_GOODS);
            FindIterable<Document> iterable = goods.find().limit(numberOfGoodsHere);
            for (String key: productNames.stream().limit(numberOfGoodsHere).collect(Collectors.toList())) {
                Document document = new Document();
                document.append("name", key)
                        .append("price", iterable.iterator().next().get(key)); //products.get(key));
                addGoodsToShop(shop, document);
            }
            shopsCollection.add(shop);
        }
        return shopsCollection;
    }

    public Integer getNUMBER_OF_SHOPS() {
        return NUMBER_OF_SHOPS;
    }

    public void setNUMBER_OF_SHOPS(Integer NUMBER_OF_SHOPS) {
        this.NUMBER_OF_SHOPS = NUMBER_OF_SHOPS;
    }

    public Integer getNUMBER_OF_GOODS() {
        return NUMBER_OF_GOODS;
    }

    public void setNUMBER_OF_GOODS(Integer NUMBER_OF_GOODS) {
        this.NUMBER_OF_GOODS = NUMBER_OF_GOODS;
    }

    public Integer getMAX_PRICE() {
        return MAX_PRICE;
    }

    public void setMAX_PRICE(Integer MAX_PRICE) {
        this.MAX_PRICE = MAX_PRICE;
    }

    public Integer getMIN_PRICE() {
        return MIN_PRICE;
    }

    public void setMIN_PRICE(Integer MIN_PRICE) {
        this.MIN_PRICE = MIN_PRICE;
    }
    //    // Создаем коллекции разных магазинов и очистим их
//    MongoCollection<Document> shopNine = addShopCollection("shopNine");
    // Создаем коллекцию ТОВАРЫ и удалим из нее все документы
//    MongoCollection<Document> goods = addGoodsCollection("goodsCollection");
    // Используем генератор коллекции товаров goods из класса GoodsGenerator



    // Делаем выборку первых N товаров из коллекции goods для магазина shopNine, где N определено случайным образом

    // Выводим в консоль результат - коллекцию товаров в shopNine
//    FindIterable<Document> iterableShopNine = shopNine.find();
//        for (Document d: iterableShopNine) {
//        System.out.println(d.toJson());
//    }
}
