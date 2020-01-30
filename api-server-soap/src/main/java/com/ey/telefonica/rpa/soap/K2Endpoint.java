package com.ey.telefonica.rpa.soap;

import com.ey.telefonica.rpa.audit.BigShaqPerformance;
import com.ey.telefonica.rpa.mongo.model.K2;
import com.ey.telefonica.rpa.mongo.service.K2Service;
import io.spring.guides.gs_producing_web_service.Agent;
import io.spring.guides.gs_producing_web_service.GetAgentRequest;
import io.spring.guides.gs_producing_web_service.GetAgentResponse;
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
public class K2Endpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
    private final Logger logger = LoggerFactory.getLogger(K2Endpoint.class);

    private K2Service k2Service;
    private final BigShaqPerformance bigShaqPerformance = BigShaqPerformance.getInstance();


    @Autowired
    public K2Endpoint(K2Service k2Service) {
        this.k2Service = k2Service;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAgentRequest")
    @ResponsePayload
    public GetAgentResponse getAgent(@RequestPayload GetAgentRequest request) {
        long before = System.currentTimeMillis();
        logger.debug("Get agent request: {}", ReflectionToStringBuilder.toString(request));
        GetAgentResponse response = new GetAgentResponse();
        try {
            validateRequest(request);
            K2 newK2 = k2Service.saveK2(new K2(request.getIdu(), request.getIdAgente()));
            logger.debug("Successfully inserted K2: {}", newK2);
            Agent responseAgent = new Agent();
            responseAgent.setIdAgente(request.getIdAgente());
            responseAgent.setIdu(request.getIdu());
            response.setAgent(responseAgent);

        } catch (IllegalArgumentException iae) {
            logger.error("Illegal argument", iae);
            throw iae; // TODO https://www.javaspringclub.com/spring-soap-web-services-add-soap-fault-exception-handling-part-iii/
        } catch(Exception e) {
            logger.error("Exception", e);
            throw e; // TODO https://www.javaspringclub.com/spring-soap-web-services-add-soap-fault-exception-handling-part-iii/
        }
        logger.debug("Response: {}", ReflectionToStringBuilder.toString(response));
        bigShaqPerformance.newResponse(System.currentTimeMillis() - before);
        return response;
    }

    private void validateRequest(GetAgentRequest request) {
        // TODO REAL validation here:
        if (request == null) {
            throw new IllegalArgumentException("Request is null");
        } else if (StringUtils.isEmpty(request.getIdAgente())){
            throw new IllegalArgumentException("IdAgente is null");
        } else if (StringUtils.isEmpty(request.getIdu())){
            throw new IllegalArgumentException("IDU is null");
        }
    }
}