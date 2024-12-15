package com.dailySync.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UserReqDto {
    @NotBlank
    private String userName;
    @Size(min=4,max=20)
    private String password;
    @NotBlank
    private String gender;
    @NotBlank
    private String email;

}