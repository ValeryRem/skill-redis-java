import java.util.LinkedHashMap;
import java.util.Map;

public class GoodsGenerator {
    private Map<String, Integer> productsMap = new LinkedHashMap<>();

    public GoodsGenerator() {
    }

    public Map<String, Integer> getProductsMap (Integer NUMBER_OF_GOODS, Integer MIN_PRICE, Integer MAX_PRICE ) {
        int price;
        String name;// = "";
        for(int i = 1; i <= NUMBER_OF_GOODS; i++){
            price = (int) (MIN_PRICE + (MAX_PRICE - MIN_PRICE) * Math.random());
            name = "Item_" + i;
            productsMap.put(name, price);
        }
        return productsMap;
    }
}
