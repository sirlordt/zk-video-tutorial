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
package commonlibs.commonclasses;

/*

<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<properties>
<comment>example pairs</comment>
<entry key="MOTD">Hello, world!</entry>
<entry key="FAVOURITE_FRUIT">Mango</entry>
<entry key="THE_COW_SAYS">The cow says [%s]</entry>
</properties>Languages ​​Translator

*/

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

import commonlibs.extendedlogger.CExtendedLogger;


public class CLanguage {

	public static CExtendedLogger LoggerMissingTranslations = null;
	public static ArrayList<CLanguage> LanguagesList = null;
	public static ArrayList<CLanguage> CommonPhrasesList = null;
	public static CLanguage LastLangFinded;
	
	static {
		
		LanguagesList = new ArrayList<CLanguage>();
		CommonPhrasesList = new ArrayList<CLanguage>();
		
	}
	
    protected CExtendedLogger Logger;
    protected String strLanguageFileName;
	protected Properties MessageMap;
	protected boolean bUseCommonPhrases;
	
	public CLanguage( CExtendedLogger Logger, String strLanguageFileName ) {

		this.Logger = Logger;
		this.strLanguageFileName = "";
		this.MessageMap = null;
        this.bUseCommonPhrases = true;
		
		this.setLanguageFileName( strLanguageFileName );
	
	}
	
	public boolean setLanguageFileName( String strLanguageFileName ) {
		
		boolean bResult = false;
		
		if ( Logger == null )
		    Logger = CExtendedLogger.getLogger( ConstantsCommonClasses._Logger_Name );

		if ( this.strLanguageFileName.equals( strLanguageFileName ) == false ) { //Check first language class name diferent

			if ( this.strLanguageFileName.equals( "" ) == true ) { //Check for no language name set yet, possible class constructor call
				
				this.strLanguageFileName = strLanguageFileName; //Force set the language name
				
			}

			try {
			       
				File XMLLanguageFile = null;
	
				if ( new File( strLanguageFileName ).isAbsolute() == false ) {
					
					XMLLanguageFile = new File( ConstantsCommonClasses._Langs_Dir + strLanguageFileName + ".xml" );
					
				}
				else {
					
					XMLLanguageFile = new File( strLanguageFileName + ".xml" );
					
				}
				
				if ( XMLLanguageFile.exists() == true ) { //Check the file exists
	
					if ( XMLLanguageFile.canRead() == true ) { //Check the file can read, security file system issue of modern system operatings
						
						if ( XMLLanguageFile.isFile() == true ) { //Check the file is a real file and not directory
	
							if ( MessageMap == null ) {   
							
								MessageMap = new Properties();
							
							}   
							   
							MessageMap.clear();
							MessageMap.loadFromXML( XMLLanguageFile.toURI().toURL().openStream() );
					
							this.strLanguageFileName = strLanguageFileName; //Set the new language name
							
							bResult = true;
							
						}
						else {
							
							//Log error message path is not file
							if ( Logger != null )
							    Logger.logError( "-1004", String.format( "The language path [%s] not is file", XMLLanguageFile.getAbsoluteFile() ) );        

						}
					
					}
					else {
						
						//Log error message file cannot read
 						if ( Logger != null )
					 	    Logger.logError( "-1003", String.format( "The language file in the path [%s] cannot read, please check the owner and permissions", XMLLanguageFile.getAbsoluteFile() ) );        
						
					}
					
				}
				else {
					
					//Log error message file no exists
					if ( Logger != null )
					    Logger.logError( "-1002", String.format( "The language file in the path [%s] not exists", XMLLanguageFile.getAbsoluteFile() ) );        
					
				}
				
			}
			catch ( Exception Ex ) {
		    
				//Log the error
				if ( Logger != null )
    			    Logger.logException( "-1001", Ex.getMessage(), Ex );        
				
			}
			
			if ( this.strLanguageFileName.equals( strLanguageFileName ) == false ) {
				
				if ( Logger != null )
				    Logger.logError( "-1005", String.format( "Cannot assign the new languaje name: [%s] falling back to old lenguaje name: [%s]", strLanguageFileName, this.strLanguageFileName ) );        

			}
		
		}
		else {
			
			bResult = true;
			
		};
		
		return bResult;
		
	} 
	
