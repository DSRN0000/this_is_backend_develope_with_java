package kr.co.hanbit.product.management.application;

import kr.co.hanbit.product.management.domain.EntityNotFoundException;
import kr.co.hanbit.product.management.presentation.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
//@ActiveProfiles("prod")
class SimpleProductServiceTest {

    @Autowired
    SimpleProductService simpleProductService;

    /**
     *  @Transactional -> 테스트코드에서 실행된 데이터가 데이터베이스에 반영되지 않도록 만든다.
     *  트랜잭션 : 작업이 모두 실행되거나 모두 실행되지 않은 상태로 만드는 것.
     *  @Transactional 사용하면 해당 테스트 코드는 코드 실행 후 '커밋'되는 것이 아니라 자동으로 '롤백'된다.
     * => 따라서 테스트 코드에서 추가한 데이터가 실제로는 데이터베이스에 반영되지 않도록 만들어 준다.
     */

    //@Transactional //@ActiveProfiles("test")로 실행할때는 주석처리
    @Test
    @DisplayName("상품을 추가한 후 id로 조회하면 해당 상품이 조회되어야 한다.")
    void productAddAndFindByIdTest(){
        ProductDto productDto = new ProductDto("연필", 300, 20);

        ProductDto savedProductDto = simpleProductService.add(productDto);
        Long savedProductId = savedProductDto.getId();

        ProductDto foundProductDto = simpleProductService.findById(savedProductId);

//        System.out.println(savedProductDto.getId() == foundProductDto.getId());
//        System.out.println(savedProductDto.getName() == foundProductDto.getName());
//        System.out.println(savedProductDto.getPrice() == foundProductDto.getPrice());
//        System.out.println(savedProductDto.getAmount() == foundProductDto.getAmount());

        /**
         * assertTrue => 참이면 녹색불. 테스트 통과, 거짓이면 빨간불. 테스트 실패
         */

        assertTrue(savedProductDto.getId().equals(foundProductDto.getId()));
        assertTrue(savedProductDto.getName().equals(foundProductDto.getName()));
        assertTrue(savedProductDto.getPrice().equals(foundProductDto.getPrice()));
        assertTrue(savedProductDto.getAmount().equals(foundProductDto.getAmount()));
    }

    @Test
    @DisplayName("존재하지 않는 상품 id로 조회하면 EntityNotFoundException이 발생해야 한다.")
    void findProductNotExistIdTest(){
        Long notExistId = -1L;

        assertThrows(EntityNotFoundException.class, () -> { //assertThrows는 예외발생을 테스트한다. 예외가 발생하면 테스트 성공.
            simpleProductService.findById(notExistId);
        });
    }

}