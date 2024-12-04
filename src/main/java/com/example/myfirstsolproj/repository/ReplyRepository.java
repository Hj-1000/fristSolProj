package com.example.myfirstsolproj.repository;

import com.example.myfirstsolproj.dto.ItemDTO;
import com.example.myfirstsolproj.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    //페이징처리로 글찾기
    public Page<Reply> findByItemIno(Long ino, Pageable pageable);
    //리스트
    public List<Reply> findByItemIno(Long ino);

    // 댓글이 참조하는 fk 가 해당 ino 인것의 댓글 갯수
    @Query("select count(r) from Reply r where r.item.ino =:ino")
    public Long countReply(Long ino);

    @Modifying
    public void deleteByItemIno(Long ino);


}
