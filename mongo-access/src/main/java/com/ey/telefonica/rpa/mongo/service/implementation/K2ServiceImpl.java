package com.ey.telefonica.rpa.mongo.service.implementation;

import com.ey.telefonica.rpa.mongo.model.Dispatcher;
import com.ey.telefonica.rpa.mongo.exception.*;
import com.ey.telefonica.rpa.mongo.model.DispatcherPageName;
import com.ey.telefonica.rpa.mongo.model.K2;
import com.ey.telefonica.rpa.mongo.repository.DispatcherRepository;
import com.ey.telefonica.rpa.mongo.model.lotus.IVR_;
import com.ey.telefonica.rpa.mongo.model.lotus.K2_;
import com.ey.telefonica.rpa.mongo.repository.K2Repository;
import com.ey.telefonica.rpa.mongo.repository.K2_Repository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import com.ey.telefonica.rpa.mongo.repository.IVR_Repository;
import com.ey.telefonica.rpa.mongo.service.K2Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component("K2Service")
@Transactional
public class K2ServiceImpl implements K2Service {
    private final Logger logger = LoggerFactory.getLogger(K2ServiceImpl.class);

    private K2Repository k2Repository;
    private DispatcherRepository drRepository;
    private K2_Repository k2_repository;
    private IVR_Repository ivr_repository;
    private MongoOperations mongoOperations;

    @Autowired
    public K2ServiceImpl(K2Repository k2Repository, DispatcherRepository drRespository, K2_Repository k2_repository, IVR_Repository ivr_repository, MongoOperations mongoOperations ) {
        this.k2Repository = k2Repository;
        this.drRepository = drRespository;
        this.k2_repository = k2_repository;
        this.ivr_repository = ivr_repository;
        this.mongoOperations = mongoOperations;
    }

    @Override
    public List<K2> findAll(){
        return k2Repository.findAll();
    }

    @Override
    public K2 saveK2(K2 k2) throws IVRException {

        //Siempre se guarda en la tabla K2
        logger.debug("Save K2: {}", k2);
        K2 newK2 = k2Repository.insert(k2);
       Optional<IVR_> ivr_ = ivr_repository.findOneByIdu(k2.getIdu());

        try{

            logger.error("antes del if, ANI:"+ivr_.get().getJsonField());
            if(ivr_.isPresent() && ivr_.get().getJsonField() != null){
                  //Si exite un IVR para este K2, se crea el K2 en el dispatcher
                logger.error("entro al bucle");
                  Dispatcher newDispatcher = drRepository.save(Dispatcher.createDispatcher(newK2, DispatcherPageName.A025_K2));
                  logger.debug("Inserted new dispatcher: {}", newDispatcher);

              }

        } catch (Exception e) {
            logger.error("Error searching IVR", e);
        }
            return newK2;
    }
}
