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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.SocketHandler;
//import java.util.logging.Level;
import java.util.logging.LogRecord;

//This custom formatter formats parts of a log record to a single line
class CExtendedLogXMLFormatter extends Formatter {

	public String strLogFilePath = "";
	public String strLogFileName = "";
	
	// This method is called for every log records
	@Override
	public String format( LogRecord rec ) {
    
        CExtendedLogRecord ExLogRecord = (CExtendedLogRecord) rec;
		
		DateFormat DFormat = new SimpleDateFormat( "dd/MM/yyyy" );
        
        DateFormat TFormat = new SimpleDateFormat( "HH:mm:ss" );
       
        //Date CurrentDateTime = new Date();
        
        StringBuffer strBuffer = new StringBuffer();
        
        if ( ExLogRecord.getThrown() != null || ExLogRecord.getData() != null ) {
         
        	strBuffer.append( "<Log InstanceID=\"" + ExLogRecord.getInstanceID() + "\" LogType=\"" + ExLogRecord.getLogType() + "\" Level=\"" + ExLogRecord.getLevel().toString() + "\" Date=\"" + DFormat.format( ExLogRecord.getLogDateTime() ) + "\" Time=\"" + TFormat.format( ExLogRecord.getLogDateTime() ) + "\" LoggerName=\"" + ExLogRecord.getLoggerName() + "\" Sequence=\"" + ExLogRecord.getSequenceNumber() + "\" ThreadID=\"" + ExLogRecord.getThreadID() + "\" ThreadName=\"" + ExLogRecord.getThreadName() + "\" PackageName=\"" + ExLogRecord.getSourcePackageName() + "\" ClassName=\"" + ExLogRecord.getSourceClassName() + "\" MethodName=\"" + ExLogRecord.getSourceMethodName() + "\" Line=\"" + ExLogRecord.getLineNunber() + "\" Code=\"" + ExLogRecord.getCode() + "\" Message=\"" + ExLogRecord.getMessage() + "\">\n" );

            if ( ExLogRecord.getThrown() != null ) {
            	
                int intStackTraceCount = ExLogRecord.getThrown().getStackTrace().length;
            	
                strBuffer.append( "  <StackTrace>\n" );

            	for ( int intIndexStackTrace = 0; intIndexStackTrace < intStackTraceCount; intIndexStackTrace++ ) {
            		
        	        String strFullSourceClassName = ExLogRecord.getThrown().getStackTrace()[ intIndexStackTrace ].getClassName(); 

        	        String strPackageName = strFullSourceClassName.substring( 0, strFullSourceClassName.lastIndexOf(".") );

        	        String strClassName = strFullSourceClassName.substring( strFullSourceClassName.lastIndexOf(".") + 1 );

        	        String strMethodName = ExLogRecord.getThrown().getStackTrace()[ intIndexStackTrace ].getMethodName(); 
        	        
        	        int intLineNumber = ExLogRecord.getThrown().getStackTrace()[ intIndexStackTrace ].getLineNumber();
        	        
        	        strBuffer.append( "    <StackTraceCall PackageName=\"" + strPackageName + "\" ClassName=\"" + strClassName + "\" MethodName=\"" + strMethodName + "\" Line=\"" + Integer.toString( intLineNumber ) + "\"/>\n" );
            		
            	}
            	
                strBuffer.append( "  </StackTrace>\n" );
                
            }
            
            if ( ExLogRecord.getData() != null ) {
            	
                strBuffer.append( "  <Data><![CDATA[\n" );

                strBuffer.append( ExLogRecord.getData() );
                
                strBuffer.append( "  ]]></Data>\n" );
            	
            }
            
            strBuffer.append( "</Log>\n" );
        	
        }    
        else {
        	
            strBuffer.append( "<Log InstanceID=\"" + ExLogRecord.getInstanceID() + "\" LogType=\"" + ExLogRecord.getLogType() + "\" Level=\"" + ExLogRecord.getLevel().toString() + "\" Date=\"" + DFormat.format( ExLogRecord.getLogDateTime() ) + "\" Time=\"" + TFormat.format( ExLogRecord.getLogDateTime() ) + "\" LoggerName=\"" + ExLogRecord.getLoggerName() + "\" Sequence=\"" + ExLogRecord.getSequenceNumber() + "\" ThreadID=\"" + ExLogRecord.getThreadID() + "\" ThreadName=\"" + ExLogRecord.getThreadName() + "\" PackageName=\"" + ExLogRecord.getSourcePackageName() + "\" ClassName=\"" + ExLogRecord.getSourceClassName() + "\" MethodName=\"" + ExLogRecord.getSourceMethodName() + "\" Line=\"" + ExLogRecord.getLineNunber() + "\" Code=\"" + ExLogRecord.getCode() + "\" Message=\"" + ExLogRecord.getMessage() + "\"/>\n" );
        	
        }
        
        
        return strBuffer.toString();
  
	}

	// This method is called just after the handler using this
	// formatter is created
	@Override
	public String getHead( Handler h ) {
	   
		DateFormat DFormat = new SimpleDateFormat( "dd/MM/yyyy" );
        
        DateFormat TFormat = new SimpleDateFormat( "HH:mm:ss" );
       
        Date CurrentDateTime = new Date();

        StringBuffer strBuffer = new StringBuffer();
        
        if ( h instanceof SocketHandler ) {
        	
        	strBuffer.append( "Type=\"init\";FilePath=\"" + strLogFilePath + "\";FileName=\"" + strLogFileName + "\"\n" );
        	
        }
        
        strBuffer.append( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" );
        strBuffer.append( "<logs>\n" );
        strBuffer.append( "<logMark Type=\"Init\" Date=\"" + DFormat.format( CurrentDateTime ) + "\" Time=\"" + TFormat.format( CurrentDateTime ) + "\"/>\n" );
        
        return strBuffer.toString();
	
	}

	// This method is called just after the handler using this
	// formatter is closed
	@Override
	public String getTail( Handler h ) {

		DateFormat DFormat = new SimpleDateFormat( "dd/MM/yyyy" );
        
        DateFormat TFormat = new SimpleDateFormat( "HH:mm:ss" );
       
        Date CurrentDateTime = new Date();

        StringBuffer strBuffer = new StringBuffer();
        
        strBuffer.append( "<logMark Type=\"End\" Date=\"" + DFormat.format( CurrentDateTime ) + "\" Time=\"" + TFormat.format( CurrentDateTime ) + "\"/>\n" );

        strBuffer.append( "</logs>\n" );
       
        if ( h instanceof SocketHandler ) {
        	
        	strBuffer.append( "Type=\"end\";FilePath=\"" + strLogFilePath + "\";FileName=\"" + strLogFileName + "\"\n" );
        	
        }
        
        return strBuffer.toString();
  
	}
	
} 
