/**
* Project: Integration Paradigm-Neptuno
*
* File: SessionFilter.java
*
* Created on: Septiembre 9, 2017 at 11:00
*
* Copyright (c) - Omw - 2017
*/
package com.televisa.integration.view.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/** Esta clase verifica el tiempo de sesion de la aplicacion y redirecciona<br/>
 * a una pantalla de error<br/><br/>
 * La aplicacion posee un tiempo limite de inactividad, al realizar<br/>
 * alguna accion esta clase verifica si la session sigue activa<br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Mexico DF. a 23 de Septiembre de 2017
 */
public class SessionFilter implements Filter {
    private FilterConfig poFilterConfig = null;
    
    /**
     * Init de implementacion para el servlet
     * @autor Jorge Luis Bautista Santiago
     * @return void
     */    
    @Override
    public void init(FilterConfig toFilterConfig) throws ServletException {
        poFilterConfig = toFilterConfig;
    }

    /**
     * doFilter de implementacion para el servlet de filtro
     * @autor Jorge Luis Bautista Santiago
     * @return void
     */
    @Override
    public void doFilter(ServletRequest toRequest, 
                         ServletResponse toResponse, 
                         FilterChain toChain
                         )  throws IOException, ServletException {
        HttpServletRequest loHttpRequest = (HttpServletRequest)toRequest;
        HttpSession        loSession = loHttpRequest.getSession();
        //System.out.println("Session - Host = " + toRequest.getServerName());
        //System.out.println("Session - Port = " + toRequest.getServerPort());
        if (loSession.getAttribute("session.integration") == null) {
            if(((HttpServletRequest)toRequest).getPathInfo().startsWith("/indexPage")) {}
            else {
                try {
                    loHttpRequest.getRequestDispatcher("/error.html").forward(toRequest, 
                                                                              toResponse);
                    return;
                }
                catch (ServletException loEx) {
                    loEx.printStackTrace();
                }
                catch (IOException loEx) {
                    loEx.printStackTrace();
                }
            }
        }
        
        try {
            toChain.doFilter(toRequest, toResponse);
        } catch (IOException loEx) {
            loEx.printStackTrace();
        } catch (ServletException loEx) {
            loEx.printStackTrace();
        }

    }

    /**
     * Destroy de implementacion para el servlet
     * @autor Jorge Luis Bautista Santiago
     * @return void
     */
    @Override
    public void destroy() {
        poFilterConfig = null;
    }
}
