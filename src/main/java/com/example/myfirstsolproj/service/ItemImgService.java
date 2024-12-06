package com.example.myfirstsolproj.service;

import com.example.myfirstsolproj.entity.Item;
import com.example.myfirstsolproj.entity.ItemImg;
import com.example.myfirstsolproj.repository.ItemImgRepository;
import com.example.myfirstsolproj.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class ItemImgService {
    private final ItemImgRepository itemImgRepository;
    private final ItemRepository itemRepository;
    private final FileService fileService;




    // 이미지 등록 메서드
    public void saveImg(Long ino, List<MultipartFile> multipartFile) throws IOException {
        //이미지 등록은 어디에 무엇을 할 것인가?
        //이미지 아이템꺼
        // 아이템 pk 이미지 파일 이미지 파일을 경로 잘라서
        // 경로와 함께 이름 저장
        log.info("아이템 이미지 서비스로 들어온 imgno" + ino);

        if (multipartFile != null) {
            for (MultipartFile img : multipartFile) {
                if (!img.isEmpty()) {
                    log.info("아이템 이미지 서비스로 들어온 이미지" + img.getOriginalFilename());
                    // 물리 적인 저장 방법

                    String savedFileName = fileService.uploadFile(img);

                    //db 저장
                    //엔티티를 가져왔다면 중복코드를 사용할 필요가 없어진다.
                    Item item =
                    itemRepository.findById(ino).orElseThrow(EntityNotFoundException::new);

                    String imgUrl = "/images/item/" + savedFileName;

                    ItemImg itemImg = new ItemImg();
                }
            }

        }

    }


}
