package com.example.myfirstsolproj.repository.search;

import com.example.myfirstsolproj.dto.PageRequestDTO;
import com.example.myfirstsolproj.entity.Item;
import com.example.myfirstsolproj.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;

public class ItemSearchImpl extends QuerydslRepositorySupport implements ItemSearch{
    public ItemSearchImpl() {
        super(Item.class);
    }

    @Override
    public Page<Item> getExpertItemPage(PageRequestDTO pageRequestDTO, Pageable pageable, String userID) {
        QItem qItem = QItem.item;

        JPQLQuery<Item> query = from(qItem);    // select * from item

        System.out.println(query);  // select * from item
        System.out.println("------------------------");
        // types에 있는 값을 검색하는데 있을 때 없을 때에 따라 동적으로 쿼리문을
        // 작성해보자
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        String [] types = pageRequestDTO.getTypes();    // 들어온 검색 타입
        String keyword = pageRequestDTO.getKeyword();   // 들어온 키워드
        String searchDateType = pageRequestDTO.getSearchDateType(); // 들어온 날짜분류
        LocalDateTime localDateTime = LocalDateTime.now();  // 현재 시간

        if (searchDateType == null || searchDateType.equals("all") || searchDateType.equals("")){

        }else if (searchDateType.equals("1d")){
            booleanBuilder.and(qItem.regTime.after(localDateTime.minusDays(1)));
        }else if (searchDateType.equals("1w")){
            booleanBuilder.and(qItem.regTime.after(localDateTime.minusWeeks(1)));
        }else if (searchDateType.equals("1m")){
            booleanBuilder.and(qItem.regTime.after(localDateTime.minusMonths(1)));
        }else if (searchDateType.equals("6m")){
            booleanBuilder.and(qItem.regTime.after(localDateTime.minusMonths(6)));
        }

        System.out.println("-------------------------------");
        System.out.println(query);

        if (types !=null && types.length >0 && keyword != null){
            for (String str : types){
                switch (str){
                    case "n" : // 상품이름
                        booleanBuilder.or(qItem.itemNm.contains(keyword));
                        break;
                    case "d" : // 상품상세설명
                        booleanBuilder.or(qItem.itemDetail.contains(keyword));
                        break;
                } // switch 문
            }// for 문
        } // if 문

        // 이제 검색 조건을 만든다
        // 여기 booleanBuilder는 위의 세세한 조건들을 다 만족하여 넘어온 booleanbuilder이다.
        // query에 다음의 조건을 넣어주도록 하자
        query.where(booleanBuilder);
        System.out.println(query);
        System.out.println("----------------------------------");

        query.where(qItem.ino.gt(0L)); // select * from item where item.ino > 0
        query.where(qItem.member.userID.eq(userID));    // select * from item where item.member의 유저 아이디가 해당 글 올린 사람의 아이디인거
                                                        // 그러면 해당 글은 userID를 같이 가지고 있겠지 컨트롤러에서 잘 넘겨줘야함
        System.out.println(query);
        System.out.println("----------------------------------");

        // 페이징
        // todo 생각해볼것 : 여기서 말하는 this 가 어떤 객체인가?
        this.getQuerydsl().applyPagination(pageable, query);
        // 데이터는 List 타입으로 받을거임
        List<Item> itemList = query.fetch();

        // 총 게시물의 수는 row의 수이다
        // todo 알아올것 : 여기 count 는 왜 소문자 long 타입인가?
        long count =
                query.fetchCount();

        return new PageImpl<>(itemList, pageable, count);
    }

    @Override
    public Page<Item> getUserItemPage(PageRequestDTO pageRequestDTO, Pageable pageable, Long ino) {
        return null;
    }
}
