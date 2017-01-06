package org.test.zk.database.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.test.zk.database.CDatabaseConnection;
import org.test.zk.database.datamodel.TBLOperator;

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedLogger;

public class OperatorDAO {

    public static TBLOperator loadData( final CDatabaseConnection databaseConnection, final String strId, CExtendedLogger localLogger, CLanguage localLanguage ) {
    
        TBLOperator result = null;
            
        try {
            
            if ( databaseConnection != null && databaseConnection.getDBConnection() != null ) {
                
                Statement statement = databaseConnection.getDBConnection().createStatement();
                
                ResultSet resultSet = statement.executeQuery( "Select * From tblOperator Where Id = '" + strId + "'" );

                if ( resultSet.next() ) {

                    result = new TBLOperator();
                    
                    result.setId( resultSet.getString( "Id" ) );
                    result.setName( resultSet.getString( "Name" ) );
                    result.setPassword( resultSet.getString( "Password" ) );
                    result.setComment( resultSet.getString( "Comment" ) );
                    
                    result.setDisabledBy( resultSet.getString( "DisabledBy" ) ); 
                    result.setDisabledAtDate( resultSet.getDate( "DisabledAtDate" ).toLocalDate() ); 
                    result.setDisabledAtTime( resultSet.getTime( "DisabledAtTime" ).toLocalTime() ); 
                    result.setLastLoginAtDate( resultSet.getDate( "LastLoginAtDate" ) != null ? resultSet.getDate( "LastLoginAtDate" ).toLocalDate() : null ); //Puede ser un null 
                    result.setLastLoginAtTime( resultSet.getTime( "LastLoginAtTime" ) != null ? resultSet.getTime( "LastLoginAtTime" ).toLocalTime() : null );  //Pueder ser null de la bd
                    
                    //Los siguientes métodos setCreatedBy bienen de la clase CAuditableDataModel
                    result.setCreatedBy( resultSet.getString( "CreatedBy" ) ); 
                    result.setCreatedAtDate( resultSet.getDate( "CreatedAtDate" ).toLocalDate() ); 
                    result.setCreatedAtTime( resultSet.getTime( "CreatedAtTime" ).toLocalTime() ); 
                    result.setUpdatedBy( resultSet.getString( "UpdatedBy" ) ); //Puede ser null, pero no es relevante por que no accedo a metodos de la clase String 
                    result.setUpdatedAtDate( resultSet.getDate( "UpdatedAtDate" ) != null ? resultSet.getDate( "UpdatedAtDate" ).toLocalDate() : null ); //Puede ser un null 
                    result.setUpdatedAtTime( resultSet.getTime( "UpdatedAtTime" ) != null ? resultSet.getTime( "UpdatedAtTime" ).toLocalTime() : null );  //Pueder ser null de la bd
                    
                }    
                
                //Cuando se termina debemos cerrar los recursos
                resultSet.close();
                statement.close();
                
                //NO Cerrarmos la conexión la mantenemos abierta para usarla en otras operaciones
                
            }
        
        }
        catch ( Exception ex ) {
            
            if ( localLogger != null )   
                localLogger.logException( "-1021", ex.getMessage(), ex );        
            
        }
        
        return result;    
        
    }
        
    public static boolean deleteData( final CDatabaseConnection databaseConnection, final String strId, CExtendedLogger localLogger, CLanguage localLanguage  ) {
        
        boolean bResult = false; 
        
        try {
            
            if ( databaseConnection != null && databaseConnection.getDBConnection() != null ) {
                
                Statement statement = databaseConnection.getDBConnection().createStatement();

                final String strSQL = "Delete From tblOperator Where Id = '" + strId + "'";
                
                //Esta es la parte fastidiosa de no usar un ORM
                statement.executeUpdate( strSQL );
                
                databaseConnection.getDBConnection().commit(); //Commit la transacción
                
                statement.close();
                
                bResult = true;
                
            }    
            
        }
        catch ( Exception ex ) {
            
            if ( databaseConnection != null && databaseConnection.getDBConnection() != null ) {

                try {
                   
                    databaseConnection.getDBConnection().rollback(); //En caso de error rollback todas las operaciones anteriores.
                     
                }
                catch ( Exception e ) {
                    
                    if ( localLogger != null )   
                        localLogger.logException( "-1021", e.getMessage(), e );        
                    
                } 
                
            }    
            
            if ( localLogger != null )   
                localLogger.logException( "-1022", ex.getMessage(), ex );        
            
        }
        
        return bResult;

    }    
            
