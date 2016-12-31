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
package commonlibs.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.sun.management.OperatingSystemMXBean;

import commonlibs.commonclasses.CLanguage;
import commonlibs.commonclasses.ConstantsCommonClasses;
import commonlibs.extendedlogger.CExtendedLogger;


@SuppressWarnings("restriction")
public class Utilities {

	public static final String _IPV4All = "0.0.0.0";
	public static final String _IPV6All = "::";
	public static final String _IPV6All1 = "0:0:0:0:0:0:0:0";

	public static final String _IPV4Localhost = "127.0.0.1";
	public static final String _IPV6Localhost = "::1";
	public static final String _IPV6Localhost1 = "0:0:0:0:0:0:0:1";
	
	public static Pattern VALID_IPV4_PATTERN = null;
	public static Pattern VALID_IPV6_PATTERN = null;
	
	public static String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
	public static String ipv6Pattern = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";

	public static Pattern VALID_HTTP_PATTERN = null;
	public static Pattern VALID_FTP_PATTERN = null;

	public static String httpPattern = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	public static String ftpPattern = "^(ftp)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	
	static {
	
		try {
		    
			VALID_IPV4_PATTERN = Pattern.compile( ipv4Pattern, Pattern.CASE_INSENSITIVE );
		    VALID_IPV6_PATTERN = Pattern.compile( ipv6Pattern, Pattern.CASE_INSENSITIVE );

			VALID_HTTP_PATTERN = Pattern.compile( httpPattern, Pattern.CASE_INSENSITIVE );
		    VALID_FTP_PATTERN = Pattern.compile( ftpPattern, Pattern.CASE_INSENSITIVE );
		    
		} 
		catch ( Exception Ex ) {
		
		}
	
	}
	
	public static boolean isValidIPV4( String strIPAddress ) {
		
		Matcher m1 = Utilities.VALID_IPV4_PATTERN.matcher( strIPAddress );

		return m1.matches();
		
	}  

	public static boolean isValidIPV6( String strIPAddress ) {
		
		Matcher m1 = Utilities.VALID_IPV6_PATTERN.matcher( strIPAddress );

		return m1.matches();
		
	}  
	
	public static boolean isValidIP( String strIPAddress ) {
		    
		return isValidIPV4( strIPAddress ) || isValidIPV6( strIPAddress );
	  
	}

	public static boolean isValidHTTPURL( String strHTTPURL ) {
		
		Matcher m1 = Utilities.VALID_HTTP_PATTERN.matcher( strHTTPURL );

		return m1.matches();
		
	}

	public static boolean isValidFTPURL( String strFTPURL ) {
		
		Matcher m1 = Utilities.VALID_FTP_PATTERN.matcher( strFTPURL );

		return m1.matches();
		
	}
	
	public static boolean isValidURL( String strURL ) {

		return isValidHTTPURL( strURL ) || isValidFTPURL( strURL );
		
	}
	
    public static boolean checkDir( String strDirToCheck ) {
    	
       return checkDir( strDirToCheck, null, null );
    
    }
    
    public static boolean checkDir( String strDirToCheck, CExtendedLogger Logger, CLanguage Lang ) {
    	
    	boolean bResult = false;
    	
	    File DirToCheck = new File( strDirToCheck );

	    try {
	       
	    	if ( DirToCheck.exists() == true ) {
              
	    		if ( DirToCheck.canRead() == true ) {
            	  
	    			if ( DirToCheck.isDirectory() == true ) {
            		 
	    				bResult = true;
            	  
	    			}
	    			else if ( Logger != null ) {

	    				if ( Lang != null )  
	    					Logger.logError( "-1003", Lang.translate( "The dir path [%s] not is dir", DirToCheck.getAbsolutePath() ) );        
	    				else
	    					Logger.logError( "-1003", String.format( "The dir path [%s] not is dir", DirToCheck.getAbsolutePath() ) );        
	    				
	    			}
              
	    		} 
	    		else if ( Logger != null ) {

	    			if ( Lang != null )  
	    				Logger.logError( "-1002", Lang.translate( "The dir in the path [%s] cannot read, please check the owner and permissions", DirToCheck.getAbsolutePath() ) );
	    			else
	    				Logger.logError( "-1002", String.format( "The dir in the path [%s] cannot read, please check the owner and permissions", DirToCheck.getAbsolutePath() ) );
              
	    		}
	       
	    	}
	       
	    	else if ( Logger != null ) {

	    		if ( Lang != null )  
	    		    Logger.logError( "-1001", Lang.translate( "The dir in the path [%s] not exists", DirToCheck.getAbsolutePath() ) );        
	    		else
	    		    Logger.logError( "-1001", String.format( "The dir in the path [%s] not exists", DirToCheck.getAbsolutePath() ) );        
					
	    	}
	   
	    }
	    catch ( Exception Ex ) {
		
			if ( Logger != null )   
	    	    Logger.logException( "-1000", Ex.getMessage(), Ex );        
	  
	    }
	   
	    return bResult;
    	
    }
	
    public static boolean checkFile( String strFileToCheck ) {

    	return checkFile( strFileToCheck, null, null );
    
    }
    	
