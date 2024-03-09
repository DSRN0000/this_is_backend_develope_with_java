package kr.co.hanbit.product.management.infrastructure;

import kr.co.hanbit.product.management.domain.EntityNotFoundException;
import kr.co.hanbit.product.management.domain.Product;
import kr.co.hanbit.product.management.domain.ProductRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("test") //특정 환경에서 특정 클래스의 빈이 생성되도록 만든다. test라는 이름의 profile로 실행하면 ListProductRepository 생성된다.
public class ListProductRepository implements ProductRepository {

    /**
     * CopyOnWriteArrayList 사용이유
     * 웹 어플리케이션이 여러 개의 스레드가 동시에 동작하는 멀티 스레드라는 특수한 환경 때문에 '스레드 세이프한' 컬렉션을 사용해야 하기 때문.
     * ArrayList는 스레드 세이프하지 않은, 즉 스레드 안전성이 없는 컬렉션이다.
     * ArrayList를 사용해야 하는 상황도 있다. 지역 변수나 매개 변수로 전달되는 리스트의 경우 보통 하나의 스레드에서만 접근하기 때문에 스레드 안정성이 필요하지 않아 ArrayList를 많이 사용한다.
     */
    private List<Product> products = new CopyOnWriteArrayList<>();
    /**
     * AtomicLong이 무엇인가?
     * AtomicLong은 CopyOnWriteArrayList와 마찬가지로 스레드 안전성을 가지는 클래스이다.
     * Long 타입의 값을 안전하게 다룰 수 있도록 만든다.
     */
    private AtomicLong sequence = new AtomicLong(1L);

    public Product add(Product product){
        product.setId(sequence.getAndAdd(1L)); //요청 보낼때마다 id 1씩 증가

        products.add(product);
        return product;
    }

    /**
     *상품 번호를 기준으로 조회 기능 추가하기
     */

    public Product findById(Long id){
        return products.stream().filter(product -> product.sameId(id)).findFirst().orElseThrow(() -> new EntityNotFoundException("Product를 찾지 못했습니다."));
    }

    /**
     * 전체 상품 목록 조회 기능 추가하기
     */
    public List<Product> findAll(){
        return products;
    }

    /**
     * 상품 이름에 포함된 문자열로 검색하는 기능 추가하기
     */
    public List<Product> findByNameContaining(String name){
        return products.stream().filter(product -> product.containsName(name)).toList();
    }

    /**
     * 상품 수정하기
     */
    public Product update(Product product) {
        Integer indexToModify = products.indexOf(product);
        products.set(indexToModify, product);
        return product;
    }

    /**
     * 상품 삭제하기
     */
    public void delete(Long id) {
        Product product = this.findById(id);
        products.remove(product);
    }
}
