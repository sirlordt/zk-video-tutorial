package commonlibs.extendedlogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Properties;

import commonlibs.commonclasses.CLanguage;
import commonlibs.utils.Utilities;

public class CExtendedConfigLogger implements Serializable {

	private static final long serialVersionUID = -2290355837858331424L;

	String strInstanceID = "";
	boolean bLogToScreen = false;
	String strClassNameMethodName = "";
	boolean bExactMatch = false;
	String strLevel = "";
	boolean bLogMissingTranslations = false;
	String strLogIP = "";
	int intLogPort = 0;
	String strHTTPLogURL = "";
	String strHTTPLogUser = "";
	String strHTTPLogPassword = "";
    String strProxyIP = "";
    int intProxyPort = 0;
    String strProxyUser = "";
    String strProxyPassword = "";
    
	public boolean loadConfig( String strConfigPath, CExtendedLogger localLogger, CLanguage localLanguage ) {
		
		boolean bResult = false;
		
		try {

			File configFilePath = new File( strConfigPath );
			
			if ( configFilePath.exists() ) {
				
				Properties configsData = new Properties();
				
				FileInputStream inputStream = new FileInputStream( configFilePath );
				
				configsData.loadFromXML( inputStream );
				
				if ( localLogger != null )
				   localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Readed config values from file [%s]" , strConfigPath ) );

				this.strInstanceID = (String) configsData.get( "instance_id" );

				if ( localLogger != null )
  				   localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "instance_id", this.strInstanceID ) );
				
				this.strClassNameMethodName = (String) configsData.get( "class_name_method_name" );
				
				if ( localLogger != null )
  				   localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "class_name_method_name", this.strClassNameMethodName ) );

				String strValue = (String) configsData.get( "exact_match" );
				
				if ( strValue == null )
					strValue = "false";
				
				this.bExactMatch = strValue.toLowerCase().equals( "true" );

				if ( localLogger != null )
  				   localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "exact_match", strValue ) );
				
				this.strLevel = (String) configsData.get( "level" );
				
				if ( localLogger != null )
	  			   localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "level", this.strLevel ) );
				
				strValue = (String) configsData.get( "log_missing_translations" );
				
				if ( strValue == null )
					strValue = "false";
				
				this.bLogMissingTranslations = strValue.toLowerCase().equals( "true" );

				if ( localLogger != null )
  				   localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "log_missing_translations", strValue ) );
				
				this.strLogIP = (String) configsData.get( "log_ip" );

				if ( localLogger != null )
	  			   localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "log_ip", this.strLogIP ) );
				
				int intValue = 0;
				strValue = (String) configsData.get( "log_port" );
				
				if ( strValue != null )
					intValue = Utilities.strToInteger( strValue );
				
				this.intLogPort = intValue;

				if ( localLogger != null )
	  			   localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "log_port", new Integer( this.intLogPort ).toString() ) );
				
				this.strHTTPLogURL = (String) configsData.get( "http_log_url" );

				if ( localLogger != null )
		  		   localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "http_log_url", this.strHTTPLogURL ) );

				this.strHTTPLogUser = (String) configsData.get( "http_log_user" );

				if ( localLogger != null )
		  		   localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "http_log_user", this.strHTTPLogUser ) );
				
				this.strHTTPLogPassword = (String) configsData.get( "http_log_password" );

				if ( localLogger != null )
			  	   localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "http_log_password", this.strHTTPLogPassword ) );
				
				this.strProxyIP = (String) configsData.get( "proxy_ip" );

				if ( localLogger != null )
			  	   localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "proxy_ip", this.strProxyIP ) );
					
				intValue = 0;
				strValue = (String) configsData.get( "proxy_port" );
				
				if ( strValue != null )
					intValue = Utilities.strToInteger( strValue );

				this.intProxyPort = intValue;

				if ( localLogger != null )
			  	   localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "proxy_port", new Integer( this.intLogPort ).toString() ) );
				
				this.strProxyUser = (String) configsData.get( "proxy_user" );

				if ( localLogger != null )
				   localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "proxy_user", this.strProxyUser ) );

				this.strProxyPassword = (String) configsData.get( "proxy_password" );
				
				if ( localLogger != null ) {
				
					localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "proxy_password", this.strProxyPassword ) );
					
					localLogger.flush();
					
				}   
				
				bResult = true;
				
			}
			else if ( localLogger != null ) {
				
				localLogger.logError( "-1001" , CLanguage.translateIf( localLanguage, "Config file in path [%s] not found" ,  strConfigPath ) );
				localLogger.flush();
				
			}
		
		}
		catch ( Exception Ex ) {

			if ( localLogger != null ) {
				
				localLogger.logException( "-1021" , Ex.getMessage(), Ex );
				
			}
			
		}
	
		return bResult;
		
	}
    
	public String getInstanceID() {
		
		return strInstanceID;
		
	}
	
	public void setStrInstanceID( String strInstanceID ) {
		
		this.strInstanceID = strInstanceID;
		
	}
	
	public boolean getLogToScreen() {
		
		return bLogToScreen;
		
	}
	
	public void setLogToScreen( boolean bLogToScreen ) {
		
		this.bLogToScreen = bLogToScreen;
		
	}
	
	public String getClassNameMethodName() {
		
		return strClassNameMethodName;
		
	}
	
	public void setClassNameMethodName( String strClassNameMethodName ) {
		
		this.strClassNameMethodName = strClassNameMethodName;
		
	}
	
	public boolean getExactMatch() {
		
		return bExactMatch;
		
	}
	
	public void setExactMatch( boolean bExactMatch ) {
		
		this.bExactMatch = bExactMatch;
		
	}
	
	public String getLevel() {

		return strLevel;
		
	}
	
	public void setLevel( String strLevel ) {
		
		this.strLevel = strLevel;
		
	}
	
	public boolean getLogMissingTranslations() {
		
		return bLogMissingTranslations;
		
	}
	
	public void setMissingTranslations( boolean bLogMissingTranslations ) {
		
		this.bLogMissingTranslations = bLogMissingTranslations;
		
	}
	
	public String getLogIP() {
	
		return strLogIP;
		
	}
	
	public void setLogIP( String strLogIP ) {
		
		this.strLogIP = strLogIP;
		
	}
	
	public int getLogPort() {
		
		return intLogPort;
		
	}
	
	public void setIntLogPort( int intLogPort ) {
		
		this.intLogPort = intLogPort;
		
	}
	
	public String getHTTPLogURL() {
		
		return strHTTPLogURL;
		
	}
	
	public void setHTTPLogURL( String strHTTPLogURL ) {
		
		this.strHTTPLogURL = strHTTPLogURL;
		
	}
	
	public String getHTTPLogUser() {
		
		return strHTTPLogUser;
		
	}
	
	public void setHTTPLogUser( String strHTTPLogUser ) {
		
		this.strHTTPLogUser = strHTTPLogUser;
		
	}
	
	public String getHTTPLogPassword() {
		
		return strHTTPLogPassword;
		
	}
	
	public void setHTTPLogPassword( String strHTTPLogPassword ) {
		
		this.strHTTPLogPassword = strHTTPLogPassword;
		
	}
	
	public String getProxyIP() {

		return strProxyIP;
		
	}
	
	public void setProxyIP( String strProxyIP ) {
		
		this.strProxyIP = strProxyIP;
		
	}
	
	public int getProxyPort() {
		
		return intProxyPort;
		
	}
	
	public void setProxyPort( int intProxyPort ) {
		
		this.intProxyPort = intProxyPort;
		
	}
	
	public String getProxyUser() {
		
		return strProxyUser;
		
	}
	
	public void setProxyUser( String strProxyUser ) {
		
		this.strProxyUser = strProxyUser;
		
	}
	
	public String getProxyPassword() {
		
		return strProxyPassword;
		
	}

	public void setProxyPassword( String strProxyPassword ) {
		
		this.strProxyPassword = strProxyPassword;
		
	}
	
}