    public static boolean checkFile( String strFileToCheck, CExtendedLogger Logger, CLanguage Lang ) {
    	
    	boolean bResult = false;
    	
	    File FileToCheck = new File( strFileToCheck );

	    try {
	       
	    	if ( FileToCheck.exists() == true ) {
              
	    		if ( FileToCheck.canRead() == true ) {
            	  
	    			if ( FileToCheck.isFile() == true ) {
            		 
	    				bResult = true;
            	  
	    			}
	    			else if ( Logger != null ) {
            		
	    				if ( Lang != null )   
	    				    Logger.logError( "-1003", Lang.translate( "The file path [%s] not is dir", FileToCheck.getAbsolutePath() ) );        
	    				else    
	    				    Logger.logError( "-1003", String.format( "The file path [%s] not is dir", FileToCheck.getAbsolutePath() ) );        
	    				
	    			}
              
	    		} 
	    		else if ( Logger != null ) {
            	
    				if ( Lang != null )   
	    			    Logger.logError( "-1002", Lang.translate( "The file in the path [%s] cannot read, please check the owner and permissions", FileToCheck.getAbsolutePath() ) );
    				else
	    			    Logger.logError( "-1002", String.format( "The file in the path [%s] cannot read, please check the owner and permissions", FileToCheck.getAbsolutePath() ) );
              
	    		}
	       
	    	}
	    	else if ( Logger != null ) {
	    	   
				if ( Lang != null )   
	    		   Logger.logError( "-1001", Lang.translate( "The file in the path [%s] not exists", FileToCheck.getAbsolutePath() ) );        
				else
		    	   Logger.logError( "-1001", String.format( "The file in the path [%s] not exists", FileToCheck.getAbsolutePath() ) );        
				
	    	}
	   
	    }
	    catch ( Exception Ex ) {
		
			if ( Logger != null )   
	    	   Logger.logException( "-1000", Ex.getMessage(), Ex );        
	  
	    }
	   
	    return bResult;
    	
    }

    public static int strToInteger( String strStringToConvert ) {
    
    	return strToInteger( strStringToConvert, null );
    
    }
    
    public static int strToInteger( String strStringToConvert, CExtendedLogger Logger ) {
    	
    	int intResult = 0;
    	
    	try {
    		
           intResult = Integer.parseInt( strStringToConvert );
    	
    	}
    	catch ( Exception Ex ) {
    		
    		if ( Logger != null )   
    	       Logger.logException( "-1015", Ex.getMessage(), Ex );        
    		
    	}
    	
    	return intResult;
    	
    }

    public static long strToLong( String strStringToConvert ) {
        
    	return strToLong( strStringToConvert, null );
    
    }
    
    public static long strToLong( String strStringToConvert, CExtendedLogger Logger ) {
    	
    	long lngResult = 0;
    	
    	try {
    		
           lngResult = Long.parseLong( strStringToConvert );
    	
    	}
    	catch ( Exception Ex ) {
    		
    		if ( Logger != null )   
    	       Logger.logException( "-1015", Ex.getMessage(), Ex );        
    		
    	}
    	
    	return lngResult;
    	
    }
    
    public static boolean checkStringIsInteger( String strStringToTest, CExtendedLogger Logger ) {
    	
    	boolean bResult = false;
    	
    	try {
    		
           Integer.parseInt( strStringToTest );
           
           bResult = true;
    	
    	}
    	catch ( Exception Ex ) {
    		
    		if ( Logger != null )   
    	       Logger.logException( "-1015", Ex.getMessage(), Ex );        
    		
    	}
    	
    	return bResult;
    	
    }
    
	public static int compareVersions( String strVersion1, String strVersion2 ) {
		
		CVersionTokenizer Tokenizer1 = new CVersionTokenizer( strVersion1 );
		CVersionTokenizer Tokenizer2 = new CVersionTokenizer( strVersion2 );

		int intNumber1 = 0; 
		String strSuffix1 = "";

		int intNumber2 = 0;
		String strSuffix2 = "";

		while ( Tokenizer1.MoveNext() ) {
			
			if ( Tokenizer2.MoveNext() == false ) {
				
				do {
				
					intNumber1 = Tokenizer1.getNumber();
					strSuffix1 = Tokenizer1.getSuffix();
					
					if ( intNumber1 != 0 || strSuffix1.length() != 0 ) {
						
						// Version one is longer than number two, and non-zero
						return 1;
						
					}
					
				} while ( Tokenizer1.MoveNext() );

				// Version one is longer than version two, but zero
				return 0;
			
			}

			intNumber1 = Tokenizer1.getNumber();
			strSuffix1 = Tokenizer1.getSuffix();
			
			intNumber2 = Tokenizer2.getNumber();
			strSuffix2 = Tokenizer2.getSuffix();

			if ( intNumber1 < intNumber2 ) {

				// Number one is less than number two
				return -1;
				
			}
			
			if ( intNumber1 > intNumber2 ) {
				
				// Number one is greater than number two
				return 1;
				
			}

			boolean bEmpty1 = strSuffix1.length() == 0;
			boolean bEmpty2 = strSuffix2.length() == 0;

			if ( bEmpty1 && bEmpty2 )
				continue; // No suffixes
			
			if ( bEmpty1 )
				return 1; // First suffix is empty (1.2 > 1.2b)
			
			if ( bEmpty2 )
				return -1; // Second suffix is empty (1.2a < 1.2)

			// Lexical comparison of suffixes
			int intResult = strSuffix1.compareTo( strSuffix2 );
			
			if ( intResult != 0 )
				return intResult;

		}

		if ( Tokenizer2.MoveNext() ) {
			
			do {
			
				intNumber2 = Tokenizer2.getNumber();
				strSuffix2 = Tokenizer2.getSuffix();
				
				if ( intNumber2 != 0 || strSuffix2.length() != 0 ) {
				
					// Version one is longer than version two, and non-zero
					return -1;
					
				}
				
			} while ( Tokenizer2.MoveNext() );

			// Version two is longer than version one, but zero
			return 0;
			
		}
		
		return 0;
		
	}
	
	public static boolean versionGreaterEquals( String strVersion1, String strVersion2 ) {
		
		int intResult = compareVersions( strVersion1, strVersion2 );
		
		return intResult >= 0;
		
	}  

	public static boolean versionLessEquals( String strVersion1, String strVersion2 ) {
		
		int intResult = compareVersions( strVersion1, strVersion2 );
		
		return intResult <= 0;
		
	}  
	
