package com.ey.telefonica.rpa.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Document(collection = "dispatcher")
public class Dispatcher {
    @Id
    private String id;

    //private String queueName;
    private DispatcherPageName Pagename;
    //private Integer requestId;
    private String Protocol;
    private String PayloadType;
    private Object Payload;
    private String ReceiveUser;
    private String SendUser;
    private String CallbackUri;
    private long createdTs;

    public static Dispatcher createDispatcher(Object Payload, DispatcherPageName Pagename) {
        return  new Dispatcher(Payload, Pagename);
    }

    private Dispatcher(Object Payload, DispatcherPageName Pagename ) {
        this();

        //this.queueName;
        this.Pagename = Pagename;
        //this.requestId;
        //this.protocol;
        //this.payloadType;
        this.Payload = Payload;
        //this.sendUser;
        //this.receiveUser;
        //this.callbackUri;

    }

    public Dispatcher( ) {
        this.createdTs = Instant.now().toEpochMilli();
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public DispatcherPageName getPagename() { return Pagename; }

    public void setPagename(DispatcherPageName pagename) {
        this.Pagename = pagename;
    }

    public String getProtocol() {
        return Protocol;
    }

    public void setProtocol(String Protocol) {
        this.Protocol = Protocol;
    }

    public String getPayloadType() {
        return PayloadType;
    }

    public void setPayloadType(String PayloadType) {
        this.PayloadType = PayloadType;
    }

    public Object getPayload() {
        return Payload;
    }

    public void setPayload(Object Payload) {
        this.Payload = Payload;
    }

    public String getSendUser() {
        return SendUser;
    }

    public void setSendUser(String SendUser) {
        this.SendUser = SendUser;
    }

    public String getReceiveUser() {
        return ReceiveUser;
    }

    public void setReceiveUser(String ReceiveUser) {
        this.ReceiveUser = ReceiveUser;
    }

    public String getCallbackUri() {
        return CallbackUri;
    }

    public void setCallbackUri(String CallbackUri) {
        this.CallbackUri = CallbackUri;
    }

    public long getCreatedTs() { return createdTs; }

    public void setCreatedTs(long createdTs) { this.createdTs = createdTs; }


    @Override
    public String toString() {
        return "Dispatcher{" +
                "id='" + id + '\'' +
                ", Pagename=" + Pagename +
                ", Protocol='" + Protocol + '\'' +
                ", PayloadType='" + PayloadType + '\'' +
                ", Payload=" + Payload +
                ", PendUser='" + SendUser + '\'' +
                ", ReceiveUser='" + ReceiveUser + '\'' +
                ", CallbackUri='" + CallbackUri + '\'' +
                ", CreatedTs=" + createdTs +
                '}';
    }
}
