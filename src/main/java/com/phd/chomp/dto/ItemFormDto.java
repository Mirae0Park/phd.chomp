package com.phd.chomp.dto;

import com.phd.chomp.constant.ItemSellStatus;
import com.phd.chomp.entity.Item;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemFormDto {
    private Long id;
    
    private String itemName; // 상품명
    
    private int price; // 가격
    
    private Integer stock; // 재고
    
    private String itemDetail; // 상품 상세 설명

    private String cate; // 카테고리

    private ItemSellStatus itemSellStatus; // 판매상태

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem(){ // 엔티티 객체의 데이터를 복사하여 복사한 객체를 반환해줌
        return modelMapper.map(this, Item.class);
    }



}