	public static ArrayList<String> parseTokensByTags( String strStartTag, String strEndTag, String strTagContained, boolean bIgnoreDuplicated, boolean bForceLowerCase ) {

		ArrayList<String> ResultListTokens = new ArrayList<String>();
		
		int intStartTagIndex = 0;
		
		while ( intStartTagIndex >= 0 && intStartTagIndex < strTagContained.length() ) {
			
			intStartTagIndex = strTagContained.indexOf( strStartTag, intStartTagIndex );
			
			if ( intStartTagIndex >= 0 ) {
				
				int intEndTagIndex = strTagContained.indexOf( strEndTag, intStartTagIndex );;
								
				if ( intEndTagIndex > intStartTagIndex ) {

					String strTagToAdd = strTagContained.substring( intStartTagIndex + strStartTag.length(), intEndTagIndex );
					
					if ( bForceLowerCase == true )
						strTagToAdd = strTagToAdd.toLowerCase();
					
					if ( bIgnoreDuplicated == false || ResultListTokens.contains( strTagToAdd ) == false )     
					    ResultListTokens.add( strTagToAdd );
					
					intStartTagIndex = intEndTagIndex + strEndTag.length();
					
				}
				else {
					
					break;
					
				}
				
			}
			
		}
		
		return ResultListTokens;
		
	} 
	
	public static String generateString( String strCharacters, int intLength ) {
	
		Random RandomGenerator = new Random();
		
		return generateString( RandomGenerator, strCharacters, intLength );
		
	}
	
    public static String hashCrypt( String StringToCryptHash, String strHashAlgorithm, CExtendedLogger Logger ) {
    	   
        MessageDigest MsgDigest;

        try {
            
            MsgDigest = MessageDigest.getInstance( strHashAlgorithm );  //"SHA-512");

            MsgDigest.update( StringToCryptHash.getBytes() );
            
            byte[] MsgBytes = MsgDigest.digest();
            
            String strOut = "";
            
            for ( int i = 0; i < MsgBytes.length; i++ ) {
                
               byte bytTemp = MsgBytes[ i ];

               String S = Integer.toHexString( new Byte( bytTemp ) );
                
               while ( S.length() < 2 ) {

                  S = "0" + S;

               }
                
               S = S.substring( S.length() - 2 );
                
               strOut += S;

            }

            return strOut;

        } 
        catch ( Exception Ex ) {
            
    		if ( Logger != null )   
     	       Logger.logException( "-1015", Ex.getMessage(), Ex );        

        }

        return "";

    }

	public static boolean isValidDateTimeFormat( String strDateTimeFormat, CExtendedLogger Logger ) {
		
		boolean bResult = false;
		
		try {
		
			SimpleDateFormat DTFormatter = new SimpleDateFormat( strDateTimeFormat );
			//SimpleDateFormat TFormatter = new SimpleDateFormat("HHmmss");
			//SimpleDateFormat DTFormatter = new SimpleDateFormat("yyyyMMdd HHmmss");
			//DateTimeFormat x = new DateTim

			Date date = new Date();
			
			String strTmp = DTFormatter.format( date );
			
			return strTmp != null && strTmp.isEmpty() == false;
			
		}
		catch ( Exception Ex ) {
			
    		if ( Logger != null )   
      	       Logger.logException( "-1015", Ex.getMessage(), Ex );        
			
		}
		
		return bResult;
		
	}

    public static java.util.Date getDateFromStringAndFormat( String strDate, String strDateFormat, CExtendedLogger Logger ) {
        
        java.util.Date Result = null;
        
        try {
        
            if ( strDateFormat == null || strDateFormat.isEmpty() ) {
                
                strDateFormat = ConstantsCommonClasses._Global_Date_Format;
                
            }
            
            SimpleDateFormat DTFormatter = new SimpleDateFormat( strDateFormat );

            Result = DTFormatter.parse( strDate );
            
        }
        catch ( Exception Ex ) {
            
            if ( Logger != null )   
               Logger.logException( "-1015", Ex.getMessage(), Ex );        
            
        }
        
        return Result;
        
    }
    
	public static String getDateInFormat( String strFormat, CExtendedLogger Logger ) {

		String strResult = "";
		
		try {

			SimpleDateFormat sdfDate = new SimpleDateFormat( strFormat ); //ConstantsCommonClasses._Global_Date_Time_Format_File_System );
			
			Date now = new Date();
		    
			strResult = sdfDate.format( now );
		
		}
		catch ( Exception Ex ) {
			
    		if ( Logger != null )   
      	       Logger.logException( "-1015", Ex.getMessage(), Ex );        
			
		}
		
		return strResult;
		
	}

    public static String getDateInFormat( String strFormat, Date dateToFormat, CExtendedLogger logger ) {

        String strResult = "";
        
        try {

            SimpleDateFormat sdfDate = new SimpleDateFormat( strFormat ); //ConstantsCommonClasses._Global_Date_Time_Format_File_System );
            
            //Date now = new Date();
            
            strResult = sdfDate.format( dateToFormat );
        
        }
        catch ( Exception Ex ) {
            
            if ( logger != null )   
               logger.logException( "-1015", Ex.getMessage(), Ex );        
            
        }
        
        return strResult;
        
    }

    public static String getDateInFormat( String strFormat, Timestamp timestampToFormat, CExtendedLogger logger ) {

        String strResult = "";
        
        try {

            SimpleDateFormat sdfDate = new SimpleDateFormat( strFormat ); //ConstantsCommonClasses._Global_Date_Time_Format_File_System );
            
            strResult = sdfDate.format( timestampToFormat );
        
        }
        catch ( Exception Ex ) {
            
            if ( logger != null )   
               logger.logException( "-1015", Ex.getMessage(), Ex );        
            
        }
        
        return strResult;
        
    }
    
    public static Date getOnlyDate( Date dateToFormat, CExtendedLogger logger ) {
        
        Date Result = null;
        
        try {

            String strDate = getDateInFormat( "dd/MM/yyyy", dateToFormat , logger );
            
            SimpleDateFormat sdfDate = new SimpleDateFormat( "dd/MM/yyyy" );
            
            Result = sdfDate.parse( strDate );
            
        }
        catch ( Exception Ex ) {
            
            if ( logger != null )   
                logger.logException( "-1015", Ex.getMessage(), Ex );        
            
        }
        
        return Result;
        
    }

