package com.phd.chomp.service;

import com.phd.chomp.dto.CartDetailDto;
import com.phd.chomp.dto.CartItemDto;
import com.phd.chomp.entity.Cart;
import com.phd.chomp.entity.CartItem;
import com.phd.chomp.entity.Item;
import com.phd.chomp.entity.Member;
import com.phd.chomp.repository.CartItemRepository;
import com.phd.chomp.repository.CartRepository;
import com.phd.chomp.repository.ItemRepository;
import com.phd.chomp.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class CartServiceImpl implements CartService{

    private final ItemRepository itemRepository;

    private final MemberRepository memberRepository;

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    @Override
    public Long addCart(CartItemDto cartItemDto, String id) {

        // 상품 조회
        Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException :: new);

        // 회원 조회
        Member member = memberRepository.findWithRoleSetByUid(id);

        // 장바구니 조회
        Cart cart = cartRepository.findByMemberId(member.getId());

        if (cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        // 현재 상품이 장바구니에 이미 들어가 있는 지
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        if (savedCartItem != null){
            // 장바구니에 이미 있는 상품이면 기존 수량에 현재 장바구니에 담을 수량 만큼 더해줌
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
        } else {
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }

    @Override
    public List<CartDetailDto> getCartList(String id) {

        log.info("id : " + id);

        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findWithRoleSetByUid(id);

        log.info("member : " + member);

        Cart cart = cartRepository.findByMemberId(member.getId());

        log.info("cart : " + cart);

        if (cart == null){
            return cartDetailDtoList;
        }

        cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());

        log.info("cartDetailDtoList : " + cartDetailDtoList);

        return cartDetailDtoList;
    }

    @Override
    public boolean validateCartItem(Long cartItemId, String uid) {
        Member member = memberRepository.findWithRoleSetByUid(uid);
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        Member saveMember = cartItem.getCart().getMember();

        if (!StringUtils.equals(member.getUid(), saveMember.getUid())){
            return false;
        }
        return true;
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);

    }
}
