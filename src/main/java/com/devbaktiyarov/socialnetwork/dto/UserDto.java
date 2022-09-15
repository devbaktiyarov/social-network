package com.devbaktiyarov.socialnetwork.dto;

import java.time.LocalDateTime;

import com.devbaktiyarov.socialnetwork.entity.Gender;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String firtsName;
    private String lastName;
    private String bio;
    private String email;
    private Gender gender;
    private LocalDateTime dateCreated;
}
