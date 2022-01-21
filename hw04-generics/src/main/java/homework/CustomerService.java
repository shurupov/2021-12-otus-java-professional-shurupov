package homework;


import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class CustomerService {

    private Map<Customer, String> map = new TreeMap<>((o1, o2) ->
        (int) (o1.getScores() - o2.getScores()));

    private PriorityQueue<Map.Entry<Customer, String>> entryPriorityQueue = new PriorityQueue<>((o1, o2) ->
        (int) (o1.getKey().getScores() - o2.getKey().getScores())
    );

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

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

    public void add(Customer customer, String data) {
        entryPriorityQueue.add(new AbstractMap.SimpleEntry<>(customer, data));
        map.put(customer, data);
    }
}
