package com.rating.services;

import com.rating.entities.Rating;

import java.util.List;

public interface RatingService {

    // create
    Rating createRating(Rating rating);

    // get all ratings
    List<Rating> getAllRating();

    // get rating by user ID
    List<Rating> getAllRatingByUserId(String userId);

    // get rating by hotelId
    List<Rating> getAllRatingByHotelId(String hotelId);

}
