package ai.ecma.appauth.exceptions;

import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.ErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHelper {
    // REQUEST VALIDATSIYADAN O'TA OLMAGAN HOLATDA

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleException(MethodArgumentNotValidException ex) {
        List<ErrorData> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(new ErrorData(errorMessage, HttpStatus.BAD_REQUEST.value()));
        });
        return new ResponseEntity<>(new ApiResult<>(errors), HttpStatus.BAD_REQUEST);
    }

    // FOYDALANUVCHI TOMONIDAN XATO SODIR BO'LGANDA
    @ExceptionHandler(value = {RestException.class})
    public ResponseEntity<?> handleException(RestException ex) {
        return new ResponseEntity<>(
                new ApiResult<>(ex.getUserMsg(), ex.getStatus().value()),
                ex.getStatus());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                new ApiResult<>("Serverga bog'liq xatolik. Iltimos qayta urinib ko'ring", 500),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(
                new ApiResult<>("So'rov body qismini unutdingiz", 400),
                HttpStatus.BAD_REQUEST);
    }
}
