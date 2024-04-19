package com.phd.chomp.repository;

import com.phd.chomp.dto.ItemSearchDto;
import com.phd.chomp.dto.MainItemDto;
import com.phd.chomp.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
