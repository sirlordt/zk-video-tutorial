package org.test.zk.database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

public class CDatabaseConnection implements Serializable {
    
    private static final long serialVersionUID = 5779415214463024552L;

    static final String _DB_URL = "jdbc:mysql://localhost/TestDB";
    
    //  Database credentials
    static final String _USER = "root";
    static final String _PASS = "rafael";
   
    protected Connection dbConnection = null;
    
    public Connection getDBConnection() {
        
        return dbConnection;
    
    }
    
    public void setDBConnection( Connection dbConnection ) {
        
        this.dbConnection = dbConnection;

    }

    public boolean makeConnectionToDatabase() {
        
        boolean bResult = false;
        
        try {
        
            Class.forName( "com.mysql.jdbc.Driver" ); //Como ya dije el driver jdbc de mysql no esta integrado en java debemos cargalo primero en la memoria

            dbConnection = DriverManager.getConnection( _DB_URL, _USER, _PASS );
            
            dbConnection.setTransactionIsolation( Connection.TRANSACTION_READ_COMMITTED ); 
            
            dbConnection.setAutoCommit( false );
            
            bResult = true;
            
        }
        catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
        
        return bResult;
        
    }
    
    public boolean closeConnectionToDatabase() {
        
        boolean bResult = false;
        
        try {
            
            if ( dbConnection != null ) {
                
                dbConnection.close();
                
                bResult = true;
                
            }
            
        }
        catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
        
        return bResult;
        
    }
    
}
