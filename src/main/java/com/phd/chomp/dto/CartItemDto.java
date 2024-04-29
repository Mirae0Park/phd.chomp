package com.phd.chomp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {

    private Long itemId;

    private int count;

    private Long cartItemId;

    private String itemName;

    private String price;

    private String imgUrl;
}
