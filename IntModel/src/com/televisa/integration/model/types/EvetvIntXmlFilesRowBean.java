package com.televisa.integration.model.types;

import java.sql.Blob;

public class EvetvIntXmlFilesRowBean {
    private Integer liIdFileXml;
    private Integer liIdRequest;
    private Integer liIdService;
    private String lsNomFile;
    private String lsIndFileType;
    private String lsIndServiceType;
    //private Blob loIndFileStream;
    private String lsIndEstatus;
    private String lsFecCreationDate;
    private String lsNomUserName;

    public void setLiIdFileXml(Integer liIdFileXml) {
        this.liIdFileXml = liIdFileXml;
    }

    public Integer getLiIdFileXml() {
        return liIdFileXml;
    }

    public void setLiIdRequest(Integer liIdRequest) {
        this.liIdRequest = liIdRequest;
    }

    public Integer getLiIdRequest() {
        return liIdRequest;
    }

    public void setLiIdService(Integer liIdService) {
        this.liIdService = liIdService;
    }

    public Integer getLiIdService() {
        return liIdService;
    }

    public void setLsNomFile(String lsNomFile) {
        this.lsNomFile = lsNomFile;
    }

    public String getLsNomFile() {
        return lsNomFile;
    }

    public void setLsIndFileType(String lsIndFileType) {
        this.lsIndFileType = lsIndFileType;
    }

    public String getLsIndFileType() {
        return lsIndFileType;
    }

    public void setLsIndServiceType(String lsIndServiceType) {
        this.lsIndServiceType = lsIndServiceType;
    }

    public String getLsIndServiceType() {
        return lsIndServiceType;
    }
/*
    public void setLoIndFileStream(Blob loIndFileStream) {
        this.loIndFileStream = loIndFileStream;
    }

    public Blob getLoIndFileStream() {
        return loIndFileStream;
    }
*/
    public void setLsIndEstatus(String lsIndEstatus) {
        this.lsIndEstatus = lsIndEstatus;
    }

    public String getLsIndEstatus() {
        return lsIndEstatus;
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
