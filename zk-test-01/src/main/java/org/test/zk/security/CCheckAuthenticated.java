package org.test.zk.security;

import java.io.File;
import java.util.Map;

import org.test.zk.database.datamodel.TBLOperator;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Initiator;

import commonlibs.commonclasses.CLanguage;
import commonlibs.commonclasses.ConstantsCommonClasses;
import commonlibs.extendedlogger.CExtendedLogger;


public class CCheckAuthenticated implements Initiator {
    
    public void doInit( Page page, Map<String, Object> args ) throws Exception {

        detectAuthenticatedAndRedirect( page, args );
        
    }
    
    public static void detectAuthenticatedAndRedirect( Page page, Map<String, Object> args ) {
        
        //Ok aqui creamos otro logger distinto al global esto con la finalidad de no sobrecargar el archivo con demasiadas entradas
        CExtendedLogger extendedLogger = CExtendedLogger.getLogger( ConstantsCommonClasses._Check_Logged_Logger_Name );

        String strRunningPath = Sessions.getCurrent().getWebApp().getRealPath( ConstantsCommonClasses._WEB_INF_Dir ) + File.separator;
        
        if ( extendedLogger != null && extendedLogger.getSetupSet() == false ) {
            
            //Establecemos el logpath
            String strLogPath = strRunningPath + ConstantsCommonClasses._Logs_Dir + ConstantsCommonClasses._Security_Dir;

            extendedLogger.setupLogger( ConstantsCommonClasses._Check_Logged_Logger_Name, false, strLogPath, ConstantsCommonClasses._Check_Logged_File_Log, ConstantsCommonClasses._Log_Class_Method, ConstantsCommonClasses._Log_Exact_Match, ConstantsCommonClasses._Log_Level, "", -1, "", "", "", "", -1, "", "" );
            
        }
        
        //Aquí por primera vez inicializamos un CLanguage que es para tener múltiples languajes en los archivos de bitacora (Logs) deben estar dentro del path WEB-INF/langs/security/checklogged.init.lang  pero si no existe no es problema se usa ingles para todo
        CLanguage languaje = CLanguage.getLanguage( extendedLogger, strRunningPath + ConstantsCommonClasses._Langs_Dir + ConstantsCommonClasses._Security_Dir + ConstantsCommonClasses._Check_Logged_Logger_Name + "." + ConstantsCommonClasses._Lang_Ext ); 
        
        Session currentSession = Sessions.getCurrent();
        
        //¿recuerdan que salvamos las credenciales del operador cuando este hace login correctamente?
        //Aqui tratamos de recuperarlo, si es null es que no hay un login válido todavía
        TBLOperator tblOperator = (TBLOperator) currentSession.getAttribute( ConstantsCommonClasses._User_Credential_Session_Key );
        
        //Aqui extraemos el request path que se coloca en el navegador
        String strRequestPath = Executions.getCurrent().getDesktop().getRequestPath();
        
        if ( tblOperator != null ) {

            //Para ser usado en el arhivo de log 
            String strLoginDateTime = (String) currentSession.getAttribute( ConstantsCommonClasses._Login_Date_Time_Session_Key );
            
            if ( strLoginDateTime == null )
                strLoginDateTime = "";

            if ( extendedLogger != null )
                extendedLogger.logMessage( "1" , languaje.translate( "Requesting [%s] from user [%s] [%s]", strRequestPath, tblOperator.getName(), strLoginDateTime ) );
        
        }
        else {
            
            if ( extendedLogger != null )
                extendedLogger.logMessage( "1" , languaje.translate( "Requesting [%s]", strRequestPath ) );
            
        }
        
        //Ok aquí viene la parte interesante
        //Si el tblOperator de la sesion es NULL es que no hay un operador con login válido
        if ( tblOperator == null ) {
        
            String strRedirectPath = "/index.zul"; //Lo mandamos para el index.zul que contiene el login.zul
            
            if ( extendedLogger != null )
                extendedLogger.logMessage( "1" , languaje.translate( "Redirecting to [%s]", strRedirectPath ) );
            
            //Que no sean iguales el uri pedido por el navegador y el que intento redireccionar, con eso paramos el bucle
            if ( strRequestPath.isEmpty() == false && strRedirectPath.equalsIgnoreCase( strRequestPath ) == false ) { 
                
                Executions.sendRedirect( strRedirectPath ); //Lo enviamos al index.zul
                
            }
            
        }
        //Con el condicional anterior establecimos que no es un usuario anonimo que tiene login
        //Sin embargo tampoco debe acceder diectamente por uri a ningún .zul como el manager.zul o el editor.zul
        //Entonces preguantemos si el uri que pide por el navegador es vacio osea o pide un archivo como manager.zul o editor.zul
        //Lo reenviamos para el home.zul
        else if ( strRequestPath.isEmpty() || strRequestPath.contains( "/home.zul" ) == false ) {
            
            String strRedirectPath = "/views/home/home.zul"; //Si no hay request path 

            if ( extendedLogger != null )
                extendedLogger.logMessage( "1" , languaje.translate( "Redirecting to [%s]", strRedirectPath ) );

            Executions.sendRedirect( strRedirectPath ); //Lo redireccionamos al home.zul por que tiene un login váĺido
            
        }
        
    }

    
}
