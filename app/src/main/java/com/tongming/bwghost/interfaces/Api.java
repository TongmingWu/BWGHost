package com.tongming.bwghost.interfaces;

/**
 * Created by Tongming on 2016/7/17.
 */
public interface Api {
    String DOMAIN = "https://api.64clouds.com/v1/";
    String VEID = "veid";
    String KEY = "api_key";

    interface Manager {
        String START = DOMAIN + "start";
        String STOP = DOMAIN + "stop";
        String RESTART = DOMAIN + "restart";
        String KILL = DOMAIN + "kill";

        String GET_MAIN_INFO = DOMAIN + "getServiceInfo";
        String GET_ALL_INFO = DOMAIN + "getLiveServiceInfo";
        String GET_AVAILABLE_OS = DOMAIN + "getAvailableOS";

        //os
        String REINSTALL_OS = DOMAIN + "reinstallOS";
        String RESET_ROOT_PASSWORD = DOMAIN + "resetRootPassword";

        @Deprecated
        String GET_USAGE_GRAPHS = DOMAIN + "getUsageGraphs";
        String GET_RAW_USAGE_STATS = DOMAIN + "getRawUsageStats";

        //newHostname
        String SET_HOSTNAME = DOMAIN + "setHostname";
        //ip,ptr
        String SET_PTR = DOMAIN + "setPTR";

        String GET_SUSPENSION_DETAILS = DOMAIN + "getSuspensionDetails";
        String UNSUSPEND = DOMAIN + "unsuspend";
        String GET_RATE_LIMIT_STATUS = DOMAIN + "getRateLimitStatus";

        //ip
        interface IPV6 {
            String ADD = DOMAIN + "ipv6/add";
            String DELETE = DOMAIN + "ipv6/delete";
        }

        interface BASIC_SHELL {
            //currentDir,newDir
            String CD = DOMAIN + "basicShell/cd";
            //command
            String EXEC = DOMAIN + "basicShell/exec";
        }

        interface BASIC_SCRIPT {
            //script
            String EXEC = DOMAIN + "shellScript/exec";
        }
    }

    interface MIGRATE {
        String GET_LOCATIONS = DOMAIN + "migrate/getLocations";
        //location
        String START = DOMAIN + "migrage/start";
        /*
        * externalServerIP
        * externalServerSSHport
        * externalServerRootPassword
        * */
        String CLONE_FROM_OHTER = DOMAIN + "cloneFromExternalServer";
    }

    interface SNAPSHOT {
        String LIST = DOMAIN + "snapshot/list";
        //description
        String CREATE = DOMAIN + "snapshot/create";
        //snapshot by filename
        String DELETE = DOMAIN + "snapshot/delete";
        String RESTORE = DOMAIN + "snapshot/restore";
        String EXPORT = DOMAIN + "snapshot/export";
        String IMPORT = DOMAIN + "snapshot/import";
        //snapshot,sticky
        String TOGGLE_STICKY = DOMAIN + "snapshot/toggleSticky";
    }

    interface PARAMS {
        String NEW_HOSTNAME = "newHostname";
        String IP = "ip";
        String PTR = "ptr";
        String OS = "os";
        String CURRENT_DIR = "currentDir";
        String NEW_DIR = "newDir";
        String COMMAND = "command";
        String SCRIPT = "script";
        String LOCATION = "location";
        String EXTERNAL_SERVER_IP = "externalServerIP";
        String EXTERNAL_SERVER_SSH_PORT = "externalServerSSHport";
        String EXTERNAL_SERVER_ROOT_PASSWORD = "externalServerRootPassword";
        String DESCRIPTION = "description";
        String SNAPSHOT = "snapshot";
        String SOURCE_VEID = "sourceVeid";
        String SOURCE_TOKEN = "sourceToken";
        String STICKY = "sticky";
    }
}
