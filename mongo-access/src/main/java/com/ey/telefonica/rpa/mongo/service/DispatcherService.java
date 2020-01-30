package com.ey.telefonica.rpa.mongo.service;

import com.ey.telefonica.rpa.mongo.exception.DispatcherStoreEmptyException;
import com.ey.telefonica.rpa.mongo.exception.DispatcherStoreException;
import org.springframework.stereotype.Service;
import com.ey.telefonica.rpa.mongo.model.Dispatcher;

import java.util.List;

@Service("DispatcherService")
public interface DispatcherService {
    public List<Dispatcher> findAll();

    //public Dispatcher saveDispatcher(Dispatcher dispatcher);

    public void updateDispatcher(Dispatcher dispatcher);

    //public List<Dispatcher> findDispatcherPoll(String process, int limit);

    public Dispatcher findAndRemoveDispatcher();

    public Long findAndRemoveDispatcherStore(Integer preservedDays)  throws DispatcherStoreEmptyException, DispatcherStoreException;

    Dispatcher findAndRemoveMorePacher();

}
