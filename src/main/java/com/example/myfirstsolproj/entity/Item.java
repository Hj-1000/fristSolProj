package com.example.myfirstsolproj.entity;

import com.example.myfirstsolproj.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class Item extends BaseEntity {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ino;   //상품코드

    @Column(nullable = false, length = 50)
    private String itemNm;  // 상품명

    @Column(name = "price", nullable = false)
    private int price;      // 가격

    @Lob
    @Column(nullable = false)
    private String itemDetail; // 상품 상세설명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}
