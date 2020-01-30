package com.ey.telefonica.rpa.mongo.service.implementation;

import com.ey.telefonica.rpa.mongo.exception.*;
import com.ey.telefonica.rpa.mongo.model.Dispatcher;
import com.ey.telefonica.rpa.mongo.model.DispatcherPageName;
import com.ey.telefonica.rpa.mongo.model.DispatcherStore;
import com.ey.telefonica.rpa.mongo.model.lotus.*;
import com.ey.telefonica.rpa.mongo.repository.DispatcherRepository;
import com.ey.telefonica.rpa.mongo.repository.LotusAtosRepository;
import com.ey.telefonica.rpa.mongo.repository.LotusRawRepository;
import com.ey.telefonica.rpa.mongo.repository.LotusMLRepository;
import com.ey.telefonica.rpa.mongo.service.LotusService;
import com.mongodb.client.result.DeleteResult;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component("LotusService")
@Transactional

public class LotusServiceImpl implements LotusService {
    private final Logger logger = LoggerFactory.getLogger(LotusServiceImpl.class);
    private static final FindAndModifyOptions removeFindAndModifyOptions = new FindAndModifyOptions().remove(true);

    private LotusRawRepository rawRepository;
    private LotusMLRepository mlRepository;
    private DispatcherRepository drRepository;
    private LotusAtosRepository laRepository;
    private MongoOperations mongoOperations;




    @Autowired
    public LotusServiceImpl(LotusRawRepository rawRepository,
                            LotusMLRepository mlRepository,
                            DispatcherRepository drRepository,
                            LotusAtosRepository laRepository, MongoOperations mongoOperations){
        this.rawRepository = rawRepository;
        this.mlRepository = mlRepository;
        this.drRepository = drRepository;
        this.laRepository = laRepository;
        this.mongoOperations = mongoOperations;
    }

    public List<LotusRaw> findRawAll() throws LotusMLEmptyException {
        try {
            List<LotusRaw> lotusrw = rawRepository.findAll();
            return lotusrw;
        } catch (LotusMLEmptyException e){
            throw new LotusMLEmptyException();
        }
    }

    public List<LotusML> findMLAll() throws LotusMLEmptyException {
        try {
            List<LotusML> lotusml = mlRepository.findAll();
            return lotusml;
        } catch (LotusMLEmptyException e){
            throw new LotusMLEmptyException();
        }
    }

    public Optional<LotusML> findMLById(String idLotus) throws LotusMLEmptyException {
        try {
            Optional<LotusML> lotusml = mlRepository.findOneByIdLotus(idLotus);
            return lotusml;
        } catch (LotusMLEmptyException e){
            throw new LotusMLEmptyException();
        }
    }

    public void addRawData(LotusRaw lotusraw) throws LotusRawInsertException{
        logger.debug("Return new lotusraw {}", lotusraw);
        try{
            rawRepository.insert(lotusraw);
        } catch(LotusRawInsertException e){
            throw new LotusRawInsertException();
        }

    }

