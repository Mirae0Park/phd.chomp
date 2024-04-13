package com.phd.chomp.controller;

import com.phd.chomp.dto.ItemFormDto;
import com.phd.chomp.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class itemController {

    private final ItemService itemService;

    @GetMapping(value = "/item/register")
    public String itemRegister(Model model){
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemRegister";
    }

    @PostMapping(value = "/item/register")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                          Model model){

        log.info("itemController.............................");

        if (bindingResult.hasErrors()){
            return "item/itemRegister";
        }

        /*if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){ , @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList
            model.addAttribute("errorMessage", "첫 번째 상품 이미지는 필수 입력 값입니다.");
            return "item/itemRegister";
        }*/

        try{
            log.info("itemcontroller : " + itemFormDto);
            itemService.saveItem(itemFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/register";
        }

        return "member/login";
    }
}
