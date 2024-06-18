package com.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;


import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest // 스프링 부트 테스트
// 테스트관련 프로퍼티 위치야 -> application-test.properties
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository; // ItemRepository객체와 연결

    @PersistenceContext
    EntityManager em; // 엔티티 매니저 객체 연결

    @Test // 테스트 메소드
    @DisplayName("상품 저장 테스트") // 테스트 이름
    public void createItemTest() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item); // save -> insert 실행 결과가 insert된 Item 객체가 나옴
        System.out.println(savedItem.toString());
    }

    public void createItemList() {
        for(int i = 1;i<=10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item); // 실제로 DB에 저장
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1"); // 여러개일수 있어서 List로 받음
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for(Item item : itemList){
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for(Item item : itemList){
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanOrderByPriceDescTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for(Item item : itemList){
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품조회 테스트")
    public void findByItemDetailTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for(Item item : itemList){
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("@NativeQuery를 이용한 상품조회 테스트")
    public void findByItemDetailNativeTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailNative("테스트 상품 상세 설명");
        for(Item item : itemList){
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void queryDslTest(){
        this.createItemList();
        // JPAQueryFactory 객체를 생성하려면 생성자 매개변수에 EntityManager
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        //QItem class에서 item(데이터베이스 item)을 추출
        QItem qItem = QItem.item;
        // JPAQueryFactory -> JPAQuery를 생성
        // JPAQuery를 사용하기 위해서는 QXXX가 필요 -> QueryDSL를 의존성 추가 -> 컴파일 -> 사용가능 설정
        JPAQuery<Item> query = queryFactory.selectFrom(qItem) // select * from item
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL)) // item_sell_status = SELL and
                .where(qItem.itemDetail.like("%"+"테스트 상품 상세 설명"+"%")) // item_detail like 테스트 상품 상세 설명
                .orderBy(qItem.price.desc()); // order by price desc
        // JPAQuery -> fetch를 하면 결과가 반환 됩니다.
        List<Item> itemList = query.fetch();
        for(Item item : itemList){
            System.out.println(item);
        }
    }

    public void createItemList2(){
        for(int i = 1;i<=5;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item); // 실제로 DB에 저장
        }
        for(int i = 6;i<=10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item); // 실제로 DB에 저장
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트2")
    public void queryDslTest2(){
        //1~5 SELL 6~10 SOLD_OUT
        this.createItemList2();
        // 쿼리에 들어갈 조건을 만들어주는 빌더입니다. 객체 생성
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        // QItem -> item 테이블 정보 가지고 오기
        QItem qItem = QItem.item;
        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStat = "SELL";

        // BooleanBuilder에서 -> and qItem.itemDetail like "테스트 상품 상세 설명"
        booleanBuilder.and(qItem.itemDetail.like("%"+itemDetail+"%"));
        // and -> qItem.price가 10003 초과인 값만
        booleanBuilder.and(qItem.price.gt(price)); // gt 초과
        // StringUtils -> String SELL == ItemSellStatus.SELL
        if(StringUtils.equals(itemSellStat, ItemSellStatus.SELL)){
            // and qItem.itemSellStatu == ItemSellStatus.SELL(SELL)
            booleanBuilder.and(qItem.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        // Pageable 객체를 생성하는 시작인덱스 0이고 사이즈 5로 생성 됩니다.
        Pageable pageable = PageRequest.of(0, 5);
        // itemRepository.findAll 모두 검색 1매개변수 -> 쿼리 조건, 2매개변수 -> 페이지 세팅
        // 결과 Page<Item> 결과를 받습니다.
        // 결과 값 개수가 있고 결과 값도 같이 가지고 있습니다.
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
        // getTotalElements() -> 결과 값 개수 나옵니다.
        System.out.println("total elements : "+ itemPagingResult.getTotalElements());
        // getContent() -> List<Item>
        List<Item> resultItemList = itemPagingResult.getContent();
        // List<Item>을 반복문 돌려서 출력
        for(Item item : resultItemList){
            System.out.println(item);
        }
    }
}