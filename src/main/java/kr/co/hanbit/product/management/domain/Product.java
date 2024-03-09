package kr.co.hanbit.product.management.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import kr.co.hanbit.product.management.presentation.ProductDto;

import java.util.Objects;

public class Product {
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    /**
     *상품 번호를 기준으로 조회 기능 추가하기
     */
    public Boolean sameId(Long id){
        return this.id.equals(id);
    }

    /**
     * 상품 수정하기
     */
    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }
    @Size(min = 1, max = 100) //Product 생성 시 유효성 검사 추가하기
    private String name;

    /**
     * 상품 이름에 포함된 문자열로 검색하는 기능 추가하기
     */
    public Boolean containsName(String name){
        return this.name.contains(name);
    }

    @Max(1_000_000) //Product 생성 시 유효성 검사 추가하기
    @Min(0)
    private Integer price;

    @Max(9_999) //Product 생성 시 유효성 검사 추가하기
    @Min(0)
    private Integer amount;

    /**
     * DatabaseProductRepository에서 Product 인스턴스의 값을 가져오기 위해 getter 사용
     */
    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    /**
     * setter 추가
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * id에 대한 getter 추가
     */
    public Long getId() {
        return id;
    }

    /**
     * 모든 필드를 파라미터로 받는 생성자 추가
     */
    public Product(){

    }

    public Product(Long id, String name, Integer price, Integer amount){
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
}
