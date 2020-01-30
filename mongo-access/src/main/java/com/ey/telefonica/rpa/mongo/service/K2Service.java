package com.ey.telefonica.rpa.mongo.service;

import com.ey.telefonica.rpa.mongo.model.K2;
import org.springframework.stereotype.Service;
import com.ey.telefonica.rpa.mongo.exception.*;

import java.util.List;

@Service("K2Service")
public interface K2Service {
    K2 saveK2(K2 k2) throws IVRException;

    public List<K2> findAll();
}
