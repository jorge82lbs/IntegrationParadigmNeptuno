package com.televisa.comer.integration.model.interfaces;

import com.televisa.comer.integration.model.beans.ResponseUpdDao;
import com.televisa.comer.integration.model.beans.RsstVentaTradicionalBean;

import com.televisa.integration.model.types.EvetvSpotsVtaTradicional;

import java.util.List;

public interface VentaTradicionalInterface {
    public String getQueryTraditionalSale(String psDate, String psChannels);
    public List<RsstVentaTradicionalBean> getTraditionalSaleFromParadigmService(String psDate, String psChannels);
    public ResponseUpdDao callTraditionalSalePr(String tsStnid, 
                                                  String tsStrdt, 
                                                  String tsEdt);
    public List<EvetvSpotsVtaTradicional> getAllTraditionaSaleSpots(String tsType);
    public String getQueryAllTraditionalSaleSpots(String tsType);
}
