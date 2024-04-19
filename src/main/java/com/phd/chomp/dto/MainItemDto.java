package com.phd.chomp.dto;

import com.phd.chomp.constant.ItemSellStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainItemDto {

    private Long id;

    private String itemName;

    private String itemDetail;

    private String imgUrl;

    private Integer price;

    private String cate;

    private Integer stock;

    private ItemSellStatus itemSellStatus;

    @QueryProjection // querydsl로 결과 조회 시 MainItemDto 객체로 바로 받아옴
    public MainItemDto(Long id, String itemName, String itemDetail, String imgUrl,
                       Integer price, String cate, Integer stock, ItemSellStatus itemSellStatus){
        this.id = id;
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
        this.cate = cate;
        this.stock = stock;
        this.itemSellStatus = itemSellStatus;
    }

}
