package com.ey.telefonica.rpa.mongo.service;

import com.ey.telefonica.rpa.mongo.exception.*;
import com.ey.telefonica.rpa.mongo.model.lotus.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("CallService")
public interface CallService {

    public Optional<IVR_> findIVRById(String idu) throws IVRException;

    public String saveB2cJsonField(CallJsonField b2cjsonfield) throws K2Exception;
}
