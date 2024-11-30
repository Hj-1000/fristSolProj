package com.example.myfirstsolproj.service;

import com.example.myfirstsolproj.constant.Role;
import com.example.myfirstsolproj.dto.MemberDTO;
import com.example.myfirstsolproj.entity.Member;
import com.example.myfirstsolproj.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    //이건 로그인
    @Override
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {

        Member member = memberRepository.findByUserID(userID);

        if (member == null){
            throw new UsernameNotFoundException(userID);
        }

        return User.builder()
                .username(member.getUserID())   // 세션에 저장된 아이디
                .password(member.getPassword()) // 로그인시 입력한 비밀번호와 비교할 값
                .roles(member.getRole().name())
                .build();
    }

    // 이건 아이디 찾기
//    public UserDetails findUserIDByEmail(String email) throws UsernameNotFoundException {
//
//        Member member = memberRepository.findByEmail(email);
//
//        if (member == null){
//            throw new UsernameNotFoundException(email);
//        }
//
//        return User.builder()
//                .username(member.getEmail())   // 세션에 이메일
//                .password(member.getPassword()) // 로그인시 입력한 비밀번호와 비교할 값
//                .roles(member.getRole().name())
//                .build();
//    }

    // 회원가입
    public Member saveMember(MemberDTO memberDTO){

        validDuplocateMember(memberDTO.getEmail(), memberDTO.getUserID());

        log.info("너는 회원가입을 했을것이여" + memberDTO);
        // 컨트롤러에서 받은 MemberDTO를 member entity 로 변경


        Member member =
        modelMapper.map(memberDTO, Member.class);

        member.setPassword(new BCryptPasswordEncoder().encode(memberDTO.getPassword()));
        member.setRole(Role.ADMIN);

        member =
        memberRepository.save(member);

        log.info("이 사람 가입하는거지?" + member);
        return member;
    }

    // 회원가입시 동일한 정보가 이미 db에 존재하는지 확인하는 작업필요
    // 회원가입 메소드에 써도 되지만 받는 파라미터인 이메일과 아이디
    // 둘 중 하나라도 이미 존재하는지를 확인해야한다.

    public void validDuplocateMember(String email, String userID){
        System.out.println(email);
        System.out.println(userID);

        Member memberID =
                memberRepository.findByUserID(userID);

        Member memberEmail =
                memberRepository.findByEmail(email);

        // 아이디 혹은 이메일(둘다 유니크임)이 이미 존재한다면 예외처리
        if (memberID != null && memberEmail ==null){
            throw new IllegalStateException("이미 존재하는 ID 입니다.");
        }else if(memberID == null && memberEmail !=null){
            throw new IllegalStateException("이미 존재하는 이메일 입니다.");
        }

        log.info("회원가입을 성공하였습니다.");

    }
    public MemberDTO readMember(Long mno){

        Member member =
        memberRepository.findById(mno).orElseThrow(EntityNotFoundException::new);

        MemberDTO memberDTO =
        modelMapper.map(member , MemberDTO.class);

        return memberDTO;
    }





}
