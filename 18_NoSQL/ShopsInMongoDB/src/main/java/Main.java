import java.util.Scanner;

public class Main {
    private final static MongoService mongoService = new MongoService();

    public static void main(String[] args) {
        System.out.println("Введите команду из ряда: ДОБАВИТЬ_МАГАЗИН, ДОБАВИТЬ_ТОВАР, ВЫСТАВИТЬ_ТОВАР, СТАТИСТИКА_ТОВАРОВ.");
        mongoService.processInput();
        mongoService.getMongoClient().close();
    }
}
