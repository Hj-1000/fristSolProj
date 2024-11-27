package com.example.myfirstsolproj.service;

import com.example.myfirstsolproj.dto.ItemDTO;
import com.example.myfirstsolproj.dto.PageRequestDTO;
import com.example.myfirstsolproj.dto.PageResponseDTO;
import com.example.myfirstsolproj.entity.Item;
import com.example.myfirstsolproj.entity.Member;
import com.example.myfirstsolproj.repository.ItemRepository;
import com.example.myfirstsolproj.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long itemRegister(ItemDTO itemDTO, String userID) {


        Member member =
        memberRepository.findByUserID(userID);

        log.info(member);

        Item item =
        modelMapper.map(itemDTO, Item.class);


        item.setMember(member);


        item =
        itemRepository.save(item);

        // 이미지등록은 이미지 엔티티 만든 뒤에
        // 추가예정

        return item.getIno();
    }

    @Override
    public ItemDTO itemRead(Long ino) {
        Item item =
                itemRepository.findById(ino).orElseThrow(EntityNotFoundException::new);

        ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);

        return itemDTO;
    }

    // ItemController의 updateGet 부분에서 읽을 때 사용할 것임
    @Override
    public ItemDTO itemExpertRead(Long ino, String userID) {

        Item item =
        itemRepository.findById(ino).orElseThrow(EntityNotFoundException::new);

        if (item.getMember().getUserID().equals(userID)){
            ItemDTO itemDTO =
                    modelMapper.map(item, ItemDTO.class);
            return itemDTO;

        }else {
            ItemDTO itemDTO = null;
            return itemDTO;
        }

    }

    // 아이템 올린사람(해당 전문가가 올린 아이템 리스트만 보기)
    @Override
    public PageResponseDTO<ItemDTO> itemList(PageRequestDTO pageRequestDTO, String userID) {
        Pageable pageable = pageRequestDTO.getPageable("ino");
        Page<Item> items = itemRepository.getExpertItemPage(pageRequestDTO,pageable,userID);

        List<ItemDTO> itemDTOList =
        items.getContent().stream().map(item -> modelMapper.map(item, ItemDTO.class))
                .collect(Collectors.toList());

        PageResponseDTO<ItemDTO> itemDTOPageResponseDTO
                = PageResponseDTO.<ItemDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(itemDTOList)
                .total((int)items.getTotalElements())
                .build();


        return itemDTOPageResponseDTO;
    }

    @Override
    public ItemDTO itemUpdate(ItemDTO itemDTO, Long ino) {
        log.info("컨트롤러로 들어온 itemDTO" + itemDTO);
        // 유효성 검사를 한것임
        Item item =
        itemRepository.findById(ino).orElseThrow(EntityNotFoundException::new);
        // 가져온거 값 set
        item.setItemNm(itemDTO.getItemNm());
        item.setPrice(itemDTO.getPrice());
        item.setItemDetail(item.getItemDetail());

        itemDTO =
        modelMapper.map(item, ItemDTO.class);

        log.info("수정된 itemDTO" + itemDTO);
        return itemDTO;
    }

    @Override
    public void itemDel(Long ino) {
        log.info("서비스로 들어온 삭제할 아이템 번호" + ino);
        itemRepository.deleteById(ino);

    }
}
