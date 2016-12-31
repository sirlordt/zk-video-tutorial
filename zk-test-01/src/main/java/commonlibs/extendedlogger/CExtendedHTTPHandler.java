package commonlibs.extendedlogger;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class CExtendedHTTPHandler extends Handler {

	protected String strLoggerName = ""; 
	protected String strHTTPLogURL = "";
	protected String strHTTPLogUser = "";
	protected String strHTTPLogPassword = "";
	protected String strProxyIP = "";
	protected int intProxyPort = -1;
	protected String strProxyUser = "";
	protected String strProxyPassword = "";
	
	CHTTPPostThreadWorker HTTPPostThreadWorker = null;
	
	public CExtendedHTTPHandler( String strLoggerName ) {
		
		this.strLoggerName = strLoggerName;
		
	}
	
	public boolean configureHandler( String strHTTPLogURL, String strHTTPLogUser, String strHTTPLogPassword, String strProxyIP, int intProxyPort, String strProxyUser, String strProxyPassword ) {
		
		boolean bResult = true;
		
		this.strHTTPLogURL = strHTTPLogURL;
		this.strHTTPLogUser = strHTTPLogUser;
		this.strHTTPLogPassword = strHTTPLogPassword;
		this.strProxyIP = strProxyIP;
		this.intProxyPort = intProxyPort;
		this.strProxyUser = strProxyUser;
		this.strProxyPassword = strProxyPassword;
		
		if ( HTTPPostThreadWorker != null ) {
		
			HTTPPostThreadWorker.bStopNow = true;
			
			try {
				
				HTTPPostThreadWorker.join();
			
			} 
			catch ( Exception Ex ) {

			}
			
		}	
		else {
		
			HTTPPostThreadWorker = new CHTTPPostThreadWorker();
		
		}
		
		if ( HTTPPostThreadWorker.configureHTTPPost( strHTTPLogURL, strHTTPLogUser, strHTTPLogPassword, strProxyIP, intProxyPort, strProxyUser, strProxyPassword ) == true ) {
			
			HTTPPostThreadWorker.setName( "HTTPPostThreadWorker-" + strLoggerName );
			
			HTTPPostThreadWorker.start();
			
		}	
		else {	
			
			HTTPPostThreadWorker = null;
			
			bResult = false;
			
		}
		
		return bResult;
		
	}
	
	@Override
	public void publish(LogRecord record) {

		if ( HTTPPostThreadWorker != null && record instanceof CExtendedLogRecord ) {
			
			HTTPPostThreadWorker.addToHTTPPostQueue( (CExtendedLogRecord) record ); 
			
		}
		
	}

	@Override
	public void flush() {
		
	}

	@Override
	public void close() throws SecurityException {

		if ( HTTPPostThreadWorker != null ) {
			
			HTTPPostThreadWorker.setStopNow();
			
			try {
				HTTPPostThreadWorker.join();
			} 
			catch ( Exception Ex ) {

			}
			
		}
		
	}

}
