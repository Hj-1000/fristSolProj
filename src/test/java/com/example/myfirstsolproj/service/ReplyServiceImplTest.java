package com.example.myfirstsolproj.service;

import com.example.myfirstsolproj.dto.ReplyDTO;
import com.example.myfirstsolproj.entity.Item;
import com.example.myfirstsolproj.entity.Member;
import com.example.myfirstsolproj.entity.Reply;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ReplyServiceImplTest {

    @Autowired
    private ReplyService replyService;

    @Test
    public void replyupdateTest(){

        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setRno(1L);
        replyDTO.setReplyContent("수정할거임ㅋㅋ");

        ReplyDTO replyDTO1 =
        replyService.replyUpdate(replyDTO);

        log.info(replyDTO1);


    }



}