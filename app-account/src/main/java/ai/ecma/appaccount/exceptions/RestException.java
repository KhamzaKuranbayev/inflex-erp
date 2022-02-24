package ai.ecma.appaccount.exceptions;

import ai.ecma.appaccount.payload.ErrorData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RestException extends RuntimeException {

    private String user_msg;
    private HttpStatus status;

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    private List<ErrorData> errors;

    public RestException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.status = HttpStatus.NOT_FOUND;
    }

    public RestException(String user_msg, HttpStatus status) {
        super(user_msg);
        this.user_msg = user_msg;
        this.status = status;
    }


    public RestException(List<ErrorData> errors, HttpStatus status) {
        this.errors = errors;
        this.status = status;
    }
}
