package com.ey.telefonica.rpa.mongo.repository;

import com.ey.telefonica.rpa.mongo.model.Dispatcher;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DispatcherRepository extends MongoRepository<Dispatcher, String>  {
    //public List<Dispatcher> findAllBypagename(String pagename, Sort sort);
    String deleteDispatcherById(String id);
}
