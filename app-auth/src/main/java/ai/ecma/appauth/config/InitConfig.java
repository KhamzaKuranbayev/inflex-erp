package ai.ecma.appauth.config;

import org.springframework.core.io.ClassPathResource;

import java.util.Properties;
import java.util.Stack;

public class InitConfig {

    public static boolean isStart() {
        Properties props = new Properties();
        try {
            props.load(new ClassPathResource("/application-prod.properties").getInputStream());
            if (props.getProperty("spring.jpa.hibernate.ddl-auto").equals("update")
                    && props.getProperty("sprin" +
                    "g.datasource.initialization-mode").equals("never")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
