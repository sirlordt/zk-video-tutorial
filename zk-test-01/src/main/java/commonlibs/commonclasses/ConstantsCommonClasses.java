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
import java.sql.Types;

public interface ConstantsCommonClasses {

    public static final String _Libs_Dir = "libs" + File.separator;
    public static final String _Lib_Ext = ".jar";

    public static final String _Langs_Dir = "langs" + File.separator;
    public static final String _Lang_Ext = "init.lang";
    public static final String _Common_Lang_File = "Common.init.lang";

    public static final String _Logs_Dir = "logs" + File.separator;

    public static final String _Temp_Dir = "temp" + File.separator;

    public static final String _Security_Dir = "security" + File.separator;

    public static final String _System_Dir = "system" + File.separator;

    public static final String _WEB_INF_Dir = "WEB-INF" + File.separator;

    public static final String _Config_Dir = "config" + File.separator;
    
    public static final double _Main_Office_Latitude = 25.693404;
    
    public static final double _Main_Office_Longitude = -80.434688;

    public static final String _Database_Connection_Config_File_Name = "database.config.xml";

    public static final String _Database_Connection_Production_Config_File_Name = "database.production.config.xml";
    
    public static final String _Logger_Config_File_Name = "logger.config.xml";

    public static final String _Logger_Config_Production_File_Name = "logger.production.config.xml";
    
    public static final String _User_Unknown = "unknown@unknown.com";

    public static final String _DB_Connection_Session_Key = "dbConnection";
    public static final String _User_Credential_Session_Key = "userCredential";
    public static final String _Login_Date_Time_Session_Key = "loginDateTime";
    public static final String _Log_Path_Session_Key = "logPath";

    public static final String _Establishment_Filter_List_Session_Key = "establishmentFilterList";
    
    public static final String _Logged_Session_Loggers = "loggedSessionLoggers";

    public static final String _Config_Logger_Session_Key = "configLogger";

    public static final String _Check_Logged_Logger_Name = "checklogged";
    // public static final String _Check_Logged_Instance = "checklogged";
    public static final String _Check_Logged_File_Log = "checklogged.log";

    public static final String _Webapp_Logger_App_Attribute_Key = "webAppLogger";
    public static final String _Webapp_Logger_Name = "webapplogger";
    public static final String _Webapp_Logger_File_Log = "webapplogger.log";

    public static final String _Logger_Name = _Webapp_Logger_Name;

    public static final String _Login_Controller_Logger_Name = "logincontroller";
    public static final String _Login_Controller_Instance = "logincontroller";
    public static final String _Login_Controller_File_Log = "logincontroller.log";

    public static final String _Logout_Controller_Session_Key = "logoutController";
    public static final String _Logout_Controller_Logger_Name = "logoutcontroller";
    public static final String _Logout_Controller_File_Log = "logoutcontroller.log";

    public static final String _Home_Controller_Logger_Name = "homecontroller";
    public static final String _Home_Controller_File_Log = "homecontroller.log";

    public static final String _SuperiorMenu_Controller_Logger_Name = "superiormenucontroller";
    public static final String _SuperiorMenu_Controller_File_Log = "superiormenucontroller.log";

    public static final String _ChangePassword_Controller_Logger_Name = "changepasswordcontroller";
    public static final String _ChangePassword_Controller_File_Log = "changepasswordcontroller.log";

    public static final String _HomeAuthenticated_Controller_Logger_Name = "homeauthenticatedcontroller";
    public static final String _HomeAuthenticated_Controller_File_Log = "homeauthenticatedcontroller.log";

    public static final String _SidebarRestaurant_Controller_Logger_Name = "sidebarrestaurantcontroller";
    public static final String _SidebarRestaurant_Controller_File_Log = "sidebarrestaurantcontroller.log";

    public static final String _SelectAddressesDialog_Controller_Logger_Name = "selectaddressesdialogcontroller";
    public static final String _SelectAddressesDialog_Controller_File_Log = "selectaddressesdialogcontroller.log";

    public static final String _OrderDialog_Controller_Logger_Name = "orderdialogcontroller";
    public static final String _OrderDialog_Controller_File_Log = "orderdialogcontroller.log";

    public static final String _OrderDetailsDialog_Controller_Logger_Name = "orderdetailsdialogcontroller";
    public static final String _OrderDetailsDialog_Controller_File_Log = "orderdetailsdialogcontroller.log";

    public static final String _ProfileDialog_Controller_Logger_Name = "profiledialogcontroller";
    public static final String _ProfileDialog_Controller_File_Log = "profiledialogcontroller.log";

    public static final String _UsersAdmin_Controller_Logger_Name = "usersadmincontroller";
    public static final String _UsersAdmin_Controller_File_Log = "usersadmincontroller.log";

    public static final String _OrdersAdmin_Controller_Logger_Name = "ordersadmincontroller";
    public static final String _OrdersAdmin_Controller_File_Log = "ordersadmincontroller.log";

    public static final String _LocationsAdmin_Controller_Logger_Name = "locationsadmincontroller";
    public static final String _LocationsAdmin_Controller_File_Log = "locationsadmincontroller.log";