	public String getLanguageFileName() {
		
		return strLanguageFileName;
		
	}
	
	public void setLogger( CExtendedLogger Logger ) {
		
		this.Logger = Logger;
		
	}
	
	CExtendedLogger getLogger() {
		
		return Logger;
		
	}
	
	public void setUseCommonPhrases( boolean bUseCommonPhrases ) {
		
		this.bUseCommonPhrases = bUseCommonPhrases;
		
	}

	public boolean getUseCommonPhrases() {
		
		return bUseCommonPhrases;
		
	}
	
	public static boolean addLanguageToCommonPhrases( CLanguage LangToAdd ) {
		
		boolean bResult = false;
		
		if ( CommonPhrasesList.contains( LangToAdd ) == false ) {
			
			CommonPhrasesList.add( LangToAdd );
			
			bResult = true;
			
		}
		
		return bResult;
		
	}
	
	public static String translateIf( CLanguage Language, String strKeyMessage, String ... strVariables ) {

		String strResult = null; //strKeyMessage;
		
		if ( Language != null ) {
			
			strResult = Language.translate(strKeyMessage, strVariables );
			
		}
		else {
			
			strResult = String.format( strKeyMessage, ( Object[] ) strVariables );
			
		}
		
		return strResult;
		
	}  
	
	public String translate( String strKeyMessage, String ... strVariables ) {
		
		String strResult = null; //strKeyMessage;
		
		if ( MessageMap != null ) {
			
			strResult = MessageMap.getProperty( strKeyMessage );
		
		}
			
		if ( strResult == null && bUseCommonPhrases == true ) {

			//Find in common phrases
			for ( CLanguage CommonPhrases : CommonPhrasesList ) {

				if ( CommonPhrases.MessageMap != null )
					strResult = CommonPhrases.MessageMap.getProperty( strKeyMessage );

				if ( strResult != null )
					break;

			}

		}

		if ( strResult == null ) {
		
			if ( LoggerMissingTranslations != null ) {
				
				LoggerMissingTranslations.logWarning( "-1", "Missing translation: [" + strKeyMessage + "] in file: [" + this.strLanguageFileName + "]" );
				
			}
			
			strResult = strKeyMessage;
		}	
	    
	    try {
	    	
	    	strResult = String.format( strResult, ( Object[] ) strVariables );
	    	
	    }
	    catch ( Exception Ex ) {
	    	
			if ( Logger != null )
				Logger.logException("-1000", Ex.getMessage(), Ex );
	    	
	    }
		
		return strResult;
		
	}
	
	public synchronized static CLanguage getLanguage( String strLanguageFileName ) { 
	
		return getLanguage( null, strLanguageFileName );  
		
	}
	
	public synchronized static CLanguage getLanguage( CExtendedLogger Logger, String strLanguageFileName ) {
		
		CLanguage Result = null; 
		
		if ( CLanguage.LastLangFinded != null && CLanguage.LastLangFinded.getLanguageFileName().equals( strLanguageFileName ) == true )
			return CLanguage.LastLangFinded;
		
		for ( int i=0; i < LanguagesList.size(); i++ ) {
			
			CLanguage LangTmp = LanguagesList.get( i );
			
			if ( LangTmp.getLanguageFileName().equals( strLanguageFileName ) == true ) {
			
				Result = LangTmp;
				CLanguage.LastLangFinded = LangTmp;
				break;
			
			}	
		
		}			
		
		if ( Result == null ) {
		
			Result = new CLanguage( Logger, strLanguageFileName );
		
			LanguagesList.add( Result );
		
		}	
		
		return Result;
		
	} 
	

}
