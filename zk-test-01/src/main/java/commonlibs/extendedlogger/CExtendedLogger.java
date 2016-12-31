/*******************************************************************************
 * Copyright (c) 2013 SirLordT <sirlordt@gmail.com>.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     SirLordT <sirlordt@gmail.com> - initial API and implementation
 ******************************************************************************/
package commonlibs.extendedlogger;

import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SocketHandler;

public class CExtendedLogger extends Logger {

	protected int intInternalSequence;
	protected boolean bSetupSet = false;
	protected int intCallStackLevel = 3; //Adjust call stack index for precise info
	
	protected String strInstanceID = ""; 
	
	protected String strLogIP = "";
	protected int intLogPort = -1;
	protected boolean bSocketHandlerActive = false;
	
	protected String strHTTPLogURL = "";
	protected String strHTTPLogUser = "";
	protected String strHTTPLogPassword = "";
	protected String strProxyIP = "";
	protected int intProxyPort = -1;
	protected String strProxyUser = "";
	protected String strProxyPassword = "";
	protected boolean bHTTPHandlerActive = false;
	
	protected String strLogPath = "";
	protected String strLogFile = "";
	
	protected SocketHandler SocketHandler = null;
	protected FileHandler FileHandler = null;
	protected ConsoleHandler ConsoleHandler = null;
	protected CExtendedHTTPHandler HTTPHandler = null;
	
	protected CExtendedLogXMLFormatter ExXmlFormatter = null;
	
	public static final int _Min_Port = 1; 
	public static final int _Max_Port = 65535; 
	
	public CExtendedLogger( String strName ) {

	   super( strName, null ); 

	   intInternalSequence = 0;
	   
	   this.setUseParentHandlers( false );
	   
	   this.setLevel( Level.ALL );
	   
	}
	
	public static CExtendedLogger getLogger( String strName ) {
	
		CExtendedLogger Result = null;
		
		try {
		
			LogManager LoggerManager = LogManager.getLogManager();

			Logger tmpObject = LoggerManager.getLogger( strName );

			if ( tmpObject == null ) LoggerManager.addLogger( new CExtendedLogger( strName ) );

			tmpObject = LoggerManager.getLogger( strName );
			
			Result = ( CExtendedLogger ) tmpObject;
		
		}
		catch ( Exception Ex ) {
			
			Ex.printStackTrace();
			
		}
		
		return Result;
		
	}	
	
    public void setupLogger( String strInstanceID, boolean bLogToScreen, String strLogPath, String strLogFile, String strLogFilters, boolean bExactMatch, String strLogLevel, String strLogIP, int intLogPort, String strHTTPLogURL, String strHTTPLogUser, String strHTTPLogPassword, String strProxyIP, int intProxyPort, String strProxyUser, String strProxyPassword ) {
    	
    	try {

    		this.strInstanceID = strInstanceID;
    		
    		if ( strLogIP.trim().isEmpty() == false && intLogPort >= _Min_Port && intLogPort <= _Max_Port ) {
    		 
    			SocketHandler = new SocketHandler( strLogIP, intLogPort );
    			
    			this.strLogIP = strLogIP;
    			this.intLogPort = intLogPort;
    			this.bSocketHandlerActive = true;
    			
    		}     

			this.strHTTPLogURL = strHTTPLogURL;
			
			if ( strHTTPLogUser.trim().isEmpty() == false ) {
				
				this.strHTTPLogUser = strHTTPLogUser;
				this.strHTTPLogPassword = strHTTPLogPassword;
				
			}

			if ( strProxyIP.trim().isEmpty() == false && intProxyPort >= _Min_Port && intProxyPort <= _Max_Port  ) {
	
				this.strProxyIP = strProxyIP;
				this.intProxyPort = intProxyPort;
				
                if ( strProxyUser.trim().isEmpty() == false ) {
				
                	this.strProxyUser = strProxyUser;
                	this.strProxyPassword = strProxyPassword;
				
                }
			
			}

			if ( strHTTPLogURL.trim().isEmpty() == false ) {
				
				HTTPHandler = new CExtendedHTTPHandler( this.getName() ); 

				if ( HTTPHandler.configureHandler( strHTTPLogURL, strHTTPLogUser, strHTTPLogPassword, strProxyIP, intProxyPort, strProxyUser, strProxyPassword ) ) {
					
					this.addHandler( HTTPHandler );
					
				}
				else {
					
					HTTPHandler = null;
					
				}
				
			}
			
    	}
    	catch ( Exception Ex ) {
    		
    		SocketHandler = null;
			Ex.printStackTrace();
    		
    	}
    	
		try { 
    	  
			this.strLogPath = strLogPath;
			this.strLogFile = strLogFile;
			
			File DirPath = new File( strLogPath );
			
			if ( DirPath.exists() == false ) {
				
				DirPath.mkdirs();
				
			}
			
			FileHandler = new FileHandler( strLogPath + strLogFile, 2048576, 50, false );
		 
			ExXmlFormatter = new CExtendedLogXMLFormatter();

			ExXmlFormatter.strLogFilePath = strLogPath;
			ExXmlFormatter.strLogFileName = strLogFile;
			
			FileHandler.setFormatter( ExXmlFormatter );

			if ( bLogToScreen == true ) {   
		     
				ConsoleHandler = new ConsoleHandler();
			
				ConsoleHandler.setFormatter( ExXmlFormatter );

			}   
			
			CExtendedLogFilter LogFilter = new CExtendedLogFilter( strLogFilters, bExactMatch );

			this.setFilter( LogFilter );
			
			if ( bLogToScreen == true )    
			   this.addHandler( ConsoleHandler ); 
		
			this.addHandler( FileHandler );
			
			if ( SocketHandler != null ) {
			
				SocketHandler.setFormatter( ExXmlFormatter );
				
				this.addHandler( SocketHandler );
				
			}	
			
			bSetupSet = true;
			
		}
		catch ( Exception Ex ) {
			
			Ex.printStackTrace();
			
		};
		
    }

