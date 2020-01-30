package com.ey.telefonica.rpa.mongo.service.implementation;

import com.ey.telefonica.rpa.mongo.exception.*;
import com.ey.telefonica.rpa.mongo.model.Dispatcher;
import com.ey.telefonica.rpa.mongo.model.DispatcherPageName;
import com.ey.telefonica.rpa.mongo.model.DispatcherStore;
import com.ey.telefonica.rpa.mongo.model.lotus.*;
import com.ey.telefonica.rpa.mongo.model.lotus.IVR_;
import com.ey.telefonica.rpa.mongo.model.lotus.K2_;
import com.ey.telefonica.rpa.mongo.repository.DispatcherRepository;
import com.ey.telefonica.rpa.mongo.repository.IVR_Repository;
import com.ey.telefonica.rpa.mongo.repository.K2_Repository;
import com.ey.telefonica.rpa.mongo.service.CallService;
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

@Component("CallService")
@Transactional

public class CallServiceImpl implements CallService {
    private final Logger logger = LoggerFactory.getLogger(CallServiceImpl.class);
    private static final FindAndModifyOptions removeFindAndModifyOptions = new FindAndModifyOptions().remove(true);

    private DispatcherRepository drRepository;
    private MongoOperations mongoOperations;
    private K2_Repository k2_repository;
    private IVR_Repository ivr_repository;




    @Autowired
    public CallServiceImpl(DispatcherRepository drRepository,
                            MongoOperations mongoOperations,K2_Repository k2_repository,  IVR_Repository ivr_repository){
        this.drRepository = drRepository;
        this.mongoOperations = mongoOperations;
        this.k2_repository = k2_repository;
        this.ivr_repository = ivr_repository;

    }

    public Optional<IVR_> findIVRById(String idu) throws IVRException {
        try {
            Optional<IVR_> ivr_ = ivr_repository.findOneByIdu(idu);
            return ivr_;
        } catch (Exception e){
            throw new IVRException(idu);
        }
    }

    public String saveB2cJsonField(CallJsonField calljsonfield) throws K2Exception {
        Optional<K2_> k2_ = k2_repository.findOneByIdu(calljsonfield.getIdu());
        try{

           Query query = new Query();

            Update update=new Update();

            update.set("jsonField", calljsonfield.getJsonField());
            query.addCriteria(Criteria.where("idu").is(calljsonfield.getIdu()));
            mongoOperations.updateFirst(query,update,IVR_.class);

        } catch (Exception e){
            throw new K2Exception(calljsonfield.getIdu());
        }

        if(k2_.isPresent()){
            return k2_.toString().replace("Optional[","").replace("]","");
        }else{
            return "";
        }
    }

}
