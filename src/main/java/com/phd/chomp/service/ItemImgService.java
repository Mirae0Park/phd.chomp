package com.phd.chomp.service;

import com.phd.chomp.entity.ItemImg;
import com.phd.chomp.repository.ItemImgRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ItemImgService {

    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgurl = "";

        // 파일 업로드
        if(!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            imgurl = "/images/productImages/" + imgName;
        }

        // 상품 이미지 정보 저장
        itemImg.updateItemImg(oriImgName, imgName, imgurl);
        itemImgRepository.save(itemImg);

    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{

        log.info("ItemImgService updateItemImg");

        if (!itemImgFile.isEmpty()){ // 상품 이미지를 수정한 경우 상품 이미지를 업데이트
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);

            // 기존 이미지 파일 삭제
            if (!StringUtils.isEmpty(savedItemImg.getImgName())){
                fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            String imgUrl = "/images/productImages/" + imgName;
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);

        }
    }
}
