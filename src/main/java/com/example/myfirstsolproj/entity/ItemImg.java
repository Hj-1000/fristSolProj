package com.example.myfirstsolproj.entity;

import com.example.myfirstsolproj.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "item_img")
public class ItemImg extends BaseEntity {

    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgno;   // 이미지의 pk

    private String imgNm;  // 상품명

    private String OriImgNm;  // 상품명

    private String imgUrl;  // 이미지 조회 경로

    private String repImgYn; // 대표이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


}
