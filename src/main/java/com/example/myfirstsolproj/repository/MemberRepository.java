package com.example.myfirstsolproj.repository;

import com.example.myfirstsolproj.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member , Long> {

    // findByEmail 로그인 때 사용
    public Member findByEmail(String email);

    // findById 는 비밀번호 찾을 때 사용
    public Member findByUserID(String id);
}
