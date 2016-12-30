package org.test.zk.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Properties;

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

    public boolean loadConfig( String strConfigPath ) {
        
        boolean bResult = false;
        
        try {
            
            File configFilePath =  new File( strConfigPath );
            
            if ( configFilePath.exists() ) {
                
                Properties configsData = new Properties();
                
                FileInputStream inputStream = new FileInputStream( configFilePath );
                
                configsData.loadFromXML( inputStream ); //Aquí leemos del archivo
                
                this.strDriver = configsData.getProperty( "driver" );
                this.strPrefix = configsData.getProperty( "prefix" );
                this.strHost = configsData.getProperty( "host" );
                this.strPort = configsData.getProperty( "port" );
                this.strDatabase = configsData.getProperty( "database" );
                this.strUser = configsData.getProperty( "user" );
                this.strPassword = configsData.getProperty( "password" );
                
                inputStream.close(); //Cerramos el stream
                
                bResult = true;
                
            }
            else {
                
                System.out.println( "Error the file not found" );
                
            }
            
        }
        catch ( Exception ex ) {
            
            ex.printStackTrace();
            
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
    
    public void setUser( String strUser ) {
        
        this.strUser = strUser;

    }
    
    public String getPassword() {
        
        return strPassword;

    }
    
    public void setPassword( String strPassword ) {
        
        this.strPassword = strPassword;
    
    }
    
}
