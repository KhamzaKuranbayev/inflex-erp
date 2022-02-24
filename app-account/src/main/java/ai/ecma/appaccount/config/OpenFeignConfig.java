package ai.ecma.appaccount.config;

import feign.Feign;
import feign.RequestInterceptor;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.apache.http.entity.ContentType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class OpenFeignConfig {
    //    @Bean
//    public Contract feignContract() {
//        return new Contract.Default();
//    }
//

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }


    @Bean
    public Encoder feignSpringFormEncoder() {
        return new MyEncoder();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("user", "Aziz");
            requestTemplate.header("password", "root123");
            requestTemplate.header("Accept", ContentType.APPLICATION_JSON.getMimeType());
        };
        //securityni ishlatish
        //test controller and repository test
        //TODO:database config custom property run time show sql speed
        //
    }

    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }
}

