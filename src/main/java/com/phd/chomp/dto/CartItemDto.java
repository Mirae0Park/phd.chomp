package com.phd.chomp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartItemDto {

    private Long itemId;

    private int count;

    private Long cartItemId;

    private String itemName;

    private String price;

    private String imgUrl;

    private List<CartItemDto> cartItemDtoList = new ArrayList<>();
}
