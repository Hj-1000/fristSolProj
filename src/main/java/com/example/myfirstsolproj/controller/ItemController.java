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
    private final MemberService memberService;

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

            return "redirect:/admin/item/read?ino=" + savedItemIno;
        }catch (Exception e){
            e.printStackTrace();
            log.info("업로드에 문제가 발생했습니다.");
            model.addAttribute("msg", "파일등록에 실패하였습니다");
            return "item/register";
        }
    }

    //내가 쓴 글
    @GetMapping("/admin/item/read")
    public String read(Long ino, Model model, RedirectAttributes redirectAttributes, Principal principal){

        try {
            ItemDTO itemDTO =
                    itemService.itemRead(ino);

            model.addAttribute("itemDTO", itemDTO);
            model.addAttribute("principal", principal.getName());

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

    @GetMapping("admin/item/update")
    public String adminUpdateGet(Long ino, PageRequestDTO pageRequestDTO,
                                 Model model, Principal principal){


        ItemDTO itemDTO=
        itemService.itemExpertRead(ino, principal.getName());
        if (itemDTO != null){
            model.addAttribute("itemDTO", itemDTO);
            return "item/update";
        }else{
            return "redirect:/admin/item/list";
        }



    }

    @PostMapping("admin/item/update")
    public String adminUpdatePost(@Valid ItemDTO itemDTO, BindingResult bindingResult){

        // if (만약 유효성검사에 이상이 있으면) if 아래 페이지로 보내라
        if (bindingResult.hasErrors()){
            log.info("유효성검사 에러");
            log.info(bindingResult.getAllErrors());
            return "/item/update";
        }
        itemDTO=
        itemService.itemUpdate(itemDTO, itemDTO.getIno());


        return "redirect:/admin/item/read?ino=" + itemDTO.getIno();
    }

    @PostMapping("/admin/item/del")
    public String adminDel(Long ino){
        log.info("삭제할 아이템의 번호 "+ino);

        itemService.itemDel(ino);

        log.info("삭제에 성공함" + ino + " 널이쥬?");

        return "redirect:/admin/item/list";
    }
}
