package com.shop.repository;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test                                                               //테스트할 메소드 위에 선언하여 해당 메소드를 테스트 대상으로 지정
                                                                        //만약 여러 테스트가 있으면 @Ignore 사용해서 무시 가능
    @DisplayName("상품 저장 테스트")                                        //테스트 코드 실행 시 지정한 테스트명이 노출
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
    public void findByItemNmOrItemDetail(){
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
    public void findByPriceLessThan(){
        this.createItemList();
        List<Item> itemList =  itemRepository.findByPriceLessThan(10005);       //10005보다 작은 1004까지 출력
        for (Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderBy(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }
}