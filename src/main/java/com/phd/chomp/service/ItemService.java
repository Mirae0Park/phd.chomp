package com.phd.chomp.service;

import com.phd.chomp.dto.ItemFormDto;
import com.phd.chomp.entity.Item;
import com.phd.chomp.entity.ItemImg;
import com.phd.chomp.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ItemService {

    private final ItemRepository itemRepository;

    private final ItemImgService itemImgService;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        // 상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        log.info("ItemService : " + item);

        // 이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if(i == 0) {
                itemImg.setRepimgYn("Y");
                log.info("repImgYn Y");
            }
            else {
                itemImg.setRepimgYn("N");
                log.info("repImgYn N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }



        return item.getId();
    }
}
