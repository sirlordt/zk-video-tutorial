package commonlibs.extendedlogger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class CHTTPPostThreadWorker extends Thread {

	protected CloseableHttpClient HTTPClient = null;
	
	protected volatile boolean bStopNow = false;
	
	protected boolean bTaskRunningLock = false;
	
	protected String strHTTPLogURL = "";
	protected String strHTTPLogUser = "";
	protected String strHTTPLogPassword = "";
	protected String strProxyIP = "";
	protected int intProxyPort = -1;
	protected String strProxyUser = "";
	protected String strProxyPassword = "";
	
	//protected String strLogPath = "";
	//protected String strLogFile = "";
	
	public static int _Min_Port_Number = 1;
	public static int _Max_Port_Number = 65535;
	
	protected final static String _ResponseFormat = "JAVA-XML-WEBROWSET";
	protected final static String _ResponseFormatVersion = "1.0";
	
	protected final static int _MAX_Queue_Length = 150;
	
	protected LinkedList<CExtendedLogRecord> HTTPPostLogRecordQueue = null;
	
	protected int intQueueLength = 0;
	
	public CHTTPPostThreadWorker() {
	} 
	
	public HttpClientContext setConfigProxy( HttpPost PostData ) {
		
		HttpClientContext Context = null;
		
		if ( this.strProxyIP.isEmpty() == false && this.intProxyPort > _Min_Port_Number && this.intProxyPort < _Max_Port_Number ) {
			
			HttpHost ProxyHost = new HttpHost( this.strProxyIP.trim(), this.intProxyPort );
			
			RequestConfig ProxyConfig = RequestConfig.custom().setProxy( ProxyHost ).build();
			
			PostData.setConfig( ProxyConfig );
			
			if ( this.strProxyUser.trim().isEmpty() == false && this.strProxyPassword.trim().isEmpty() == false ) {
				
				CredentialsProvider credsProvider = new BasicCredentialsProvider();
				credsProvider.setCredentials( new AuthScope( ProxyHost.getHostName(), ProxyHost.getPort() ), new UsernamePasswordCredentials( this.strProxyUser.trim(), this.strProxyPassword.trim() ));    				
			
				// Create AuthCache instance
				AuthCache authCache = new BasicAuthCache();
				// Generate BASIC scheme object and add it to the local auth cache
				BasicScheme basicAuth = new BasicScheme();
				authCache.put( ProxyHost, basicAuth );
				
    			Context = HttpClientContext.create();
    			Context.setCredentialsProvider( credsProvider );
    			Context.setAuthCache( authCache );
    			
			}
			
		}

		return Context;
		
	}
	
	public boolean configureHTTPPost( String strHTTPLogURL, String strHTTPLogUser, String strHTTPLogPassword, String strProxyIP, int intProxyPort, String strProxyUser, String strProxyPassword ) {
		
		boolean bResult = false;
		
		HTTPPostLogRecordQueue = new LinkedList<CExtendedLogRecord>();
		
		this.strHTTPLogURL = strHTTPLogURL;
		this.strHTTPLogUser = strHTTPLogUser;
		this.strHTTPLogPassword = strHTTPLogPassword;
		this.strProxyIP = strProxyIP;
		this.intProxyPort = intProxyPort;
		this.strProxyUser = strProxyUser;
		this.strProxyPassword = strProxyPassword;
	
		//this.strLogPath = strLogPath;
		//this.strLogFile = strLogFile;
		
		try {

			if ( this.HTTPClient == null ) {

				RequestConfig clientConfig = RequestConfig.custom().setConnectTimeout( 3000 ).setConnectionRequestTimeout( 3000 ).setSocketTimeout( 3000 ).build();

				this.HTTPClient = HttpClientBuilder.create().setDefaultRequestConfig( clientConfig ).build();
			
			};
			
			bResult = true;
			
		} 
		catch ( Exception Ex ) {


		}
		catch ( Error Err ) {


		}
		
		return bResult;
		
	} 
	
	protected void finalize() {
		
		if ( HTTPClient != null ) {
			
			try {

				HTTPPostLogRecordQueue.clear();
				
				HTTPClient.close();
				
				bStopNow = true;
				
			} 
			catch ( Exception Ex ) {

			}

			HTTPPostLogRecordQueue = null;
			
			HTTPClient = null;
			
		}
		
	}
	
	public boolean addToHTTPPostQueue( CExtendedLogRecord LogRecord ) {
		
		boolean bResult = false;

		if ( intQueueLength < _MAX_Queue_Length ) {
		
			synchronized ( HTTPPostLogRecordQueue ) {

				HTTPPostLogRecordQueue.add( LogRecord );

				intQueueLength += 1;

				bResult = true;
				
			}
		
		}
		
		return bResult;
		
	}
	
	public synchronized boolean getStopNow() {
		
		return bStopNow;
		
	}
	
	public synchronized void setStopNow() {
		
		this.bStopNow = true;
		
	}
	
	public boolean getTaskRunningLock() {
		
		return bTaskRunningLock;
		
	} 
	
	public void run() {

		try {

    		HttpPost PostData = new HttpPost( this.strHTTPLogURL );
			
    		//Set the proxy
    	    HttpClientContext Context = this.setConfigProxy( PostData );
    		
    		// add header
    		PostData.setHeader( "User-Agent", "HTTPLogPostClient" );
			
    		DateFormat DFormat = new SimpleDateFormat( "dd/MM/yyyy" );
            
            DateFormat TFormat = new SimpleDateFormat( "HH:mm:ss" );
    		
			while ( bStopNow == false ) {
				
				bTaskRunningLock = true;
				
				CExtendedLogRecord LogRecordToPost;
				
				synchronized ( HTTPPostLogRecordQueue ) {

					LogRecordToPost = HTTPPostLogRecordQueue.remove( 0 );
					
					intQueueLength--;
					
				}
				
				if ( LogRecordToPost != null ) {

					try {
						
			        	CloseableHttpResponse Response = null;

			        	ArrayList<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			    		urlParameters.add( new BasicNameValuePair( "ServiceName", "System.Save.LogRecord" ) );
			    		urlParameters.add( new BasicNameValuePair( "User", this.strHTTPLogUser ) );
			    		urlParameters.add( new BasicNameValuePair( "Password", this.strHTTPLogPassword ) );
			    		urlParameters.add( new BasicNameValuePair( "ResponseFormat", _ResponseFormat ) );
			    		urlParameters.add( new BasicNameValuePair( "ResponseFormatVersion", _ResponseFormatVersion ) );
			    		urlParameters.add( new BasicNameValuePair( "InstanceID", LogRecordToPost.getInstanceID() ) );
			    		urlParameters.add( new BasicNameValuePair( "LogType", LogRecordToPost.getLogType() ) );
			    		urlParameters.add( new BasicNameValuePair( "Level", LogRecordToPost.getLevel().toString() ) );
			    		urlParameters.add( new BasicNameValuePair( "Date", DFormat.format( LogRecordToPost.getLogDateTime() ) ) );
			    		urlParameters.add( new BasicNameValuePair( "Time", TFormat.format( LogRecordToPost.getLogDateTime() ) ) );
			    		urlParameters.add( new BasicNameValuePair( "LoggerName", LogRecordToPost.getLoggerName() ) );
			    		urlParameters.add( new BasicNameValuePair( "Sequence", Long.toString( LogRecordToPost.getSequenceNumber() ) ) );
			    		urlParameters.add( new BasicNameValuePair( "ThreadID", Integer.toString( LogRecordToPost.getThreadID() ) ) );
			    		urlParameters.add( new BasicNameValuePair( "ThreadName", LogRecordToPost.getThreadName() ) );
			    		urlParameters.add( new BasicNameValuePair( "PackageName", LogRecordToPost.getSourcePackageName() ) );
			    		urlParameters.add( new BasicNameValuePair( "ClassName", LogRecordToPost.getSourceClassName() ) );
			    		urlParameters.add( new BasicNameValuePair( "MethodName", LogRecordToPost.getSourceMethodName() ) );
			    		urlParameters.add( new BasicNameValuePair( "Line", Long.toString( LogRecordToPost.getLineNunber() ) ) );
			    		urlParameters.add( new BasicNameValuePair( "Code", LogRecordToPost.getCode() ) );
			    		urlParameters.add( new BasicNameValuePair( "Message", LogRecordToPost.getMessage() ) );
			    		urlParameters.add( new BasicNameValuePair( "Data", LogRecordToPost.getData() ) );
			    		
			            if ( LogRecordToPost.getThrown() != null ) {
			    		
			                int intStackTraceCount = LogRecordToPost.getThrown().getStackTrace().length;
			            	
				    		urlParameters.add( new BasicNameValuePair( "StackTraceCount", Long.toString( LogRecordToPost.getLineNunber() ) ) );

			            	for ( int intIndexStackTrace = 0; intIndexStackTrace < intStackTraceCount; intIndexStackTrace++ ) {
			            		
			        	        String strFullSourceClassName = LogRecordToPost.getThrown().getStackTrace()[ intIndexStackTrace ].getClassName(); 

			        	        String strPackageName = strFullSourceClassName.substring( 0, strFullSourceClassName.lastIndexOf( "." ) );

			        	        String strClassName = strFullSourceClassName.substring( strFullSourceClassName.lastIndexOf( "." ) + 1 );

			        	        String strMethodName = LogRecordToPost.getThrown().getStackTrace()[ intIndexStackTrace ].getMethodName(); 
			        	        
			        	        long lngLineNumber = LogRecordToPost.getThrown().getStackTrace()[ intIndexStackTrace ].getLineNumber();
			        	        
					    		urlParameters.add( new BasicNameValuePair( "StackTraceCallPackageName" + Integer.toString( intIndexStackTrace ), strPackageName ) );
					    		urlParameters.add( new BasicNameValuePair( "StackTraceCallClassName" + Integer.toString( intIndexStackTrace ), strClassName ) );
					    		urlParameters.add( new BasicNameValuePair( "StackTraceCallMethodName" + Integer.toString( intIndexStackTrace ), strMethodName ) );
					    		urlParameters.add( new BasicNameValuePair( "StackTraceCallLine" + Integer.toString( intIndexStackTrace ), Long.toString( lngLineNumber ) ) );
			            		
			            	}
			            	
			            }
			    		
						PostData.setEntity( new UrlEncodedFormEntity( urlParameters ) );
						
						if ( Context == null )
							Response = HTTPClient.execute( PostData );
						else
							Response = HTTPClient.execute( PostData, Context );
						
						if ( Response != null ) { 

							Response.close();
							
						}
			    		
					}
					catch ( Exception Ex ) {
						
						
					}
					catch ( Error Err ) {
						
						
					}
					
				}
				else {
					
					Thread.sleep( 2000 );
					
				}
				
				bTaskRunningLock = false;
				
			}
			
		} 
		catch ( Exception Ex ) {


		}
		catch ( Error Err ) {


		}
	
	}	
	
}
