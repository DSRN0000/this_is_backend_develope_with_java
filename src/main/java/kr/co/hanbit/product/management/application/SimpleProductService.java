package kr.co.hanbit.product.management.application;

import kr.co.hanbit.product.management.domain.Product;
import kr.co.hanbit.product.management.domain.ProductRepository;
import kr.co.hanbit.product.management.infrastructure.DatabaseProductRepository;
import kr.co.hanbit.product.management.infrastructure.ListProductRepository;
import kr.co.hanbit.product.management.presentation.ProductDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleProductService {

//    /**
//     * ListProductRepository 사용하기
//     */

//    private ListProductRepository listProductRepository;
//    private ModelMapper modelMapper;
//    private ValidationService validationService;
//
//    @Autowired
//    SimpleProductService(ListProductRepository listProductRepository, ModelMapper modelMapper, ValidationService validationService){
//        this.listProductRepository = listProductRepository;
//        this.modelMapper = modelMapper;
//        this.validationService = validationService;
//    }
//
//    public ProductDto add(ProductDto productDto){
//        //1. ProductDto를 Product로 변환하는 코드
//        Product product = modelMapper.map(productDto, Product.class);
//        validationService.checkValid(product); //product 유효성 검사 코드
//
//        //2. 레포지토리를 호출하는 코드
//        Product savedProduct = listProductRepository.add(product);
//
//        //3. Product를 ProductDTO로 변환하는 코드
//        ProductDto savedProductDto = modelMapper.map(savedProduct, ProductDto.class);
//
//        //4. DTO를 반환하는 코드
//        return savedProductDto;
//    }
//
//    /**
//     *상품 번호를 기준으로 조회 기능 추가하기
//     */
//
//    public ProductDto findById(Long id){
//        Product product = listProductRepository.findById(id);
//        ProductDto productDto = modelMapper.map(product, ProductDto.class);
//        return productDto;
//    }
//
//    /**
//     * 전체 상품 목록 조회 기능 추가하기
//     */
//    public List<ProductDto> findAll(){
//        List<Product> products = listProductRepository.findAll();
//        List<ProductDto> productDtos = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
//        return productDtos;
//    }
//
//    /**
//     * 상품 이름에 포함된 문자열로 검색하는 기능 추가하기
//     */
//    public List<ProductDto> findBYNameContatining(String name){
//        List<Product> products = listProductRepository.findByNameContaining(name);
//        List<ProductDto> productDtos = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
//        return productDtos;
//    }
//
//    /**
//     * 상품 수정하기
//     */
//    public ProductDto update(ProductDto productDto) {
//        Product product = modelMapper.map(productDto, Product.class);
//        Product updatedProduct = listProductRepository.update(product);
//        ProductDto updatedProductDto = modelMapper.map(updatedProduct, ProductDto.class);
//        return updatedProductDto;
//    }
//
//    /**
//     * 상품 삭제하기
//     */
//    public void delete(Long id) {
//        listProductRepository.delete(id);
//    }

//    /**
//     * ListProductRepository 대신 DatabaseProductRepository 사용하기
//     */

//    private DatabaseProductRepository databaseProductRepository;
//    private ModelMapper modelMapper;
//    private ValidationService validationService;
//
//    @Autowired
//    SimpleProductService(DatabaseProductRepository databaseProductRepository, ModelMapper modelMapper, ValidationService validationService){
//        this.databaseProductRepository = databaseProductRepository;
//        this.modelMapper = modelMapper;
//        this.validationService = validationService;
//    }
//
//    public ProductDto add(ProductDto productDto){
//        //1. ProductDto를 Product로 변환하는 코드
//        Product product = modelMapper.map(productDto, Product.class);
//        validationService.checkValid(product); //product 유효성 검사 코드
//
//        //2. 레포지토리를 호출하는 코드
//        Product savedProduct = databaseProductRepository.add(product);
//
//        //3. Product를 ProductDTO로 변환하는 코드
//        ProductDto savedProductDto = modelMapper.map(savedProduct, ProductDto.class);
//
//        //4. DTO를 반환하는 코드
//        return savedProductDto;
//    }
//
//    /**
//     *상품 번호를 기준으로 조회 기능 추가하기
//     */
//
//    public ProductDto findById(Long id){
//        Product product = databaseProductRepository.findById(id);
//        ProductDto productDto = modelMapper.map(product, ProductDto.class);
//        return productDto;
//    }
//
//    /**
//     * 전체 상품 목록 조회 기능 추가하기
//     */
//    public List<ProductDto> findAll(){
//        List<Product> products = databaseProductRepository.findAll();
//        List<ProductDto> productDtos = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
//        return productDtos;
//    }
//
//    /**
//     * 상품 이름에 포함된 문자열로 검색하는 기능 추가하기
//     */
//    public List<ProductDto> findBYNameContatining(String name){
//        List<Product> products = databaseProductRepository.findByNameContaining(name);
//        List<ProductDto> productDtos = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
//        return productDtos;
//    }
//
//    /**
//     * 상품 수정하기
//     */
//    public ProductDto update(ProductDto productDto) {
//        Product product = modelMapper.map(productDto, Product.class);
//        Product updatedProduct = databaseProductRepository.update(product);
//        ProductDto updatedProductDto = modelMapper.map(updatedProduct, ProductDto.class);
//        return updatedProductDto;
//    }
//
//    /**
//     * 상품 삭제하기
//     */
//    public void delete(Long id) {
//        databaseProductRepository.delete(id);
//    }

//    /**
//     * ProductRepository 인터페이스를 이용하여 추상화 하기
//     * ListProductRepository , DatabaseProductRepository 상황에 맞게 사용가능한 코드로 만들기
//     * ListProductRepository -> ProductRepository로 변환
//     */
//
//    private ProductRepository productRepository;
//    private ModelMapper modelMapper;
//    private ValidationService validationService;
//
//    @Autowired
//    SimpleProductService(ProductRepository productRepository, ModelMapper modelMapper, ValidationService validationService){
//        this.productRepository = productRepository;
//        this.modelMapper = modelMapper;
//        this.validationService = validationService;
//    }
//
//    public ProductDto add(ProductDto productDto){
//        //1. ProductDto를 Product로 변환하는 코드
//        Product product = modelMapper.map(productDto, Product.class);
//        validationService.checkValid(product); //product 유효성 검사 코드
//
//        //2. 레포지토리를 호출하는 코드
//        Product savedProduct = productRepository.add(product);
//
//        //3. Product를 ProductDTO로 변환하는 코드
//        ProductDto savedProductDto = modelMapper.map(savedProduct, ProductDto.class);
//
//        //4. DTO를 반환하는 코드
//        return savedProductDto;
//    }
//
//    /**
//     *상품 번호를 기준으로 조회 기능 추가하기
//     */
//
//    public ProductDto findById(Long id){
//        Product product = productRepository.findById(id);
//        ProductDto productDto = modelMapper.map(product, ProductDto.class);
//        return productDto;
////        return new ProductDto(); // -> 테스트코드 실패확인 위한 코드
//    }
//
//    /**
//     * 전체 상품 목록 조회 기능 추가하기
//     */
//    public List<ProductDto> findAll(){
//        List<Product> products = productRepository.findAll();
//        List<ProductDto> productDtos = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
//        return productDtos;
//    }
//
//    /**
//     * 상품 이름에 포함된 문자열로 검색하는 기능 추가하기
//     */
//    public List<ProductDto> findBYNameContatining(String name){
//        List<Product> products = productRepository.findByNameContaining(name);
//        List<ProductDto> productDtos = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
//        return productDtos;
//    }
//
//    /**
//     * 상품 수정하기
//     */
//    public ProductDto update(ProductDto productDto) {
//        Product product = modelMapper.map(productDto, Product.class);
//        Product updatedProduct = productRepository.update(product);
//        ProductDto updatedProductDto = modelMapper.map(updatedProduct, ProductDto.class);
//        return updatedProductDto;
//    }
//
//    /**
//     * 상품 삭제하기
//     */
//    public void delete(Long id) {
//        productRepository.delete(id);
//    }

    /**
     * ModelMapper를 사용하던 코드 제거하기 -> toEntity와 toDto를 사용하도록 변경
     */

    private ProductRepository productRepository;
    private ValidationService validationService;

    @Autowired
    SimpleProductService(ProductRepository productRepository, ValidationService validationService){
        this.productRepository = productRepository;
        this.validationService = validationService;
    }

    public ProductDto add(ProductDto productDto){
        //1. ProductDto를 Product로 변환하는 코드
        Product product = ProductDto.toEntity(productDto);
        validationService.checkValid(product); //product 유효성 검사 코드

        //2. 레포지토리를 호출하는 코드
        Product savedProduct = productRepository.add(product);

        //3. Product를 ProductDTO로 변환하는 코드
        ProductDto savedProductDto = ProductDto.toDto(savedProduct);

        //4. DTO를 반환하는 코드
        return savedProductDto;
    }

    /**
     *상품 번호를 기준으로 조회 기능 추가하기
     */

    public ProductDto findById(Long id){
        Product product = productRepository.findById(id);
        ProductDto productDto = ProductDto.toDto(product);
        return productDto;
//        return new ProductDto(); // -> 테스트코드 실패확인 위한 코드
    }

    /**
     * 전체 상품 목록 조회 기능 추가하기
     */
    public List<ProductDto> findAll(){
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream().map(product -> ProductDto.toDto(product)).toList();
        return productDtos;
    }

    /**
     * 상품 이름에 포함된 문자열로 검색하는 기능 추가하기
     */
    public List<ProductDto> findBYNameContatining(String name){
        List<Product> products = productRepository.findByNameContaining(name);
        List<ProductDto> productDtos = products.stream().map(product -> ProductDto.toDto(product)).toList();
        return productDtos;
    }

    /**
     * 상품 수정하기
     */
    public ProductDto update(ProductDto productDto) {
        Product product = ProductDto.toEntity(productDto);
        Product updatedProduct = productRepository.update(product);
        ProductDto updatedProductDto = ProductDto.toDto(product);
        return updatedProductDto;
    }

    /**
     * 상품 삭제하기
     */
    public void delete(Long id) {
        productRepository.delete(id);
    }
}
