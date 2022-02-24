package ai.ecma.appaccount.config;

import ai.ecma.appaccount.exceptions.RestException;
import com.google.common.io.CharStreams;
import feign.Response;
import feign.codec.ErrorDecoder;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        String message = null;
        Reader reader = null;

        try {
            reader = response.body().asReader(StandardCharsets.UTF_8);
            //Easy way to read the stream and get a String object
            message = CharStreams.toString(reader);

        } catch (IOException e) {

            e.printStackTrace();
        } finally {

            //It is the responsibility of the caller to close the stream.
            try {

                if (reader != null)
                    reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        switch (response.status()) {
            case 400:
                return new Exception("");
            case 404:
                return new NotFoundException("not found");
            case 401:
                return new RestException("Tizimga kirishga ruxsat yo'q", HttpStatus.UNAUTHORIZED);
            case 403:
                return new RestException("Tizimga kirishga ruxsat yo'q", HttpStatus.UNAUTHORIZED);

            default:
                return new Exception("Generic error");
        }
    }


}
