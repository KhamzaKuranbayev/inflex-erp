package ai.ecma.appauth.utils;

import ai.ecma.appauth.controller.AuthController;
import springfox.documentation.service.Tag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface RestConstants {


    Tag SWAGGER_AUTH_TAG = new Tag("Authentication", "Authentication jarayonlari uchun", 0);
    Tag[] SWAGGER_TAGS = {
            new Tag("Module", "Module jarayonlari uchun", 1),
            new Tag("Department", "Department API", 2),
    };

    String[] OPEN_PAGES = {
            "/api/click/*",
            "/ws/*",
            AuthController.AUTH_CONTROLLER_PATH + "check-phone",
            "/v2/api-docs",
    };

    Set<String> OPEN_PAGES_SET = new HashSet<>(Arrays.asList(OPEN_PAGES));

    String AUTHENTICATION_HEADER = "Authorization";

    String LANGUAGE_HEADER = "language";

    String BASE_PATH_V1 = "/api/auth/v1/";

    String INITIAL_EXECUTING_QUERY = "DROP INDEX if exists uk_role_role_enum;" +
            "CREATE UNIQUE INDEX uk_role_role_enum ON role (role_type) WHERE role_type<>'OTHER';" +
            "DROP INDEX if exists uk_user_phone_number_enabled_true;\n" +
            "CREATE UNIQUE INDEX uk_user_phone_number_enabled_true ON users (phone_number) WHERE enabled;";

    //MAX SMS YUBORISHLAR SONI
    Integer LIMIT_SMS_CODE = 5;

    //SMS YUBORISHGA CHEKLOV SOATI (EX: 24 SOATDA 5 TA SMS MUMKIN)
    Integer LIMIT_SMS_CODE_DURATION_HOUR = 24;

    Long JWT_EXPIRED_TIME_FOR_ACCESS_TOKEN = 43_200_000L;

    Long JWT_EXPIRED_TIME_FOR_REFRESH_TOKEN = 604_800_000L;

    String TOKEN_TYPE = "Bearer ";

    Integer USER_QUESTION_MUST_COUNT = 3;
}
