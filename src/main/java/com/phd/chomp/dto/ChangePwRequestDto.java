package com.phd.chomp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePwRequestDto {

    private String uid;
    private String exPw;
    private String newPw;

}
