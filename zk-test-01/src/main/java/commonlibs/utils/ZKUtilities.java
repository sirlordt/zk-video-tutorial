package commonlibs.utils;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Textbox;

import commonlibs.extendedlogger.CExtendedLogger;

public class ZKUtilities {

	public static String getTextBoxValue( Textbox edTextBox, CExtendedLogger localLogger ) {
		
		String strResult = "";
		
		try {
			
			strResult = edTextBox.getValue();
			
		}
		catch ( Exception Ex ) {
			
			if ( localLogger != null )
				localLogger.logException( "-2021" , Ex.getMessage(), Ex );
			
		}
		
		return strResult;		
		
	}

	public static boolean setTextBoxValue( Textbox edTextBox, String strValue, CExtendedLogger localLogger ) {
		
		boolean bResult = false;
		
		try {
			
			edTextBox.setValue( strValue );
			
			bResult = true;
			
		}
		catch ( Exception Ex ) {
			
			if ( localLogger != null )
				localLogger.logException( "-2021" , Ex.getMessage(), Ex );
			
		}
		
		return bResult;		
		
	}
	
	public static Component getComponent( Component components[], String strClassName  ) {
	    
	    Component Result = null;
	    
	    for ( int intIndex=0; intIndex < components.length; intIndex++ ) {
	        
	        String strCurrentClassName = components[ intIndex ].getClass().getSimpleName();
	        
	        if ( strCurrentClassName.equalsIgnoreCase( strClassName ) ) {
	            
	            Result = components[ intIndex ];
	            break;
	            
	        }
	        
	    }
	    
	    return Result;
	    
	}
	
}
