package ai.ecma.appaccount.utils;

import java.util.ArrayList;
import java.util.Arrays;

public interface RestConstants {

    //REQUESTGA CURRENT USERNI OLISH VA SET QILISH UCHUN KEY NOMI
    String REQUEST_ATTRIBUTE_CURRENT_USER = "user";

    String GATEWAY_URL = "http://localhost:8085";
    String AUTH_URL = "http://localhost:81";
    String AUTH_SERVICE = AUTH_URL + "/api/auth";
    String ACADEMIC_URL = "http://localhost:82";
    String ACADEMIC_SERVICE = ACADEMIC_URL + "/api/academic";
    String SALES_URL = "http://localhost:84";
    String SALES_SERVICE = SALES_URL + "/api/sales";
    String SWAGGER_GATEWAY="http://localhost:8083";

    String BASE_PATH = "/api/account/";
    String LOCALHOST_SECOND ="0:0:0:0:0:0:0:1";

    ArrayList<String> corsOrigins = new ArrayList<>(Arrays.asList(
            GATEWAY_URL,
            AUTH_URL,
            ACADEMIC_URL,
            SALES_URL,
            LOCALHOST_SECOND,
            SWAGGER_GATEWAY
    ));
}
