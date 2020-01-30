package com.ey.telefonica.rpa.mongo.service.implementation;


import com.ey.telefonica.rpa.mongo.model.Dispatcher;
import com.ey.telefonica.rpa.mongo.model.DispatcherPageName;
import com.ey.telefonica.rpa.mongo.model.ivr.IVR;
import com.ey.telefonica.rpa.mongo.model.lotus.IVR_;
import com.ey.telefonica.rpa.mongo.repository.DispatcherRepository;
import com.ey.telefonica.rpa.mongo.repository.IVRRepository;
import com.ey.telefonica.rpa.mongo.service.IVRService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component("IVRService")
@Transactional

public class IVRServiceImpl implements IVRService {
    private final Logger logger = LoggerFactory.getLogger(IVRServiceImpl.class);

    private IVRRepository ivrRepository;
    private DispatcherRepository drRepository;




    @Autowired
    public IVRServiceImpl(IVRRepository ivrRepository, DispatcherRepository drRepository){
        this.ivrRepository = ivrRepository;
        this.drRepository = drRepository;
    }
/*
    public IVR findByIVRId(String ivrId) {
        Optional<IVR> ivr = ivrRepository.findOneByIvrId(ivrId);
        if (ivr.isPresent()) {
            log.debug(String.format("Read ivrId '{}'", ivrId));
            return ivr.get();
        }else
            throw new IVRException(ivrId);
    }
*/
    public List<IVR> findAll() {
        List<IVR> ivr = ivrRepository.findAll();
        return ivr;
    }

    public IVR saveIVR(IVR ivr) {
        logger.debug("Save Ivr: {}", ivr);
        IVR newIvr = ivrRepository.insert(ivr);
        logger.debug("Inserted new IVR: {}", newIvr);
        Dispatcher newDispatcher = drRepository.save(Dispatcher.createDispatcher(newIvr, DispatcherPageName.A025_IVR));
        logger.debug("Inserted new Dispatcher: {}", newDispatcher);
        return newIvr;
    }


/*
    public void updateIVR(IVR ivr) {
        ivrRepository.save(ivr);
    }

    public void deleteIVR(String ivrId) {
        ivrRepository.removeByIvrId(ivrId);
    }
*/
}
