package kr.co.hanbit.product.management.presentation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import kr.co.hanbit.product.management.domain.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 예외처리 핸들러는 예외가 컨트롤러에서도 처리되지 않고 던져졌을 때 알아서 호출되는 것이다.
     * 정해진 틀에 따라 코드를 작성하면 된다.
     */

//    @ExceptionHandler(ConstraintViolationException.class) //'에러 메세지'라는 문자열을 제외하고 유용한 정보가 담겨 있지 않다.
//    public ResponseEntity<String> handleConstraintViolatedException(ConstraintViolationException ex){
//        //예외에 대한 처리
//        String errorMessage = "에러 메세지";
//        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
//    }

    /**
     * ConstraintViolationException 예외에 대한 핸들링 코드
     * 도메인 객체에 대한 유효성을 검증 => ex) 값 범위 맞지 않음
     */
    @ExceptionHandler(ConstraintViolationException.class) //클라이언트에게 유의미한 응답 바디를 제공하기.
    public ResponseEntity<ErrorMessage> handleConstraintViolatedException(ConstraintViolationException ex){
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<String> errors = constraintViolations.stream().map(constraintViolation -> extractField(constraintViolation.getPropertyPath()) + ", " + constraintViolation.getMessage()).toList();
        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * 도메인 객체에 대한 유효성을 검증하는 ConstraintViolationException 예외처리 핸들러에서는
     * 'checkValid.validationTarget.amount' 처럼 사용자가 보낸 필드 자체가 아니라 validationService에서 사용하는 메서드와 매개 변수 이름이 함께 노출된다.
     * 따라서 아래와 같은 코드 일관성 있게 맞퉈주는 코드 작성
     */
    private String extractField(Path path) {
        String[] splittedArray = path.toString().split("[.]"); //split 사용하여 .을 기준으로 나눈다. => checkValid.validationTarget.amount
        int lastIndex = splittedArray.length - 1; // 그 후 가장 마지막 요소만 사용. => amount
        return splittedArray[lastIndex];
    }


    /**
     * MethodAgumentNotValidException 예외 처리 코드
     * 컨트롤러에서 유효성 검증 => ex) 3가지 요소 필요하지만 요소의 개수 부족
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream().map(fieldError -> fieldError.getField() + ", " + fieldError.getDefaultMessage()).toList();
        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * EntityNotFoundException에 대한 예외 처리 핸들러
     * URL의 경로에 존재하는 id를 가진 product가 없으면 404 Not Found를 응답 상태 코드로 준다.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleEntityNotFoundExceptionException(EntityNotFoundException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
