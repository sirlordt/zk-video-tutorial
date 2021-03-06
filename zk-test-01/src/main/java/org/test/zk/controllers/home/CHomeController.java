package org.test.zk.controllers.home;

import java.io.File;
import java.util.LinkedList;

import org.test.zk.contants.SystemConstants;
import org.test.zk.database.datamodel.TBLOperator;
import org.test.zk.utilities.SystemUtilities;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;

import commonlibs.commonclasses.CLanguage;
import commonlibs.commonclasses.ConstantsCommonClasses;
import commonlibs.extendedlogger.CExtendedConfigLogger;
import commonlibs.extendedlogger.CExtendedLogger;
import commonlibs.utils.Utilities;
import commonlibs.utils.ZKUtilities;


public class CHomeController extends SelectorComposer<Component> {

    private static final long serialVersionUID = 1040032932048818844L;

    //Lo mismo para que la variable haga bien el binding se necestan los dos id
    @Wire( "#includeNorthContent #labelHeader" )
    Label labelHeader;
    
    @Wire
    Tabbox tabboxMainContent;
    
    protected CExtendedLogger controllerLogger = null;
    
    protected CLanguage controllerLanguage = null;
    
    public void initcontrollerLoggerAndcontrollerLanguage( String strRunningPath, Session currentSession ) {
        
        //Leemos la configuración del logger del archivo o de la sesión
        CExtendedConfigLogger extendedConfigLogger = SystemUtilities.initLoggerConfig( strRunningPath, currentSession );

        //Obtenemos las credenciales del operador las cuales debieron ser guardadas por el CLoginController.java
        TBLOperator operatorCredential = (TBLOperator) currentSession.getAttribute( SystemConstants._Operator_Credential_Session_Key );

        //Inicializamos los valores de las variables
        String strOperator = SystemConstants._Operator_Unknown; //Esto es un valor por defecto no debería quedar con el pero si lo hacer el algoritmo no falla
        String strLoginDateTime = (String) currentSession.getAttribute( SystemConstants._Login_Date_Time_Session_Key ); //Recuperamos información de fecha y hora del inicio de sesión Login
        String strLogPath = (String) currentSession.getAttribute( SystemConstants._Log_Path_Session_Key ); //Recuperamos el path donde se guardarn los log ya que cambia según el nombre de l operador que inicie sesion  

        if ( operatorCredential != null )
            strOperator = operatorCredential.getName();  //Obtenemos el nombre del operador que hizo login

        if ( strLoginDateTime == null ) //En caso de ser null no ha fecha y hora de inicio de sesión colocarle una por defecto
            strLoginDateTime = Utilities.getDateInFormat( ConstantsCommonClasses._Global_Date_Time_Format_File_System_24, null );

        final String strLoggerName = SystemConstants._Home_Controller_Logger_Name;
        final String strLoggerFileName = SystemConstants._Home_Controller_File_Log;
        
        //Aqui creamos el logger para el operador que inicio sesión login en el sistem
        controllerLogger = CExtendedLogger.getLogger( strLoggerName + " " + strOperator + " " + strLoginDateTime );

        //strRunningPath = Sessions.getCurrent().getWebApp().getRealPath( SystemConstanst._WEB_INF_Dir ) + File.separator;

        //Esto se ejecuta si es la primera vez que esta creando el logger recuerden lo que pasa 
        //Cuando el usuario hace recargar en el navegador todo el .zul se vuelve a crear de cero, 
        //pero el logger persiste de manera similar a como lo hacen la viriables de session
        if ( controllerLogger.getSetupSet() == false ) {

            //Aquí vemos si es null esa varible del logpath intentamos poner una por defecto
            if ( strLogPath == null )
                strLogPath = strRunningPath + "/" + SystemConstants._Logs_Dir;

            //Si hay una configucación leida de session o del archivo la aplicamos
            if ( extendedConfigLogger != null )
                controllerLogger.setupLogger( strOperator + " " + strLoginDateTime, false, strLogPath, strLoggerFileName, extendedConfigLogger.getClassNameMethodName(), extendedConfigLogger.getExactMatch(), extendedConfigLogger.getLevel(), extendedConfigLogger.getLogIP(), extendedConfigLogger.getLogPort(), extendedConfigLogger.getHTTPLogURL(), extendedConfigLogger.getHTTPLogUser(), extendedConfigLogger.getHTTPLogPassword(), extendedConfigLogger.getProxyIP(), extendedConfigLogger.getProxyPort(), extendedConfigLogger.getProxyUser(), extendedConfigLogger.getProxyPassword() );
            else    //Si no usamos la por defecto
                controllerLogger.setupLogger( strOperator + " " + strLoginDateTime, false, strLogPath, strLoggerFileName, SystemConstants._Log_Class_Method, SystemConstants._Log_Exact_Match, SystemConstants._Log_Level, "", -1, "", "", "", "", -1, "", "" );

            //Inicializamos el lenguage para ser usado por el logger
            controllerLanguage = CLanguage.getLanguage( controllerLogger, strRunningPath + SystemConstants._Langs_Dir + strLoggerName + "." + SystemConstants._Lang_Ext ); 

            //Protección para el multi hebrado, puede que dos usuarios accedan exactamente al mismo tiempo a la página web, este código en el servidor se ejecuta en dos hebras
            synchronized( currentSession ) { //Aquí entra un asunto de habras y acceso multiple de varias hebras a la misma variable
            
                //Guardamos en la sesisón los logger que se van creando para luego ser destruidos.
                @SuppressWarnings("unchecked")
                LinkedList<String> loggedSessionLoggers = (LinkedList<String>) currentSession.getAttribute( SystemConstants._Logged_Session_Loggers );

                if ( loggedSessionLoggers != null ) {

                    //sessionLoggers = new LinkedList<String>();

                    //El mismo problema de la otra variable
                    synchronized( loggedSessionLoggers ) {

                        //Lo agregamos a la lista
                        loggedSessionLoggers.add( strLoggerName + " " + strOperator + " " + strLoginDateTime );

                    }

                    //Lo retornamos la sesión de este operador
                    currentSession.setAttribute( SystemConstants._Logged_Session_Loggers, loggedSessionLoggers );

                }
            
            }
            
        }
    
    }
    
