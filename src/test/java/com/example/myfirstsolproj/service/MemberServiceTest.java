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



}