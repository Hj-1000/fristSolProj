package com.example.myfirstsolproj.repository;

import com.example.myfirstsolproj.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    public List<ItemImg> findByItemIno(Long ino);

    public ItemImg findByItemInoAndRepImgYn(Long ino, String val);
}
