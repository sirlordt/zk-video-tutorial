package org.test.zk.database.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.test.zk.database.CDatabaseConnection;
import org.test.zk.database.datamodel.TBLPerson;

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedLogger;

public class PersonDAO {
    
    public static TBLPerson loadData( final CDatabaseConnection databaseConnection, final String strId, CExtendedLogger localLogger, CLanguage localLanguage  ) {
        
        TBLPerson result = null;
        
        try {
            
            if ( databaseConnection != null && databaseConnection.getDBConnection() != null ) {
                
                Statement statement = databaseConnection.getDBConnection().createStatement();
                
                ResultSet resultSet = statement.executeQuery( "Select * From tblPerson Where Id = '" + strId + "'" );

                if ( resultSet.next() ) {

                    result = new TBLPerson();
                    
                    result.setId( resultSet.getString( "Id" ) );
                    result.setFirstName( resultSet.getString( "FirstName" ) );
                    result.setLastName( resultSet.getString( "LastName" ) );
                    result.setGender( resultSet.getInt( "Gender" ) );
                    result.setBirthDate( resultSet.getDate( "BirthDate" ).toLocalDate() );
                    result.setComment( resultSet.getString( "Comment" ) );
                    
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

                final String strSQL = "Delete From tblPerson Where Id = '" + strId + "'";
                
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
    
    public static boolean instertData( final CDatabaseConnection databaseConnection, final TBLPerson tblPerson, CExtendedLogger localLogger, CLanguage localLanguage  ) {
        
        boolean bResult = false; 
        
        try {
            
            if ( databaseConnection != null && databaseConnection.getDBConnection() != null ) {
                
                Statement statement = databaseConnection.getDBConnection().createStatement();

                final String strSQL = "Insert Into tblPerson(Id,FirstName,LastName,Gender,BirthDate,Comment,CreatedBy,CreatedAtDate,CreatedAtTime,UpdatedBy,UpdatedAtDate,UpdatedAtTime) Values('" + tblPerson.getId() + "','" + tblPerson.getFirstName() + "','" + tblPerson.getLastName() + "'," + tblPerson.getGender() + ",'" + tblPerson.getBirthDate().toString() + "','" + tblPerson.getComment() + "','test','" + LocalDate.now().toString() + "','" + LocalTime.now().toString() + "', null,null,null )";
                
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
    
    public static boolean updateData( final CDatabaseConnection databaseConnection, final TBLPerson tblPerson, CExtendedLogger localLogger, CLanguage localLanguage  ) {
        
        boolean bResult = false; 
        
        try {
            
            if ( databaseConnection != null && databaseConnection.getDBConnection() != null ) {
                
                Statement statement = databaseConnection.getDBConnection().createStatement();

                //Esto es un dolor de cabeza sin ORM como hibernate o mybatis
                final String strSQL = "Update tblPerson Set Id='" + tblPerson.getId() + "', FirstName = '" + tblPerson.getFirstName() + "',LastName = '" + tblPerson.getLastName() + "', Gender = " + tblPerson.getGender() + ",BirthDate = '" + tblPerson.getBirthDate().toString() + "', Comment = '" + tblPerson.getComment() + "',UpdatedBy = 'Test01', UpdatedAtDate = '" + LocalDate.now().toString() + "',UpdatedAtTime = '" + LocalTime.now().toString() + "' Where Id = '" + tblPerson.getId() + "'";
                
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
    
    public static List<TBLPerson> searchData( final CDatabaseConnection databaseConnection, CExtendedLogger localLogger, CLanguage localLanguage  ) {
        
        List<TBLPerson> result = new ArrayList<TBLPerson>(); 
        
        try {
            
            if ( databaseConnection != null && databaseConnection.getDBConnection() != null ) {
                
                Statement statement = databaseConnection.getDBConnection().createStatement();
                
                ResultSet resultSet = statement.executeQuery( "Select * From tblPerson" );

                while ( resultSet.next() ) {

                    TBLPerson tblPerson = new TBLPerson();
                    
                    tblPerson.setId( resultSet.getString( "Id" ) );
                    tblPerson.setFirstName( resultSet.getString( "FirstName" ) );
                    tblPerson.setLastName( resultSet.getString( "LastName" ) );
                    tblPerson.setGender( resultSet.getInt( "Gender" ) );
                    tblPerson.setBirthDate( resultSet.getDate( "BirthDate" ).toLocalDate() );
                    tblPerson.setComment( resultSet.getString( "Comment" ) );
                    
                    //Los siguientes métodos setCreatedBy bienen de la clase CAuditableDataModel
                    tblPerson.setCreatedBy( resultSet.getString( "CreatedBy" ) ); 
                    tblPerson.setCreatedAtDate( resultSet.getDate( "CreatedAtDate" ).toLocalDate() ); 
                    tblPerson.setCreatedAtTime( resultSet.getTime( "CreatedAtTime" ).toLocalTime() ); 
                    tblPerson.setUpdatedBy( resultSet.getString( "UpdatedBy" ) ); //Puede ser null, pero no es relevante por que no accedo a metodos de la clase String 
                    tblPerson.setUpdatedAtDate( resultSet.getDate( "UpdatedAtDate" ) != null ? resultSet.getDate( "UpdatedAtDate" ).toLocalDate() : null ); //Puede ser un null 
                    tblPerson.setUpdatedAtTime( resultSet.getTime( "UpdatedAtTime" ) != null ? resultSet.getTime( "UpdatedAtTime" ).toLocalTime() : null );  //Pueder ser null de la bd
                    
                    result.add( tblPerson ); //Agregarlo a la lista de resultado
                    
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
