package com.example.myfirstsolproj.repository.search;

import com.example.myfirstsolproj.dto.PageRequestDTO;
import com.example.myfirstsolproj.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemSearch {

    // 전문가로 등록한 사람만 아이템 올릴 수 있음
    // 해당 ID(Unique임)의 전문가가 올린 글(아이템)만 보는 서치 메서드
    Page<Item> getExpertItemPage(PageRequestDTO pageRequestDTO, Pageable pageable, String userID);

    // 해당 유저(로그인한 사람) 구매한 아이템만 보는 서치 메서드
    // 맞는지는 확인해봐야함 ㅋㅋ 나를 믿지마
    // 이것은 차후 장바구니 페이지에서 쓰일것같음
    Page<Item> getUserItemPage(PageRequestDTO pageRequestDTO, Pageable pageable, Long ino);
}
