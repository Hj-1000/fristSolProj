package com.example.myfirstsolproj.service;


import com.example.myfirstsolproj.dto.ItemDTO;
import com.example.myfirstsolproj.dto.PageRequestDTO;
import com.example.myfirstsolproj.dto.PageResponseDTO;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    //아이템 등록
    public Long itemRegister(ItemDTO itemDTO);

    //아이템 읽기
    public ItemDTO itemRead(Long ino, String userID);

    //아이템 목록
    public PageResponseDTO<ItemDTO> itemList(PageRequestDTO pageRequestDTO, String userID);

    //아이템 수정
    //삭제는 업데이트 메서드에 함께 구현할 예정
    public ItemDTO itemUpdate(ItemDTO itemDTO, Long ino);


}
