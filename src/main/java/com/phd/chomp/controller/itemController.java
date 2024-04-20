package com.phd.chomp.controller;

import com.phd.chomp.dto.ItemFormDto;
import com.phd.chomp.dto.ItemSearchDto;
import com.phd.chomp.dto.MainItemDto;
import com.phd.chomp.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Log4j2
public class itemController {

    private final ItemService itemService;

    @GetMapping(value = "/item/register")
    public String itemRegister(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemRegister";
    }

    @PostMapping(value = "/item/register")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
        // 상품 등록하기

        log.info("itemController.............................");

        if (bindingResult.hasErrors()) {
            return "item/itemRegister";
        }

        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫 번째 상품 이미지는 필수 입력 값입니다.");
            return "item/itemRegister";
        }

        try {
            log.info("itemController : " + itemImgFileList);
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/itemRegister";
        }

        return "member/login";
    }

    @GetMapping(value = "/admin/item/{ItemId}")
    public String itemDtl(@PathVariable("ItemId") Long ItemId, Model model) {
        try {
            ItemFormDto itemFormDto = itemService.getItemDtl(ItemId); // 조회한 상품 데이터를 모델에 담아서 뷰로 전달
            model.addAttribute("itemFormDto", itemFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errormessage", "존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());

            return "item/itemRegister";
        }
        return "item/itemRegister";
    }

    @PostMapping(value = "/admin/item/{ItemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                             @RequestParam(value = "itemImgFile", required = false) List<MultipartFile> itemImgFileList, Model model) {
        if (bindingResult.hasErrors()) {
            return "item/itemRegister";
        }

        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/itemRegister";
        }

        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "item/itemRegister";
        }

        return "redirect:/login";

    }

    @GetMapping("/shop")
    public String shop(@RequestParam(name = "itemName", required = false) String itemName,
                       @RequestParam(name = "cate", required = false) String cate,
                       Optional<Integer> page, Model model) {

        ItemSearchDto itemSearchDto = new ItemSearchDto();

        if (cate != null && ! cate.isEmpty()){
            itemSearchDto.setCate(cate);
        }

        if (itemName != null && ! itemName.isEmpty()){
            itemSearchDto.setItemName(itemName);
        }

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 9);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 3);

        return "item/shop";
    }
}
