package ai.ecma.academic.utils;

import java.util.ArrayList;
import java.util.Arrays;

public interface AppConstants {
    String requestAttributeCurrentUser = "user";

    String auth = "http://localhost:81/api/auth";

    String basePath = "/api/academic/";

    ArrayList<String> corsOrigins =new ArrayList<>(Arrays.asList(
            "http://localhost:80",
            "http://localhost:81",
//            "http://localhost:82",
            "http://localhost:83",
            "http://localhost:84"
    ));

}