    public static final String _DeliveriesMapAdmin_Controller_Logger_Name = "deliveriesmapadmincontroller";
    public static final String _DeliveriesMapAdmin_Controller_File_Log = "deliveriesmapadmincontroller.log";
    
    public static final String _UserDialog_Controller_Logger_Name = "userdialogcontroller";
    public static final String _UserDialog_Controller_File_Log = "userdialogcontroller.log";

    public static final String _OrderAdminDialog_Controller_Logger_Name = "orderadmindialogcontroller";
    public static final String _OrderAdminDialog_Controller_File_Log = "orderadmindialogcontroller.log";

    public static final String _SelectDriverDialog_Controller_Logger_Name = "selectdriverdialogcontroller";
    public static final String _SelectDriverDialog_Controller_File_Log = "selectdriverdialogcontroller.log";

    public static final String _SelectReportDialog_Controller_Logger_Name = "selectreportdialogcontroller";
    public static final String _SelectReportDialog_Controller_File_Log = "selectreportdialogcontroller.log";

    public static final String _AuthorizedDialog_Controller_Logger_Name = "authorizeddialogcontroller";
    public static final String _AuthorizedDialog_Controller_File_Log = "authorizeddialogcontroller.log";
    
    public static final String _SelectEstablishmentDialog_Controller_Logger_Name = "selectestablishmentdialogcontroller";
    public static final String _SelectEstablishmentDialog_Controller_File_Log = "selectestablishmentdialogcontroller.log";
    
    public static final String _Map_Controller_Logger_Name = "mapcontroller";
    public static final String _Map_Controller_File_Log = "mapcontroller.log";

    public static final String _LocationAdminDialog_Controller_Logger_Name = "locationadmindialogcontroller";
    public static final String _LocationAdminDialog_Controller_File_Log = "locationadmindialogcontroller.log";

    public static final String _AssociatedOrdersDetails_Controller_Logger_Name = "associatedordersdetailscontroller";
    public static final String _AssociatedOrdersDetails_Controller_File_Log = "associatedordersdetailscontroller.log";
    
    public static final String _Desktop_Info_Session_Key = "desktopInfo";

    public static final String _Log_Class_Method = "*.*";
    public static final boolean _Log_Exact_Match = false;
    public static final boolean _Log_Missing_Translations = false;
    public static final String _Logger_Name_Missing_Translations = "MissingTranslations";
    public static final String _Missing_Translations_File_Log = _Logger_Name_Missing_Translations
            + ".log";
    public static final String _Log_Level = "ALL";

    public static final String _BorderLayout_NorthContent_Session_Key = "borderLayoutNorthContent";
    public static final String _BorderLayout_WestContent_Session_Key = "borderLayoutWestContent";
    public static final String _BorderLayout_MainContent_Session_Key = "borderLayoutMainContent";
    public static final String _BorderLayout_SouthContent_Session_Key = "borderLayoutSouthContent";
    public static final String _BorderLayout_EastContent_Session_Key = "borderLayoutEastContent";

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

    public final static String _Request_SecurityTokenID = "SecurityTokenID";
    public final static String _Request_SecurityTokenID_Type = "Bigint";
    public final static String _Request_SecurityTokenID_Length = "0";
    public final static String _Request_TransactionID = "TransactionID";
    public final static String _Request_TransactionID_Type = "Bigint";
    public final static String _Request_TransactionID_Length = "0";
    public final static String _Request_ServiceName = "ServiceName";
    public final static String _Request_ServiceName_Type = "Varchar";
    public final static String _Request_ServiceName_Length = "255";
    public final static String _Request_ResponseFormat = "ResponseFormat";
    public final static String _Request_ResponseFormat_Type = "Varchar";
    public final static String _Request_ResponseFormat_Length = "75";
    public final static String _Request_ResponseFormatVersion = "ResponseFormatVersion";
    public final static String _Request_ResponseFormatVersion_Type = "Varchar";
    public final static String _Request_ResponseFormatVersion_Length = "15";

    public final static String _SessionSecurityTokens = "Security.Tokens";

    // public final static String _SessionKey = "Session.Key";

    public final static String _Default = "default";

    public final static String _SecurityTokenID = "SecurityTokenID";
    public final static String _SecurityTokenID_Type = NamesSQLTypes._BIGINT;
    public final static int _SecurityTokenID_TypeID = Types.BIGINT;
    public final static String _TransactionID = "TransactionID";
    public final static String _TransactionID_Type = NamesSQLTypes._BIGINT;
    public final static int _TransactionID_TypeID = Types.BIGINT;
    public final static String _Code = "Code";
    public final static String _Code_Type = NamesSQLTypes._INTEGER;
    public final static int _Code_TypeID = Types.INTEGER;
    public final static String _Description = "Description";
    public final static String _Description_Type = NamesSQLTypes._VARCHAR;
    public final static int _Description_TypeID = Types.VARCHAR;
    public final static int _Description_Length = 255;

    public static final String _Header_Name_User_Agent = "User-Agent";
    
    public static final String _Authorized_To_Close_Order = "AuthorizedToCloseOrder";
    public static final String _Authorized_To_Manipulate_Old_Order = "AuthorizedToManipulateOldOrder";
    
}
