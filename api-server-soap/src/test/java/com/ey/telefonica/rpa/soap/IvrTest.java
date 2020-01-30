package com.ey.telefonica.rpa.soap;


import com.ey.telefonica.rpa.mongo.model.Dispatcher;
import com.ey.telefonica.rpa.mongo.model.K2;
import com.ey.telefonica.rpa.mongo.repository.DispatcherRepository;
import com.ey.telefonica.rpa.mongo.repository.K2Repository;
import com.ey.telefonica.rpa.mongo.repository.K2_Repository;
import com.ey.telefonica.rpa.mongo.repository.IVR_Repository;
import com.ey.telefonica.rpa.mongo.service.implementation.K2ServiceImpl;
import com.ey.telefonica.rpa.mongo.model.lotus.B2cIVR_AND_K2;
import com.ey.telefonica.rpa.mongo.repository.B2cIVR_AND_K2_repository;
import io.spring.guides.gs_producing_web_service.GetAgentRequest;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;


public class IvrTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private K2Repository k2Repository;
    @Mock
    private DispatcherRepository drRespository;
    @Mock
    private K2_Repository k2_repository;
    @Mock
    private IVR_Repository ivr_repository;

    @Test
    public void testIvrInsertOk() {
        K2Endpoint k2Endpoint = new K2Endpoint(new K2ServiceImpl(k2Repository, drRespository,  k2_repository,  ivr_repository ));
        GetAgentRequest newReq = new GetAgentRequest();
        newReq.setIdAgente("IDAGENTE");
        newReq.setIdu("IDU");
        Assert.assertNotNull(k2Endpoint.getAgent(newReq));


        final ArgumentCaptor<K2> captor = ArgumentCaptor.forClass(K2.class);
        verify(k2Repository).insert(captor.capture());
        K2 insertedK2 = captor.getValue();
        Assert.assertEquals( "IDAGENTE", insertedK2.getIdAgente());
        Assert.assertEquals(insertedK2.getIdu(), "IDU");
        verify(drRespository).save(any(Dispatcher.class));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testIvrInsertIduIsNull() {
        K2Endpoint k2Endpoint = new K2Endpoint(new K2ServiceImpl(k2Repository, drRespository,  k2_repository,  ivr_repository));
        GetAgentRequest newReq = new GetAgentRequest();
        newReq.setIdAgente("IDAGENTE");
        k2Endpoint.getAgent(newReq);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testIvrInsertIdClienteIsNull() {
        K2Endpoint k2Endpoint = new K2Endpoint(new K2ServiceImpl(k2Repository, drRespository,  k2_repository,  ivr_repository));
        GetAgentRequest newReq = new GetAgentRequest();
        newReq.setIdu("IDU");
        k2Endpoint.getAgent(newReq);

    }

}
