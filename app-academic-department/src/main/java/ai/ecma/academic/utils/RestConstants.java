package ai.ecma.academic.utils;

import java.util.ArrayList;
import java.util.Arrays;

public interface RestConstants {

    //REQUESTGA CURRENT USERNI OLISH VA SET QILISH UCHUN KEY NOMI
    String REQUEST_ATTRIBUTE_CURRENT_USER = "user";
    String BASE_HTTP = "http://";

    String GATEWAY_URL_IP = "127.0.0.1";
    String GATEWAY_URL = BASE_HTTP + GATEWAY_URL_IP + ":80";
    String AUTH_URL_IP = "127.0.0.1";
    String AUTH_URL = BASE_HTTP + AUTH_URL_IP + ":81";
    String AUTH_SERVICE = AUTH_URL + "/api/auth";
    String ACCOUNT_URL_IP = "127.0.0.1";
    String ACCOUNT_URL = BASE_HTTP + ACCOUNT_URL_IP + ":83";
    String ACCOUNT_SERVICE = ACCOUNT_URL + "/api/account";
    String SALES_URL_IP = "127.0.0.1";
    String SALES_URL = BASE_HTTP + SALES_URL_IP + ":84";
    String SALES_SERVICE = SALES_URL + "/api/sales";
    String LOCALHOST_SECOND ="0:0:0:0:0:0:0:1";

    String BASE_PATH = "/api/academic/";

    ArrayList<String> corsOrigins = new ArrayList<>(Arrays.asList(
            GATEWAY_URL_IP,
            AUTH_URL_IP,
            ACCOUNT_URL_IP,
            SALES_URL_IP,
            LOCALHOST_SECOND
    ));
}
