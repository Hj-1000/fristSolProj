package com.example.myfirstsolproj.controller;


import com.example.myfirstsolproj.dto.ItemDTO;
import com.example.myfirstsolproj.dto.PageRequestDTO;
import com.example.myfirstsolproj.dto.PageResponseDTO;
import com.example.myfirstsolproj.service.ItemService;
import com.example.myfirstsolproj.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
@Log4j2
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/admin/item/register")
    public String itemRegisterGet(Model model, Principal principal){
        if(principal ==null){
            return "redirect:/member/login";
        }else if(principal !=null){
            log.info("현재 로그인 한 사람");
            log.info(principal.getName());

        }
        model.addAttribute("itemDTO", new ItemDTO());
        return "item/register";
    }
    @PostMapping("/admin/item/register")
    public String itemRegisterPost(@Valid ItemDTO itemDTO, BindingResult bindingResult, Model model, Principal principal){
        log.info("들어오는 값 확인 " + itemDTO);

        String userID = principal.getName();

        if (bindingResult.hasErrors()){
            log.info("유효성 검사에 문제가 있음");
            log.info(bindingResult.getAllErrors()); // 확인된 모든 에러 콘솔창에 출력

            return "item/register";
        }

        try {

            Long savedItemIno =
                    itemService.itemRegister(itemDTO, userID);

            if(savedItemIno == null){
                return "redirect:/admin/item/list";
            }
            log.info("상품등록이 되었습니다!!");

            // todo 나중에 등록한 글을 바로 볼 수 있도록 return을
            //  return "redirect:/admin/item/read?id="+savedItemIno; 로 바꾸기
            return "redirect:/admin/item/list";
        }catch (Exception e){
            e.printStackTrace();
            log.info("업로드에 문제가 발생했습니다.");
            model.addAttribute("msg", "파일등록에 실패하였습니다");
            return "item/register";
        }
    }

    @GetMapping("/admin/item/read")
    public String read(Long ino, Model model, RedirectAttributes redirectAttributes){

        try {
            ItemDTO itemDTO =
                    itemService.itemRead(ino);

            model.addAttribute("itemDTO", itemDTO);

            return "item/read";
        }catch (EntityNotFoundException e){
            redirectAttributes.addFlashAttribute("msg", "존재하지 않는 상품입니다.");
            return "redirect:/admin/item/list";
        }
    }

    // 글쓴사람이 자기글만 볼 수 있는 리스트
    @GetMapping("/admin/item/list")
    public String adminList(PageRequestDTO pageRequestDTO, Model model, Principal principal){

        PageResponseDTO<ItemDTO> pageResponseDTO =
                itemService.itemList(pageRequestDTO,principal.getName());

        model.addAttribute("pageResponseDTO", pageResponseDTO);

        return "item/list";

    }
}
