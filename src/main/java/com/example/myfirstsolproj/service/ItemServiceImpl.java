package com.example.myfirstsolproj.service;

import com.example.myfirstsolproj.dto.ItemDTO;
import com.example.myfirstsolproj.dto.PageRequestDTO;
import com.example.myfirstsolproj.dto.PageResponseDTO;
import com.example.myfirstsolproj.entity.Item;
import com.example.myfirstsolproj.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long itemRegister(ItemDTO itemDTO) {

        Item item =
        modelMapper.map(itemDTO, Item.class);

        item =
        itemRepository.save(item);

        // 이미지등록은 이미지 엔티티 만든 뒤에
        // 추가예정

        return item.getIno();
    }

    @Override
    public ItemDTO itemRead(Long ino, String userID) {
        return null;
    }

    @Override
    public PageResponseDTO<ItemDTO> itemList(PageRequestDTO pageRequestDTO, String userID) {
        Pageable pageable = pageRequestDTO.getPageable("ino");



        return null;
    }

    @Override
    public ItemDTO itemUpdate(ItemDTO itemDTO, Long ino) {
        return null;
    }
}
