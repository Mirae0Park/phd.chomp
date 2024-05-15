package com.phd.chomp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phd.chomp.dto.CartDetailDto;
import com.phd.chomp.dto.CartItemDto;
import com.phd.chomp.service.CartService;
import com.phd.chomp.service.MemberService;
import com.phd.chomp.service.ShippingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class CartController {

    private final CartService cartService;
    private final ShippingService shippingService;
    private final MemberService memberService;


    @PostMapping(value="/cart")
    public @ResponseBody ResponseEntity cartPage(@RequestBody @Valid CartItemDto cartItemDto,
                                   BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String uid = principal.getName();
        Long cartItemId;

        try{
            cartItemId = cartService.addCart(cartItemDto, uid);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @GetMapping(value = "/cart")
    public String orderHist(Principal principal, Model model){

        log.info("cart 페이지 접근");

        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems", cartDetailList);

        return "cart/cart";
    }

    @DeleteMapping(value = "/cartItem/{cartItemId}/userId/{userId}")
    public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId, @PathVariable("userId") String userId) {
        if (!cartService.validateCartItem(cartItemId, userId)) {
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        cartService.deleteCartItem(cartItemId);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam(name = "orderData") String orderData,
                           Principal principal, Model model){

        log.info("cartDetailDtoJson : " + orderData);

        String uid = principal.getName();
        CartDetailDto cartDetailDto = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            cartDetailDto = objectMapper.readValue(orderData, CartDetailDto.class);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        List<CartItemDto> cartItems = cartDetailDto.getCartDetailDto();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("memberInfo", memberService.getMemberInfo(uid));
        model.addAttribute("shippingInfo", shippingService.getDefaultAddr(uid));

        return "order/checkout";
    }

}
