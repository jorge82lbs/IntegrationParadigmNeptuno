package com.televisa.comer.integration.model.interfaces;

import com.televisa.comer.integration.model.beans.ResponseUpdDao;
import com.televisa.comer.integration.model.beans.RsstTargetsBean;

import java.util.List;

public interface TargetsInterfaces {
    
    public List<RsstTargetsBean> getTargetsFromDataBase();
    public String getQueryTargetsFromDataBase();
    public ResponseUpdDao updateTargetsParadigm(RsstTargetsBean loRsstTargetsBean);
    public String getQueryupdateTargetsParadigm(RsstTargetsBean loRsstTargetsBean);
}
