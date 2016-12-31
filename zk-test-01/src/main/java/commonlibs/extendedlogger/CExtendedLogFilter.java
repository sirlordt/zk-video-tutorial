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

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class CExtendedLogFilter implements Filter {

    protected ArrayList<String> LogFilters; 
    protected ArrayList<String> LogFiltersLowerCase; 
    protected boolean bExactMatch;
	
	public CExtendedLogFilter( String strLogFilters, boolean bExactMatch ) {
		
		LogFilters = new ArrayList<String>();

		LogFiltersLowerCase = new ArrayList<String>();
		
		this.setExactMatch( bExactMatch );

		this.setLogFilters( strLogFilters );
				
	}
    
	public void setLogFilters( String strLogFilters ) {
		
		LogFilters.clear(); 
		LogFiltersLowerCase.clear();
		
		String[] strTmpArray = strLogFilters.split( ";" ); 
		
		Collections.addAll( LogFilters, strTmpArray );
		
		if ( LogFilters.contains( "*.*" ) == true ) {
			
			LogFilters.clear();
		
		}
		else if ( bExactMatch == false ) {
			
			for ( int i=0; i < LogFilters.size(); i++ ) {
			
				LogFiltersLowerCase.add( LogFilters.get( i ).toLowerCase() );
			
			}			
			
		}
		
	
	}
	
	public String getLogFilters() {
		
		String strResult = "";
		
		if ( LogFilters != null ) {
		
			for ( int i=0; i < LogFilters.size(); i++ ) {
				
				if ( strResult == "" )
					strResult = LogFilters.get( i );
				else
					strResult = ";" + LogFilters.get( i );
				
			}			
			
		}
		
		return strResult;
		
	}
	
    public void setExactMatch( boolean bExactMatch ) {
    	
		this.bExactMatch = bExactMatch;
    	
    	if ( bExactMatch == false ) {
		
		   for ( int i=0; i < LogFilters.size(); i++ ) {
			
			   LogFiltersLowerCase.add( LogFilters.get( i ).toLowerCase() );
			
		   }			
    	
    	}
    	
    }
	
	public boolean getExactMatch() {

		return bExactMatch;

	}	
	
	public boolean isLoggable( LogRecord record ) {

    	boolean bResult = false;
		
		Logger OwnerLogger = Logger.getLogger( record.getLoggerName() );
		
    	if ( OwnerLogger.getLevel() == Level.ALL ||  record.getLevel().intValue() >= OwnerLogger.getLevel().intValue() ) {
    		
    	   if ( LogFilters.isEmpty() == true ) { //no defined filters
    		  
    		  bResult = true; 
    		   
    	   }	
    	   else {
    		   
    		   if ( bExactMatch == false ) {
	    		   
    			   if ( LogFiltersLowerCase.contains( record.getSourceClassName().toLowerCase() + ".*" ) == true ) {
	    			 
	    			   bResult = true; 

	    		   }  
	    		   else if ( LogFiltersLowerCase.contains( "*." + record.getSourceMethodName().toLowerCase() ) == true ) {

	    			   bResult = true; 

	    		   }
	    		  
	    		   else if ( LogFiltersLowerCase.contains( record.getSourceClassName().toLowerCase() + "." + record.getSourceMethodName().toLowerCase() ) == true ) {
	    			
	    			   bResult = true; 
	    		  
	    		   }
    		   
    		   }
    		   else {
    			   
    			   if ( LogFilters.contains( record.getSourceClassName() + ".*" ) == true ) {
  	    			 
	    			   bResult = true; 

	    		   }  
	    		   else if ( LogFilters.contains( "*." + record.getSourceMethodName() ) == true ) {

	    			   bResult = true; 

	    		   }
	    		  
	    		   else if ( LogFilters.contains( record.getSourceClassName() + "." + record.getSourceMethodName() ) == true ) {
	    			
	    			   bResult = true; 
	    		  
	    		   }
    			   
    		   }
    		   
    	   }
    		
    	}

    	return bResult;
    	
	}

	
	
}
