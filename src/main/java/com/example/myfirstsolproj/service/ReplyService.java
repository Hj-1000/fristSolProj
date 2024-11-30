package com.example.myfirstsolproj.service;

import com.example.myfirstsolproj.dto.PageRequestDTO;
import com.example.myfirstsolproj.dto.PageResponseDTO;
import com.example.myfirstsolproj.dto.ReplyDTO;
import com.example.myfirstsolproj.entity.Reply;


public interface ReplyService {

    // 댓글 등록
    public Reply replyRegister(ReplyDTO replyDTO,String userID);

    // 댓글 읽기
    public ReplyDTO replyRead(Long rno);
    // 댓글 목록


    public PageResponseDTO<ReplyDTO> pageList(PageRequestDTO pageRequestDTO, Long ino);

    // 댓글 총 수 확인
    public int totalEl();

    // 댓글 수정
    public ReplyDTO replyUpdate(ReplyDTO replyDTO);
    // 댓글 삭제
    public void replyDel(Long rno);


}
