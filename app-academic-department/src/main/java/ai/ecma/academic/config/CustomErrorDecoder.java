package ai.ecma.academic.config;

import ai.ecma.academic.exceptions.RestException;
import ai.ecma.academic.payload.ApiResult;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
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
        Gson gson = new Gson();
        ApiResult apiResult = gson.fromJson(message, ApiResult.class);
        switch (response.status()) {
            default:
                return new RestException(apiResult.getErrors(), HttpStatus.valueOf(response.status()));
        }
    }


}
