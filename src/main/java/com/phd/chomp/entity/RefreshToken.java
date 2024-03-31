package com.phd.chomp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class RefreshToken {

    @Id
    @Column(name = "rt_key")
    private String key; // Member ID 값이 들어감

    @Column(name = "rt_value")
    private String value; // Refresh Token String 이 들어감

    @Builder
    public RefreshToken(String key, String value){
        this.key = key;
        this.value = value;
    }

    public RefreshToken updateValue(String token){
        this.value = token;
        return this;
    }
}
