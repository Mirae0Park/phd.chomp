package com.phd.chomp.repository;

import com.phd.chomp.constant.ItemSellStatus;
import com.phd.chomp.dto.ItemSearchDto;
import com.phd.chomp.dto.MainItemDto;
import com.phd.chomp.dto.QMainItemDto;
import com.phd.chomp.entity.QItem;
import com.phd.chomp.entity.QItemImg;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

    private JPAQueryFactory queryFactory; // 동적으로 쿼리를 생성하기 위해서 JPAQueryFactory 클래스를 사용

    // JPAQueryFactory의 생성자로 EntityManager 객체를 넣어줌
    public ItemRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
        // 상품 판매 상태 조건이 전체일 경우에는 null을 리턴, 결과값이 null이면 where절에서 해당 조건이 무시됨
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression regDtsAfter(String searchDateType){

        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }

        return QItem.item.regTime.after(dateTime);

    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){

        if (StringUtils.equals("title", searchBy)){
            return QItem.item.itemName.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createdBy", searchBy)){
            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }
        return  null;
    }

    private BooleanExpression itemNameLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemName.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){

        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        log.info("getMainItemPage query parameters: itemSearchDto = {}, pageable = {}", itemSearchDto, pageable);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (itemSearchDto.getItemName() != null && !itemSearchDto.getItemName().isEmpty()){
            booleanBuilder.and(item.itemName.contains(itemSearchDto.getItemName()));
        }

        if (itemSearchDto.getCate() != null && !itemSearchDto.getCate().isEmpty()){
            booleanBuilder.and(item.cate.eq(itemSearchDto.getCate()));
        }

        booleanBuilder.and(item.itemSellStatus.ne(ItemSellStatus.valueOf(ItemSellStatus.PAUSE.name())));

        log.info("booleanBuilder : {} ", booleanBuilder);

        List<MainItemDto> content = queryFactory
                .select(
                        new QMainItemDto(
                                item.id,
                                item.itemName,
                                item.itemDetail,
                                itemImg.imgUrl,
                                item.price,
                                item.cate,
                                item.stock,
                                item.itemSellStatus
                        )
                )
                .from(itemImg)
                .join(itemImg.item, item) // itemImg와 item을 내부 조인함
                .where(itemImg.repimgYn.eq("y"), booleanBuilder) // 상품 이미지의 경우 대표 이미지만 불러옴
                .where(itemNameLike(itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.repimgYn.eq("Y"))
                .where(itemNameLike(itemSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

}
