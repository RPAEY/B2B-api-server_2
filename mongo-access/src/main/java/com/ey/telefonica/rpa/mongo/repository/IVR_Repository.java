package com.ey.telefonica.rpa.mongo.repository;

import com.ey.telefonica.rpa.mongo.model.lotus.IVR_;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.List;

public interface IVR_Repository extends MongoRepository<IVR_, String> {
    public Optional<IVR_> findOneByIdu(String idu);
    public List<IVR_> findAllByIdu(String idu);
}