    public Optional<LotusML> atosRequestURL(String idLotus, Integer idAtos, String matricula, String uf, String tipoDocumento, String numeroDocumento, String fechaRecepcion) throws LotusMLException {
        logger.debug("New atos request with idLotus: {}, idAtos: {}, matricula: {}, uf: {}, tipoDocumento: {}, numeroDocumento: {}, fechaRecepcion: {}", idLotus, idAtos, matricula, uf, tipoDocumento, numeroDocumento, fechaRecepcion);

        if (!StringUtils.isEmpty(matricula) && !StringUtils.isEmpty(uf)) {
            logger.debug("Matricula and UF not empty");
            try {
                LotusAtos lotusAtos = LotusAtos.newLotusAtos(idLotus, idAtos, matricula, uf, tipoDocumento, numeroDocumento, fechaRecepcion);

                //TO-DO --> comprobar si ya existe tipoDocumento, numeroDocumento y fechaRecepcion en lotus_ml
                List<LotusML> lotusOri = mlRepository.findAllByIdLotus(idLotus);
                LotusML ml = lotusOri.get(0);
                if (!StringUtils.isEmpty(ml.getTipoDocumento()) && !StringUtils.isEmpty(ml.getNumeroDocumento())) {
                    //TO-DO --> si hay nos quedamos con lo nuestro y les devolvemos nuestros valores
                    lotusAtos.setTipoDocumento(ml.getTipoDocumento());
                    lotusAtos.setNumeroDocumento(ml.getNumeroDocumento());
                    lotusAtos.setFechaRecepcion(ml.getFechaRecepcion());

                } else {
                    //TO-DO --> si no tenemos lo guardamos en la BBDD

                    Query query = new Query();

                    Update update=new Update();

                    update.set("tipoDocumento", tipoDocumento);
                    update.set("numeroDocumento", numeroDocumento);
                    update.set("fechaRecepcion", fechaRecepcion);
                    query.addCriteria(Criteria.where("idLotus").is(idLotus));

                    mongoOperations.updateFirst(query,update,LotusML.class);
                }
                logger.debug("Inserting new document in lotusAtos repository: {}", lotusAtos);
                laRepository.save(lotusAtos);
                Dispatcher mlDispatcher = Dispatcher.createDispatcher(lotusAtos, DispatcherPageName.A025_ATOS);
                logger.debug("Insert new dispatcher: {}", mlDispatcher);
                drRepository.save(mlDispatcher);
            } catch (Exception e) {
                logger.error("Error inserting lotusAtos", e);
            }
        }

            //Buscamos si se encuentra en idLotus
            logger.debug("Find lotusMl by lotusId: {}", idLotus);
            Optional<LotusML> lotusml = mlRepository.findOneByIdLotus(idLotus);
            logger.debug("Found? {}", lotusml);

            if (lotusml.isPresent()) {

                if (!StringUtils.isEmpty(lotusml.get().getTipoDocumento()) && !StringUtils.isEmpty(lotusml.get().getNumeroDocumento())) {
                    //TO-DO --> Como ya lo tenemos nos quedamos con nuestros valores

                } else if (!StringUtils.isEmpty(tipoDocumento) && !StringUtils.isEmpty(numeroDocumento)) {
                    //TO-DO --> si no tenemos lo guardamos en la BBDD

                    Query query = new Query();

                    Update update = new Update();
                    update.set("jsonField", tipoDocumento);   query.addCriteria(Criteria.where("idLotus").is(idLotus));

                    mongoOperations.updateFirst(query, update, LotusML.class);


                    update.set("tipoDocumento", tipoDocumento);
                    update.set("numeroDocumento", numeroDocumento);
                    update.set("fechaRecepcion", fechaRecepcion);
                    query.addCriteria(Criteria.where("idLotus").is(idLotus));

                    mongoOperations.updateFirst(query, update, LotusML.class);


                    List<LotusML> lotusOri = mlRepository.findAllByIdLotus(idLotus);
                    LotusML lotusml_updated = lotusOri.get(0);

                    //Si no teniamos tipo y numero de documento y ellos nos lo pasan lo introducimos creamos de nuevo entrada de CATEG
                    Dispatcher mlDispatcher = Dispatcher.createDispatcher(lotusml_updated, DispatcherPageName.A025_CATEG);
                    drRepository.save(mlDispatcher);
                }

            }

            return lotusml;

    }


    public void saveLotusCategorized(LotusML lotusml) throws LotusMLException {
        logger.debug("Save lotus categorized: {}", lotusml);
        try {
            mlRepository.insert(lotusml);
            Dispatcher mlDispatcher = Dispatcher.createDispatcher(lotusml, DispatcherPageName.A025_CATEG);
            logger.debug("Insert new dispatcher: {}", mlDispatcher);
            drRepository.save(mlDispatcher);

        } catch (Exception e) {
            logger.error("Error saving LotusMl", e);
        }
    }


    public void saveLotusUrl(LotusUrl lotusurl) throws LotusMLException {
        List<LotusML> lotusOri = mlRepository.findAllByIdLotus(lotusurl.getIdLotus());
        try{
            LotusML ml = lotusOri.get(0);

            ml.setUrl(lotusurl.getUrl());
            mlRepository.save(ml);
        } catch (LotusMLException e){
            throw new LotusMLException(lotusurl.getIdLotus());
        }
    }


    public void saveLotusKeyField(LotusKeyField lotuskeyfield) throws LotusMLException {
        List<LotusML> lotusOri = mlRepository.findAllByIdLotus(lotuskeyfield.getIdLotus());
        try{
            LotusML ml = lotusOri.get(0);

            ml.setKeyField(lotuskeyfield.getKeyField());
            mlRepository.save(ml);
        } catch (LotusMLException e){
            throw new LotusMLException(lotuskeyfield.getIdLotus());
        }
    }

