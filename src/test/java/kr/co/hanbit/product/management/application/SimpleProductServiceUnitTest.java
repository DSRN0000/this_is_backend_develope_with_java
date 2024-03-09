package kr.co.hanbit.product.management.application;

import jakarta.validation.constraints.Max;
import kr.co.hanbit.product.management.domain.Product;
import kr.co.hanbit.product.management.domain.ProductRepository;
import kr.co.hanbit.product.management.presentation.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * 단위 테스트 : 다른 클래스를 사용하지 않고 작동하는 테스트
 * => 개발 중인 로직만 간결하고 빠르게 테스트하기 위해 실행한다.
 *
 * 통합 테스트 : '내가 개발 중인 코드' 외에도 '내가 개발 중인 코드가 의존하고 있는 코드'까지 대상으로 실행.
 * => ex) SimpleProductService와 ProductRepository의 두 구현체 사용하여 '상품 추가 후 상품 조회'를 테스트
 */

/**
 * 단위테스트에서는 @SpringBootTest 대신 @ExtendWith(MockitoExtension.class) 사용
 *  @SpringBootTest -> 스프링 부트 애플리케이션 시작되면서 필요한 의존성들을 빈으로 등록하고 주입하는 과정을 거친다.
 *  @ExtendWith(MockitoExtension.class) -> 스프링 부트를 애플리케이션을 실행시키지 않고도 테스트 코드를 실행시킬 수 있다.
 */

@ExtendWith(MockitoExtension.class)
public class SimpleProductServiceUnitTest {
    /**
     * 모킹 사용해보기 (Mockito). Mockito로 단위 테스트 코드 작성하기
     * ProductRepository 구현체 없이도 SimpleProductService를 테스트할 수 있도록 도와준다.
     * Mock은 '모조품' 혹은 '모의'라는 의미를 가지고 있어 '목 객체'를 '모의 객체'라고도 표현한다.
     */

    @Mock //해당 의존성에 '목 객체'를 주입한다는 의미
    private ProductRepository productRepository;

    @Mock //해당 의존성에 '목 객체'를 주입한다는 의미
    private ValidationService validationService;

    @InjectMocks //위에서 @Mock으로 주입해 준 목 객체들을 SimpleProductService 내에 있는 의존성에 주입해 주는 역할을 한다.
    private SimpleProductService simpleProductService;

    @Test
    @DisplayName("상품 추가 후에는 추가된 상품이 반환되어야 한다.")
    void productAddTest(){
        ProductDto productDto = new ProductDto("연필", 300, 20);
        Long PRODUCT_ID = 1L;

        Product product = ProductDto.toEntity(productDto);
        product.setId(PRODUCT_ID);
        when(productRepository.add(any())).thenReturn(product); //목 객체가 어떻게 행동해야 하는지를 정의한다.

        ProductDto savedProductDto = simpleProductService.add(productDto);

        assertTrue(savedProductDto.getId().equals(PRODUCT_ID));
        assertTrue(savedProductDto.getName().equals(productDto.getName()));
        assertTrue(savedProductDto.getPrice().equals(productDto.getPrice()));
        assertTrue(savedProductDto.getAmount().equals(productDto.getAmount()));
    }
}
