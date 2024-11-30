package com.userservice.external.service;

import com.userservice.entities.Rating;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Service
@EnableFeignClients("RATING-SERVICE")
public interface RatingService {

    // get

    // create
    @PostMapping("/ratings")
    Rating createRating(Rating values);

    // update
    @PutMapping("/ratings/{ratingId}")
    Rating updateRating(@PathVariable String ratingId , Rating rating);


}
