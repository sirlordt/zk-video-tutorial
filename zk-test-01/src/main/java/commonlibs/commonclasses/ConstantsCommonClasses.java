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

import java.io.File;

public interface ConstantsCommonClasses {

    public static final String _Langs_Dir = "langs" + File.separator;
    public static final String _Logger_Name = "webapplogger";
    
    public static final int _Min_Port_Number = 1;
    public static final int _Max_Port_Number = 65535;

    public static final int _Min_Idle_Time = 100;
    public static final int _Max_Idle_Time = 30000;
    public static final int _Min_Request_Header_Size = 1024;
    public static final int _Max_Request_Header_Size = 8192;

    public static final String _Log_IP = "";
    public static final int _Log_Port_Number = 30000;

    public static final String _HTTP_Log_URL = "";
    public static final String _HTTP_Log_User = "";
    public static final String _HTTP_Log_Password = "";
    public static final String _Proxy_IP = "";
    public static final int _Proxy_Port = -1;
    public static final String _Proxy_User = "";
    public static final String _Proxy_Password = "";

    public static final String _Crypt_Algorithm = "DES";
    public static final String _Hash_Algorithm = "SHA512";

    public static final String _Global_Date_Time_Format_24 = "dd/MM/yyyy HH:mm:ss";
    public static final String _Global_Date_Time_Format_12 = "dd/MM/yyyy hh:mm:ss a";
    public static final String _Global_Date_Time_Format_File_System_24 = "yyyy-MM-dd-HH-mm-ss";
    public static final String _Global_Date_Time_Format_File_System_12 = "yyyy-MM-dd-hh-mm-ss-a";
    public static final String _Global_Date_Format = "dd/MM/yyyy";
    public static final String _Global_Date_Format_File_System = "dd-MM-yyyy";
    public static final String _Global_Time_Format_24 = "HH:mm:ss";
    public static final String _Global_Time_Format_12 = "hh:mm:ss a";
    public static final String _Global_Time_Format_File_System_24 = "HH-mm-ss";
    public static final String _Global_Time_Format_File_System_12 = "hh-mm-ss a";

    public static final String _Chaset_XML = "UTF-8";
    public static final String _Content_Type_XML = "text/xml";

    public static final String _Chaset_JSON = "UTF-8";
    public static final String _Content_Type_JSON = "text/json";

    public static final String _Chaset_CSV = "UTF-8";
    public static final String _Content_Type_CSV = "text/plain";
    public static final boolean _Fields_Quote_CSV = true;
    public static final String _Separator_Symbol_CSV = ";";
    public static final boolean _Show_Headers_CSV = true;

    public static final String _Header_Name_User_Agent = "User-Agent";
    
}
