package com.ey.telefonica.rpa.mongo.model.lotus;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "k2")
public class K2_ {



    @ApiModelProperty(required=true)
    private String idu;

    private String idAgente;


    @ApiModelProperty(hidden=true)
    private long createdTs;

    @Override
    public String toString() {
        return "K2{" +
                "idu='" + idu + '\'' +
                ", idAgente='" + idAgente + '\'' +
                ", createdTs=" + createdTs +
                '}';
    }

    public K2_() {
        this.createdTs = Instant.now().toEpochMilli();
    }

}