    public static Date getOnlyTime( Date dateToFormat, CExtendedLogger logger ) {
        
        Date Result = null;
        
        try {

            String strTime = getDateInFormat( "HH:mm:ss", dateToFormat , logger );
            
            SimpleDateFormat sdfTime = new SimpleDateFormat( "HH:mm:ss" );
            
            Result = sdfTime.parse( strTime );
            
        }
        catch ( Exception Ex ) {
            
            if ( logger != null )   
                logger.logException( "-1015", Ex.getMessage(), Ex );        
            
        }
        
        return Result;
        
    }
    
    public static Timestamp getOnlyDate( Timestamp dateToFormat, CExtendedLogger logger ) {
        
        Timestamp Result = null;
        
        try {

            String strDate = getDateInFormat( "dd/MM/yyyy", dateToFormat , logger );
            
            SimpleDateFormat sdfDate = new SimpleDateFormat( "dd/MM/yyyy" );
            
            Result = new Timestamp( sdfDate.parse( strDate ).getTime() );
            
        }
        catch ( Exception Ex ) {
            
            if ( logger != null )   
                logger.logException( "-1015", Ex.getMessage(), Ex );        
            
        }
        
        return Result;
        
    }

    public static Timestamp getOnlyTime( Timestamp dateToFormat, CExtendedLogger logger ) {
        
        Timestamp Result = null;
        
        try {

            String strTime = getDateInFormat( "HH:mm:ss", dateToFormat , logger );
            
            SimpleDateFormat sdfTime = new SimpleDateFormat( "HH:mm:ss" );
            
            Result = new Timestamp( sdfTime.parse( strTime ).getTime() );
            
        }
        catch ( Exception Ex ) {
            
            if ( logger != null )   
                logger.logException( "-1015", Ex.getMessage(), Ex );        
            
        }
        
        return Result;
        
    }
    
    public static java.util.Date getJoinedDateFrom( java.util.Date dateFrom, java.util.Date timeFrom, CExtendedLogger logger ) {
        
        java.util.Date result = null; 

        try {

            Calendar calendarDateFrom = Calendar.getInstance();
            
            Calendar calendarTimeFrom = Calendar.getInstance();
            
            if ( dateFrom != null && timeFrom != null ) {
             
                calendarDateFrom.setTime( dateFrom );
                
                calendarTimeFrom.setTime( timeFrom );
                
                calendarTimeFrom.set( Calendar.YEAR, calendarDateFrom.get( Calendar.YEAR ) );
                calendarTimeFrom.set( Calendar.MONTH, calendarDateFrom.get( Calendar.MONTH ) );
                calendarTimeFrom.set( Calendar.DAY_OF_MONTH, calendarDateFrom.get( Calendar.DAY_OF_MONTH ) );
                
                result =  calendarTimeFrom.getTime(); 
                
                //parseDateTime( calendarDateFrom.get( Calendar.YEAR ) + "-" + ( calendarDateFrom.get( Calendar.MONTH ) + 1 )  + "-" +  calendarDateFrom.get( Calendar.DAY_OF_MONTH ) + "-" +  calendarTimeFrom.get( Calendar.HOUR ) + "-" + calendarTimeFrom.get( Calendar.MINUTE ) + "-" + calendarTimeFrom.get( Calendar.SECOND ), "yyyy-MM-dd-HH-mm-ss", logger );
                
            }    
            else if ( timeFrom != null ) {
                
                calendarTimeFrom.setTime( timeFrom );
                
                calendarDateFrom.setTime( new Date() );
                
                calendarTimeFrom.set( Calendar.YEAR, calendarDateFrom.get( Calendar.YEAR ) );
                calendarTimeFrom.set( Calendar.MONTH, calendarDateFrom.get( Calendar.MONTH ) );
                calendarTimeFrom.set( Calendar.DAY_OF_MONTH, calendarDateFrom.get( Calendar.DAY_OF_MONTH ) );
                
                result = calendarTimeFrom.getTime(); 
                        
                //parseDateTime( calendarDateFrom.get( Calendar.YEAR ) + "-" + ( calendarDateFrom.get( Calendar.MONTH ) + 1 )  + "-" +  calendarDateFrom.get( Calendar.DAY_OF_MONTH ) + "-" +  calendarTimeFrom.get( Calendar.HOUR ) + "-" + calendarTimeFrom.get( Calendar.MINUTE ) + "-" + calendarTimeFrom.get( Calendar.SECOND ), "yyyy-MM-dd-HH-mm-ss", logger );
                
            }
            else if ( dateFrom != null ) {
                
                calendarTimeFrom.setTime( new Date() );
                
                calendarDateFrom.setTime( dateFrom );
                
                calendarTimeFrom.set( Calendar.YEAR, calendarDateFrom.get( Calendar.YEAR ) );
                calendarTimeFrom.set( Calendar.MONTH, calendarDateFrom.get( Calendar.MONTH ) );
                calendarTimeFrom.set( Calendar.DAY_OF_MONTH, calendarDateFrom.get( Calendar.DAY_OF_MONTH ) );
                
                result = calendarTimeFrom.getTime(); 

                //Result = parseDateTime( calendarDateFrom.get( Calendar.YEAR ) + "-" + ( calendarDateFrom.get( Calendar.MONTH ) + 1 )  + "-" +  calendarDateFrom.get( Calendar.DAY_OF_MONTH ) + "-" +  calendarTimeFrom.get( Calendar.HOUR ) + "-" + calendarTimeFrom.get( Calendar.MINUTE ) + "-" + calendarTimeFrom.get( Calendar.SECOND ), "yyyy-MM-dd-HH-mm-ss", logger );
                
            }
        
        }
        catch ( Exception Ex ) {
            
            if ( logger != null )   
                logger.logException( "-1015", Ex.getMessage(), Ex );        
            
        }
            
        return result;
        
    }
    
