package com.example.myfirstsolproj.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/reply")
public class RestReplyController {

    private final ReplyService replyService;

    @PostMapping("/register")
    public ResponseEntity replyRegister(@Valid ReplyDTO replyDTO, BindingResult bindingResult, Principal principal, Model model){
        log.info("댓글 들어온 값 : " + replyDTO);

        Long ino =
        replyDTO.getItemDTO().getIno();

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


        if (ino ==null || ino.equals("")){
            return new ResponseEntity<String>("댓글값들이 안들어옴", HttpStatus.BAD_REQUEST);
        }

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

}
