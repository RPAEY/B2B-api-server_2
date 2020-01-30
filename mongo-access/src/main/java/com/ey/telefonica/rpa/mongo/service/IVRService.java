package com.ey.telefonica.rpa.mongo.service;

import com.ey.telefonica.rpa.mongo.model.ivr.IVR;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("IVRService")
public interface  IVRService {
    public List<IVR> findAll();

    //public IVR findByIVRId(String ivrId);

    public IVR saveIVR(IVR ivr);

    //public void updateIVR(IVR ivr);

    //public void deleteIVR(String ivrId);

    //public Map<String, String> defineObjFromText(String codes, String values);
}