    public boolean flushAndClose() {
    	
    	boolean bResult = false;
    	
    	try {
    	
    		if ( FileHandler != null ) {
    			
    			FileHandler.flush();
    			FileHandler.close();
    			
    			FileHandler = null;
    			
    		}

    		if ( ConsoleHandler != null ) {
    			
    			ConsoleHandler.flush();
    			ConsoleHandler.close();
    			ConsoleHandler = null;
    			
    		}
    		
    		if ( SocketHandler != null ) {
    			
    			SocketHandler.flush();
    			SocketHandler.close();
    			
    			SocketHandler = null;
    			
    		}
    		
    		if ( HTTPHandler != null ) {
    			
    			HTTPHandler.flush();
    			HTTPHandler = null;
    			 
    		}
    		
			ExXmlFormatter = null;
		
			bSetupSet = false;
			
			bResult = true;
			
    	}
    	catch ( Exception Ex ) {
    		
    		
    	}
    	
    	return bResult;
    	
    } 
    
    public boolean flush() {
    	
    	boolean bResult = false;
    	
    	try {
    	
    		if ( FileHandler != null ) {
    			
    			FileHandler.flush();
    			
    		}

    		if ( ConsoleHandler != null ) {
    			
    			ConsoleHandler.flush();
    			
    		}
    		
    		if ( SocketHandler != null ) {
    			
    			SocketHandler.flush();
    			
    		}
    		
    		if ( HTTPHandler != null ) {
    			
    			HTTPHandler.flush();
    			 
    		}
    		
			bResult = true;
			
    	}
    	catch ( Exception Ex ) {
    		
    		
    	}
    	
    	return bResult;
    	
    }
    
    public void setInstanceID( String strInstanceID ) {
		
		this.strInstanceID = strInstanceID;
		
	}
    
	public String getInstanceID() {
		
	   return strInstanceID;	
		
	}

	public void setInternalSequence( int intInternalSequence ) {
		
		this.intInternalSequence = intInternalSequence;
		
	}
    
	public int getInternalSequence() {
		
	   return intInternalSequence;	
		
	}
	
	public boolean getSetupSet() {
		
		return bSetupSet;
		
	}
	
	public void setCallStackLevel( int intCallStackLevel ) {

		this.intCallStackLevel = intCallStackLevel;
		
	}
	
	public int getCallStackLevel() {
		
		return intCallStackLevel;
		
	}
	
