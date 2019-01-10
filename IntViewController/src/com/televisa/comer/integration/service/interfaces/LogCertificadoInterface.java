package com.televisa.comer.integration.service.interfaces;

import com.televisa.comer.integration.service.beans.logcertificado.LogCertificadoInputParameters;
import com.televisa.comer.integration.service.beans.logcertificado.LogCertificadoResponse;
import com.televisa.comer.integration.service.beans.programs.ProgramsInputParameters;
import com.televisa.comer.integration.service.beans.types.RequestResponseBean;

public interface LogCertificadoInterface {
    public LogCertificadoResponse invokeLogCertificado(LogCertificadoInputParameters psLogCertificado);    
   
}
