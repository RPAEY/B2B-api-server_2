package com.ey.telefonica.rpa.rest;


import com.ey.telefonica.rpa.audit.BigShaqPerformance;
import com.ey.telefonica.rpa.mongo.model.lotus.*;
import com.ey.telefonica.rpa.mongo.service.CallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("call")
@Api(value = "Call Controller", description = "Operations pertaining to Calls.")
public class CallController {
    private final Logger logger = LoggerFactory.getLogger(CallController.class);
    private final BigShaqPerformance bigShaqPerformance = BigShaqPerformance.getInstance();



    @Autowired
    private CallService callService;


    @ApiOperation(value = "Returns all list of pending tasks.", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )


    @RequestMapping(value="/getivr/{idu}", method=RequestMethod.GET)
    public Optional<IVR_> getIVRStored(@PathVariable("idu") String idu){

        return callService.findIVRById(idu);
    }


    @ApiOperation(value = "Update JsonField to IVR Document.")
    @RequestMapping(value="/setJsonField", method=RequestMethod.PATCH)
    public String updateJsonField(@RequestBody CallJsonField calljsonfield){
        long before = System.currentTimeMillis();
        logger.info("Update JsonField to IVR Document: {}", calljsonfield.toString());

        return callService.saveB2cJsonField(calljsonfield);

    }


}
