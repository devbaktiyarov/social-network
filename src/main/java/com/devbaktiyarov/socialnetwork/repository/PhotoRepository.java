package com.devbaktiyarov.socialnetwork.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.devbaktiyarov.socialnetwork.entity.Photo;

@Repository
public interface PhotoRepository extends MongoRepository<Photo, String>  {
        List<Photo> findByHashtag(String hashtag);

}
