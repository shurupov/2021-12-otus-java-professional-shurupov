package ru.otus.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<K, V> innerCache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        innerCache.put(key, value);
        listeners.forEach((l) -> {
            l.notify(key, value, "PUT");
        });
    }

    @Override
    public void remove(K key) {
        innerCache.remove(key);
        listeners.forEach((l) -> {
            l.notify(key, null, "REMOVE");
        });
    }

    @Override
    public V get(K key) {
        V value = innerCache.get(key);
        listeners.forEach((l) -> {
            l.notify(key, value, "PUT");
        });
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
