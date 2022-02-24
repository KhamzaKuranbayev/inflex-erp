package ai.ecma.academic.exceptions;

import ai.ecma.academic.payload.ApiResult;
import ai.ecma.academic.payload.ErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHelper {
    /**
     * REQUEST VALIDATSIYADAN O'TA OLMAGAN HOLATDA
     *
     * @param ex
     * @return
     */
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

    /**
     * FOYDALANUVCHI TOMONIDAN XATO SODIR BO'LGANDA
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {RestException.class})
    public ResponseEntity<?> handleException(RestException ex) {
        //AGAR RESOURSE TOPILMAGANLIGI XATOSI BO'LSA CLIENTGA QAYSI TABLEDA NIMA TOPILMAGANLIGI HAQIDA XABAR QAYTADI
        if (ex.getFieldName() != null)
            return new ResponseEntity<>(new ApiResult<>(String.format("%s not found with %s : '%s'", ex.getResourceName(), ex.getFieldName(), ex.getFieldValue()), ex.getStatus().value()), ex.getStatus());
        //AKS HOLDA DOIMIY EXCEPTIONLAR ISHLAYVERADI
        if (ex.getErrors() != null)
            return new ResponseEntity<>(new ApiResult<>(ex.getErrors()), ex.getStatus());
        return new ResponseEntity<>(new ApiResult<>(ex.getMessage(), ex.getStatus().value()), ex.getStatus());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleServerException(Exception ex) {
        ex.printStackTrace();
        //AGAR RESOURSE TOPILMAGANLIGI XATOSI BO'LSA CLIENTGA QAYSI TABLEDA NIMA TOPILMAGANLIGI HAQIDA XABAR QAYTADI
        return new ResponseEntity<>(new ApiResult<>("Server bilan muammo",500), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
