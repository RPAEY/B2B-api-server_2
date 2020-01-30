package com.ey.telefonica.rpa.mongo.model.lotus;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "lotus_ml")
public class LotusML {


    @Id
    @ApiModelProperty(required=true)
    private String idLotus;

    //desde Bots
    @ApiModelProperty(hidden=true)
    private String url;
    @ApiModelProperty(hidden=true)
    private String keyField;
    /*
    @ApiModelProperty(hidden=true)a
    private Integer flag_url;
    */

    private String sender;
    private String to;
    private String copies;
    private String hiddenCopies;
    private String fecha_envio;
    private List<String> dir_cc;
    private String dir_pos;
    private String subject;
    private String body;
    private String motivo;
    private String tipoDocumento;
    private String numeroDocumento;
    private String fechaRecepcion;

    private Integer id_adjunto;
    private List<String> numero_fijo;
    private List<String> numero_movil;
    private List<String> cif;
    private List<String> num_gea;
    private List<String> num_heco;
    private List<String> num_factura_fija;
    private List<String> num_factura_movil;
    private String pedido_movil;
    private String rep_terminal;
    private String sac_averia;
    private List<String> sam_averia;
    private List<String> espejo_averia;
    private List<String> sirio_averia;
    private String movil_averia;
    private List<String>  imei;
    private Integer flag_analyzed;
    private List<String> nif;
    private List<String> dni;
    private List<String> nie;

    @Override
    public String toString() {
        return "LotusML{" +
                "idLotus='" + idLotus + '\'' +
                ", url='" + url + '\'' +
                ", keyField='" + keyField + '\'' +
                ", sender='" + sender + '\'' +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                ", fechaRecepcion='" + fechaRecepcion + '\'' +
                ", to='" + to + '\'' +
                ", copies='" + copies + '\'' +
                ", hiddenCopies='" + hiddenCopies + '\'' +
                ", fecha_envio='" + fecha_envio + '\'' +
                ", dir_cc=" + dir_cc +
                ", dir_pos='" + dir_pos + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", motivo='" + motivo + '\'' +
                ", id_adjunto=" + id_adjunto +
                ", numero_fijo=" + numero_fijo +
                ", numero_movil=" + numero_movil +
                ", cif=" + cif +
                ", num_gea=" + num_gea +
                ", num_heco=" + num_heco +
                ", num_factura_fija=" + num_factura_fija +
                ", num_factura_movil=" + num_factura_movil +
                ", pedido_movil='" + pedido_movil + '\'' +
                ", rep_terminal='" + rep_terminal + '\'' +
                ", sac_averia='" + sac_averia + '\'' +
                ", sam_averia=" + sam_averia +
                ", espejo_averia=" + espejo_averia +
                ", sirio_averia=" + sirio_averia +
                ", movil_averia='" + movil_averia + '\'' +
                ", imei=" + imei +
                ", flag_analyzed=" + flag_analyzed +
                ", nif=" + nif +
                ", nif=" + dni +
                ", nif=" + nie +
                ", createdTs=" + createdTs +
                '}';
    }

    @ApiModelProperty(hidden=true)
    private long createdTs;

    public LotusML() {
        this.createdTs = Instant.now().toEpochMilli();
    }

    public String getIdLotus() {
        return idLotus;
    }

