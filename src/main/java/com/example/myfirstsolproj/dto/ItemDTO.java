package com.example.myfirstsolproj.dto;

import com.example.myfirstsolproj.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {

    private Long ino;   //상품코드

    private String itemNm;  // 상품명

    private int price;      // 가격

    private String itemDetail; // 상품 상세설명

    private MemberDTO memberDTO;

    private LocalDate regDate;

    private LocalDate updateDate;
}