	public static String generateString( Random RandomGenerator, String strCharacters, int intLength ) {
	    
		char[] strText = new char[ intLength ];
	
	    for (int i = 0; i < intLength; i++) {

	    	strText[i] = strCharacters.charAt( RandomGenerator.nextInt( strCharacters.length() ) );
	    
	    }
	    
	    return new String(strText);
	    
	}	

    public static String cryptString( String strStringToCrypt, String strCryptAlgorithm, String strCryptKey, CExtendedLogger Logger ) {
    	
        String strResult = strStringToCrypt;
    	
    	try {
    		
    		DESKeySpec keySpec = new DESKeySpec( strCryptKey.getBytes( "UTF8" ) ); 
    		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance( strCryptAlgorithm );
    		SecretKey key = keyFactory.generateSecret(keySpec);
    		
    		byte[] arrClearTextBytes = strStringToCrypt.getBytes( "UTF8" );      

    		Cipher cipher = Cipher.getInstance( strCryptAlgorithm ); // cipher is not thread safe
    		cipher.init( Cipher.ENCRYPT_MODE, key );
    		strResult = new String( Base64.encode( cipher.doFinal( arrClearTextBytes ) ) );
    		
    	}
        catch ( Exception Ex ) {
            
    		if ( Logger != null )   
     	       Logger.logException( "-1015", Ex.getMessage(), Ex );        

        }
    	
    	return strResult;
    	
    }
    
    public static String uncryptString( String strStringToUncrypt, String strUncryptAlgorithm, String strUncryptKey, CExtendedLogger Logger ) {
    	
        String strResult = strStringToUncrypt;
    	
    	try {
    		
    		DESKeySpec keySpec = new DESKeySpec( strUncryptKey.getBytes( "UTF8" ) ); 
    		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance( strUncryptAlgorithm );
    		SecretKey key = keyFactory.generateSecret( keySpec );
    		
    		byte[] arrCryptedBytes = strStringToUncrypt.getBytes( "UTF8" );      

    		Cipher cipher = Cipher.getInstance( strUncryptAlgorithm ); // cipher is not thread safe
    		cipher.init( Cipher.DECRYPT_MODE, key );
    		strResult = new String( cipher.doFinal( Base64.decode( arrCryptedBytes ) ) );
    		
    	}
        catch ( Exception Ex ) {
            
    		if ( Logger != null )   
     	       Logger.logException( "-1015", Ex.getMessage(), Ex );        

        }
    	
    	return strResult;
    	
    }
    
    public static int getIndexByValue( String[] strStringArray, String strValue ) {
    	
    	strValue = strValue.toLowerCase();
    	
        for ( int intIndex = 0; intIndex < strStringArray.length; intIndex++ ) {
     	   
     	   if ( strStringArray[ intIndex ].toLowerCase().equals( strValue ) ) {
     		
     		   return intIndex;
     		   
     	   }
     	   
        }
        
        return -1;
     	
     	
     }
 
    public static int countSubString( String strToFind, String strSearch ) {

    	 try {

    		 int intCount = 0;

    		 for ( int intFromIndex = 0; intFromIndex > -1; intCount++ ) {
    		
    			 intFromIndex = strToFind.indexOf( strSearch, intFromIndex + ( ( intCount > 0 ) ? 1 : 0 ) );
    		 
    		 }	 

    		 return intCount - 1;
    		 
    	 }
    	 catch ( Exception Ex ) {

        	 return -1;
        	 
    	 }

     }
   
	public static String uncryptString( String strPasswordCrypted, String strPasswordCryptedSep, String strDefaultCryptAlgorithm, String strCryptedPassword, CExtendedLogger Logger, CLanguage Lang ) {
		
		String strResult = strCryptedPassword;
		
		int intPassCryptedLength = strPasswordCrypted.length(); //ConfigXMLTagsDBServicesManager._Password_Crypted.length();
		int intPassCryptedSepLength = strPasswordCryptedSep.length(); //ConfigXMLTagsDBServicesManager._Password_Crypted_Sep.length();
		
		if ( strCryptedPassword.length() > intPassCryptedLength && strResult.substring( 0, intPassCryptedLength ).equals( strPasswordCrypted) ) {

			strResult =  strResult.substring( intPassCryptedLength + intPassCryptedSepLength, strResult.length() );
			
			String strCryptKeys[] = { 
					                  "xfzm29dp",
					                  "6m3m7xa5",
			                          "e48c4xyi",		                  
			                          "6we7og02",		                  
			                          "4m7gypao",		                  
			                          "hy6z2m0x",		                  
			                          "2zx6kynd",		                  
			                          "1k9c0666",		                  
			                          "q3f5i11j",		                  
			                          "4y84x0j7"		                  
			                        };

			int intIndexPassSep = strResult.indexOf( strPasswordCryptedSep, 0 );
			
			String strCryptKeyIndex =  strResult.substring( 0, intIndexPassSep );
			
			int intCryptKeyIndex = Utilities.strToInteger( strCryptKeyIndex );
			
			if ( intCryptKeyIndex > 0 && intCryptKeyIndex <= strCryptKeys.length ) {
				
				strResult =  strResult.substring( intIndexPassSep + intPassCryptedSepLength, strResult.length() );
				
				//DefaultConstantsSystemStartSession.strDefaultCryptAlgorithm
				strResult = Utilities.uncryptString( strResult, strDefaultCryptAlgorithm, strCryptKeys[ intCryptKeyIndex ], Logger );
				
				
			}
			else if ( Logger != null ) {

				if ( Lang != null )
					Logger.logError( "-1001", Lang.translate( "The crypt key index [%s] is not valid", strCryptKeyIndex ) );
				else
					Logger.logError( "-1001", String.format( "The crypt key index [%s] is not valid", strCryptKeyIndex ) );

			}
			
		}
		else if ( Logger != null ) {
			
			if ( Lang != null )
				Logger.logWarning( "-1", Lang.translate( "Using clear text password" ) );
			else
				Logger.logWarning( "-1", "Using clear text password" );
			
		}
		
		return strResult;
		
	}
 	
