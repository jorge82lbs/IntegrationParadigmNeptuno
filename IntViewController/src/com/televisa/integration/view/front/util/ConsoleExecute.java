package com.televisa.integration.view.front.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleExecute {
    public ConsoleExecute() {
        super();
    }

    public static void main(String[] args) {
        ConsoleExecute consoleExecute = new ConsoleExecute();
        String lsIni = "2018-02-14";
        String lsFin = "2018-03-10";
        Double liRes = consoleExecute.getAmoutTaxi(lsIni,lsFin);
        System.out.println("Cuenta L-V: "+liRes);
        //java.sql.Date lsFec =  consoleExecute.getDateYYYYMMDD("2017-12-20");        
        //System.out.println("ID: "+lsFec);
        //consoleExecute.usuarios();
        
    }
    
    public Double getAmoutTaxi(String lsFecIni, String lsFecFin){
        Double liResponse = 0.0;
        String lsStartDate = lsFecIni;
        String lsFinalDate = lsFecFin;
        Date ltFechaIni = new Date();
        Date ltFechaFin = new Date();
        // Convertir cadenas a java.util.Date
        SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ltFechaIni = lodf.parse(lsStartDate);
            ltFechaFin = lodf.parse(lsFinalDate);
            System.out.println("Hora Inicio: ["+ltFechaIni+" ]");
            System.out.println("Hora Fin: ["+ltFechaFin+" ]");
        } catch (ParseException e) {
            System.out.println("Error al parsear: "+e.getMessage());
            e.printStackTrace();
        }
        
        Calendar loCalIni = Calendar.getInstance();
        loCalIni.setTime(ltFechaIni);
        Calendar loCalFin = Calendar.getInstance();
        loCalFin.setTime(ltFechaFin);
        
        Calendar loCalIniSd = Calendar.getInstance();
        loCalIniSd.setTime(ltFechaIni);
        
        Integer liLv = getDiasHabiles(loCalIni, loCalFin);        
        System.out.println("Dias habilies L-V: ["+liLv+"]");
        Integer liSd = getFinDeSemana(loCalIniSd, loCalFin);
        System.out.println("Dias Sabado y domingo: ["+liSd+"]");
        liResponse = (liLv * 250.0) + (liSd * 150.0);
        return liResponse;
    }
    
    public Date getDateYyyyMmDd(Date ttDate){
        String           lsCurrDate = "";
        SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
        lsCurrDate = lodfCurrent.format(ttDate);
        SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd");
        Date             ltFechaReturn = new Date();
        try {
            ltFechaReturn = lodf.parse(lsCurrDate);
        } catch (ParseException e) {
            System.out.println("Error al parsear: "+e.getMessage());
            e.printStackTrace();
        }
        return ltFechaReturn;
    }
    
    public int getDiasHabiles(Calendar fechaInicial, Calendar fechaFinal) {
      int diffDays= 0;
      //mientras la fecha inicial sea menor o igual que la fecha final se cuentan los dias
      while (fechaInicial.before(fechaFinal) || fechaInicial.equals(fechaFinal)) {
        //System.out.println("Fecha Actual["+fechaInicial+"] dia["+fechaInicial.get(Calendar.DAY_OF_WEEK)+"]");
      //si el dia de la semana de la fecha minima es diferente de sabado o domingo
      if (fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
       //se aumentan los dias de diferencia entre min y max
       diffDays++;
       }
      //se suma 1 dia para hacer la validacion del siguiente dia.
      fechaInicial.add(Calendar.DATE, 1);
      }
    return diffDays;
    }
    
    public int getFinDeSemana(Calendar fechaInicial, Calendar fechaFinal) {        
      int diffDays= 0;
      //mientras la fecha inicial sea menor o igual que la fecha final se cuentan los dias
      while (fechaInicial.before(fechaFinal) || fechaInicial.equals(fechaFinal)) {
        //System.out.println("Fecha Actual["+fechaInicial.getTime()+"] dia["+fechaInicial.get(Calendar.DAY_OF_WEEK)+"]");
      //si el dia de la semana de la fecha minima es diferente de sabado o domingo
          //System.out.println("Dia Actual["+fechaInicial.get(Calendar.DAY_OF_WEEK)+"] domingo["+Calendar.SUNDAY+"] sabado["+Calendar.SATURDAY+"]");
      if (fechaInicial.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || fechaInicial.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
       //se aumentan los dias de diferencia entre min y max
       diffDays++;
       }
      //se suma 1 dia para hacer la validacion del siguiente dia.
      fechaInicial.add(Calendar.DATE, 1);
      }
    return diffDays;
    }
    
    private String getCurrentDayOfWeek(){
        String   lsResponse = null;
        Calendar loCal = Calendar.getInstance();
        String[] laStrDays = new String[]{
                                        "SUNDAY",
                                        "MONDAY",
                                        "TUESDAY",
                                        "WEDNESDAY",
                                        "THURSDAY",
                                        "FRIDAY",
                                        "SATURDAY"};

        // El dia de la semana inicia en el 1 mientras que el array empieza en el 0
        //System.out.println("Hoy es : " + strDays[loCal.get(Calendar.DAY_OF_WEEK) - 1]);
        lsResponse = laStrDays[loCal.get(Calendar.DAY_OF_WEEK) - 1];
        return lsResponse;
    }
    
    private String getCurrentHour(){
        String   lsResponse = null;
        Calendar loCal = Calendar.getInstance();
        int      liHour = loCal.get(Calendar.HOUR_OF_DAY);
        int      liMin = loCal.get(Calendar.MINUTE);
        String   lsZero = "";
        if(liMin < 10){
            lsZero = "0";
        }
        lsResponse = String.valueOf(liHour)+":"+lsZero+String.valueOf(liMin);
        return lsResponse;
    }
    
    private String buildDate(String lsDate, String lsInMask){
        String lsResponse = null;
        String[] laArrDate = lsDate.split("/");
        if(laArrDate.length > 0){
            String lsMonth = laArrDate[0];
            String lsDay = laArrDate[1];
            String lsYear = convierteFecha(lsDate,lsInMask,"YYYY");
            lsResponse = lsDay+"/"+lsMonth+"/"+lsYear;
        }
        return lsResponse;
    }
    
    private String buildDateEveTv(String lsDate, String lsInMask){
        String lsResponse = null;
        String[] laArrDate = lsDate.split("/");
        if(laArrDate.length > 0){
            String lsDay = laArrDate[0];
            String lsMonth = laArrDate[1];            
            String lsYear = convierteFecha(lsDate,lsInMask,"YYYY");
            lsResponse = lsDay+"/"+lsMonth+"/"+lsYear;
        }
        return lsResponse;
    }
    
    private String buildDatePardigm(String lsDate, String lsInMask){
        String lsResponse = null;
        String[] laArrDate = lsDate.split("/");
        if(laArrDate.length > 0){
            String lsDay = laArrDate[0];
            String lsMonth = laArrDate[1];            
            String lsYear = convierteFecha(lsDate,lsInMask,"YYYY");
            lsResponse = lsYear+"-"+lsMonth+"-"+lsDay;
        }
        return lsResponse;
    }
    
    private String convierteFecha(String lsCadenaFecha, 
                                        String lsMascaraEntrada,
                                        String lsMascaraSalida) {

        SimpleDateFormat sdfEntrada = new SimpleDateFormat(lsMascaraEntrada);
        SimpleDateFormat sdfSalida = new SimpleDateFormat(lsMascaraSalida);

        Date loMiFecha = new Date();
        String loFechaFormateada = "";

        try {
            loMiFecha = sdfEntrada.parse(lsCadenaFecha);
            loFechaFormateada = sdfSalida.format(loMiFecha);
        } 
        catch (ParseException ex) {
            loFechaFormateada = null;
        }

        return loFechaFormateada;
    }

    
    public String getIdBitacora(){
        String lsResponse = null;
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        lsResponse = df.format(new Date(System.currentTimeMillis()));
        System.out.println(lsResponse);

        return lsResponse;
            
    }
    
    public void usuarios(){
        String UserEncript;
        String pwdEncript;
        try {
            UtilFaces uf = new UtilFaces();
            String usuario = "neptuno";
            //String pwd = "Televisa.33";
            //String pwd = "T3l3visa2013";//Rocio
            String pwd = "Arisbeth31.11";//lmercador
            //String pwd = "Jema1272#6";//amorela
            //String pwd = "Ddakywdd.94";
            String llave = "LFXqSn21ptd+rNihAuZeMg=="; //llave para loggin
            //String llave = "JYIqNn21ptd+rGihRuIeDog=="; //llave para pregunta secreta
            UserEncript = uf.encryptObject(usuario, llave);
            pwdEncript = uf.encryptObject(pwd, llave);
            
            System.out.println("Usuario("+usuario+") ["+UserEncript+"]");            
            System.out.println("pwd("+usuario+") ["+pwdEncript+"]");            
            
            //lmercador : 
            /*
             * <UserLogin>
            <sec:UserName>iIE8JZ8kr+nwCRwZFcGBmQ==</sec:UserName>
            <sec:NomAplicacion>OrduniOnAir</sec:NomAplicacion>
            <sec:Accion>login</sec:Accion>
            <sec:UserPassword>tOcVZKIFZA6/rvs5zQ2uPg==</sec:UserPassword>
            <sec:Token/>
         </UserLogin>
             * */
        } catch (Exception e) {
            System.out.println("ERROR: ");
        }    
    }
    
    public void pregSecreta(){
        String respuesta;
        try {
            System.out.println("Desencriptando");
            String respuestaEncript = "gXZT5jSy/ekyHRxNEhYPuA==";
            String llave = "IYYqAn21ptd+rGihAuEeJog=="; //llave para pregunta secreta
            respuesta = "";//UtilSecman.decryptObject(respuestaEncript, llave);
            System.out.println("RESPUESTA("+respuestaEncript+") ["+respuesta+"]");            
        } catch (Exception e) {
            System.out.println("ERROR: "+e.getMessage());
        }    
    }
    
    public void leeLineas() {
          java.io.File archivo = null;
          FileReader fr = null;
          BufferedReader br = null;
          FileWriter archivoSalida = null;
          PrintWriter lineaEscritura = null;
          String nl = System.getProperty("line.separator");
          try {
             // Apertura del fichero y creacion de BufferedReader para poder
             // hacer una lectura comoda (disponer del metodo readLine()).
             //archivo = new java.io.File ("C:\\JDeveloper\\mywork\\SecmanApp\\ViewController\\src\\mx\\com\\televisa\\secman\\view\\beans\\ConfigCredCorpBean.java");
              archivo = new java.io.File ("C:\\Users\\aph\\Desktop\\tmp\\privates.java");
             fr = new FileReader (archivo);
             br = new BufferedReader(fr);
     
             // Lectura del fichero
             String linea;
              
             archivoSalida = new FileWriter("C:\\Users\\aph\\Desktop\\tmp\\comments.txt");
             lineaEscritura = new PrintWriter(archivoSalida); 
              while((linea=br.readLine())!=null){                  
                  if(linea.length()>7){                      
                      if(linea.trim().substring(0, 7).equalsIgnoreCase("private")){
                          String al[] = linea.trim().split(" ");
                          String alcance = al[0];
                          String tipoDato = al[1];
                          String nomVariable = al[2].substring(0, al[2].length()-1);
                          String parametros = al[2].substring(2, al[2].length()-1);
                          //System.out.println(alcance);
                          //System.out.println(tipoDato);
                          //System.out.println(nomVariable);
                          //System.out.println(parametros);
                          String prTipo = "o";
                          if(tipoDato.equalsIgnoreCase("String")){
                              prTipo = "s";
                          }else if(tipoDato.equalsIgnoreCase("boolean")){
                              prTipo = "b";
                          }else if(tipoDato.equalsIgnoreCase("int")){
                              prTipo = "i";
                          }else if(tipoDato.equalsIgnoreCase("Integer")){
                              prTipo = "i";
                          }else if(tipoDato.equalsIgnoreCase("Number")){
                              prTipo = "i";
                          }
                          else{
                              prTipo = "o";
                          }
                          String comment = "    /**" + nl +
                          "     * Sets <code>t"+prTipo+parametros+"</code> as the attribute value for the " + nl +
                          "     * calculated attribute "+nomVariable+"." + nl +
                          "     * @param t"+prTipo+parametros+" value to set the "+nomVariable+ nl +
                          "     */ "+nl +
                          "    public void setP"+prTipo+parametros+"("+tipoDato+" t"+prTipo+parametros+") {" + nl +
                          "        this.p"+prTipo+parametros+" = t"+prTipo+parametros+";" + nl +
                          "    }" + nl + nl +
                          "    /**" + nl +
                          "     * Gets the attribute value for the calculated attribute p"+prTipo+parametros+"." + nl +
                          "     * @return the p"+prTipo+parametros + nl +
                          "     */" + nl +
                          "    public "+tipoDato+" getP"+prTipo+parametros+"() {" + nl +
                          "        return p"+prTipo+parametros+";" + nl +
                          "    }"+nl;
                          System.out.println(comment);
                          lineaEscritura.println(comment);     
                      }
                  }
              }
              archivoSalida.close();
                
          }
          catch(Exception e){
             e.printStackTrace();
              System.out.println("ERROR: "+e.getMessage());
          }finally{
             try{                   
                if( null != fr ){  
                   fr.close();    
                }                 
             }catch (Exception e2){
                e2.printStackTrace();
             }
          }
           System.out.println("fin...");
       }
    
    public void regularExpressionMethod(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean lbFlag = true;
        do{
            try {
                System.out.println("REGULAR EXPRESSION TYPE: ");
                String lsRegExpType;
                lsRegExpType = br.readLine();
                if(lsRegExpType != null){
                    if(lsRegExpType.equalsIgnoreCase("salir")){
                        lbFlag = false;
                    }
                    if(lbFlag){
                        String lsRegExp = getRegularExpression(lsRegExpType);
                        String lsStringValue = "";
                        if(lsRegExp != null){
                            do{
                                System.out.println("CADENA A EVALUAR: ");
                                lsStringValue = br.readLine();  
                                if(!lsStringValue.equalsIgnoreCase("salir")){
                                    if(validateRegularExpression(lsStringValue, lsRegExp)){                            
                                        System.out.println("***************************************************************");
                                        System.out.println("***                    CADENA VALIDA                        ***");
                                        System.out.println("***************************************************************");
                                    }else{
                                        System.out.println("La cadena["+lsStringValue+"] \nNo pertence a la expresion regular:\n "+lsRegExp);
                                    }
                                }
                            }while(!lsStringValue.equalsIgnoreCase("salir"));
                        }else{
                            System.out.println("TIPO DE EXPRESION REGULAR INVALIDO!!! ");
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("ERROR: "+e.getMessage());
            }
            System.out.println("--------------------------------------------------------------------------");
        }while(lbFlag);
        
    }
    
    private java.sql.Date getDateYYYYMMDD(String lsDateStr){
        SimpleDateFormat loFormatText = new SimpleDateFormat("yyyy-MM-dd");
        String           lsStrDate = lsDateStr;
        Date             ltDatePivot = null;
        try {
            ltDatePivot = loFormatText.parse(lsStrDate);
        } catch (ParseException loEx) {
            loEx.printStackTrace();
        }
        java.sql.Date ltDateResponse = new java.sql.Date(ltDatePivot.getTime());
        return ltDateResponse;
    }
    
    /** Valida expresiones regulares de forma dinamica
      * @autor Jorge Luis Bautista 
      * @return boolean
    */
    public boolean validateRegularExpression(String tsClientString, String tsRegularExpression){
        boolean lbReturn = false;
        String lsToValidate = 
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
    
    /** Valida expresiones regulares de forma dinamica
      * @autor Jorge Luis Bautista 
      * @return String
    */
    public String getRegularExpression(String tsRegExpType){
        String lsResRegExp = null;        
        RegExpOrduniUtil loExReg = RegExpOrduniUtil.valueOf(tsRegExpType.toUpperCase());
        switch(loExReg){
        case SCHEDULE: //Horario
            lsResRegExp = "^(0*[2-9]|1[0-9]|2[0-5]):[0-5][0-9]:[0-5][0-9]:[0-5][0-9]$|^(0*[2-9]|1[0-9]|2[0-5]):[0-5][0-9]$";
          //lsResRegExp = "^(2*[6-9]|1[0-9]|2[0-5]):[0-5][0-9][0-5][0-9]:[0-5][0-9][0-5][0-9]$|^(2*[6-9]|1[0-9]|2[0-5]):[0-5][0-9]$";
            break;
        case EMAIL: //Correo Electr�nico
            lsResRegExp = 
                //"^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,3})$";
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,3})$";
            break;
        case DATEFORMAT: //Formato de calendario
            lsResRegExp = 
                "^(?:(?:0?[1-9]|1\\d|2[0-8])(\\/|-)(?:0?[1-9]|1[0-2]))(\\/|-)(?:[1-9]\\d\\d\\d|\\d[1-9]\\d\\d|\\d\\d[1-9]\\d|\\d\\d\\d[1-9])$|^(?:(?:31(\\/|-)(?:0?[13578]|1[02]))|(?:(?:29|30)(\\/|-)(?:0?[1,3-9]|1[0-2])))(\\/|-)(?:[1-9]\\d\\d\\d|\\d[1-9]\\d\\d|\\d\\d[1-9]\\d|\\d\\d\\d[1-9])$|^(29(\\/|-)0?2)(\\/|-)(?:(?:0[48]00|[13579][26]00|[2468][048]00)|(?:\\d\\d)?(?:0[48]|[2468][048]|[13579][26]))$";
            break;
        case NUMBER:
            lsResRegExp = "^[0-9]+$";
            break;
        case SCHEDULE_SHIFT: //
            String lsHourInFirst =  "0";
            String lsHourInSecond = "2";
            lsResRegExp = "^("+"0"+"*["+"0"+"-9]|1[0-9]|"+lsHourInFirst+"[0-"+lsHourInSecond+"]):[0-5][0-9]:[0-5][0-9]:[0-2][0-9]$|^("+"0"+"*["+"0"+"-9]|1[0-9]|"+lsHourInFirst+"[0-"+lsHourInSecond+"]):[0-5][0-9]$";  
            break;
        default:
            lsResRegExp = null;
            break;
        }
        return lsResRegExp;
    }
    
    private enum RegExpOrduniUtil{
        SCHEDULE, EMAIL, DATEFORMAT, NUMBER, SCHEDULE_SHIFT;
    }
    
    public void desEncriptarCadena(){
        String lsCadEncrypt;
        try {
            String lsParameter = "+7/vNEdF4N1HLIu7OfKFWa/qRuYeNLw8sG6M7/78efD/6tL9YIlGfhIH3vljMo0QJnIVmuRhG4MANJaZsywGsDCoDlf8JXh9rLiNayAxCyYQFYmM9ojD2Pau4/sxIcc/Uc/36IiqY1JfBb7DhiKHfeX8DRdQZME5uTaE+RIZP5aLXDKFyG244g3SwKeldfKsId2J8GRayP+96hfYlw==";
            String llave = "LFXqSn21ptd+rNihAuZeMg=="; //llave para loggin
            lsCadEncrypt = "";//UtilSecman.decryptObject(lsParameter, llave);
            System.out.println("CADENA     ("+lsParameter+")\t\t\t["+lsCadEncrypt+"]");
        } catch (Exception e) {
            System.out.println("ERROR: "+e.getMessage());
        }    
    }
}
