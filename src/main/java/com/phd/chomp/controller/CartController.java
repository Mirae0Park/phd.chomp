package com.phd.chomp.controller;

import com.phd.chomp.dto.CartDetailDto;
import com.phd.chomp.dto.CartItemDto;
import com.phd.chomp.service.CartService;
import com.phd.chomp.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
public class CartController {

    private final CartService cartService;

    private final MemberService memberService;

    /*@PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity order (@RequestBody @Valid CartItemDto cartItemDto, BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            return new ResponseEntity<String> (sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String id = principal.getName();

        Long cartItemId;

        try {
            cartItemId = cartService.addCart(cartItemDto, id);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }*/

    @PostMapping(value = "/cart")
    public ResponseEntity<Map<String, Object>> order (@RequestBody @Valid CartItemDto cartItemDto, BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            return new ResponseEntity<Map<String, Object>>(Collections.singletonMap("message", sb.toString()), HttpStatus.BAD_REQUEST);
        }
        String id = principal.getName();

        Long cartItemId;

        try {
            cartItemId = cartService.addCart(cartItemDto, id);
        } catch (Exception e) {
            return new ResponseEntity<Map<String, Object>>(Collections.singletonMap("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("userId", id);
        responseBody.put("cartItemId", cartItemId);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping(value = "/cart")
    public String orderHist(@RequestParam("userId") String userId, Model model){

        /*log.info("Principal : " + principal.getName());

        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());*/

        log.info("Principal : " + userId);

        List<CartDetailDto> cartDetailList = cartService.getCartList(userId);

        log.info("cartItems : " + cartDetailList);

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

}
