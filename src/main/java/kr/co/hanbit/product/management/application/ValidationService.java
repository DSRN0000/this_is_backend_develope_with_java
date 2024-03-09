package kr.co.hanbit.product.management.application;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated // @Valid가 붙은 매개변수를 유효성 검사한다.
public class ValidationService {
    /**
     *
     * <T> : 제네릭 사용. 해당 메서드 내에서 T라는 이름의 제네릭을 사용하겠다는 선언. T에는 어떤 타입이든 올 수 있다는 것을 의미한다.
     *     : 매개변수 쪽에서는 T를 매개변수의 타입으로 지정 해 줬다. 결국, 해당 메서드의 매개변수로는 어떤 타입이든 올 수 있다.
     */
    public <T> void checkValid(@Valid T validationTarget){
        // do nothing
    }
}
