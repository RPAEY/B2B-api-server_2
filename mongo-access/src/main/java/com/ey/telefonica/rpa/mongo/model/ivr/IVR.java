package com.ey.telefonica.rpa.mongo.model.ivr;

import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "ivr")
public class IVR {
    private static final Logger logger = LoggerFactory.getLogger(IVR.class);

    private static final Map<String, Field> KEY_MAP = new HashMap<>();

    static {
        Arrays.stream(IVR.class.getDeclaredFields()).filter(field -> field.isAnnotationPresent(IvrKeyName.class)).forEach(field -> KEY_MAP.put(field.getAnnotation(IvrKeyName.class).keyName(), field));
    }

    @IvrKeyName(keyName = "IDU")
    private String idu;
    @IvrKeyName(keyName = "ANI")
    private String ani;
    @IvrKeyName(keyName = "ANI_VALIDADO")
    private String aniValidado;
    @IvrKeyName(keyName = "CUC")
    private String cuc;
    @IvrKeyName(keyName = "TIPO_IDENTIFICACION")
    private String tipoIdentificacion;
    @IvrKeyName(keyName = "NUMERO_IDENTIFICACION")
    private String numeroIdentificacion;
    @IvrKeyName(keyName = "ETIQUETA_PA")
    private String etiquetaPa;
    @IvrKeyName(keyName = "TIPO_CLIENTE")
    private String tipoCliente;
    @IvrKeyName(keyName = "SEGMENTO_MKT")
    private String segmentoMkt;
    @IvrKeyName(keyName = "DNI")
    private String dni;

    @ApiModelProperty(hidden=true)
    private long createdTs;

    public IVR() { this.createdTs = Instant.now().toEpochMilli(); }

    public static IVR fromStringsWithPipes(String claves, String valores) throws IllegalArgumentException {
        logger.debug("Mapping keys {} with values {}", claves, valores);
        IVR ivr = new IVR();
        String[] clavesSplitted = claves.split("\\|");
        String[] valoresSplitted = valores.split("\\|");
        if (clavesSplitted.length != valoresSplitted.length) {
            throw new IllegalArgumentException("Claves and Valores size mismatch");
        }
        Map<String, String> keyValueMap = new HashMap<>();

        for (int i=0; i < clavesSplitted.length; i++) {
            keyValueMap.put(clavesSplitted[i].trim(), valoresSplitted[i].trim());
            Field field = KEY_MAP.get(clavesSplitted[i].trim());
            if (field == null) {
                throw new IllegalArgumentException("Key: " + clavesSplitted[i].trim() + " not found");
            }
            try {
                field.set(ivr, valoresSplitted[i].trim());
            } catch (IllegalAccessException e) {
                logger.error("Exception", e);
            }

        }

        logger.debug("New ivr: {}", ivr);
        return ivr;

    }

    public String getIdu() {
        return idu;
    }

    public void setIdu(String idu) {
        this.idu = idu;
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

    @Override
    public String toString() {
        return "IVR{" +
                "idu='" + idu + '\'' +
                ", ani='" + ani + '\'' +
                ", aniValidado='" + aniValidado + '\'' +
                ", cuc='" + cuc + '\'' +
                ", tipoIdentificacion='" + tipoIdentificacion + '\'' +
                ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
                ", etiquetaPa='" + etiquetaPa + '\'' +
                ", tipoCliente='" + tipoCliente + '\'' +
                ", segmentoMkt='" + segmentoMkt + '\'' +
                ", dni='" + dni + '\'' +
                ", createdTs=" + createdTs +
                '}';
    }
}