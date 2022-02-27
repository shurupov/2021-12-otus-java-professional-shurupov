package ru.otus.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectForMessage {
    private List<String> data;

    public ObjectForMessage() {
    }

    public ObjectForMessage(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public Builder toBuilder() {
        return new Builder(data);
    }

    public static class Builder {
        private List<String> data;

        public Builder(List<String> data) {
            this.data = data;
        }

        public Builder data(List<String> data) {
            this.data = data;
            return this;
        }

        public ObjectForMessage build() {
            return new ObjectForMessage(List.copyOf(data));
        }
    }
}
