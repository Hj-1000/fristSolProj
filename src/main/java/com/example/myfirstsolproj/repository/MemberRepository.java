package com.example.myfirstsolproj.repository;

import com.example.myfirstsolproj.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member , Long> {

    // findByEmail 는 아이디 찾을 때 사용
    public Member findByEmail(String email);

    // findByUserId 여러군대에 사용함
    public Member findByUserID(String id);
}
