package com.example.myfirstsolproj.controller;

import com.example.myfirstsolproj.dto.PageRequestDTO;
import com.example.myfirstsolproj.dto.PageResponseDTO;
import com.example.myfirstsolproj.dto.ReplyDTO;
import com.example.myfirstsolproj.service.ReplyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/reply")
public class RestReplyController {

    private final ReplyService replyService;

    @PostMapping("/register")
    public ResponseEntity register(@Valid ReplyDTO replyDTO, BindingResult bindingResult, Principal principal, Model model){
        log.info("댓글 들어온 값 : " + replyDTO);

        if (bindingResult.hasErrors()){
            log.info("에러가 있습니다.");
            log.info("에러가 있습니다.");
            log.info("에러가 있습니다.");
            log.info(bindingResult.getAllErrors());

            List<FieldError> fieldErrors =
                    bindingResult.getFieldErrors();
            log.info("-------------------------------");
            fieldErrors.forEach(a->log.info(a.getDefaultMessage()));

            return new ResponseEntity<String>(fieldErrors.get(0).getDefaultMessage(), HttpStatus.OK);
        }

        log.info("유효성검사를 통과한 댓글정보 : " + replyDTO);

        // 댓글 저장
        try {
            replyService.replyRegister(replyDTO, principal.getName());
            log.info("댓글이 저장되었습니다.");
            model.addAttribute("replyDTO", replyDTO);

        }catch (EntityNotFoundException e){

            return new ResponseEntity<String>("입력하신 글에는 댓글이 없습니다." ,HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<String>("댓글이 저장되었습니다", HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity replyList(Long ino, PageRequestDTO pageRequestDTO){
        log.info("댓글 리스트" + ino);
        log.info(pageRequestDTO);
        if (ino == null || ino.equals("")){

            return new ResponseEntity<String>("요청값을 확인해주세요", HttpStatus.BAD_REQUEST);
        }
        PageResponseDTO<ReplyDTO> responseDTO =
                replyService.pageList(pageRequestDTO, ino);

        log.info(responseDTO);


        return new ResponseEntity<PageResponseDTO<ReplyDTO>>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/read/{rno}")
    public ResponseEntity read(@PathVariable("rno") Long rno){

        log.info("컨트롤러로 불러온 댓글 번호 : " + rno);
        ReplyDTO replyDTO =replyService.replyRead(rno);

        return new ResponseEntity<ReplyDTO>(replyDTO, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(ReplyDTO replyDTO){

        log.info(replyDTO);
        log.info(replyDTO);

        try {
            replyService.replyUpdate(replyDTO);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<String>("댓글 못 찾음", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("댓글 수정됨" , HttpStatus.OK);
    }
    @PostMapping("/delete/{rno}")
    public ResponseEntity delete(@PathVariable("rno") Long rno){

        log.info("컨트롤러로 들어온 삭제할 댓글번호 : " + rno);
        replyService.replyDel(rno);

        return new ResponseEntity<String>("댓글이 삭제되었습니다.", HttpStatus.OK);
    }
}
