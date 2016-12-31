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

import java.sql.Types;
import java.util.ArrayList;

public class NamesSQLTypes {

	public final static String _NULL = "null";

	public final static String _VARCHAR = "varchar";
	public final static String _CHAR = "char";
	public final static String _INTEGER = "integer";
	public final static String _SMALLINT = "smallint";
	public final static String _BIGINT = "bigint";
	public final static String _DATE = "date";
	public final static String _TIME = "time";
	public final static String _TIMESTAMP = "timestamp";
	public final static String _BLOB = "blob";
	public final static String _FLOAT = "float";
	public final static String _NUMERIC = "numeric";
	public final static String _DECIMAL = "decimal";
	public final static String _DOUBLE = "double";
	public final static String _CURRENCY = "currency";
	public final static String _MONEY = "money";
	public final static String _BOOLEAN = "boolean";
	
	public static ArrayList<String> SQLTypes = null;
	
	static {
		
		SQLTypes = new ArrayList<String>();
		SQLTypes.add( _VARCHAR );	
		SQLTypes.add( _CHAR );	
		SQLTypes.add( _INTEGER );	
		SQLTypes.add( _SMALLINT );	
		SQLTypes.add( _BIGINT );	
		SQLTypes.add( _DATE );	
		SQLTypes.add( _TIME );	
		SQLTypes.add( _TIMESTAMP );	
		SQLTypes.add( _BLOB );	
		SQLTypes.add( _FLOAT );	
		SQLTypes.add( _NUMERIC );	
		SQLTypes.add( _DECIMAL );	
		SQLTypes.add( _CURRENCY );	
		SQLTypes.add( _MONEY );	
		SQLTypes.add( _BOOLEAN );	
		
	}
	
	public static boolean CheckJavaSQLType( String strSQLType ) {
		
		return SQLTypes.contains( strSQLType.toLowerCase() );
		
	}
	
	public static boolean IsCompatible( int intSQLType1, int intSQLType2, int Compatibles[] ) {
		
		boolean bCompatibleType1 = false;
		boolean bCompatibleType2 = false;
		
		
		for ( int intIndex = 0; intIndex < Compatibles.length; intIndex++ ) {

			if ( bCompatibleType1 == false )
				bCompatibleType1 = Compatibles[ intIndex ] == intSQLType1;

			if ( bCompatibleType1 == false )
				bCompatibleType2 = Compatibles[ intIndex ] == intSQLType2;
			
		}
		
		return bCompatibleType1 && bCompatibleType2;
		
	}
	
	public static boolean IsStringCompatible( int intSQLType1, int intSQLType2 ) {

		int Compatibles[] = { Types.VARCHAR, Types.CHAR };

		return IsCompatible( intSQLType1, intSQLType2, Compatibles );
		
	}
	
	public static boolean IsString( int intSQLType ) {
		
		return intSQLType == Types.VARCHAR || intSQLType == Types.CHAR;
		
	} 
	
	public static boolean IsIntegerCompatible( int intSQLType1, int intSQLType2 ) {
		
		int Compatibles[] = { Types.BIGINT, Types.INTEGER, Types.SMALLINT };

		return IsCompatible( intSQLType1, intSQLType2, Compatibles );
		
	}
	
	public static boolean IsFloatCompatible( int intSQLType1, int intSQLType2 ) {
		
		int Compatibles[] = { Types.FLOAT, Types.DECIMAL, Types.NUMERIC, Types.DOUBLE };

		return IsCompatible( intSQLType1, intSQLType2, Compatibles );
		
	}

	public static boolean IsDateCompatible( int intSQLType1, int intSQLType2 ) {
		
		int Compatibles[] = { Types.DATE, Types.TIME, Types.TIMESTAMP };

		return IsCompatible( intSQLType1, intSQLType2, Compatibles );
		
	}

	public static boolean IsCompatiblesSQLTypes( int intSQLType1, int intSQLType2 ) {
	
		boolean bResult = false;
		
		if ( intSQLType1 == intSQLType2 ) {
			
			bResult = true;
			
		}
		else {
			
			bResult = IsStringCompatible( intSQLType1, intSQLType2 ) || IsIntegerCompatible( intSQLType1, intSQLType2 ) || IsFloatCompatible( intSQLType1, intSQLType2 ) || IsDateCompatible( intSQLType1, intSQLType2 );
			
		}
		
		return bResult;
		
	}
	
