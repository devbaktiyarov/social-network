package com.devbaktiyarov.socialnetwork.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private String bio;

    @Indexed(unique = true)
    private String email;
    private String password;
    private String role;
    private Gender gender;

    @CreatedDate
    private LocalDateTime dateCreated;
}