    public void initView() {
        
        TBLOperator tblOperator = (TBLOperator) Sessions.getCurrent().getAttribute( SystemConstants._Operator_Credential_Session_Key ); 
        
        if ( tblOperator != null ) {
            
            if ( labelHeader != null ) {
                
                labelHeader.setValue( tblOperator.getRole() );
                
            }
            
        }

        //Aqui iniciamos los tab de manera dinámica
        
        
        //El home lo mostramos a todos los operadores
        //Creamos el componente a partir del .zul, createComponents crea un arreglo con los dos componentes raices 
        Component[] components = Executions.getCurrent().createComponents( "/views/tabs/home/tabhome.zul", null );
        
        //Buscamos el componente de tipo tab
        Tab tab = (Tab) ZKUtilities.getComponent( components, "Tab" );      
        
        if ( tab != null ) {
            
            //Lo anexamos de manera dinámica a su padre el tabbox del home.zul
            tabboxMainContent.getTabs().appendChild( tab );
            
            //Buscamos el componente de tipo tabpanel
            Tabpanel tabPanel = (Tabpanel) ZKUtilities.getComponent( components, "Tabpanel" );      
            
            //Lo anexamos de manera dinámica a su padre el tabbox del home.zul
            if ( tabPanel != null )
                tabboxMainContent.getTabpanels().appendChild( tabPanel );
        
        }
        
        if ( tblOperator.getRole().equalsIgnoreCase( "admin" ) ) {
            
            //Creamos el componente a partir del .zul, createComponents crea un arreglo con los dos componentes raices 
            components = Executions.getCurrent().createComponents( "/views/tabs/admin/tabadmin.zul", null );
            
            //Buscamos el componente de tipo tab
            tab = (Tab) ZKUtilities.getComponent( components, "Tab" );      
            
            if ( tab != null ) {
                
                //Lo anexamos de manera dinámica a su padre el tabbox del home.zul
                tabboxMainContent.getTabs().appendChild( tab );
                
                //Buscamos el componente de tipo tabpanel
                Tabpanel tabPanel = (Tabpanel) ZKUtilities.getComponent( components, "Tabpanel" );      
                
                //Lo anexamos de manera dinámica a su padre el tabbox del home.zul
                if ( tabPanel != null )
                    tabboxMainContent.getTabpanels().appendChild( tabPanel );
            
            }
            
        }
        else if ( tblOperator.getRole().equalsIgnoreCase( "operator.type1" ) ) {
            
            
        }
        else if ( tblOperator.getRole().equalsIgnoreCase( "operator.type2" ) ) {
            
            
        }
        
    }
    
    @Override
    public void doAfterCompose( Component comp ) {
        
        try {
         
            super.doAfterCompose( comp );

            final String strRunningPath = Sessions.getCurrent().getWebApp().getRealPath( SystemConstants._WEB_INF_Dir ) + File.separator;
            
            //Inicializamos el logger y el language
            initcontrollerLoggerAndcontrollerLanguage( strRunningPath, Sessions.getCurrent() );
    
            initView();
            
        }
        catch ( Exception ex ) {
            
            if ( controllerLogger != null )   
                controllerLogger.logException( "-1021", ex.getMessage(), ex );        
            
        }
    
    }    

    
    @Listen("onTimer=#includeNorthContent #timerKeepAliveSession" )
    public void onTimer( Event event ) {
        
        //Este evento se ejecutara cada 120000 milisegundos 1 minuto aprox, esto no es un relog atomico a si que son tiempos aproximados
        //Muestra un tablerito en la parte superior central de la pantalla
        Clients.showNotification( "Automatic renewal of the session successful", "info",  null, "before_center", 2000, true );
        
    }
    
    @Listen( "onClick = #includeNorthContent #buttonChangePassword" )  
    public void onClickbuttonChangePassword( Event event ) {

        if ( controllerLogger != null )
            controllerLogger.logMessage( "1" , CLanguage.translateIf( controllerLanguage, "Button change password clicked" ) );
        
    }    
    
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    @Listen( "onClick = #includeNorthContent #buttonLogout" )  
    public void onClickbuttonLogout( Event event ) {
        
        if ( controllerLogger != null )
            controllerLogger.logMessage( "1" , CLanguage.translateIf( controllerLanguage, "Button change password clicked" ) );
        
        Messagebox.show( "You are sure do you want logout from system?", "Logout", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
            
            public void onEvent(Event evt) throws InterruptedException {
                
                if ( evt.getName().equals( "onOK" ) ) {
                    
                    if ( controllerLogger != null )
                        controllerLogger.logMessage( "1" , CLanguage.translateIf( controllerLanguage, "Logout confirm accepted" ) );
                    
                    //Ok aquí vamos hacer el logout
                    Sessions.getCurrent().invalidate(); //Listo obliga limpiar la sessión mejor que ir removeAttribute a removeAttribute
                   
                    Executions.sendRedirect( "/index.zul"); //Lo enviamos al login
                    
                }
                else {
                    
                    if ( controllerLogger != null )
                        controllerLogger.logMessage( "1" , CLanguage.translateIf( controllerLanguage, "Logout confirm canceled" ) );
                    
                }
                
            }
            
        });         
        
    }
    
}
