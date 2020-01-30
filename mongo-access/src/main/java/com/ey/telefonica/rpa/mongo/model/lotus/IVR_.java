package com.ey.telefonica.rpa.mongo.model.lotus;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "ivr")
public class IVR_ {



    @ApiModelProperty(required=true)
    private String idu;
    private String jsonField;


    private String ani;
    private String aniValidado;
    private String cuc;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String etiquetaPa;
    private String tipoCliente;
    private String segmentoMkt;
    private String dni;



    @ApiModelProperty(hidden=true)
    private long createdTs;

    public String getIdu() {
        return idu;
    }

    public void setIdu(String idu) {
        this.idu = idu;
    }

    public String getJsonField() {
        return jsonField;
    }

    public void setJsonField(String jsonField) {
        this.jsonField = jsonField;
    }

    public String getAni() {
        return ani;
    }

    public void setAni(String ani) {
        this.ani = ani;
    }

    public String getAniValidado() {
        return aniValidado;
    }

    public void setAniValidado(String aniValidado) {
        this.aniValidado = aniValidado;
    }

    public String getCuc() {
        return cuc;
    }

    public void setCuc(String cuc) {
        this.cuc = cuc;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getEtiquetaPa() {
        return etiquetaPa;
    }

    public void setEtiquetaPa(String etiquetaPa) {
        this.etiquetaPa = etiquetaPa;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getSegmentoMkt() {
        return segmentoMkt;
    }

    public void setSegmentoMkt(String segmentoMkt) {
        this.segmentoMkt = segmentoMkt;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public long getCreatedTs() {
        return createdTs;
    }


    public void setCreatedTs(long createdTs) {
        this.createdTs = createdTs;
    }

    public IVR_() {
        this.createdTs = Instant.now().toEpochMilli();
    }


    @Override
    public String toString() {
        return "IVR{" +
                "idu='" + idu + '\'' +
                ",jsonField='" + jsonField + '\'' +
                ",ani='" + ani + '\'' +
                ",aniValidado='" + aniValidado + '\'' +
                ",cuc='" + cuc + '\'' +
                ",tipoIdentificacion='" + tipoIdentificacion + '\'' +
                ",numeroIdentificacion='" + numeroIdentificacion + '\'' +
                ",etiquetaPa='" + etiquetaPa + '\'' +
                ",tipoCliente='" + tipoCliente + '\'' +
                ",segmentoMkt='" + segmentoMkt + '\'' +
                ",dni='" + dni + '\'' +
                ",createdTs=" + createdTs +
                '}';
    }

}
