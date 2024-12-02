package com.example.myfirstsolproj.service;

import com.example.myfirstsolproj.dto.*;

import com.example.myfirstsolproj.entity.Item;
import com.example.myfirstsolproj.entity.Member;
import com.example.myfirstsolproj.entity.Reply;
import com.example.myfirstsolproj.repository.ItemRepository;
import com.example.myfirstsolproj.repository.MemberRepository;
import com.example.myfirstsolproj.repository.ReplyRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;
    

    @Override
    public Reply replyRegister(ReplyDTO replyDTO, String userID) {

        log.info("컨트롤러로 들어온 댓글 작성자" + userID);


        Member member =
        memberRepository.findByUserID(userID);

        log.info("서비스에 들어온 댓글 작성자" + member);

        Item item =
                itemRepository.findById(replyDTO.getIno()).orElseThrow(EntityNotFoundException::new);
        log.info("서비스에 들어온 댓갈 달린 본문 번호" + item);

        Reply reply =
        modelMapper.map(replyDTO, Reply.class);

        reply.setMember(member);
        reply.setItem(item);

        // 위의 내용들을 모두 저장
        log.info("필살기 쓰기 전에 저장되라" + reply);

        reply =
        replyRepository.save(reply);

        return reply;

    }

    @Override
    public ReplyDTO replyRead(Long rno) {

        Reply reply =
                replyRepository.findById(rno).orElseThrow(EntityNotFoundException::new);

        ReplyDTO replyDTO
                = modelMapper.map(reply, ReplyDTO.class);

        return replyDTO;
    }

    @Override
    public PageResponseDTO<ReplyDTO> pageList(PageRequestDTO pageRequestDTO, Long ino) {

        log.info("해당 댓글이 달린 글 번호 " + ino);
        Pageable pageable = pageRequestDTO.getPageable("rno");

        Page<Reply> replyPage =
                replyRepository.findByItemIno(ino, pageable);

        List<Reply> replyList = replyPage.getContent();

        replyList.forEach(a-> log.info(a));

        List<ReplyDTO> replyDTOList =
                replyList.stream()
                        .map(
                                reply -> modelMapper.map( reply, ReplyDTO.class)
                                        .setItemDTO( modelMapper.map(reply.getItem(), ItemDTO.class) )
                        )
                        .collect(Collectors.toList());
        replyDTOList = replyList.stream().map(reply -> modelMapper.map(reply, ReplyDTO.class).setMemberDTO(modelMapper.map(reply.getMember(), MemberDTO.class)))
                .collect(Collectors.toList());

        PageResponseDTO<ReplyDTO> responseDTO =
                PageResponseDTO.<ReplyDTO>withAll()
                        .dtoList(replyDTOList)
                        .total((int) replyPage.getTotalElements())
                        .pageRequestDTO(pageRequestDTO)
                        .build();

        return responseDTO;
    }

    @Override
    public int totalEl() {
        return 0;
    }

    @Override
    public ReplyDTO replyUpdate(ReplyDTO replyDTO) {
        return null;
    }

    @Override
    public void replyDel(Long rno) {

    }
}
