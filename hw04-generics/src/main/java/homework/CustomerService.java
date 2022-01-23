package homework;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final Map<Customer, String> map = new TreeMap<>((o1, o2) ->
        (int) (o1.getScores() - o2.getScores())
    );

    public Map.Entry<Customer, String> getSmallest() {
        for (Map.Entry<Customer, String> entry : map.entrySet()) {
            return entry;
        }
        return null;
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        if (!map.containsKey(customer)) {
            map.put(customer, "nothing");
        }

        Iterator<Map.Entry<Customer, String>> entryIterator = map.entrySet().iterator();

        while (entryIterator.hasNext()) {
            Map.Entry<Customer, String> entry = entryIterator.next();
            if (entry.getKey().equals(customer)) {
                if (entryIterator.hasNext()) {
                    return entryIterator.next();
                }
                return null;
            }
        }
        return null;
    }

    public <T extends Customer> void add(T customer, String data) {
        map.put(new ImmutableCustomer(customer), data);
    }
}