    public void setIdLotus(String id_lotus) {
        this.idLotus = id_lotus;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(String fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public List<String> getDir_cc() {
        return dir_cc;
    }

    public void setDir_cc(List<String> dir_cc) {
        this.dir_cc = dir_cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyField() {
        return keyField;
    }

    public void setKeyField(String keyField) {
        this.keyField = keyField;
    }

    public Integer getId_adjunto() {
        return id_adjunto;
    }

    public void setId_adjunto(Integer id_adjunto) {
        this.id_adjunto = id_adjunto;
    }

    public List<String> getNumero_fijo() {
        return numero_fijo;
    }

    public void setNumero_fijo(List<String> numero_fijo) {
        this.numero_fijo = numero_fijo;
    }

    public List<String> getNumero_movil() {
        return numero_movil;
    }

    public void setNumero_movil(List<String> numero_movil) {
        this.numero_movil = numero_movil;
    }

    public List<String> getCif() {
        return cif;
    }

    public void setCif(List<String> cif) {
        this.cif = cif;
    }

    public List<String> getNum_gea() {
        return num_gea;
    }

    public void setNum_gea(List<String> num_gea) {
        this.num_gea = num_gea;
    }

    public List<String> getNum_heco() {
        return num_heco;
    }

    public void setNum_heco(List<String> num_heco) {
        this.num_heco = num_heco;
    }

    public List<String> getNum_factura_fija() {
        return num_factura_fija;
    }

    public void setNum_factura_fija(List<String> num_factura_fija) {
        this.num_factura_fija = num_factura_fija;
    }

    public List<String> getNum_factura_movil() {
        return num_factura_movil;
    }

    public void setNum_factura_movil(List<String> num_factura_movil) {
        this.num_factura_movil = num_factura_movil;
    }

    public String getPedido_movil() {
        return pedido_movil;
    }

    public void setPedido_movil(String pedido_movil) {
        this.pedido_movil = pedido_movil;
    }

    public String getRep_terminal() {
        return rep_terminal;
    }

    public void setRep_terminal(String rep_terminal) {
        this.rep_terminal = rep_terminal;
    }

    public String getSac_averia() {
        return sac_averia;
    }

    public void setSac_averia(String sac_averia) {
        this.sac_averia = sac_averia;
    }

    public List<String> getSam_averia() {
        return sam_averia;
    }

    public void setSam_averia(List<String> sam_averia) {
        this.sam_averia = sam_averia;
    }

    public List<String> getEspejo_averia() {
        return espejo_averia;
    }

    public void setEspejo_averia(List<String> espejo_averia) {
        this.espejo_averia = espejo_averia;
    }

    public List<String> getSirio_averia() {
        return sirio_averia;
    }

    public void setSirio_averia(List<String> sirio_averia) {
        this.sirio_averia = sirio_averia;
    }

    public String getMovil_averia() {
        return movil_averia;
    }

    public void setMovil_averia(String movil_averia) {
        this.movil_averia = movil_averia;
    }

    public List<String>  getImei() {
        return imei;
    }

    public void setImei(List<String>  imei) {
        this.imei = imei;
    }

    public long getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(long createdTs) {
        this.createdTs = createdTs;
    }

    public String getDir_pos() {
        return dir_pos;
    }

    public void setDir_pos(String dir_pos) {
        this.dir_pos = dir_pos;
    }

    public Integer getFlag_analyzed() {
        return flag_analyzed;
    }

    public void setFlag_analyzed(Integer flag_analyzed) {
        this.flag_analyzed = flag_analyzed;
    }

    public List<String> getNif() {
        return nif;
    }

    public void setNif(List<String> nif) {
        this.nif = nif;
    }

    public String getCopies() {
        return copies;
    }

    public void setCopies(String copies) {
        this.copies = copies;
    }

    public String getHiddenCopies() {
        return hiddenCopies;
    }

    public void setHiddenCopies(String hiddenCopies) {
        this.hiddenCopies = hiddenCopies;
    }

    public String getTo() {
        return to;
    }

    public List<String> getDni() { return dni; }

    public List<String> getNie() { return nie; }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public void setDni(List<String> dni) { this.dni = dni; }

    public void setNie(List<String> nie) { this.nie = nie; }

    public void setTo(String to) {
        this.to = to;
    }

    @ApiModelProperty(hidden=true)
    public boolean isMotivoNull(){
        if(this.getMotivo() == null ) { return true; } else { return false; }
    }

    @ApiModelProperty(hidden=true)
    public boolean isUrlNull(){
        if(this.getUrl() == null) { return true; } else { return false; }
    }
}
