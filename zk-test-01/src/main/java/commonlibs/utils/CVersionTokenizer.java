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

//VersionTokenizer.java
public class CVersionTokenizer {
	
	protected final String strVersionString;
	protected final int intLength;

	protected int intPosition;
	protected int intNumber;
	protected String strSuffix;
	protected boolean bHasValue;

	public int getNumber() {
		
		return intNumber;
		
	}

	public String getSuffix() {
		
		return strSuffix;
		
	}

	public CVersionTokenizer(String strVersionString) {
		
		if ( strVersionString == null )
			throw new IllegalArgumentException("strVersionString is null");

		this.strVersionString = strVersionString;
		this.intLength = strVersionString.length();
		
	}

	public boolean MoveNext() {
		
		intNumber = 0;
		strSuffix = "";
		bHasValue = false;

		// No more characters
		if ( intPosition >= intLength )
			return false;

		bHasValue = true;

		while ( intPosition < intLength ) {
			
			char c = strVersionString.charAt( intPosition );
			
			if ( c < '0' || c > '9' )
				break;
			
			int intTmp = ( c - '0' );
			
			intNumber = intNumber * 10 + intTmp; //Very simple accumulator
			
			intPosition++;
			
		}

		int intSuffixStart = intPosition;

		while ( intPosition < intLength ) {
			
			char c = strVersionString.charAt( intPosition );
			
			if ( c == '.' )
				break;
			
			intPosition++;
			
		}

		strSuffix = strVersionString.substring( intSuffixStart, intPosition );

		if ( intPosition < intLength )
			intPosition++;

		return true;
		
	}
}