    public static String replaceToHTMLEntity( String strToFindAndReplace ) {
		
        strToFindAndReplace = strToFindAndReplace.replaceAll( "&", "&amp;" );
        strToFindAndReplace = strToFindAndReplace.replaceAll( "<", "&lt;" );
        strToFindAndReplace = strToFindAndReplace.replaceAll( ">", "&gt;" );
        strToFindAndReplace = strToFindAndReplace.replaceAll( "\"", "&quot;" );
        strToFindAndReplace = strToFindAndReplace.replaceAll( "\'", "&apos;" );
		
		return strToFindAndReplace;
		
	}
	
	public static ArrayList<String> extractTokens( String strStartToken, String strEndToken, String strExpression ) {
		
		ArrayList<String> Result = new ArrayList<String>();
		
		if ( strExpression.contains( strStartToken ) ) {
			
			String strTmp = strExpression;

			int intStartIndex = strTmp.indexOf( strStartToken ); //"${" );
			int intEndIndex = strTmp.indexOf( strEndToken ); //"}$" );
			
			while ( intStartIndex > 0 && intEndIndex > intStartIndex ) {
			
				if ( intEndIndex > intStartIndex ) {

					String strCallToAdd = strTmp.substring( intStartIndex + strStartToken.length(), intEndIndex );
					
					Result.add( strCallToAdd );

					strTmp = strTmp.replace( strStartToken + strCallToAdd + strEndToken, "" ); //"${" + strCallToAdd + "}$", "" );
					
				}
			
				intStartIndex = strTmp.indexOf( strStartToken ); // "${" );
				intEndIndex = strTmp.indexOf( strEndToken ); //"}$" );
				
			}
			
		}
		
		return Result;
		
	}
    
	public final static String getJarFolder( Class<?> ClassDef ) {

    	String s = "";

    	try {

    		String name = ClassDef.getCanonicalName().replace( '.', '/' );

    		s = ClassDef.getClass().getResource( "/" + name + ".class" ).toString();

    		s = URLDecoder.decode( s, "UTF-8" );
    		
    		s = s.replace( '/', File.separatorChar );

    		if ( s.indexOf(".jar") >= 0 )
    			s = s.substring( 0, s.indexOf(".jar") + 4 );
    		else
    			s = s.substring( 0, s.indexOf(".class") );

    		if ( s.indexOf( "jar:file:\\" )  == 0 ) { //Windows style path SO inside jar file 

    			s = s.substring( 10 );

    		}
    		else if ( s.indexOf( "file:\\" )  == 0 ) { //Windows style path SO .class file

    			s = s.substring( 6 );

    		}
    		else { //Unix family ( Linux/BSD/Mac/Solaris ) style path SO

    			s = s.substring( s.lastIndexOf(':') + 1 );

    		}

    		s = s.substring( 0, s.lastIndexOf( File.separatorChar ) + 1 );

    	}
    	catch ( Exception Ex ) { 

    		Ex.printStackTrace();

    	}

    	return s;
    	
    }
    
	public final static String getJarFolder( Class<?> ClassDef, CExtendedLogger Logger ) {

    	String s = "";

    	try {

    		String name = ClassDef.getCanonicalName().replace( '.', '/' );

    		s = ClassDef.getClass().getResource( "/" + name + ".class" ).toString();

    		s = URLDecoder.decode( s, "UTF-8" );
    		
    		s = s.replace( '/', File.separatorChar );

    		if ( s.indexOf(".jar") >= 0 )
    			s = s.substring( 0, s.indexOf(".jar") + 4 );
    		else
    			s = s.substring( 0, s.indexOf(".class") );

    		if ( s.indexOf( "jar:file:\\" )  == 0 ) { //Windows style path SO inside jar file 

    			s = s.substring( 10 );

    		}
    		else if ( s.indexOf( "file:\\" )  == 0 ) { //Windows style path SO .class file

    			s = s.substring( 6 );

    		}
    		else { //Unix family ( Linux/BSD/Mac/Solaris ) style path SO

    			s = s.substring( s.lastIndexOf(':') + 1 );

    		}

    		s = s.substring( 0, s.lastIndexOf( File.separatorChar ) + 1 );

    	}
    	catch ( Exception Ex ) { 

    		if ( Logger != null )
    			Logger.logException( "-1020" , Ex.getMessage(), Ex );

    	}

    	return s;
    	
    }

	public static int getSystemLoad( boolean bIncludeSystemMemoryUsage ) {
    	
    	int intSystemLoad = 0;
    	
    	OperatingSystemMXBean OperatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    	
    	int intCPULoad = (int) OperatingSystemMXBean.getSystemCpuLoad() * 100;
    	
    	if ( bIncludeSystemMemoryUsage ) {
    		
    		int intMemoryUsage = 0;
    		
    		long lngTotalMemoryAvailable = OperatingSystemMXBean.getTotalPhysicalMemorySize();
    		
    		long lngUsedMemory = lngTotalMemoryAvailable - OperatingSystemMXBean.getFreePhysicalMemorySize();
    		
    		intMemoryUsage = (int) ( lngUsedMemory * 100 / lngTotalMemoryAvailable );
    		
    		if ( intMemoryUsage >= 70 && intMemoryUsage >= intCPULoad )
    			intSystemLoad = intMemoryUsage;
    		else if ( intCPULoad >= 70 )
    			intSystemLoad = intCPULoad;
    		else
    			intSystemLoad = (( intCPULoad + intMemoryUsage ) / 2);
    		
    	}
    	else {
    		
    		intSystemLoad = intCPULoad;
    		
    	}
    	
    	return intSystemLoad;
    	
    }
    
