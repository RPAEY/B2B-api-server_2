package com.ey.telefonica.rpa.mongo.repository;

import com.ey.telefonica.rpa.mongo.model.lotus.K2_;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.List;

public interface K2_Repository extends MongoRepository<K2_, String> {
    public Optional<K2_> findOneByIdu(String idu);
    public List<K2_> findAllByIdu(String idu);
}