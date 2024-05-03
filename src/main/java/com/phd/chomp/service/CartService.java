package com.phd.chomp.service;

import com.phd.chomp.dto.CartDetailDto;
import com.phd.chomp.dto.CartItemDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface CartService {

    Long addCart(CartItemDto cartItemDto, String id);

    List<CartDetailDto> getCartList(String id);

    boolean validateCartItem(Long cartItemId, String uid);

    void deleteCartItem(Long cartItemId);
}
