package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item,Long>, QuerydslPredicateExecutor<Item> {        //QueryDslPredicateExecutor 인터페이스 상속 추가
    List<Item> findByItemNm(String itemNm);     //itemNm으로 데이터를 조회하기 위해 By 뒤에 붙여줌. 매개변수는 검색시 사용할 상품명 변수
                                                //엔티티명은 생략 가능 ----> findItemByItemNm() == findByItemNm()
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")  //SQL과 유사한 JPQL
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);       //다른 방법이 있지만, @Param을 추천

    @Query(value = "select * from Item i where i.item_detail like %:itemDetail% order by i.price desc",nativeQuery = true)
                                                                //value 안에 네이티브 쿼리문 작성하고 nativeQeury = true 작성해준다.
                                                                //데이터베이스에 사용하던 쿼리를 그대로 사용해야 할 때는 nativeQeury속성을 사용하면
                                                                //기존 쿼리 그대로 활용 가능
                                                                //But 특정 DB에 종속되는 쿼리문을 사용하기 때문에 DB에 대한 독립적이라는 장점 잃는다.
                                                                //기존에 작성한 통계용 쿼리처럼 복잡한 쿼리 그대로 사용해야 하는 경우 활용
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);



}
