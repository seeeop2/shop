package com.shop.entity;


import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity                                                 //Item클래스를 entity로 선언
@Table(name = "item")                                   //어떤 테이블과 매핑될지를 지정
@Getter
@Setter
@ToString
public class Item {

    @Id                                                 //entity로 선언한 클래스는 반드시 기본키가 필요. 기본키가 되는 멤버변수에 @id 설정
    @Column(name = "item_id")                           //테이블에 매핑될 컬럼의 이름을 설정 item클래스의 id 변수 == item테이블의 item_id
    @GeneratedValue(strategy = GenerationType.AUTO)     //기본키 생성 전략
    private Long id;                        //상품 코드

    @Column(nullable = false, length = 50)              //항상 값이 있어야하는 값은 nullable = false
    private String itemNm;                  //상품명

    @Column(name = "price", nullable = false)
    private int price;                      //가격

    @Column(nullable = false)
    private int stockNumber;                //재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail;              //상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;  //상품 판매 상태

    private LocalDateTime regTime;          //등록 시간

    private LocalDateTime updateTime;       //수정 시간
}
