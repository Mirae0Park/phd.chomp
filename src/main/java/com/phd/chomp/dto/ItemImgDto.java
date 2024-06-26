package com.phd.chomp.dto;

import com.phd.chomp.entity.ItemImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemImgDto {

    private Long id;

    private String imgnew; // 이미지 파일명

    private String imgori; // 원본 파일명

    private String imgurl; // 이미지 조회 경로

    private String imgrep; // 대표 이미지 여부


    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg) {
        // ItemImg 객체로 받아 ItemImg 객체의 자료형과 멤버변수의 이름이 같을 때 ItemImgDto로 값을 복사해서 반환
        return modelMapper.map(itemImg, ItemImgDto.class);
    }

}
