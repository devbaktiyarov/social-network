package com.devbaktiyarov.socialnetwork.controller;

import java.util.List;

import com.devbaktiyarov.socialnetwork.security.AuthDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devbaktiyarov.socialnetwork.entity.Photo;
import com.devbaktiyarov.socialnetwork.repository.PhotoRepository;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/photos")
public class PhotoController {

    private final PhotoRepository photoRepository;

    public PhotoController(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }


    @GetMapping("/")
    public ResponseEntity<List<Photo>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(photoRepository.findAll());
    }

    
    @PostMapping("/")
    public ResponseEntity<Photo> savePhoto(Authentication authentication,  @RequestBody Photo photo) {
        AuthDetails authDetails = (AuthDetails) authentication.getPrincipal();
        photo.setOwnerUsername(authDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(photoRepository.save(photo));
    }

    @GetMapping("/{hashtag}")
    public ResponseEntity<List<Photo>> findPhotoByHashtag(@PathVariable String hashtag) {
        hashtag = "#" + hashtag;
        log.info("findPhotoByHashtag(): Found photo:  " + photoRepository.findByHashtag(hashtag));
        return ResponseEntity.ok().body(photoRepository.findByHashtag(hashtag));
    }
}
