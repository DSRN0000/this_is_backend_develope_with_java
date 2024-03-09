package kr.co.hanbit.product.management.infrastructure;

import kr.co.hanbit.product.management.domain.EntityNotFoundException;
import kr.co.hanbit.product.management.domain.Product;
import kr.co.hanbit.product.management.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@Profile("prod") //특정 환경에서 특정 클래스의 빈이 생성되도록 만든다. prod라는 이름의 profile로 실행하면 DatabaseProductRepository 생성된다.
public class DatabaseProductRepository implements ProductRepository {

    /**
     *데이터베이스에 상품을 추가하려면 데이터베이스에 insert sql을 전송해야 한다.
     * 데이터베이스에 sql을 전송하려면 아래와 같이 JdbcTemplate라는 의존성을 사용하면 된다.
     * 다음과 같이 JdbcTemplate 의존성을 주입받자
     */

//    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    public DatabaseProductRepository(JdbcTemplate jdbcTemplate){
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public Product add(Product product){
//        jdbcTemplate.update("insert into products (name, price, amount) values (?, ?, ?)",
//                product.getName(), product.getPrice(), product.getAmount());
//
//        return product;
//    }

    /**
     * JdbcTemplate -> namedParameterJdbcTemplate로 변경
     * SQL 쿼리를 보낼 때 물음표로 매개변수를 매핑하지 않고 매개변수의 이름을 통해 SQL쿼리와 값을 매핑한다.
     * 물음표로 매핑되는 방식과 비교해 이 방식은 개발 과정에서 매개변수 순서가 바뀌거나 매개변수의 수가 많은 경우에도 헷갈리지 않는다.
     * 또한 Product에 대한 getter 메서드를 사용하는 코드가 없어졌다. => SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(product); 에서 처리해준다.
     */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    public DatabaseProductRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Product add(Product product){

        KeyHolder keyHolder = new GeneratedKeyHolder(); //id를 채워주기 위한 코드 ("id" : null로 나오는 문제 해결)
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(product);

        namedParameterJdbcTemplate.update("insert into products (name, price, amount) values (:name, :price, :amount)", namedParameter, keyHolder); //id를 채워주기 위해 keyHolder 추가

        Long generatedId = keyHolder.getKey().longValue(); //id를 채워주기 위한 코드
        product.setId(generatedId); //id를 채워주기 위한 코드

        return product;
    }

    public Product findById(Long id){
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);

        Product product = null;

        try {
            product = namedParameterJdbcTemplate.queryForObject(
                    "select id, name, price, amount from products where id= :id",
                    namedParameter, new BeanPropertyRowMapper<>(Product.class));
        }catch (EmptyResultDataAccessException exception){
            throw new EntityNotFoundException("Product를 찾지 못했습니다.");
        }

        return product;
    }

    public List<Product> findAll(){
        List<Product> products = namedParameterJdbcTemplate.query(
                "selet * form products",
                new BeanPropertyRowMapper<>(Product.class));

        return products;
    }

    public List<Product> findByNameContaining(String name){
        SqlParameterSource namedParameter = new MapSqlParameterSource("name", "%" + name + "%");

        List<Product> products = namedParameterJdbcTemplate.query(
                "selete * from products where name like :name",
                namedParameter, new BeanPropertyRowMapper<>(Product.class));

        return products;
    }

    public Product update(Product product){
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(product);

        namedParameterJdbcTemplate.update("update products set name=:name, price=:price, amount=:amount where id=:id", namedParameter);

        return product;
    }

    public void delete(Long id){
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);

        namedParameterJdbcTemplate.update("delete from products where id=:id", namedParameter);
    }
}
