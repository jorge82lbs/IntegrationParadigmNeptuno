/**
* Project: Paradigm - eVeTV Integration
*
* File: UserInfoBean.java
*
* Created on:  Septiembre 6, 2017 at 11:00
*
* Copyright (c) - Omw - 2017
*/

package com.televisa.integration.view.users;

/** Esta clase es un bean para almacenar los datos informativos del usuario en 
  * sesion <br/><br/>  
  * 
  * @author Jorge Luis Bautista - Omw
  * 
  * @version 01.00.01
  * 
  * @date Septiembre 14, 2017, 12:00 pm
  */

public class UserInfoBean {
    public UserInfoBean() {
        super();
    }
    
    private String psIdUser;
    private String psUserName;
    private String psUserFirstName;
    private String psLastName;
    private String psLastSecondName;
    private String psUserFullName;
    private String psIsGroup;
    private String psIsInternal;
    private String psUserVisible;
    private String psDateTimeLogin;
    private String psLogged;
    private String psMenu;
    private String psComponents;
    private String psRol;
    private String psFlagRootPortal;
    private String psEmail;
    private String psToken;

    public void setPsToken(String psToken) {
        this.psToken = psToken;
    }

    public String getPsToken() {
        return psToken;
    }

    public void setPsEmail(String psEmail) {
        this.psEmail = psEmail;
    }

    public String getPsEmail() {
        return psEmail;
    }

    /**
     * Sets <code>tsIdUser</code> as the attribute value for the
     * calculated attribute psIdUser.
     * @param tsIdUser value to set the psIdUser
     */ 
    public void setPsIdUser(String tsIdUser) {
        this.psIdUser = tsIdUser;
    }

    /**
     * Gets the attribute value for the calculated attribute psIdUser.
     * @return the psIdUser
     */
    public String getPsIdUser() {
        return psIdUser;
    }

    /**
     * Sets <code>tsUserName</code> as the attribute value for the 
     * calculated attribute psUserName.
     * @param tsUserName value to set the psUserName
     */ 
    public void setPsUserName(String tsUserName) {
        this.psUserName = tsUserName;
    }

    /**
     * Gets the attribute value for the calculated attribute psUserName.
     * @return the psUserName
     */
    public String getPsUserName() {
        return psUserName;
    }

    /**
     * Sets <code>tsUserFirstName</code> as the attribute value for the 
     * calculated attribute psUserFirstName.
     * @param tsUserFirstName value to set the psUserFirstName
     */ 
    public void setPsUserFirstName(String tsUserFirstName) {
        this.psUserFirstName = tsUserFirstName;
    }

    /**
     * Gets the attribute value for the calculated attribute psUserFirstName.
     * @return the psUserFirstName
     */
    public String getPsUserFirstName() {
        return psUserFirstName;
    }

    /**
     * Sets <code>tsLastName</code> as the attribute value for the 
     * calculated attribute psLastName.
     * @param tsLastName value to set the psLastName
     */ 
    public void setPsLastName(String tsLastName) {
        this.psLastName = tsLastName;
    }

    /**
     * Gets the attribute value for the calculated attribute psLastName.
     * @return the psLastName
     */
    public String getPsLastName() {
        return psLastName;
    }

    /**
     * Sets <code>tsLastSecondName</code> as the attribute value for the 
     * calculated attribute psLastSecondName.
     * @param tsLastSecondName value to set the psLastSecondName
     */ 
    public void setPsLastSecondName(String tsLastSecondName) {
        this.psLastSecondName = tsLastSecondName;
    }

    /**
     * Gets the attribute value for the calculated attribute psLastSecondName.
     * @return the psLastSecondName
     */
    public String getPsLastSecondName() {
        return psLastSecondName;
    }

    /**
     * Sets <code>tsUserFullName</code> as the attribute value for the 
     * calculated attribute psUserFullName.
     * @param tsUserFullName value to set the psUserFullName
     */ 
    public void setPsUserFullName(String tsUserFullName) {
        this.psUserFullName = tsUserFullName;
    }

    /**
     * Gets the attribute value for the calculated attribute psUserFullName.
     * @return the psUserFullName
     */
    public String getPsUserFullName() {
        return psUserFullName;
    }

