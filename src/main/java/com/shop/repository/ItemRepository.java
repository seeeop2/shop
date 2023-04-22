package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByItemNm(String itemNm);     //itemNm으로 데이터를 조회하기 위해 By 뒤에 붙여줌. 매개변수는 검색시 사용할 상품명 변수
                                                //엔티티명은 생략 가능 ----> findItemByItemNm() == findByItemNm()
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
}
