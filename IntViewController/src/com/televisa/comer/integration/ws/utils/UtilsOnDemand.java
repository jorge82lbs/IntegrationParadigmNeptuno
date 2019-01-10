package com.televisa.comer.integration.ws.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsOnDemand {
    public UtilsOnDemand() {
        super();
    }
    public boolean isFormatDateNeptuno(String tsDate){
        boolean lbReturn = true;
        lbReturn = validateRegularExpression(tsDate,"^[0-3][0-9]/[0-3][0-9]/(?:[0-9][0-9])?[0-9][0-9]$");
        return lbReturn;
    }
    
    public String getDatePrgmFormat(String tsDateNeptuno){
        String lsDatePgm = "";
        //tsDateNeptuno puede llegar como dd/mm/yy o dd/mm/yyyy
        String[] laDtNp = tsDateNeptuno.split("/");
        if(laDtNp.length == 3){
            if(laDtNp[2].length()==2){
                lsDatePgm += "20" + laDtNp[2];
            }else{
                lsDatePgm += laDtNp[2];   
            }
            lsDatePgm += "-";   
            lsDatePgm += laDtNp[1];   
            lsDatePgm += "-";   
            lsDatePgm += laDtNp[0];   
        }
        return lsDatePgm;
    }
    
    /** Valida expresiones regulares de forma dinamica
      * @autor Jorge Luis Bautista 
      * @param tsClientString
      * @param tsRegularExpression
      * @return boolean
    */
    public boolean validateRegularExpression(String tsClientString, String tsRegularExpression){
        boolean lbReturn = false;
        String  lsToValidate = 
            tsClientString == null ? "" : tsClientString;
        if(!lsToValidate.trim().equalsIgnoreCase("")){
            Matcher loMat = null;
            Pattern loPat = null;
            String  lsExpReg = tsRegularExpression;
            loPat = Pattern.compile(lsExpReg);
            loMat = loPat.matcher(lsToValidate);
            if (!loMat.find()){
                lbReturn = false; 
            }else{
                lbReturn = true;
            }
        }        
        return lbReturn;
    }
    
    /** Valida longitud de la cadena
      * @autor Jorge Luis Bautista 
      * @param tsCadena
      * @param tiSize
      * @return boolean
    */
    public boolean validateLength(String tsCadena, Integer tiSize){
        boolean lbRes = true;
        if(tsCadena != null){
            if(tsCadena.length() > tiSize){
                lbRes = false;
            }
        }else{
            lbRes = false;
        }
        return lbRes;
    }
}
