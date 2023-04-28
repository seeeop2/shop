package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") //정렬할 때 사용하는 order 키워드가 있기 때문에 order 엔티티에 매핑되는 테이블로 orders를 지정
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;  //한 명의 회원은 여러 주문을 할 수 있으므로 주문 엔티티 기준에서 다대일 단방향 매핑을 한다.

    private LocalDateTime orderDate; //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태

    @OneToMany(mappedBy = "order")  /*주문 상품 엔티티와 일대다 매핑을 한다. 외래키(orderID)가 order_item 테이블에 있으므로 연관 관계
                                        주인은 order_Item 엔티티 입니다. 따라서, Order 엔티티가 주인이 아니므로 "mappedBy" 속성으로
                                        연관 관계의 주인을 설정. 속성의 값으로 ORDER을 적어준 이유는 orderItem에 있는 Order에 의해
                                        관리된다는 의미로 해석하면 된다. 즉, 연관 관계의 주인의 필드인 ORDER을 세팅해줬다.  */
    private List<OrderItem> orderItems = new ArrayList<>(); //하나의 주문이 여러 개의 주문 상품을 갖으므로 List 자료형 사용


    private LocalDateTime regTime;

    private LocalDateTime updateTime;



}