    /**
     * Sets <code>tsIsGroup</code> as the attribute value for the 
     * calculated attribute psIsGroup.
     * @param tsIsGroup value to set the psIsGroup
     */ 
    public void setPsIsGroup(String tsIsGroup) {
        this.psIsGroup = tsIsGroup;
    }

    /**
     * Gets the attribute value for the calculated attribute psIsGroup.
     * @return the psIsGroup
     */
    public String getPsIsGroup() {
        return psIsGroup;
    }

    /**
     * Sets <code>tsIsInternal</code> as the attribute value for the 
     * calculated attribute psIsInternal.
     * @param tsIsInternal value to set the psIsInternal
     */ 
    public void setPsIsInternal(String tsIsInternal) {
        this.psIsInternal = tsIsInternal;
    }

    /**
     * Gets the attribute value for the calculated attribute psIsInternal.
     * @return the psIsInternal
     */
    public String getPsIsInternal() {
        return psIsInternal;
    }

    /**
     * Sets <code>tsUserVisible</code> as the attribute value for the 
     * calculated attribute psUserVisible.
     * @param tsUserVisible value to set the psUserVisible
     */ 
    public void setPsUserVisible(String tsUserVisible) {
        this.psUserVisible = tsUserVisible;
    }

    /**
     * Gets the attribute value for the calculated attribute psUserVisible.
     * @return the psUserVisible
     */
    public String getPsUserVisible() {
        return psUserVisible;
    }

    /**
     * Sets <code>tsDateTimeLogin</code> as the attribute value for the 
     * calculated attribute psDateTimeLogin.
     * @param tsDateTimeLogin value to set the psDateTimeLogin
     */ 
    public void setPsDateTimeLogin(String tsDateTimeLogin) {
        this.psDateTimeLogin = tsDateTimeLogin;
    }

    /**
     * Gets the attribute value for the calculated attribute psDateTimeLogin.
     * @return the psDateTimeLogin
     */
    public String getPsDateTimeLogin() {
        return psDateTimeLogin;
    }

    /**
     * Sets <code>tsLogged</code> as the attribute value for the 
     * calculated attribute psLogged.
     * @param tsLogged value to set the psLogged
     */ 
    public void setPsLogged(String tsLogged) {
        this.psLogged = tsLogged;
    }

    /**
     * Gets the attribute value for the calculated attribute psLogged.
     * @return the psLogged
     */
    public String getPsLogged() {
        return psLogged;
    }

    /**
     * Sets <code>tsMenu</code> as the attribute value for the 
     * calculated attribute psMenu.
     * @param tsMenu value to set the psMenu
     */ 
    public void setPsMenu(String tsMenu) {
        this.psMenu = tsMenu;
    }

    /**
     * Gets the attribute value for the calculated attribute psMenu.
     * @return the psMenu
     */
    public String getPsMenu() {
        return psMenu;
    }

    /**
     * Sets <code>tsComponents</code> as the attribute value for the 
     * calculated attribute psComponents.
     * @param tsComponents value to set the psComponents
     */ 
    public void setPsComponents(String tsComponents) {
        this.psComponents = tsComponents;
    }

    /**
     * Gets the attribute value for the calculated attribute psComponents.
     * @return the psComponents
     */
    public String getPsComponents() {
        return psComponents;
    }

    /**
     * Sets <code>tsRol</code> as the attribute value for the 
     * calculated attribute psRol.
     * @param tsRol value to set the psRol
     */ 
    public void setPsRol(String tsRol) {
        this.psRol = tsRol;
    }

    /**
     * Gets the attribute value for the calculated attribute psRol.
     * @return the psRol
     */
    public String getPsRol() {
        return psRol;
    }

    /**
     * Sets <code>tsFlagRootPortal</code> as the attribute value for the 
     * calculated attribute psFlagRootPortal.
     * @param tsFlagRootPortal value to set the psFlagRootPortal
     */ 
    public void setPsFlagRootPortal(String tsFlagRootPortal) {
        this.psFlagRootPortal = tsFlagRootPortal;
    }

    /**
     * Gets the attribute value for the calculated attribute psFlagRootPortal.
     * @return the psFlagRootPortal
     */
    public String getPsFlagRootPortal() {
        return psFlagRootPortal;
    }

    
}
