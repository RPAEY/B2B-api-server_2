package com.ey.telefonica.rpa.mongo.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "k2")
public class K2 {
    private String idu;
    private String idAgente;

    @ApiModelProperty(hidden=true)
    private long createdTs;

    public K2() { this.createdTs = Instant.now().toEpochMilli(); }

    public K2(String idu, String idAgente) {
        this();
        this.idu = idu;
        this.idAgente = idAgente;
    }

    public String getIdu() {
        return idu;
    }

    public void setIdu(String idu) {
        this.idu = idu;
    }

    public String getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(String idAgente) {
        this.idAgente = idAgente;
    }

    @Override
    public String toString() {
        return "K2{" +
                "idu='" + idu + '\'' +
                ", idAgente='" + idAgente + '\'' +
                '}';
    }
}
