package com.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.PersistenceContext;
import org.thymeleaf.util.StringUtils;

@SpringBootTest                                                         //통합 테스트를 위해 스프링부트에서 제공하는 어노테이션.
                                                                        //실제 애플리케이션을 구동할 때처럼 모든 Bean을 IoC 컨테이너에 등록
                                                                        //애플리케이션의 규모가 크면 속도 저하 우려

@TestPropertySource(locations="classpath:application-test.properties")  //테스트 코드 실행 우선 순위 :
                                                                        // application-test.properties > application.properties
                                                                        // test프로퍼티 : H2데이터베이스 > 일반 프로퍼티 : MySQL
                                                                        //H2데이터베이스로 설정해서 테스트할 것이다
class ItemRepositoryTest {

    @Autowired                                                          //ItemRepository를 사용하기 위해 Bean 주입
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;                                                   //영속성 컨텍스트를 사용하기 위해 @PersistenceContext 어노테이션을
                                                                        //이용해서 EntityManager Bean 주입

    @Test                                                               //테스트할 메소드 위에 선언하여 해당 메소드를 테스트 대상으로 지정
                                                                        //Spring은 만약 여러 테스트가 있으면 @Ignore 사용해서 무시 가능
    @DisplayName("상품 저장 테스트")                                      //테스트 코드 실행 시 지정한 테스트명이 노출
    public void createItemTest(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    public void createItemList(){                   //테스트 코드 실행 시 DB에 상품 데이터가 없어서 이 메소드를 실행하여 데이터 넣어준다.
        for(int i = 1 ; i <= 10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");   //ItemRepository 인터페이스에서 작성한 메소드 호출
        for(Item item : itemList){
            System.out.println(item.toString());                          //조회 결과 얻은 item 객체 출력
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest(){
        this.createItemList();                          //위에 존재하는 메소드 실행
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1","테스트 상품 상세 설명5");
                                                        //상품명이 "테스트 상품1" 또는 상품 상세 설명이 "테스트 상품 상세 설명5"인 itemList를 반환
                                                        //or이라 두개의 item 객체가 나올 것이다.
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest(){
        this.createItemList();
        List<Item> itemList =  itemRepository.findByPriceLessThan(10005);       //10005보다 작은 1004까지 출력
        for (Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for (Item item : itemList){
            System.out.println(item.toString());
        }
    }
    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
    public void findByItemDetailByNative(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");

        for (Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void queryDslTest(){
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);         //JPAQueryFactory를 이용하여 쿼리를 동적으로 생성.
                                                                        //생성자의 파라미터로 EntityManager 객체 투입
        QItem qItem = QItem.item;                                       //Querydsl을 통해 쿼리를 생성하기 위해
                                                                        //플러그인을 통해 자동으로 생성된 Qitem 객체 이용
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)           //자바 소스코드지만 SQL문과 비슷하게 소스를 작성할 수 있다
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%"+"테스트 상품 상세 설명"+"%"))
                .orderBy(qItem.price.desc());

        List<Item> itemList = query.fetch();                            //JPAQeury 메소드 중 하나인 fetch를 이용해서 쿼리 결과를 리스트로 반환
                                                                        //fetch() 메소드 실행 시점에 쿼리문 실행
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    public void createItemList2(){                   //상품 데이터를 만드는 새로운 메소드를 하나 만듦
                                                     //1~5 판매상태 SELL, 6~10 판매상태 SOLD_OUT 세팅
        for(int i=1;i<=5;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }

        for(int i=6;i<=10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품 Querydsl 조회 테스트 2")
    public void queryDslTest2(){
        this.createItemList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();       //BooleanBuilder는 쿼리에 들어갈 조건을 만들어주는 빌더라고 생각
                                                                    //Predicate를 구현하고 있으며, 메소드 체인 형식으로 사용
        QItem item = QItem.item;

        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStat = "SELL";

        booleanBuilder.and(item.itemDetail.like("%" +itemDetail+"%"));  //필요한 상품을 조회하는데 필요한 "and"조건을 추가
                                                                            //상품의 판매상태가 SELL일 때만 booleanBuilder에 판매상태 조건을 동적으로 추가
        booleanBuilder.and(item.price.gt(price));
        System.out.println(ItemSellStatus.SELL);
        if(StringUtils.equals(itemSellStat, ItemSellStatus.SELL)){
            booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }


        Pageable pageable = PageRequest.of(0,5);        //데이터를 페이징해 조회하도록 PageRequest.of() 메소드를 이용해
                                                                  //Pageable 객체 생성. 첫 번째 인자는 조회할 페이지 번호,
                                                                  //두 번째 인자는 한 페이지당 조회할 데이터의 개수
        Page<Item> itemPagingResult =
                itemRepository.findAll(booleanBuilder,pageable);  //QueryDslPredicateExecutor 인터페이스에서 정의한 findAll() 메소드를 이용해
                                                                  //조건에 맞는 데이터를 Page 객체로 받아옴
        System.out.println("total elements : " + itemPagingResult. getTotalElements ());



        List<Item> resultItemList = itemPagingResult.getContent();
        for(Item resultItem: resultItemList){
            System.out.println(resultItem.toString());
        }
    }















}