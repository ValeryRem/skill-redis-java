import java.util.HashMap;

public class CustomerStorage
{
    private HashMap<String, Customer> storage;

    public CustomerStorage()
    {
        storage = new HashMap<>();
    }

    public void addCustomer(String data)
    {
        String[] components = data.split("\\s+");
        try {
            String name = components[0] + " " + components[1];
            storage.put(name, new Customer(name, components[3], components[2]));
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Too short input string!");
        }
    }

    public void listCustomers()
    {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name)
    { try {
        storage.remove(name);
    } catch (NullPointerException ex) {
        ex.getMessage();
    }
    }

    public int getCount()
    {
        return storage.size();
    }
}