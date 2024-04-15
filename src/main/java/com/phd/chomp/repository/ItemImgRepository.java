package com.phd.chomp.repository;

import com.phd.chomp.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    List<ItemImg> findByItemIdOrderByIdAsc(Long ItemId);

    ItemImg findByIdAndRepimgYn(Long id, String repimgYn); // 상품의 대표 이미지를 찾음
}
