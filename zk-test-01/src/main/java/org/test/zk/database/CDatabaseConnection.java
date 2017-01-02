package org.test.zk.database;

import java.sql.Connection;
import java.sql.DriverManager;

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedLogger;

public class CDatabaseConnection {

    protected Connection dbConnection; 
    
    protected CDatabaseConnectionConfig dbConnectionConfig;
    
    public CDatabaseConnection() {
        
        dbConnection = null;
        
        dbConnectionConfig = null;
        
    }
    
    public Connection getDBConnection() {
        
        return dbConnection;
        
    }

    public void setDBConnection( Connection dbConnection, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        localLogger.logWarning( "-1" , CLanguage.translateIf( localLanguage, "Set database connection manually" ) );
        
        this.dbConnection = dbConnection;
        
    }
    
    public CDatabaseConnectionConfig getDBConnectionConfig() {
        
        return dbConnectionConfig;
        
    }

    public void setDBConnectionConfig( CDatabaseConnectionConfig dbConnectionConfig, CExtendedLogger localLogger, CLanguage localLanguage  ) {
        
        localLogger.logWarning( "-1" , CLanguage.translateIf( localLanguage, "Set database connection config manually" ) );

        this.dbConnectionConfig = dbConnectionConfig;
        
    }
    
    public boolean makeConnectionToDB( CDatabaseConnectionConfig localDBConnectionConfig, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        boolean bResult = false;
        
        try {

            if ( this.dbConnection == null ) {

                Class.forName( localDBConnectionConfig.strDriver );

                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded driver [%s]", localDBConnectionConfig.strDriver ) );
                
                String strDatabaseURL = localDBConnectionConfig.strPrefix + localDBConnectionConfig.strHost + ":" + localDBConnectionConfig.strPort + "/" + localDBConnectionConfig.strDatabase;

                localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Try to connecting to [%s] user [%s] password [%s]", strDatabaseURL, localDBConnectionConfig.strUser, localDBConnectionConfig.strPassword ) );

                Connection localDBConnection = DriverManager.getConnection( strDatabaseURL, localDBConnectionConfig.strUser, localDBConnectionConfig.strPassword );
                //DBConnection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/" + strDatabase, strDBUserName, strDBPassword );

                localDBConnection.setTransactionIsolation( Connection.TRANSACTION_READ_COMMITTED );
                
                localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Connected to [%s] user [%s] password [%s]", strDatabaseURL, localDBConnectionConfig.strUser, localDBConnectionConfig.strPassword ) );

                bResult = localDBConnection != null && localDBConnection.isValid( 5 );
                
                if ( bResult ) {
                 
                    localDBConnection.setAutoCommit( false );
                    
                    localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Connection auto commit set to false" ) );

                    this.dbConnection = localDBConnection; //Save the database connection to this instance object
                    
                    this.dbConnectionConfig = localDBConnectionConfig; //Save the config for the connection to this instance object

                    localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Connection checked" ) );
                    
                }    
                else {
                
                    localDBConnection.close();
                    
                    localDBConnection = null;

                    localLogger.logError( "-1001" , CLanguage.translateIf( localLanguage, "Failed check the connection" ) );
                
                }   

            }
            else {
                
                localLogger.logWarning( "-1" , CLanguage.translateIf( localLanguage, "The database is already initiated" ) );
                
            }
            
        }
        catch ( Exception Ex ) {

            if ( localLogger != null ) {
                
                localLogger.logException( "-1021" , Ex.getMessage(), Ex );
                
            }
            
        }       
        
        return bResult;
        
    }
    
    public boolean closeConnectionToDB( CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        boolean bResult = false;
        
        try {

            localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Trying to close the connection to the database" ) );

            if ( dbConnection != null ) {
                
                dbConnection.close();
                
                localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Database connection closed successfully" ) );
                
            }
            else {
                
                localLogger.logWarning( "-1" , CLanguage.translateIf( localLanguage, "The connection variable is null" ) );
                
            }

            dbConnection = null;
            dbConnectionConfig = null;
            
            localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Set to null connection and conection config variable" ) );

            bResult = true;
        
        }
        catch ( Exception Ex ) {

            if ( localLogger != null ) {
                
                localLogger.logException( "-1021" , Ex.getMessage(), Ex );
                
            }

        }       

        return bResult;
            
    }
    
    public boolean isValid( CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        boolean bResult = false;
        
        try {
            
            localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Checking for database connection is valid" ) );
            
            bResult = dbConnection.isValid( 5 ); //wait max for 5 seconds
            
        } 
        catch ( Exception Ex ) {
            
            if ( localLogger != null ) {
                
                localLogger.logException( "-1021" , Ex.getMessage(), Ex );
                
            }
            
        }
        
        return bResult;
        
    }
    
}    