	protected void internalLog( String strLogType, String strCode, String strMessage, String strData, Throwable Thrown, Level ... level ) {
		  
		CExtendedLogRecord LogRecord = null;
		   
		if ( level.length == 0 )    
		   LogRecord = new CExtendedLogRecord( Level.INFO, strMessage ); 
		else
		   LogRecord = new CExtendedLogRecord( level[ 0 ], strMessage ); 

		if ( this.isLoggable( LogRecord.getLevel() ) ) {
			
		    intInternalSequence++;
	      
	        LogRecord.setLogType( strLogType );
	        
	        LogRecord.setInstanceID( strInstanceID );
	        
		    LogRecord.setThrown( Thrown );

		    if ( strData != null )
		    	LogRecord.setData( strData );
		    
		    LogRecord.setThreadID( (int) Thread.currentThread().getId() );
	      
	        LogRecord.setThreadName( Thread.currentThread().getName() );
	      
	        String strFullSourceClassName = Thread.currentThread().getStackTrace()[ intCallStackLevel ].getClassName();            

	        LogRecord.setSourcePackageName( strFullSourceClassName.substring( 0, strFullSourceClassName.lastIndexOf(".") ) );

	        LogRecord.setSourceClassName( strFullSourceClassName.substring( strFullSourceClassName.lastIndexOf(".") + 1 ) );
	      
	        LogRecord.setSourceMethodName( Thread.currentThread().getStackTrace()[ intCallStackLevel ].getMethodName() );
	      
	        LogRecord.setLineNumber( Thread.currentThread().getStackTrace()[ intCallStackLevel ].getLineNumber() );
	      
	        LogRecord.setCode( strCode );
	      
	        LogRecord.setSequenceNumber( intInternalSequence );
	      
	        LogRecord.setLoggerName( this.getName() );
	        
	        this.log( LogRecord );
		
		}
	
	}	
	
	public void logEntry( String strLogType, String strCode, String strMessage, Level ... level ) {

		internalLog( strLogType, strCode, strMessage, null, null, level );		

	}

	public void logEntry( String strLogType, String strCode, String strMessage, Throwable Thrown, Level ... level ) {
		
		internalLog( strLogType, strCode, strMessage, null, Thrown, level );		
		
	}

	public void logMessage( String strCode, String strMessage, Level ... level ) {
		
		internalLog( "Message", strCode, strMessage, null, null, level );		
		
	}

	public void logMessage( String strCode, String strMessage, Throwable Thrown, Level ... level ) {
		
		internalLog( "Message", strCode, strMessage, null, Thrown, level );		
		
	}

	public void logInfo( String strCode, String strMessage, Level ... level ) {
		
		internalLog( "Info", strCode, strMessage, null, null, level );		
		
	}
	
	public void logInfo( String strCode, String strMessage, Throwable Thrown, Level ... level ) {
		
		internalLog( "Info", strCode, strMessage, null, Thrown, level );		
		
	}

	public void logDebug( String strCode, String strMessage, Level ... level ) {
		
		internalLog( "Debug", strCode, strMessage, null, null, level );		
		
	}

	public void logDebug( String strCode, String strMessage, Throwable Thrown, Level ... level ) {
		
		internalLog( "Debug", strCode, strMessage, null, Thrown, level );		
		
	}

	public void logError( String strCode, String strMessage, Level ... level ) {
		
		internalLog( "Error", strCode, strMessage, null, null, level );		
		
	}
	
	public void logError( String strCode, String strMessage, Throwable Thrown, Level ... level ) {
		
		internalLog( "Error", strCode, strMessage, null, Thrown, level );		
		
	}

	public void logWarning( String strCode, String strMessage, Level ... level ) {
		
		internalLog( "Warning", strCode, strMessage, null, null, level );		
		
	}

	public void logWarning( String strCode, String strMessage, Throwable Thrown, Level ... level ) {
		
		internalLog( "Warning", strCode, strMessage, null, Thrown, level );		
		
	}

	public void logData( String strCode, String strMessage, String strData, Level ... level ) {
		
		internalLog( "Info", strCode, strMessage, strData, null, level );		
		
	}
	
	public void logData( String strCode, String strMessage, String strData, Throwable Thrown, Level ... level ) {
		
		internalLog( "Info", strCode, strMessage, strData, Thrown, level );		
		
	}
	
	public void logMethodEntry( String strCode, String strMessage, Level ... level ) {
		
		internalLog( "MethodEntry", strCode, strMessage, null, null, level );		
		
	}
	
	public void logMethodEntry( String strCode, String strMessage, Throwable Thrown, Level ... level ) {
		
		internalLog( "MethodEntry", strCode, strMessage, null, Thrown, level );		
		
	}

	public void logMethodLeave( String strCode, String strMessage, Level ... level ) {
		
		internalLog( "MethodLeave", strCode, strMessage, null, null, level );		
		
	}
	
	public void logMethodLeave( String strCode, String strMessage, Throwable Thrown, Level ... level ) {
		
		internalLog( "MethodLeave", strCode, strMessage, null, Thrown, level );		
		
	}

