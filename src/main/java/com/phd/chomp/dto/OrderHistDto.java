package com.phd.chomp.dto;

import com.phd.chomp.constant.OrderStatus;
import com.phd.chomp.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {

    private Long orderId; // 주문 아이디
    private String orderDate; // 주문 날짜
    private OrderStatus orderStatus; // 주문 상태

    private MemberRequestDto memberRequestDto;

    private String address; // 주소

    private int orderTotalPrice;

    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    public OrderHistDto(Order order){
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
        this.orderTotalPrice = order.getTotalPrice();
        this.memberRequestDto = new MemberRequestDto();
        this.address = order.getAddress();
    }

    public void addOrderItemDto(OrderItemDto orderItemDto){
        orderItemDtoList.add(orderItemDto);
    }
}
