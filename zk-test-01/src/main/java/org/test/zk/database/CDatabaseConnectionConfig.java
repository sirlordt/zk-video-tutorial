package org.test.zk.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Properties;

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedLogger;

public class CDatabaseConnectionConfig implements Serializable {

    private static final long serialVersionUID = 3434139873022621518L;

    protected String strDriver = null;
    protected String strPrefix = null;
    protected String strHost = null;
    protected String strPort = null;
    protected String strDatabase = null;
    protected String strUser = null;
    protected String strPassword = null;

    public CDatabaseConnectionConfig( String strDriver, String strPrefix, String strHost, String strPort, String strDatabase, String strUser, String strPassword ) {
        
        this.strDriver = strDriver;
        this.strPrefix = strPrefix;
        this.strHost = strHost;
        this.strPort = strPort;
        this.strDatabase = strDatabase;
        this.strUser = strUser;
        this.strPassword = strPassword;
        
    }   
    
    public CDatabaseConnectionConfig() {
        
        
        
    }
    
    public boolean loadConfig( String strConfigPath, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        boolean bResult = false;
        
        try {

            File configFilePath = new File( strConfigPath );
            
            if ( configFilePath.exists() ) {

                Properties configsData = new Properties();
                
                FileInputStream inputStream = new FileInputStream( configFilePath );
                
                configsData.loadFromXML( inputStream );
                
                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Readed config values from file [%s]" ,  strConfigPath ) );

                this.strDriver = (String) configsData.get( "driver" );
                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "driver",  this.strDriver ) );
                this.strPrefix = (String) configsData.get( "prefix" );
                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "prefix",  this.strPrefix ) );
                this.strHost = (String) configsData.get( "host" );
                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "host",  this.strHost ) );
                this.strPort = (String) configsData.get( "port" );
                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "port", this.strPort ) );
                this.strDatabase = (String) configsData.get( "database" );
                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "database",  this.strDatabase ) );
                this.strUser = (String) configsData.get( "user" );
                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "user",  this.strUser ) );
                this.strPassword = (String) configsData.get( "password" );
                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "password",  this.strPassword ) );
                
                inputStream.close();                
                
                bResult = true;
                
            }
            else if ( localLogger != null ) {
                
                localLogger.logError( "-1001" , CLanguage.translateIf( localLanguage, "Config file in path [%s] not found" ,  strConfigPath ) );
                
            }
            
        }
        catch ( Exception Ex ) {
            
            if ( localLogger != null ) {
                
                localLogger.logException( "-1021" , Ex.getMessage(), Ex );
                
            }
            
        }
        
        return bResult;
        
    }

    public String getDriver() {
        
        return strDriver;
        
    }

    public void setDriver( String strDriver ) {
        
        this.strDriver = strDriver;
        
    }

    public String getPrefix() {
        
        return strPrefix;
        
    }

    public void setPrefix( String strPrefix ) {
        
        this.strPrefix = strPrefix;
        
    }

    public String getHost() {
        
        return strHost;
        
    }

    public void setHost( String strHost ) {
        
        this.strHost = strHost;
        
    }

    public String getPort() {
        
        return strPort;
        
    }

    public void setPort( String strPort ) {
        
        this.strPort = strPort;
        
    }

    public String getDatabase() {
        
        return strDatabase;
        
    }

    public void setDatabase( String strDatabase ) {
        
        this.strDatabase = strDatabase;
        
    }

    public String getUser() {
        
        return strUser;
        
    }

    public void setUser(String strUser) {
        
        this.strUser = strUser;
        
    }

    public String getPassword() {
        
        return strPassword;
        
    }

    public void setPassword(String strPassword) {
        
        this.strPassword = strPassword;
        
    }
    
}
