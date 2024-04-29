package com.phd.chomp.repository;

import com.phd.chomp.dto.CartDetailDto;
import com.phd.chomp.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long CartId, Long ItemId);

    @Query("select new com.phd.chomp.dto.CartDetailDto(ci.id, i.itemName, i.price, ci.count, im.imgUrl) " +
            "from CartItem ci, ItemImg im join ci.item i where ci.cart.id = :id and im.item.id = ci.item.id and im.repimgYn = 'Y' order by ci.regTime desc")
    List<CartDetailDto> findCartDetailDtoList(Long id);
}