    public static boolean instertData( final CDatabaseConnection databaseConnection, final TBLOperator tblOperator, CExtendedLogger localLogger, CLanguage localLanguage  ) {
    
        boolean bResult = false; 
        
        try {
            
            if ( databaseConnection != null && databaseConnection.getDBConnection() != null ) {
                
                Statement statement = databaseConnection.getDBConnection().createStatement();

                final String strSQL = "Insert Into tblOperator(Id,Name,Password,Comment,CreatedBy,CreatedAtDate,CreatedAtTime) Values('" + tblOperator.getId() + "','" + tblOperator.getName() + "','" + tblOperator.getPassword() + "','" + tblOperator.getComment() + "','test','" + LocalDate.now().toString() + "','" + LocalTime.now().toString() + "')";
                
                //Esta es la parte fastidiosa de no usar un ORM
                statement.executeUpdate( strSQL );
                
                databaseConnection.getDBConnection().commit(); //Commit la transacción
                
                statement.close(); //Cerrar y liberar recursos
                
                bResult = true;
                
            }    
            
        }
        catch ( Exception ex ) {

            if ( databaseConnection != null && databaseConnection.getDBConnection() != null ) {

                try {
                   
                    databaseConnection.getDBConnection().rollback(); //En caso de error rollback todas las operaciones anteriores.
                     
                }
                catch ( Exception e ) {
                    
                    if ( localLogger != null )   
                        localLogger.logException( "-1021", e.getMessage(), e );        
                    //e.printStackTrace(); //Podemos tenes problemas en el rollback nos exige un try catch
                    
                } 
                
            }    
            
            if ( localLogger != null )   
                localLogger.logException( "-1022", ex.getMessage(), ex );        
            
        }
        
        return bResult;
    
    }
    
    public static boolean updateData( final CDatabaseConnection databaseConnection, final TBLOperator tblOperator, CExtendedLogger localLogger, CLanguage localLanguage  ) {
        
        boolean bResult = false; 
        
        try {
            
            if ( databaseConnection != null && databaseConnection.getDBConnection() != null ) {
                
                Statement statement = databaseConnection.getDBConnection().createStatement();

                final String strDisabledAtDate = tblOperator.getDisabledBy() != null ? "'" + LocalDate.now().toString() + "'" : "null";
                final String strDisabledAtTime = tblOperator.getDisabledBy() != null ? "'" + LocalTime.now().toString() + "'" : "null";
                
                //Esto es un dolor de cabeza sin ORM como hibernate o mybatis
                final String strSQL = "Update tblOperator Set Id='" + tblOperator.getId() + "', Name = '" + tblOperator.getName() + "', Password = '" + tblOperator.getPassword() + "', Comment = '" + tblOperator.getComment() + "', UpdatedBy = 'Test01', UpdatedAtDate = '" + LocalDate.now().toString() + "',UpdatedAtTime = '" + LocalTime.now().toString() + "', DisabledBy = '" + tblOperator.getDisabledBy() + "', DisabledAtDate = " + strDisabledAtDate + ", DisabledAtTime = " +  strDisabledAtTime + " Where Id = '" + tblOperator.getId() + "'";
                
                //Esta es la parte fastidiosa de no usar un ORM
                statement.executeUpdate( strSQL );
                
                databaseConnection.getDBConnection().commit(); //Commit la transacción
                
                statement.close(); //Cerrar y liberar recursos
                
                bResult = true;
                
            }    
            
        }
        catch ( Exception ex ) {

            if ( databaseConnection != null && databaseConnection.getDBConnection() != null ) {

                try {
                   
                    databaseConnection.getDBConnection().rollback(); //En caso de error rollback todas las operaciones anteriores.
                     
                }
                catch ( Exception e ) {
                    
                    if ( localLogger != null )   
                        localLogger.logException( "-1021", e.getMessage(), e );        
                    //e.printStackTrace(); //Podemos tenes problemas en el rollback nos exige un try catch
                    
                } 
                
            }    
            
            if ( localLogger != null )   
                localLogger.logException( "-1022", ex.getMessage(), ex );        
            
        }
        
        return bResult;
        
    }

