package com.ey.telefonica.rpa.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "dispatcher_store")
public class DispatcherStore {
    @Id
    private String id;
    private Dispatcher disptacher;

    public static DispatcherStore createDispatcherStore(Dispatcher dispatcher) {
        return  new DispatcherStore(dispatcher);
    }

    private DispatcherStore( Dispatcher dispatcher) {
        this();
        this.id = dispatcher.getId();
        this.disptacher = dispatcher;
    }

    public DispatcherStore( ) { }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public Dispatcher getDisptacher() {
        return disptacher;
    }

    public void setDisptacher(Dispatcher disptacher) {
        this.disptacher = disptacher;
    }

    @Override
    public String toString() {
        return "DispatcherStore{" +
                "id='" + id + '\'' +
                ", disptacher=" + disptacher.toString() +
                '}';
    }
}
