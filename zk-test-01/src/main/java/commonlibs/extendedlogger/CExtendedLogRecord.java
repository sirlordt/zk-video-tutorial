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

//import java.util.List;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class CExtendedLogRecord extends LogRecord  {

	private static final long serialVersionUID = -7645250759981736025L;
	protected String strInstanceID;
	protected String strCode;
	protected String strSourcePackageName;
    protected long intLineNumber;
	protected String strThreadName;
	protected String strLogType;
	protected Date logDateTime;
	protected String strData;
	
	public CExtendedLogRecord( Level level, String msg ) {
		
		super( level, msg );
		
		strCode = "";
		strSourcePackageName = "";
		intLineNumber = -1;
		strThreadName = "";
		strLogType = "";
		
		logDateTime = new Date();
		
		strData = null;
		
	}

	public void setSourcePackageName( String strSourcePackageName ) {
		
		this.strSourcePackageName = strSourcePackageName;
		
	}

	public String getSourcePackageName() {
		
		return strSourcePackageName;
		
	}

	public void setInstanceID( String strInstanceID ) {
		
		this.strInstanceID = strInstanceID;
		
	}

	public String getInstanceID() {
		
		return strInstanceID;
		
	}
	
	public void setCode( String strCode ) {
		
		this.strCode = strCode;
		
	}

	public String getCode() {
		
		return strCode;
		
	}

	public void setLineNumber( long intLineNumber ) {
		
		this.intLineNumber = intLineNumber;
		
	}

	public long getLineNunber() {
		
		return intLineNumber;
		
	}
	
	public void setThreadName( String strThreadName ) {
		
		this.strThreadName = strThreadName;
		
	}

	public String getThreadName() {
		
		return strThreadName;
		
	}
	
	public void setLogType( String strLogType ) {
		
		this.strLogType = strLogType;
		
	}

	public String getLogType() {
		
		return strLogType;
		
	}
	
	public void setLogDateTime( Date logDateTime ) {
		
		this.logDateTime = logDateTime;
		
	}

	public Date getLogDateTime() {
		
		return logDateTime;
		
	}

	public void setData( String strData ) {
		
		this.strData = strData;
		
	}
	
	public String getData() {
		
		return strData;
		
	}
}
