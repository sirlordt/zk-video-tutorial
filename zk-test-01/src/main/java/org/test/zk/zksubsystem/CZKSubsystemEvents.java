package org.test.zk.zksubsystem;

import java.util.LinkedList;
import java.util.List;

import org.test.zk.contants.SystemConstants;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.DesktopCleanup;
import org.zkoss.zk.ui.util.DesktopInit;
import org.zkoss.zk.ui.util.ExecutionCleanup;
import org.zkoss.zk.ui.util.ExecutionInit;
import org.zkoss.zk.ui.util.SessionCleanup;
import org.zkoss.zk.ui.util.SessionInit;
import org.zkoss.zk.ui.util.WebAppCleanup;
import org.zkoss.zk.ui.util.WebAppInit;

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedConfigLogger;
import commonlibs.extendedlogger.CExtendedLogger;

public class CZKSubsystemEvents implements DesktopInit, DesktopCleanup, SessionInit, SessionCleanup, WebAppInit, WebAppCleanup, ExecutionInit, ExecutionCleanup {

    @Override
    public void cleanup( Execution execution0, Execution execution1, List<Throwable> arg2 ) throws Exception {
        
        System.out.println( "Execution cleanup" );
        
    }

    @Override
    public void init( Execution execution0, Execution execution1 ) throws Exception {
        
        System.out.println( "Execution init" );
        
    }

    @Override
    public void cleanup( WebApp webApp ) throws Exception {
        
        System.out.println( "Web app cleanup" ); //Este evento se llama cuando el servidor de aplicaciones termina (Civilizadamente) el jetty en nuestro caso
        
        try {

            //Obtenermos el logger
            CExtendedLogger webAppLogger = CExtendedLogger.getLogger( SystemConstants._Webapp_Logger_Name );

            if ( webAppLogger != null ) {
                
                //Escribimos un mensajes al log
                webAppLogger.logMessage( "1" , CLanguage.translateIf( null, "Webapp ending now." ) );

                //Cerramos el log
                webAppLogger.flushAndClose();
                
            }

            //Eliminamos el atributo del webapp
            webApp.removeAttribute( SystemConstants._Webapp_Logger_Name );
            
        }
        catch ( Exception ex ) {
            
            System.out.println( ex.getMessage() );
            
        }
        
    }

    @Override
    public void init( WebApp webApp ) throws Exception {
        
        System.out.println( "Web app init" ); //Este evento se llama cuando el servidor de aplicaciones inicia el jetty en nuestro caso
        
        try {
            
            String strRunningPath = webApp.getRealPath( SystemConstants._WEB_INF_Dir ) + "/";

            //Se encarga de leer el archivo de configuración logger.config.xml
            CExtendedConfigLogger configLogger = new CExtendedConfigLogger(); //Esta clase viene de mis librerías que importamos en la estructura del proyecto

            //Le decimos la ruta del archivo WEB-INF/config/logger.config.xml
            String strConfigPath = strRunningPath + SystemConstants._Config_Dir + SystemConstants._Logger_Config_File_Name;

            if ( configLogger.loadConfig( strConfigPath, null, null ) ) { //Cargamos la configuración

                //Aquí creamos el logger como tal
                CExtendedLogger webAppLogger = CExtendedLogger.getLogger( SystemConstants._Webapp_Logger_Name );

                if ( webAppLogger.getSetupSet() == false ) { //Preguntamos si todavía no esta configurado

                    //Aquí le decimos donde va a crear los archivos de log WEB-INF/logs/system
                    String strLogPath = strRunningPath + SystemConstants._Logs_Dir + SystemConstants._System_Dir;

                    //Configuramos el logger según los parámetros de el archivo logger.config.xml y la ruta para escribir los archivos de log
                    webAppLogger.setupLogger( configLogger.getInstanceID(), configLogger.getLogToScreen(), strLogPath, SystemConstants._Webapp_Logger_File_Log, configLogger.getClassNameMethodName(), configLogger.getExactMatch(), configLogger.getLevel(), configLogger.getLogIP(), configLogger.getLogPort(), configLogger.getHTTPLogURL(), configLogger.getHTTPLogUser(), configLogger.getHTTPLogPassword(), configLogger.getProxyIP(), configLogger.getProxyPort(), configLogger.getProxyUser(), configLogger.getProxyPassword() );

                    //Guardamos el logger principal en un atributo del webapp
                    webApp.setAttribute( SystemConstants._Webapp_Logger_App_Attribute_Key, webAppLogger );

                }

                //Aquí escribimos al log en un archivo en WEB-INF/logs/system/webapplogger.log
                //Fijense en la clase CLanguage es otra de mis clases, pero no le presten mucha atención todavía
                //Basta con decir que es una clase que permite escribir los mensajes del log en varios idiomas
                webAppLogger.logMessage( "1" , CLanguage.translateIf( null, "Webapp logger loaded and configured [%s].", SystemConstants._Webapp_Logger_Name ) );
                
            }

        }
        catch ( Exception ex ) {
            
            System.out.println( ex.getMessage() ); //Aquí no queda más remedio que usar la salida estandar el sistem la consola, por que todavia no existe el logger
            
        }
        
    }

    @Override
    public void cleanup( Session session ) throws Exception {
        
        System.out.println( "Session cleanup" );
        
        //Aquí debemos limpiar logger que estan en la sesión del operador que invocó el Session.getCurrent().invalidate();
        
        @SuppressWarnings( "unchecked" )
        LinkedList<String> loggedSessionLoggers = (LinkedList<String>) session.getAttribute( SystemConstants._Logged_Session_Loggers );

        for ( String strLoggername : loggedSessionLoggers ) {
            
            CExtendedLogger currentLogger = CExtendedLogger.getLogger( strLoggername );
            
            currentLogger.flushAndClose(); //Cerrar el logger
            
        }
        
        //Vaciar la lista
        loggedSessionLoggers.clear();
        
        
    }

    @Override
    public void init( Session session, Object object ) throws Exception {
        
        System.out.println( "Session init" );
        
    }

    @Override
    public void cleanup( Desktop desktop ) throws Exception {
        
        System.out.println( "Desktop cleanup" );
        
    }

    @Override
    public void init( Desktop desktop, Object object ) throws Exception {
        
        System.out.println( "Desktop init" );
        
    }
    
}
