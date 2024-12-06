package com.example.myfirstsolproj.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
    //물리적인 사진저장 혹은 읽기등을 하려면
    //사진파일과 그로 만들어진 내용들이 필요하다
    // 경로
    @Value("${itemImgLocation}")
    String itemImgLocation;

    public String uploadFile(MultipartFile multipartFile) throws IOException {

        UUID uuid = UUID.randomUUID();  // 서로 다른 개체를 구별하기 위해
                                        // 이름을 부여할 때 사용 실제 사용시 중복될 가능성
                                        // 거의 없기 때문에
        String extension = multipartFile.getOriginalFilename()
                .substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            // .jpg
        String savedFileName = uuid.toString()+extension;    // 사진이름 +.png 이런식




        // 경로
        String fileUploadFullUrl = itemImgLocation+"/"+savedFileName;

        // 물리적인 저장  // 다른방법으로는
        // multipartFile.transferTo(file) 이 있다
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(multipartFile.getBytes());
        fos.close();

        return savedFileName;
    }

    public void removeFile(String imgName){

        String delFileurl = itemImgLocation + "/" + imgName;
        System.out.println(delFileurl);
        System.out.println(delFileurl);
        System.out.println(delFileurl);
        System.out.println(delFileurl);
        File file = new File(delFileurl);

        if (file.exists()){ // 파일존재여부확인
            file.delete();
        }
    }
}
