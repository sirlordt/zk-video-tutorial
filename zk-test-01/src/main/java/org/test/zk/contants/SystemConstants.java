package org.test.zk.contants;

import java.io.File;

public class SystemConstants {
 
    //Declaración de todas las contantes usadas en el sistema
    
    public static final String _Libs_Dir = "libs" + File.separator;
    public static final String _Lib_Ext = ".jar";

    public static final String _Langs_Dir = "langs" + File.separator;
    public static final String _Lang_Ext = "init.lang";
    public static final String _Common_Lang_File = "Common.init.lang";

    public static final String _Logs_Dir = "logs" + File.separator;

    public static final String _Temp_Dir = "temp" + File.separator;

    public static final String _Security_Dir = "security" + File.separator;

    public static final String _System_Dir = "system" + File.separator;

    public static final String _WEB_INF_Dir = "WEB-INF"; // + File.separator; No funciona bien en windows con el \ al final

    public static final String _Config_Dir = "config" + File.separator;

    public static final String _Database_Connection_Config_File_Name = "database.config.xml";

    public static final String _Database_Connection_Production_Config_File_Name = "database.production.config.xml";
    
    public static final String _Logger_Config_File_Name = "logger.config.xml";

    public static final String _Logger_Config_Production_File_Name = "logger.production.config.xml";

    public static final String _User_Unknown = "unknown@unknown.com";

    public static final String _DB_Connection_Session_Key = "dbConnection";
    public static final String _User_Credential_Session_Key = "userCredential";
    public static final String _Login_Date_Time_Session_Key = "loginDateTime";
    public static final String _Log_Path_Session_Key = "logPath";
    
    public static final String _Webapp_Logger_App_Attribute_Key = "webAppLogger";
    public static final String _Webapp_Logger_Name = "webapplogger";
    public static final String _Webapp_Logger_File_Log = "webapplogger.log";
    
    
    
}
