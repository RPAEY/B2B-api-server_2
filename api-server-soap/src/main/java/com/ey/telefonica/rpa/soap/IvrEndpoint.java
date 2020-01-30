package com.ey.telefonica.rpa.soap;

import com.ey.telefonica.rpa.audit.BigShaqPerformance;
import com.ey.telefonica.rpa.mongo.model.ivr.IVR;
import com.ey.telefonica.rpa.mongo.service.IVRService;
import io.spring.guides.gs_producing_web_service.Call;
import io.spring.guides.gs_producing_web_service.GetCallRequest;
import io.spring.guides.gs_producing_web_service.GetCallResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class IvrEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
    private final Logger logger = LoggerFactory.getLogger(IvrEndpoint.class);

    private IVRService ivrService;
    private final BigShaqPerformance bigShaqPerformance = BigShaqPerformance.getInstance();

    @Autowired
    public IvrEndpoint(IVRService ivrService) {
        this.ivrService = ivrService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCallRequest")
    @ResponsePayload
    public GetCallResponse getCall(@RequestPayload GetCallRequest request) {
        long before = System.currentTimeMillis();
        logger.debug("getCall REQUEST: {}", ReflectionToStringBuilder.toString(request));
        GetCallResponse response = new GetCallResponse();

        try {
            validateRequest(request);
            logger.debug("REQUEST:", request);
            IVR ivrRequest = IVR.fromStringsWithPipes(request.getClaves(), request.getValores());
            IVR newIvr = ivrService.saveIVR(ivrRequest);
            logger.debug("Successfully inserted IVR: {}", newIvr);
        } catch (IllegalArgumentException iae) {
            logger.error("Illegal argument", iae);
            throw iae; // TODO https://www.javaspringclub.com/spring-soap-web-services-add-soap-fault-exception-handling-part-iii/
        } catch(Exception e) {
            logger.error("Exception", e);
            throw e; // TODO https://www.javaspringclub.com/spring-soap-web-services-add-soap-fault-exception-handling-part-iii/
        }
        Call callResponse = new Call();
        callResponse.setClaves(request.getClaves());
        callResponse.setValores(request.getValores());
        response.setCall(callResponse);
        logger.debug("Response: {}", ReflectionToStringBuilder.toString(response));
        bigShaqPerformance.newResponse(System.currentTimeMillis() - before);
        return response;

    }

    private void validateRequest(GetCallRequest request) throws IllegalArgumentException {
        // TODO REAL validation here:
        if (request == null) {
            throw new IllegalArgumentException("Request is null");
        } else if (StringUtils.isEmpty(request.getClaves())){
            throw new IllegalArgumentException("Claves is null");
        } else if (StringUtils.isEmpty(request.getValores())){
            throw new IllegalArgumentException("Valores is null");
        }

    }

}