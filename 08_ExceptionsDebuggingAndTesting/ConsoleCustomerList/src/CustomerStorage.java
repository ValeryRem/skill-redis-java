import java.util.HashMap;

public class CustomerStorage {
    private HashMap<String, Customer> storage;

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public boolean addCustomer(String data) {
        String[] components = data.split("\\s+");
        String name = components[0] + " " + components[1];
        if (components[3].matches("([+7]\\d{11})")) {
            storage.put(name, new Customer(name, components[3], components[2]));
            return true;
        } else {
           return false;
        }
    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
            storage.remove(name);
    }

    public int getCount()
    {
        return storage.size();
    }
}