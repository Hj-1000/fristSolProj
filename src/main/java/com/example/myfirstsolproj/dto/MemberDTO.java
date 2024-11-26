package com.example.myfirstsolproj.dto;

import com.example.myfirstsolproj.constant.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class MemberDTO {

    private Long mno;

    @Email
    @NotBlank(message = "이메일을 빈칸일 수 없습니다.")
    @Size(min = 2, max = 50, message = "이메일은 2~50글자 사이입니다.")
    private String email;
    @NotBlank(message = "이름은 빈칸일 수 없습니다.")
    @Size(min = 2, max = 10, message = "이름은 2~50글자 사이입니다.")
    private String name;
    @NotBlank(message = "ID는 빈칸일 수 없습니다.")
    @Size(min = 2, max = 20, message = "ID는 2~50글자 사이입니다.")
    private String userID;
    @NotBlank(message = "비밀번호는 빈칸일 수 없습니다.")
    @Size(min = 8, message = "비밀번호는 최소 8, 최대 16자입니다.")
    private String password;

    private String address;

    private Role role;
}
