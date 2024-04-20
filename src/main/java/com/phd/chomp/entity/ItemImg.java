package com.phd.chomp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="itemimage")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "item")
public class ItemImg extends BaseEntity{

    @Id
    @Column(name = "iimgno")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgName; //이미지 파일명

    private String oriImgName; //원본 이미지 파일명

    private String imgUrl; //이미지 조회 경로

    /*private String repImgName; // 대표 이미지*/

    private String repimgYn; //대표 이미지 여부

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private Item item;

    public void updateItemImg(String oriImgName, String imgName, String imgurl){
        // 원본 이미지 파일명, 업데이트할 이미지 파일명, 이미지 경로를 파라미터로 입력받아 이미지 정보를 업데이트
        this.oriImgName = oriImgName;
        this.imgUrl = imgurl;
        this.imgName = imgName;


    }

}
