package com.example.myfirstsolproj.repository;

import com.example.myfirstsolproj.entity.Item;
import com.example.myfirstsolproj.repository.search.ItemSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> , ItemSearch {

    public List<Item> findByItemNm(String itemNm);

    // select i from Item i where i.ino == i.member.id 인거
    public Item findByInoAndMemberUserID(Long mno, String id);

}
