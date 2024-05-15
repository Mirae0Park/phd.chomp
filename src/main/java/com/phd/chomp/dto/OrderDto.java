package com.phd.chomp.dto;

import groovy.transform.Sealed;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Sealed
@ToString
public class OrderDto {
    
    private Long itemId;
    
    private String itemName;
    
    private int price;
    
    private int count;
    
    private LocalDateTime date; // 주문일
}