	public static int ConvertToJavaSQLType( String strSQLName ) {
		
		int intResult = -1;
		
		if ( strSQLName.equals( NamesSQLTypes._VARCHAR ) ) {

			intResult = Types.VARCHAR;

		}
		else if ( strSQLName.equals( NamesSQLTypes._CHAR ) ) {

			intResult = Types.CHAR;

		}
		else if ( strSQLName.equals( NamesSQLTypes._INTEGER ) ) {

			intResult = Types.INTEGER;

		}
		else if ( strSQLName.equals( NamesSQLTypes._SMALLINT ) ) {

			intResult = Types.SMALLINT;

		}
		else if ( strSQLName.equals( NamesSQLTypes._BIGINT ) ) {

			intResult = Types.BIGINT;

		}
		else if ( strSQLName.equals( NamesSQLTypes._DATE ) ) {

			intResult = Types.DATE;

		}
		else if ( strSQLName.equals( NamesSQLTypes._TIME ) ) {

			intResult = Types.TIME;

		}
		else if ( strSQLName.equals( NamesSQLTypes._TIMESTAMP ) ) {

			intResult = Types.TIMESTAMP;

		}
		else if ( strSQLName.equals( NamesSQLTypes._BLOB ) ) {

			intResult = Types.BLOB;

		}
		else if ( strSQLName.equals( NamesSQLTypes._FLOAT ) || strSQLName.equals( NamesSQLTypes._CURRENCY ) || strSQLName.equals( NamesSQLTypes._MONEY ) ) {

			intResult = Types.FLOAT;

		}
		else if ( strSQLName.equals( NamesSQLTypes._NUMERIC ) ) {
			
			intResult = Types.NUMERIC;
			
		}
		else if ( strSQLName.equals( NamesSQLTypes._DECIMAL ) ) {

			intResult = Types.DECIMAL;

		}
		else if ( strSQLName.equals( NamesSQLTypes._DOUBLE ) ) {

			intResult = Types.DOUBLE;

		}
		else if ( strSQLName.equals( NamesSQLTypes._BOOLEAN ) ) {

			intResult = Types.BOOLEAN;

		}
		
		return intResult;
		
	}
	
	public static boolean IsJavaSQLType( int intSQlType ) {
		
		boolean bResult = false;
		
		switch ( intSQlType ) {
	
			case Types.INTEGER:
			case Types.BIGINT:
			case Types.SMALLINT:
			case Types.VARCHAR: 
			case Types.CHAR:  
			case Types.BOOLEAN:
			case Types.BLOB:
			case Types.DATE:
			case Types.TIME:
			case Types.TIMESTAMP:
			case Types.FLOAT: 
			case Types.DECIMAL: 
			case Types.NUMERIC:
			case Types.DOUBLE: { bResult = true; break; }

	    }
		
		return bResult;
		
	}
	
	public static String getJavaSQLTypeName( int intSQlType ) {
		
		String strResult = "";
		
		switch ( intSQlType ) {
	
			case Types.INTEGER: { strResult = NamesSQLTypes._INTEGER; break; }
			case Types.BIGINT: { strResult = NamesSQLTypes._BIGINT; break; }
			case Types.SMALLINT: { strResult = NamesSQLTypes._SMALLINT; break; }
			case Types.VARCHAR: { strResult = NamesSQLTypes._VARCHAR; break; }
			case Types.CHAR: { strResult = NamesSQLTypes._CHAR; break; } 
			case Types.BOOLEAN:  { strResult = NamesSQLTypes._BOOLEAN; break; }
			case Types.BLOB: { strResult = NamesSQLTypes._BLOB; break; }
			case Types.DATE: { strResult = NamesSQLTypes._DATE; break; }
			case Types.TIME: { strResult = NamesSQLTypes._TIME; break; }
			case Types.TIMESTAMP: { strResult = NamesSQLTypes._TIMESTAMP; break; }
			case Types.FLOAT: { strResult = NamesSQLTypes._FLOAT; break; } 
			case Types.DECIMAL: { strResult = NamesSQLTypes._DECIMAL; break; }
			case Types.DOUBLE:  { strResult = NamesSQLTypes._DOUBLE; break; }
			case Types.NUMERIC: { strResult = NamesSQLTypes._NUMERIC; break; }

	    }
		
		return strResult;
		
	}
	
}
