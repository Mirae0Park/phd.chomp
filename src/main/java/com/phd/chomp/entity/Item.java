package com.phd.chomp.entity;

import com.phd.chomp.constant.ItemSellStatus;
import com.phd.chomp.dto.ItemFormDto;
import jakarta.persistence.*;
import lombok.*;

@Entity //클래스를 엔티티로 선언
@Table(name="item") //엔티티와 매핑할 테이블을 지정(테이블 명)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName; // 상품명

    @Column(nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private Integer stock; // 재고

    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Column(nullable = false)
    private String cate; // 카테고리

    private ItemSellStatus itemSellStatus;

    public void updateItem(ItemFormDto itemFormDto){
        this.itemName = itemFormDto.getItemName();
        this.price = itemFormDto.getPrice();
        this.cate = itemFormDto.getCate();
        this.stock = itemFormDto.getStock();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

}
