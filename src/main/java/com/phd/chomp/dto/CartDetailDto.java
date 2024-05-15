package com.phd.chomp.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CartDetailDto {

    private Long cartItemId; // 장바구니 상품 아이디

    private String itemName; // 상품명

    private int price; // 상품 금액

    private int count; // 수량

    private String imgUrl; // 상품 이미지 경로

    private String orderTotalPrice; // 총 주문 금액

    private List<Long> cartItemIds; // 배열 추가

    // 기본 생성자
    public CartDetailDto() {
    }

    public CartDetailDto(Long cartItemId, String itemName, int price, int count, String imgUrl){
        this.cartItemId = cartItemId;
        this.itemName = itemName;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }

    // 역직렬화에 사용할 생성자
    @JsonCreator
    public CartDetailDto(@JsonProperty("cartItemId") Long cartItemId,
                         @JsonProperty("itemName") String itemName,
                         @JsonProperty("price") int price,
                         @JsonProperty("count") int count,
                         @JsonProperty("imgUrl") String imgUrl,
                         @JsonProperty("orderTotalPrice") String orderTotalPrice,
                         @JsonProperty("cartItemIds") List<Long> cartItemIds,
                         @JsonProperty("cartDetailDto") List<CartItemDto> cartDetailDto) {
        this.cartItemId = cartItemId;
        this.itemName = itemName;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
        this.orderTotalPrice = orderTotalPrice;
        this.cartItemIds = cartItemIds;
        this.cartDetailDto = cartDetailDto;
    }

    @JsonProperty("cartDetailDto")
    private List<CartItemDto> cartDetailDto;

}
