package com.ey.telefonica.rpa.mongo.model.lotus;

import io.swagger.annotations.ApiModelProperty;

public class CallJsonField {

    @ApiModelProperty(required=true)
    private String idu;
    private String jsonField;

    public CallJsonField() {

    }

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

    @Override
    public String toString() {
        return "CallJsonField{" +
                "idu='" + idu + '\'' +
                ", jsonField='" + jsonField + '\'' +
                '}';
    }
}