	public void logException( String strCode, String strMessage, Exception ExceptionInfo, Level ... level ) {
	
		CExtendedLogRecord LogRecord = null;
		   
		if ( level.length == 0 )    
		   LogRecord = new CExtendedLogRecord( Level.SEVERE, strMessage ); 
		else
		   LogRecord = new CExtendedLogRecord( level[ 0 ], strMessage ); 

		if ( this.isLoggable( LogRecord.getLevel() ) ) {
			
		    intInternalSequence++;
	      
		    LogRecord.setThrown( ExceptionInfo );
		    
	        LogRecord.setLogType( "Java-Exception" );
	        
	        LogRecord.setInstanceID( strInstanceID );
	        
	        LogRecord.setThreadID( (int) Thread.currentThread().getId() );
	      
	        LogRecord.setThreadName( Thread.currentThread().getName() );
	      
	        String strFullSourceClassName = Thread.currentThread().getStackTrace()[ intCallStackLevel - 1 ].getClassName(); 

	        LogRecord.setSourcePackageName( strFullSourceClassName.substring( 0, strFullSourceClassName.lastIndexOf(".") ) );

	        LogRecord.setSourceClassName( strFullSourceClassName.substring( strFullSourceClassName.lastIndexOf(".") + 1 ) );
	        
        	LogRecord.setSourceMethodName( Thread.currentThread().getStackTrace()[ intCallStackLevel - 1 ].getMethodName() );
	        
        	LogRecord.setLineNumber( Thread.currentThread().getStackTrace()[ intCallStackLevel - 1 ].getLineNumber() );

        	/*if ( ExceptionInfo != null ) {
	        
	        	strFullSourceClassName = ExceptionInfo.getStackTrace()[ 0 ].getClassName();
	        
	        	LogRecord.setSourceMethodName( ExceptionInfo.getStackTrace()[ 0 ].getMethodName() );
		        
	        	LogRecord.setLineNumber( ExceptionInfo.getStackTrace()[ 0 ].getLineNumber() );
	        	
	        }	
	        else {*/
	        
	      
	        //};
	        
	        LogRecord.setCode( strCode );
	      
	        LogRecord.setSequenceNumber( intInternalSequence );
	      
	        LogRecord.setLoggerName( this.getName() );
	        
	        this.log( LogRecord );
		
		}
		
	}
	
	public void logError( String strCode, String strMessage, Error ErrorInfo, Level ... level ) {
		
		CExtendedLogRecord LogRecord = null;
		   
		if ( level.length == 0 )    
		   LogRecord = new CExtendedLogRecord( Level.SEVERE, strMessage ); 
		else
		   LogRecord = new CExtendedLogRecord( level[ 0 ], strMessage ); 

		if ( this.isLoggable( LogRecord.getLevel() ) ) {
			
		    intInternalSequence++;
	      
		    LogRecord.setThrown( ErrorInfo );
		    
	        LogRecord.setLogType( "Java-Error" );
	        
	        LogRecord.setInstanceID( strInstanceID );
	        
	        LogRecord.setThreadID( (int) Thread.currentThread().getId() );
	      
	        LogRecord.setThreadName( Thread.currentThread().getName() );
	      
	        String strFullSourceClassName = Thread.currentThread().getStackTrace()[ intCallStackLevel - 1 ].getClassName(); 

	        LogRecord.setSourcePackageName( strFullSourceClassName.substring( 0, strFullSourceClassName.lastIndexOf(".") ) );

	        LogRecord.setSourceClassName( strFullSourceClassName.substring( strFullSourceClassName.lastIndexOf(".") + 1 ) );
	        
        	LogRecord.setSourceMethodName( Thread.currentThread().getStackTrace()[ intCallStackLevel - 1 ].getMethodName() );
	        
        	LogRecord.setLineNumber( Thread.currentThread().getStackTrace()[ intCallStackLevel - 1 ].getLineNumber() );

        	/*if ( ExceptionInfo != null ) {
	        
	        	strFullSourceClassName = ExceptionInfo.getStackTrace()[ 0 ].getClassName();
	        
	        	LogRecord.setSourceMethodName( ExceptionInfo.getStackTrace()[ 0 ].getMethodName() );
		        
	        	LogRecord.setLineNumber( ExceptionInfo.getStackTrace()[ 0 ].getLineNumber() );
	        	
	        }	
	        else {*/
	        
	      
	        //};
	        
	        LogRecord.setCode( strCode );
	      
	        LogRecord.setSequenceNumber( intInternalSequence );
	      
	        LogRecord.setLoggerName( this.getName() );
	        
	        this.log( LogRecord );
		
		}
		
	}
	
