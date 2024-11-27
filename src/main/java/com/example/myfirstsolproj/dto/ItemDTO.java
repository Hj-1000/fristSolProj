package com.example.myfirstsolproj.dto;

import com.example.myfirstsolproj.constant.ItemSellStatus;
import com.example.myfirstsolproj.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {

    private Long ino;   //상품코드
//    private Long member_id;  // 해당 작성자 Member pk의 컬럼명

    private String itemNm;  // 상품명

    private int price;      // 가격

    private String itemDetail; // 상품 상세설명

    private ItemSellStatus itemSellStatus;

    private MemberDTO memberDTO;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

//    public ItemDTO setMemberDTO(MemberDTO memberDTO){
//        this.memberDTO = memberDTO;
//        return this;
//    }
}
