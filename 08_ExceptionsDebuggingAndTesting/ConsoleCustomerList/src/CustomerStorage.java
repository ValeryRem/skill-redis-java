import java.util.HashMap;

public class CustomerStorage {
    private HashMap<String, Customer> storage;

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) throws Exception {
        String[] components = data.split("\\s+");
        String name = components[0] + " " + components[1];
        if (components[2].matches("(\\w+\\.?\\w+@\\w+\\.\\w+)") && components[3].matches("(\\+[7]\\d{10})")) {
            storage.put(name, new Customer(name, components[3], components[2]));
        } else {
          throw new Exception("Wrong form of phone number or e-mail input!");
        }
    }

    public void listCustomers() throws Exception {
        if (storage.isEmpty())
        {
            throw new Exception("List of customers is empty!");
        } else {
            storage.values().forEach(System.out::println);
        }
    }

    public void removeCustomer (String name) throws Exception {
        if (storage.containsKey(name)) {
            storage.remove(name);
        } else {
            throw new Exception("No such name!");
        }
    }

    public int getCount()
    {
        return storage.size();
    }
}