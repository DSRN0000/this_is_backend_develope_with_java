package kr.co.hanbit.product.management.presentation;

import jakarta.validation.Valid;
import kr.co.hanbit.product.management.application.SimpleProductService;
import kr.co.hanbit.product.management.infrastructure.ListProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private SimpleProductService simpleProductService;

    @Autowired
    ProductController(SimpleProductService simpleProductService){  //생성자 주입
        this.simpleProductService = simpleProductService;
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto){
        // Product를 생성하고 리스트에 넣는 작업.
        return simpleProductService.add(productDto);
    }

    /**
     *상품 번호를 기준으로 조회 기능 추가하기
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ProductDto findProductById(@PathVariable Long id){
        return simpleProductService.findById(id);
    }

//    /**
//     * 전체 상품 목록 조회 기능 추가하기
//     */
//    @RequestMapping(value = "/products", method = RequestMethod.GET)
//    public List<ProductDto> findAllProduct(){
//        return simpleProductService.findAll();
//    }

    /**
     * 상품 이름에 포함된 문자열로 검색하는 기능 추가하기
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductDto> findProducts(@RequestParam(required = false) String name){
        if (null == name){
            return simpleProductService.findAll();

        }
        return simpleProductService.findBYNameContatining(name);
    }
    /**
     * 상품 수정하기
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto){
        productDto.setId(id);
        return simpleProductService.update(productDto);
    }

    /**
     * 상품 삭제하기
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable Long id){
        simpleProductService.delete(id);
    }
}