	public static ArrayList<String> getNetAddressList( CExtendedLogger Logger, boolean bIncludeLocalHost ) {
    	
    	ArrayList<String> Result = new ArrayList<String>();
    	
		try {
			
			Enumeration<NetworkInterface> DefinedNetworksInterfaces = NetworkInterface.getNetworkInterfaces(); 

			while ( DefinedNetworksInterfaces.hasMoreElements() ) {

				NetworkInterface NetInterface = DefinedNetworksInterfaces.nextElement();

				if( NetInterface.isUp() ) {

					Enumeration<InetAddress> DefinedNetAddress = NetInterface.getInetAddresses();

					while ( DefinedNetAddress.hasMoreElements() ) {

						InetAddress NetAddress = DefinedNetAddress.nextElement();

						String strIP = NetAddress.toString();
						
						if ( bIncludeLocalHost == true || ( strIP.contains( _IPV4Localhost ) == false && strIP.contains( _IPV6Localhost ) == false && strIP.contains( _IPV6Localhost1 ) == false ) ) {

							if ( strIP.startsWith( "/" ) )
								Result.add( strIP.substring( 1 ) );
							else
								Result.add( strIP );
							
						}

					}

				}

			}
		
		}
		catch ( Exception Ex ) {
			
			if ( Logger != null ) {
			
				Logger.logException( "-1025" , Ex.getMessage(), Ex );
				
			}
			
		}
    	
    	return Result;
    	
    }

    public static ArrayList<String> getNetAddressList( CExtendedLogger Logger, boolean bIncludeLocalHost, boolean bOnlyIPV4, boolean bOnlyIPV6 ) {
    	
    	ArrayList<String> Result = new ArrayList<String>();
    	
		try {
			
			Enumeration<NetworkInterface> DefinedNetworksInterfaces = NetworkInterface.getNetworkInterfaces(); 

			while ( DefinedNetworksInterfaces.hasMoreElements() ) {

				NetworkInterface NetInterface = DefinedNetworksInterfaces.nextElement();

				if( NetInterface.isUp() ) {

					Enumeration<InetAddress> DefinedNetAddress = NetInterface.getInetAddresses();

					while ( DefinedNetAddress.hasMoreElements() ) {

						InetAddress NetAddress = DefinedNetAddress.nextElement();

						String strIP = NetAddress.toString();
						
						if ( bIncludeLocalHost == true || ( strIP.contains( _IPV4Localhost ) == false && strIP.contains( _IPV6Localhost ) == false && strIP.contains( _IPV6Localhost1 ) == false ) ) {

							if ( ( bOnlyIPV4 && isValidIPV4( strIP ) ) || ( bOnlyIPV6 && isValidIPV6( strIP ) ) ) {

								if ( strIP.startsWith( "/" ) )
									Result.add( strIP.substring( 1 ) );
								else
									Result.add( strIP );

							}
							
						}

					}

				}

			}
		
		}
		catch ( Exception Ex ) {
			
			if ( Logger != null ) {
			
				Logger.logException( "-1025" , Ex.getMessage(), Ex );
				
			}
			
		}
    	
    	return Result;
    	
    }

    public static boolean checkFileExt( File FileToTest, String strFileExtensions[] ) {
    	
    	if ( strFileExtensions != null ) {
    	
    		for ( int I = 0; I < strFileExtensions.length; I++ ) {

    			if ( FileToTest.getAbsolutePath().endsWith( strFileExtensions[ I ] ) )
    				return true;

    		}
    	
    	}
    	
    	return false;
    	
    }

    public static void cleanupDirectory( File FileOrDirectory, String arrExcludesExt[], int intCurrentLevelDeep ) {
    	
    	try {

    		if ( FileOrDirectory.isDirectory() ) {

    			for ( File SubFileOrDirectory : FileOrDirectory.listFiles() ) {
    				     
    				if ( SubFileOrDirectory.isDirectory() ) {
    				
    					cleanupDirectory( SubFileOrDirectory, arrExcludesExt, intCurrentLevelDeep + 1 );
    				
    				}
    				else {
    					
        				if ( checkFileExt( SubFileOrDirectory, arrExcludesExt ) == false ) {
              				 
        					SubFileOrDirectory.delete();

        				}
    					
    				}
    				
    			}

    		}    

    		File Files[] = FileOrDirectory.listFiles();
    		
    		if ( intCurrentLevelDeep > 0 && ( Files == null || Files.length == 0 ) && checkFileExt( FileOrDirectory, arrExcludesExt ) == false ) {
    		
    			FileOrDirectory.delete();
        
    		}	
    		
    	}
    	catch ( Error Err ) {

    		
    	}
    	catch ( Exception Ex ) {
    		
    		
    	}
    	
    }

    public static boolean cleanupDirectory( File FileOrDirectory, String arrExcludesExt[], int intCurrentLevelDeep, CExtendedLogger Logger ) {
    	
    	try {

    		if ( FileOrDirectory.isDirectory() ) {

    			for ( File SubFileOrDirectory : FileOrDirectory.listFiles() ) {
    				     
    				if ( SubFileOrDirectory.isDirectory() ) {
    				
    					cleanupDirectory( SubFileOrDirectory, arrExcludesExt, intCurrentLevelDeep + 1, Logger );
    				
    				}
    				else {
    					
        				if ( checkFileExt( SubFileOrDirectory, arrExcludesExt ) == false ) {
              				 
        					SubFileOrDirectory.delete();

        				}
    					
    				}
    				
    			}

    		}    

    		File Files[] = FileOrDirectory.listFiles();
    		
    		if ( intCurrentLevelDeep > 0 && ( Files == null || Files.length == 0 ) && checkFileExt( FileOrDirectory, arrExcludesExt ) == false ) {
    		
    			FileOrDirectory.delete();
        
    		}	
    		
    	}
    	catch ( Error Err ) {
    		
    		if ( Logger != null )   
       	       Logger.logError( "-1015", Err.getMessage(), Err );        
    		
    	}
    	catch ( Exception Ex ) {
    		
    		if ( Logger != null )   
       	       Logger.logException( "-1015", Ex.getMessage(), Ex );        
    		
    	}
    	
    	return false;
    	
    }

