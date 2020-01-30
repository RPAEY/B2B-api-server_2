package com.ey.telefonica.rpa.mongo.repository;

import com.ey.telefonica.rpa.mongo.model.DispatcherStore;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DispatcherStoreRepository extends MongoRepository<DispatcherStore, String>  {

}
