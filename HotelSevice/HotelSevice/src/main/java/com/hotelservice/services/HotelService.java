package com.hotelservice.services;

import com.hotelservice.entities.Hotel;

import java.util.List;

public interface HotelService {

    // create
    Hotel create(Hotel hotel);

    // get all hotels
    List<Hotel> getAllHotel();

    // get single hotel by hotelId
    Hotel getSingleHotel(String hotelId);



}
