package com.ey.telefonica.rpa.mongo.service.implementation;

import com.ey.telefonica.rpa.mongo.exception.DispatcherEmptyException;
import com.ey.telefonica.rpa.mongo.exception.DispatcherSaveException;
import com.ey.telefonica.rpa.mongo.exception.DispatcherStoreEmptyException;
import com.ey.telefonica.rpa.mongo.model.Dispatcher;
import com.ey.telefonica.rpa.mongo.exception.DispatcherStoreException;
import com.ey.telefonica.rpa.mongo.model.DispatcherStore;
import com.ey.telefonica.rpa.mongo.repository.DispatcherRepository;
import com.ey.telefonica.rpa.mongo.repository.DispatcherStoreRepository;
import com.ey.telefonica.rpa.mongo.service.DispatcherService;
import com.mongodb.client.result.DeleteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Component("DispatcherService")
@Transactional

public class DispatcherServiceImpl implements DispatcherService {
    private final Logger logger = LoggerFactory.getLogger(DispatcherServiceImpl.class);

    private static final FindAndModifyOptions removeFindAndModifyOptions = new FindAndModifyOptions().remove(true);
    private static final Query findLastOneQuery = new Query().limit(1).with(Sort.by(Sort.Order.asc("_id")));


    private DispatcherRepository dispatcherRepository;
    private DispatcherStoreRepository dpsRepository;
    private MongoOperations mongoOperations;


    @Autowired
    public DispatcherServiceImpl(DispatcherRepository dispatcherRepository,
                                 DispatcherStoreRepository dpsRepository, MongoOperations mongoOperations){
        this.dispatcherRepository = dispatcherRepository;
        this.dpsRepository = dpsRepository;
        this.mongoOperations = mongoOperations;
    }

    public List<Dispatcher> findAll() {
        List<Dispatcher> dispatcher = dispatcherRepository.findAll();
        return dispatcher;
    }

    public void updateDispatcher(Dispatcher dispatcher) throws DispatcherSaveException {
        try{
            dispatcherRepository.save(dispatcher);
        } catch (DispatcherSaveException e) {
            throw new DispatcherSaveException();
        }

    }

    public Dispatcher findAndRemoveDispatcher()  throws DispatcherEmptyException, DispatcherStoreException {
        logger.debug("Find and remove dispatcher");

        try {
            Dispatcher retrievedDispatcher = mongoOperations.findAndRemove(findLastOneQuery, Dispatcher.class);
            //Dispatcher retrievedDispatcher = mongoOperations.findAndModify(findLastOneQuery, new Update(), removeFindAndModifyOptions, Dispatcher.class);
            logger.debug("Retrieved dispatcher: {}", retrievedDispatcher);

            if (retrievedDispatcher == null) {
                logger.debug("No more dispatchers to serve");
                throw new DispatcherEmptyException();
            }
            DispatcherStore dps = DispatcherStore.createDispatcherStore(retrievedDispatcher);
            logger.debug("New dispatcher store: {}", dps);
            dpsRepository.insert(dps);
            return retrievedDispatcher;
        } catch (DispatcherEmptyException dee) {
            throw dee;
        } catch (Exception e) {
            logger.error("Error finding or removing dispatcher", e);
            throw new DispatcherStoreException(e);
        }

    }

    public Long findAndRemoveDispatcherStore(Integer preservedDays)  throws DispatcherStoreEmptyException, DispatcherStoreException {
        logger.debug("Find and remove dispatcher_store");

        try {

            Criteria criteria = new Criteria();
            Query query = new Query(criteria);

            // Me quedo con los que tienen fecha menor a ahora-preservedDays para eliminarlos
            query.addCriteria(Criteria.where("disptacher.createdTs").lte((Instant.now().getEpochSecond()-preservedDays*86400)*1000));

            //List<DispatcherStore> retrievedDispatcherStore = mongoOperations.findAllAndRemove(query, DispatcherStore.class);
            DeleteResult retrievedDispatcherStore = mongoOperations.remove(query, DispatcherStore.class);

            logger.debug("Retrieved dispatcher_store: {}", retrievedDispatcherStore);

            if (retrievedDispatcherStore == null) {
                logger.debug("No more dispatchers_store to serve");
                throw new DispatcherStoreEmptyException();
            }

            return retrievedDispatcherStore.getDeletedCount();
        } catch (DispatcherStoreEmptyException dse) {
            throw dse;
        } catch (Exception e) {
            logger.error("Error finding or removing dispatcher_store", e);
            throw new DispatcherStoreException(e);
        }

    }




    public Dispatcher findAndRemoveMorePacher()  throws DispatcherEmptyException, DispatcherStoreException {
        logger.debug("Find and remove dispatcher");

        try {
            Criteria criteria = new Criteria();
            criteria.orOperator(Criteria.where("Pagename").is("A025_ATOS"),Criteria.where("Pagename").is("A025_CATEG"));
            Query query = new Query(criteria);
            query.limit(1).with(Sort.by(Sort.Order.asc("_id")));

            Dispatcher retrievedDispatcher = mongoOperations.findAndRemove(query, Dispatcher.class);
            //Dispatcher retrievedDispatcher = mongoOperations.findAndModify(findLastOneQuery, new Update(), removeFindAndModifyOptions, Dispatcher.class);
            logger.debug("Retrieved dispatcher: {}", retrievedDispatcher);

            if (retrievedDispatcher == null) {
                logger.debug("No more dispatchers to serve");
                throw new DispatcherEmptyException();
            }
            DispatcherStore dps = DispatcherStore.createDispatcherStore(retrievedDispatcher);
            logger.debug("New dispatcher store: {}", dps);
            dpsRepository.insert(dps);
            return retrievedDispatcher;
        } catch (DispatcherEmptyException dee) {
            throw dee;
        } catch (Exception e) {
            logger.error("Error finding or removing dispatcher", e);
            throw new DispatcherStoreException(e);
        }

    }

}
