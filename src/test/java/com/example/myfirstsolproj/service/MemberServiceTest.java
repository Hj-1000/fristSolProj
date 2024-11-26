package com.example.myfirstsolproj.service;

import com.example.myfirstsolproj.dto.MemberDTO;
import com.example.myfirstsolproj.entity.Member;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원가입테스트")
    public void signupTest(){
        MemberDTO memberDTO = MemberDTO.builder()
                .email("test2@t.t")
                .name("테스트4")
                .id("test2")
                .password("123451234")
                .address("테스트주")
                .build();

        try {
            Member member
                    =memberService.saveMember(memberDTO);
            log.info(member);
        }catch (IllegalStateException e){
            log.info(e.getMessage());
        }
    }

}