	public void setLogIP( String strLogIP ) {
		
		this.strLogIP = strLogIP;
		
		this.bSocketHandlerActive = false;
		
	}
	
	public String getLogIP() {
		
		return strLogIP;
		
	}
	
	public void setLogPort( int intLogPort ) {
		
		this.intLogPort = intLogPort;
		
		this.bSocketHandlerActive = false;
		
	}
	
	public int getLogPort() {
		
		return intLogPort;
		
	}
	
	public void setHTTPLogURL( String strHTTPLogURL ) {
		
		this.strHTTPLogURL = strHTTPLogURL;
		
		this.bHTTPHandlerActive = false;
		
	}
	
	public String getHTTPLogURL() {
		
		return strHTTPLogURL;
		
	}

	public void setHTTPLogUser( String strHTTPLogUser ) {
		
		this.strHTTPLogUser = strHTTPLogUser;
		
		this.bHTTPHandlerActive = false;
		
	}
	
	public String getHTTPLogUser() {
		
		return strHTTPLogURL;
		
	}

	public void setHTTPLogPassword( String strHTTPLogPassword ) {
		
		this.strHTTPLogPassword = strHTTPLogPassword;
		
		this.bHTTPHandlerActive = false;
		
	}
	
	public String getHTTPLogPassword() {
		
		return strHTTPLogURL;
		
	}
	
	public void setProxyIP( String strProxyIP ) {
		
		this.strProxyIP = strProxyIP;
		
		this.bHTTPHandlerActive = false;
		
	}

	public String getProxyIP() {
		
		return strProxyIP;
		
	}
	
	public void setProxyPort( int intProxyPort ) {
		
		this.intProxyPort = intProxyPort;
		
		this.bHTTPHandlerActive = false;
		
	}

	public int getProxyPort() {
		
		return intProxyPort;
		
	}

	public void setProxyUser( String strProxyUser ) {
		
		this.strProxyUser = strProxyUser;
		
		this.bHTTPHandlerActive = false;
		
	}

	public String getProxyUser() {
		
		return strProxyUser;
		
	}

	public void setProxyPassword( String strProxyPassword ) {
		
		this.strProxyPassword = strProxyPassword;
		
		this.bHTTPHandlerActive = false;
		
	}

	public String getProxyPassword() {
		
		return strProxyPassword;
		
	}

	public boolean activateSocketHandler( boolean bAddSocketHandlerToList ) {
		
		boolean bResult = false;
		
		if ( this.bSocketHandlerActive == false && this.strLogIP.trim().isEmpty() == false ) {
			
			if ( this.SocketHandler != null && bAddSocketHandlerToList == false )
				this.removeHandler( this.SocketHandler );
			
			try {
				
    			SocketHandler = new SocketHandler( strLogIP, intLogPort );

				SocketHandler.setFormatter( ExXmlFormatter );
    			
    			this.addHandler( SocketHandler );
				
    			bResult = true;
    			
			}
			catch ( Exception Ex ) {
				
				if ( bSetupSet == false )
					Ex.printStackTrace();
				else
					this.logException( "-1020", Ex.getMessage(), Ex );
				
			}
			
			this.bSocketHandlerActive = true;
			
		}
		
		return bResult;
		
	}

	public boolean activateHTTPHandler( boolean bAddHTTPHandlerToList ) {
		
		boolean bResult = false;
		
		if ( this.bHTTPHandlerActive == false && this.strHTTPLogURL.trim().isEmpty() == false ) {
			
			if ( this.HTTPHandler != null && bAddHTTPHandlerToList == false )
				this.removeHandler( this.HTTPHandler );
			
			try {
				
				if ( HTTPHandler == null ) 
					HTTPHandler = new CExtendedHTTPHandler( this.getName() ); 

				if ( HTTPHandler.configureHandler( strHTTPLogURL, strHTTPLogUser, strHTTPLogPassword, strProxyIP, intProxyPort, strProxyUser, strProxyPassword ) ) {

					this.addHandler( HTTPHandler );

					bResult = true;

					this.bHTTPHandlerActive = true;
					
				}
				else {

					HTTPHandler = null;

				}
				
			}
			catch ( Exception Ex ) {
				
				if ( bSetupSet == false )
					Ex.printStackTrace();
				else
					this.logException( "-1020", Ex.getMessage(), Ex );
				
			}
			
		}
		
		return bResult;
		
	}
	
}
