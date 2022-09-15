package com.devbaktiyarov.socialnetwork.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "photos")
public class Photo {

    @Id
    private ObjectId id;

    @CreatedBy
    private ObjectId createdById;

    @ReadOnlyProperty
    @DocumentReference(lookup = "{'user': ?#{#self.createdById} }")
    private User createdBy;

    private String caption;
    private String latitude;
    private String longitude;
    private String imagePath;
    private List<Comment> comments;
    private List<String> hashtag;
    private List<Like> likes;

    @CreatedDate
    private LocalDateTime dateCreated;

    @LastModifiedDate
    private LocalDateTime dateUpdated;

}
