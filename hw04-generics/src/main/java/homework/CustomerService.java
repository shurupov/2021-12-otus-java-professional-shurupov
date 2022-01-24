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
            return entryCopy(entry);
        }
        return null;
    }

    private Map.Entry<Customer, String> entryCopy(Map.Entry<Customer, String> entry) {
        return Map.entry(
            new Customer(entry.getKey().getId(), entry.getKey().getName(), entry.getKey().getScores()),
            entry.getValue()
        );
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
                    return entryCopy(entryIterator.next());
                }
                return null;
            }
        }
        return null;
    }

    public <T extends Customer> void add(T customer, String data) {
        map.put(customer, data);
    }
}
