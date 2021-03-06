package mx.com.televisa.secman.view.permisos;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (11.1.1.0.0, build 110329.0915.20612)

@WebService(wsdlLocation="http://tvmiddev1.televisa.net:8011/Secman/Proxy/SecmanDasPermisosUsuarioProxy?wsdl",
  targetNamespace="http://webServices.view.secman.televisa.com.mx/", name="SecmanDasUsuarioPermisos")
@XmlSeeAlso(
  { mx.com.televisa.secman.view.permisos.types.ObjectFactory.class })
public interface SecmanDasUsuarioPermisos
{
  @WebMethod
  @Action(input="http://webServices.view.secman.televisa.com.mx/SecmanDasUsuarioPermisos/obtenerRolTareaPermisosRequest",
    output="http://webServices.view.secman.televisa.com.mx/SecmanDasUsuarioPermisos/obtenerRolTareaPermisosResponse")
  @ResponseWrapper(localName="obtenerRolTareaPermisosResponse",
    targetNamespace="http://webServices.view.secman.televisa.com.mx/",
    className="mx.com.televisa.secman.view.permisos.types.ObtenerRolTareaPermisosResponse")
  @RequestWrapper(localName="obtenerRolTareaPermisos", targetNamespace="http://webServices.view.secman.televisa.com.mx/",
    className="mx.com.televisa.secman.view.permisos.types.ObtenerRolTareaPermisos")
  @WebResult(targetNamespace="")
  public mx.com.televisa.secman.view.permisos.types.PermisoOutputCollection obtenerRolTareaPermisos(@WebParam(targetNamespace="",
      name="arg0")
    mx.com.televisa.secman.view.permisos.types.PermisosUsuarioInputParameters arg0);
}
