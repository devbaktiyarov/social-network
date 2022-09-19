package com.devbaktiyarov.socialnetwork.dto;


import com.devbaktiyarov.socialnetwork.entity.Gender;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String bio;
    private Gender gender;
}
