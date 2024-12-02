package com.example.myfirstsolproj.dto;

import com.example.myfirstsolproj.entity.Item;
import com.example.myfirstsolproj.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDTO {

    private Long rno;

    private Long ino;

    private Long mno;

    @NotBlank(message = "댓글은 공백일 수 없습니다.")
    @Size(min = 2, max = 200, message = "댓글은 2 ~ 200자 사이입니다.")
    private String replyContent;

    private ItemDTO itemDTO;      // 댓글이 달릴 글번호

    private MemberDTO memberDTO;  // 댓글의 작성자

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    public ReplyDTO setItemDTO(ItemDTO itemDTO) {
        this.itemDTO = itemDTO;
        return this;
    }

    public ReplyDTO setMemberDTO(MemberDTO memberDTO) {
        this.memberDTO = memberDTO;
        return this;
    }
}
