package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader {

    private Map<Long, Message> history = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        history.put(msg.getId(), msg.toBuilder().build());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        if (history.containsKey(id)) {
            return Optional.of(history.get(id));
        }
        return Optional.empty();
    }
}