    public void saveLotusTypeNumAndDate(LotusTypeNumAndDate lotustypenumanddate) throws LotusMLException {
        List<LotusML> lotusOri = mlRepository.findAllByIdLotus(lotustypenumanddate.getIdLotus());
        try{
            LotusML ml = lotusOri.get(0);

            ml.setNumeroDocumento(lotustypenumanddate.getNumeroDocumento());
            ml.setTipoDocumento(lotustypenumanddate.getTipoDocumento());
            ml.setFechaRecepcion(lotustypenumanddate.getFechaRecepcion());
            mlRepository.save(ml);
        } catch (LotusMLException e){
            throw new LotusMLException(lotustypenumanddate.getIdLotus());
        }
    }



    public Long updateDaysLotusML (Integer preservedDays) throws LotusMLEmptyException, LotusStoreException {
        logger.debug("Find and remove LotusML");

        try {
            Criteria criteria = new Criteria();
            Query query = new Query(criteria);

            // Me quedo con los que tienen fecha menor a ahora-preservedDays para eliminarlos
            query.addCriteria(Criteria.where("createdTs").lte(Instant.now().getEpochSecond() - preservedDays * 86400));

            //List<LotusML> listalotusml = mongoOperations.findAllAndRemove(query, LotusML.class);
            DeleteResult listalotusml = mongoOperations.remove(query, LotusML.class);

            logger.debug("Retrieved LotusML: {}", listalotusml);

            if (listalotusml == null) {
                logger.debug("No listalotusml");
                throw new LotusMLEmptyException();
            }

            return listalotusml.getDeletedCount();
        } catch (LotusMLEmptyException lee) {
            throw lee;
        } catch (Exception e) {
            logger.error("Error finding or removing in LotusML", e);
            throw new LotusStoreException(e);
        }
    }


    public Long updateDaysLotusRaw (Integer preservedDays) throws LotusRawEmptyException, LotusStoreException {
        logger.debug("Find and remove LotusRaw");

        try {
            Criteria criteria = new Criteria();
            Query query = new Query(criteria);

            // Me quedo con los que tienen fecha menor a ahora-preservedDays para eliminarlos
            query.addCriteria(Criteria.where("createdTs").lte(Instant.now().getEpochSecond() - preservedDays * 86400));

            //List<LotusRaw> listalotusraw = mongoOperations.findAllAndRemove(query, LotusRaw.class);
            DeleteResult listalotusraw = mongoOperations.remove(query, LotusRaw.class);

            logger.debug("Retrieved LotusRaw: {}", listalotusraw);

            if (listalotusraw == null) {
                logger.debug("No listalotusraw");
                throw new LotusRawEmptyException();
            }

            return listalotusraw.getDeletedCount();
        } catch (LotusRawEmptyException lre) {
            throw lre;
        } catch (Exception e) {
            logger.error("Error finding or removing in LotusRaw", e);
            throw new LotusStoreException(e);
        }
    }

        public Long updateDaysLotusAtos (Integer preservedDays) throws LotusAtosEmptyException, LotusStoreException{
            logger.debug("Find and remove Lotusatos");

            try {
                Criteria criteria = new Criteria();
                Query query = new Query(criteria);


                // Me quedo con los que tienen fecha menor a ahora-preservedDays para eliminarlos
                query.addCriteria(Criteria.where("createdTs").lte((Instant.now().getEpochSecond()-preservedDays*86400)*1000));

                //List<LotusAtos> listalotusatos = mongoOperations.findAllAndRemove(query, LotusAtos.class);
                DeleteResult listalotusatos = mongoOperations.remove(query, LotusAtos.class);

                logger.debug("Retrieved LotusAtos: {}", listalotusatos);

                if (listalotusatos == null) {
                    logger.debug("No listalotusatos");
                    throw new LotusMLEmptyException();
                }

                return listalotusatos.getDeletedCount();
            } catch (LotusAtosEmptyException lae) {
                throw lae;
            } catch (Exception e) {
                logger.error("Error finding or removing in LotusAtos", e);
                throw new LotusStoreException(e);
            }


    }



}
