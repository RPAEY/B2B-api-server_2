package com.ey.telefonica.rpa.mongo.repository;

import com.ey.telefonica.rpa.mongo.model.ivr.IVR;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IVRRepository extends MongoRepository<IVR, String> {
}