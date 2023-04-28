package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter @Setter
@ToString
public class Cart {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne /* @OneToOne 이용해서 회원 엔티티와 일대일 매핑해준다 */
    @JoinColumn(name="member_id")/*@JoinColumn 이용해서 매핑할 외래키를 지정. name 속성에는 매핑할 외래키의 이름을 설정
                                        name속성을 명시하지 않으면 JPA가 알아서 ID를 찾지만, 컬럼명이 원하는대로 생성되지 않을 수 있기에
                                        직접 지정*/
    private Member member;

}