    public static void recursiveDelete( File FileOrDirectory ) {
        
    	try {

    		if ( FileOrDirectory.isDirectory() ) {

    			for ( File SubFileOrDirectory : FileOrDirectory.listFiles() ) {

    				SubFileOrDirectory.delete();

    				recursiveDelete( SubFileOrDirectory );

    			}

    		}    

    		FileOrDirectory.delete();
        
    	}
    	catch ( Error Err ) {
    		
    		
    	}
    	catch ( Exception Ex ) {
    		
    		
    	}
    	
    }   

    public static void recursiveDelete( File FileOrDirectory, CExtendedLogger Logger ) {
        
    	try {

    		if ( FileOrDirectory.isDirectory() ) {

    			for ( File SubFileOrDirectory : FileOrDirectory.listFiles() ) {

    				SubFileOrDirectory.delete();

    				recursiveDelete( SubFileOrDirectory );

    			}

    		}    

    		FileOrDirectory.delete();
        
    	}
    	catch ( Error Err ) {
    		
    		if ( Logger != null )   
       	       Logger.logError( "-1015", Err.getMessage(), Err );        
    		
    	}
    	catch ( Exception Ex ) {
    		
    		if ( Logger != null )   
       	       Logger.logException( "-1015", Ex.getMessage(), Ex );        
    		
    	}
    	
    }   

    public static File[] recursiveFindFilesToLoad( String strPath, String strFileExtension, int intMaxDepth, int intActuakDepth ) {
    	
        Vector<File> vUrls = new Vector<File>();

        File Directory = new File( strPath );
 
        if ( Directory.exists() && Directory.isDirectory() ) {
            
            File[] ListFoundFile = Directory.listFiles(); 
         
			Arrays.sort( ListFoundFile );
            
            for ( File FileFound : ListFoundFile ) {
                
            	if ( FileFound.isDirectory() == true && FileFound.canRead() == true ) { 
            	
            		if ( intActuakDepth + 1 <= intMaxDepth ) {  
            			
            			File[] DeepListFoundFile = recursiveFindFilesToLoad( FileFound.getAbsolutePath(), strFileExtension, intMaxDepth, intActuakDepth + 1 );
            		
                        for ( File DeepFileFound : DeepListFoundFile ) {
  
                    		vUrls.add(  DeepFileFound );
                        }
            		
            		}	
            	
            	}	
            	else if ( FileFound.isFile() == true && FileFound.getAbsolutePath().endsWith( strFileExtension ) == true ) {
            	
            		vUrls.add(  FileFound );

            	}	
            
            }
        
        }

        return vUrls.toArray( new File[0] );
        
    }
    
    //Taked from http://javawithswaranga.blogspot.com/2011/06/generic-method-to-sort-hashmap.html
    //Original author: Swaranga Sarma g+ profile on https://plus.google.com/116998045991621590873  
    public static  < K, V extends Comparable< ? super V >  >  Map< K, V > sortMapByValues( final Map < K, V > mapToSort ) {  
        
    	List< Map.Entry < K, V >  >  entries = new ArrayList< Map.Entry < K, V >  >( mapToSort.size() );    
      
        entries.addAll( mapToSort.entrySet() );  
      
        Collections.sort( entries, new Comparator< Map.Entry < K, V >  >() {  
            
        	@Override  
            public int compare( final Map.Entry< K, V >  entry1, final Map.Entry< K, V >  entry2 ) {  
                
        		return entry1.getValue().compareTo( entry2.getValue() );  
            
        	}  
        	
        });        
      
        Map< K, V > sortedMap = new LinkedHashMap< K, V >();        
      
        for ( Map.Entry< K, V > entry : entries ) {
        	
            sortedMap.put( entry.getKey(), entry.getValue() );  
        
        }        
      
        return sortedMap;
        
    }  	
    
    public static String quoteMySQLDataField( Timestamp fieldData ) {

    	String strResult = "";
    	
    	if ( fieldData != null )
    		strResult = "\"" + fieldData + "\"";
    	else
    		strResult = "null"; 

    	return strResult;
    	
    }

    public static String quoteMySQLDataField( java.sql.Date fieldData ) {

    	String strResult = "";
    	
    	if ( fieldData != null )
    		strResult = "\"" + fieldData + "\"";
    	else
    		strResult = "null"; 

    	return strResult;
    	
    }

    public static String quoteMySQLDataField( java.sql.Time fieldData ) {

    	String strResult = "";
    	
    	if ( fieldData != null )
    		strResult = "\"" + fieldData + "\"";
    	else
    		strResult = "null"; 

    	return strResult;
    	
    }
    
    public static long minutesBetween( Date dateStart, Date dateEnd ){
        
        long lngDuration  = dateEnd.getTime() - dateStart.getTime();

        long lngDiff = lngDuration / (60 * 1000) % 60; //TimeUnit.MILLISECONDS.toMinutes( lngDuration );        
        
        return lngDiff;
        
    }    
 
    public static String convertStreamToString( InputStream inputStream, CExtendedLogger localLogger ) {

        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream ) );

        StringBuilder stringBuilder = new StringBuilder();

        String strLine = null;

        try {

            while ( ( strLine = bufferedReader.readLine() ) != null) {

                stringBuilder.append( strLine );

            }

        }
        catch ( Exception Ex ) {

            if ( localLogger != null ) {

                localLogger.logException( "-1021", Ex.getMessage(), Ex );

            }

        }
        finally {

            try {

                inputStream.close();

            }
            catch ( Exception Ex ) {

                if ( localLogger != null ) {

                    localLogger.logException( "-1021", Ex.getMessage(), Ex );

                }

            }

        }

        return stringBuilder.toString();

    }
    
}
