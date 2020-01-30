package com.ey.telefonica.rpa.rest;



import java.util.concurrent.atomic.AtomicLong;
import java.util.List;
import javax.validation.Valid;

import java.lang.String;

import com.ey.telefonica.rpa.audit.BigShaqPerformance;
import com.ey.telefonica.rpa.mongo.exception.DispatcherEmptyException;
import com.ey.telefonica.rpa.mongo.exception.DispatcherStoreException;
import com.ey.telefonica.rpa.mongo.model.Dispatcher;
import com.ey.telefonica.rpa.mongo.service.DispatcherService;
import com.ey.telefonica.rpa.mongo.exception.DispatcherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.*;

import org.springframework.http.ResponseEntity;



@RestController
@RequestMapping("dispatchers")
@Api(value = "Dispatcher Controller", description = "Operations pertaining to Dispatchers.")
public class DispatcherController {
    private final Logger logger = LoggerFactory.getLogger(DispatcherController.class);
    private final BigShaqPerformance bigShaqPerformance = BigShaqPerformance.getInstance();


    @Autowired
    private DispatcherService dispatcherService;
    private Dispatcher dispatcher;

    @ApiOperation(value = "Returns all list of pending tasks.", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Dispatcher>> dispatchers(){
        logger.info("Get allDispatchers");
        return ResponseEntity.ok(dispatcherService.findAll());
    }

    @ApiOperation(value = "Update dispatcher doc.")
    @RequestMapping(method=RequestMethod.PATCH)
    public  ResponseEntity<Dispatcher> updateDispatcher(@RequestBody @Valid Dispatcher dispatcher){
        logger.info("Udpate Dispatcher");
        dispatcherService.updateDispatcher(dispatcher);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Returns one task for polling request and remove from queue.", response = Dispatcher.class)
    @RequestMapping(value="/poll", method = RequestMethod.GET)
    public ResponseEntity<Dispatcher> dispatcherPolling2()  throws DispatcherStoreException, DispatcherEmptyException {
        long before = System.currentTimeMillis();
        logger.info("Get dispatcherPolling");
        Dispatcher result = dispatcherService.findAndRemoveDispatcher();
        logger.debug("Return dispatcher: {}", result);
        bigShaqPerformance.newResponse(System.currentTimeMillis() - before);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value="/morepacher", method = RequestMethod.GET)
    public ResponseEntity<Dispatcher> dispatcherPolling3()  throws DispatcherStoreException, DispatcherEmptyException {
        long before = System.currentTimeMillis();
        logger.info("Get dispatcherPolling");
        Dispatcher result = dispatcherService.findAndRemoveMorePacher();
        logger.debug("Return dispatcher: {}", result);
        bigShaqPerformance.newResponse(System.currentTimeMillis() - before);
        return ResponseEntity.ok(result);
    }


}