    public static boolean updateLastLogin( final CDatabaseConnection databaseConnection, final String strId, CExtendedLogger localLogger, CLanguage localLanguage  ) {
        
        boolean bResult = false; 
        
        try {
            
            if ( databaseConnection != null && databaseConnection.getDBConnection() != null ) {
                
                Statement statement = databaseConnection.getDBConnection().createStatement();

                //Esto es un dolor de cabeza sin ORM como hibernate o mybatis
                final String strSQL = "Update tblOperator Set LastLoginAtDate = '" + LocalDate.now().toString() + "', LastLoginAtTime = '" + LocalTime.now().toString() + "' Where Id = '" + strId + "'";
                
                //Esta es la parte fastidiosa de no usar un ORM
                statement.executeUpdate( strSQL );
                
                databaseConnection.getDBConnection().commit(); //Commit la transacción
                
                statement.close(); //Cerrar y liberar recursos
                
                bResult = true;
                
            }    
            
        }
        catch ( Exception ex ) {

            if ( databaseConnection != null && databaseConnection.getDBConnection() != null ) {

                try {
                   
                    databaseConnection.getDBConnection().rollback(); //En caso de error rollback todas las operaciones anteriores.
                     
                }
                catch ( Exception e ) {
                    
                    if ( localLogger != null )   
                        localLogger.logException( "-1021", e.getMessage(), e );        
                    //e.printStackTrace(); //Podemos tenes problemas en el rollback nos exige un try catch
                    
                } 
                
            }    
            
            if ( localLogger != null )   
                localLogger.logException( "-1022", ex.getMessage(), ex );        
            
        }
        
        return bResult;
        
    }
    
    public static List<TBLOperator> searchData( final CDatabaseConnection databaseConnection, CExtendedLogger localLogger, CLanguage localLanguage  ) {
        
        List<TBLOperator> result = new ArrayList<TBLOperator>(); 
        
        return result;
        
    }
 
    public static TBLOperator checkValid( final CDatabaseConnection databaseConnection, final String strName, final String strPassword, CExtendedLogger localLogger, CLanguage localLanguage  ) {
        
        TBLOperator result = null; 

        try {
            
            if ( databaseConnection != null && databaseConnection.getDBConnection() != null ) {
                
                Statement statement = databaseConnection.getDBConnection().createStatement();
                
                ResultSet resultSet = statement.executeQuery( "Select * From tblOperator Where Name = '" + strName + "' And Password = '" + strPassword + "'" );

                if ( resultSet.next() ) {

                    result = new TBLOperator();
                    
                    result.setId( resultSet.getString( "Id" ) );
                    result.setName( resultSet.getString( "Name" ) );
                    result.setPassword( resultSet.getString( "Password" ) );
                    result.setComment( resultSet.getString( "Comment" ) );
                    
                    result.setDisabledBy( resultSet.getString( "DisabledBy" ) ); 
                    result.setDisabledAtDate( resultSet.getDate( "DisabledAtDate" ) != null ? resultSet.getDate( "DisabledAtDate" ).toLocalDate() : null ); //Pueder ser null de la bd
                    result.setDisabledAtTime( resultSet.getTime( "DisabledAtTime" ) != null ? resultSet.getTime( "DisabledAtTime" ).toLocalTime() : null ); //Pueder ser null de la bd
                    result.setLastLoginAtDate( resultSet.getDate( "LastLoginAtDate" ) != null ? resultSet.getDate( "LastLoginAtDate" ).toLocalDate() : null ); //Puede ser un null 
                    result.setLastLoginAtTime( resultSet.getTime( "LastLoginAtTime" ) != null ? resultSet.getTime( "LastLoginAtTime" ).toLocalTime() : null );  //Pueder ser null de la bd
                    
                    //Los siguientes métodos setCreatedBy bienen de la clase CAuditableDataModel
                    result.setCreatedBy( resultSet.getString( "CreatedBy" ) ); 
                    result.setCreatedAtDate( resultSet.getDate( "CreatedAtDate" ).toLocalDate() ); 
                    result.setCreatedAtTime( resultSet.getTime( "CreatedAtTime" ).toLocalTime() ); 
                    result.setUpdatedBy( resultSet.getString( "UpdatedBy" ) ); //Puede ser null, pero no es relevante por que no accedo a metodos de la clase String 
                    result.setUpdatedAtDate( resultSet.getDate( "UpdatedAtDate" ) != null ? resultSet.getDate( "UpdatedAtDate" ).toLocalDate() : null ); //Puede ser un null 
                    result.setUpdatedAtTime( resultSet.getTime( "UpdatedAtTime" ) != null ? resultSet.getTime( "UpdatedAtTime" ).toLocalTime() : null );  //Pueder ser null de la bd
                    
                }    
                
                //Cuando se termina debemos cerrar los recursos
                resultSet.close();
                statement.close();
                
                //NO Cerrarmos la conexión la mantenemos abierta para usarla en otras operaciones
                
            }
        
        }
        catch ( Exception ex ) {
            
            if ( localLogger != null )   
                localLogger.logException( "-1021", ex.getMessage(), ex );        
            
        }
        
        return result;
        
    }
    
}
