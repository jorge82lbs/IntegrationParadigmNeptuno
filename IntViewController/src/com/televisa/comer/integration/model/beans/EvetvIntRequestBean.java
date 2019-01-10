package com.televisa.comer.integration.model.beans;

public class EvetvIntRequestBean {
    private String lsIdRequest;
    private String lsIdService;
    private String lsIndServiceType;
    private String lsIndStatus;
    private String lsFecCreationDate;
    private String lsNomUserName;
    
    public void setLsIdRequest(String lsIdRequest) {
        this.lsIdRequest = lsIdRequest;
    }

    public String getLsIdRequest() {
        return lsIdRequest;
    }

    public void setLsIdService(String lsIdService) {
        this.lsIdService = lsIdService;
    }

    public String getLsIdService() {
        return lsIdService;
    }

    public void setLsIndServiceType(String lsIndServiceType) {
        this.lsIndServiceType = lsIndServiceType;
    }

    public String getLsIndServiceType() {
        return lsIndServiceType;
    }

    public void setLsIndStatus(String lsIndStatus) {
        this.lsIndStatus = lsIndStatus;
    }

    public String getLsIndStatus() {
        return lsIndStatus;
    }

    public void setLsFecCreationDate(String lsFecCreationDate) {
        this.lsFecCreationDate = lsFecCreationDate;
    }

    public String getLsFecCreationDate() {
        return lsFecCreationDate;
    }

    public void setLsNomUserName(String lsNomUserName) {
        this.lsNomUserName = lsNomUserName;
    }

    public String getLsNomUserName() {
        return lsNomUserName;
    }
    
}
