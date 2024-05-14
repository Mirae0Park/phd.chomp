package com.phd.chomp.controller;

import com.phd.chomp.config.auth.UserAdapter;
import com.phd.chomp.dto.CartDetailDto;
import com.phd.chomp.service.CartService;
import com.phd.chomp.service.MemberServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class CartController {

    private final CartService cartService;

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

    /*@PostMapping(value = "/cart")
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
    }*/

    @PostMapping(value="/cart")
    public ResponseEntity cartPage(Principal principal, Model model){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/cart")
    public String orderHist(@AuthenticationPrincipal UserAdapter userAdapter, Model model){

        log.info("cart 페이지 접근");

        log.info("Principal : " + userAdapter.getUsername());

        List<CartDetailDto> cartDetailList = cartService.getCartList(userAdapter.getUsername());

        log.info("cartItems : " + cartDetailList);

        model.addAttribute("cartItems", cartDetailList);

        return "cart/cart";
    }

    /*서버 로그 쿠키 테스트 확인용*/
    @GetMapping("/cookie/test")
    public String test(@CookieValue(value = "Authorization", defaultValue = "", required = false) String test) {
        log.info(test);
        return "index";
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
