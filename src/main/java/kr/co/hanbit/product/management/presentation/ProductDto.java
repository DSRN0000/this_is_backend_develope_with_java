package kr.co.hanbit.product.management.presentation;

import jakarta.validation.constraints.NotNull;
import kr.co.hanbit.product.management.domain.Product;

public class ProductDto {
    private Long id;
    private String name;
    private Integer price;
    private Integer amount;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    @NotNull //null인 경우 유효성 검사를 통과하지 못하도록 한다.
    public String getName() {
        return name;
    }

    @NotNull
    public Integer getPrice() {
        return price;
    }

    @NotNull
    public Integer getAmount() {
        return amount;
    }

    /**
     * 모든 필드를 파라미터로 받는 생성자 추가
     */
    public ProductDto(){

    }

    public ProductDto(String name, Integer price, Integer amount){
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public ProductDto(Long id, String name, Integer price, Integer amount){
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    /**
     * ModelMapper와 같은 동작을 하는 코드 추가하기
     */
//    public static Product toEntity(ProductDto productDto){ //생성자 생성 전 코드
//        Product product = new Product();
//
//        product.setId(productDto.getId());
//        product.setName(productDto.getName());
//        product.setPrice(productDto.getPrice());
//        product.setAmount(productDto.getAmount());
//
//        return product;
//    }

    public static Product toEntity(ProductDto productDto){ //생성자 생성 후 코드
        Product product = new Product(productDto.getId(), productDto.getName(), productDto.getPrice(), productDto.getAmount());

        return product;
    }
    public static ProductDto toDto(Product product){
        ProductDto productDto = new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getAmount());

        return productDto;
    }
}
