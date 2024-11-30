package com.userservice.services.impl;

import com.userservice.entities.Hotel;
import com.userservice.entities.Rating;
import com.userservice.entities.User;
import com.userservice.external.service.HotelService;
import com.userservice.repositories.UserRepository;
import com.userservice.services.UserService;
import com.userservice.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;



@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public User saveUser(User user) {
        // generate unique userID
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        // TODO: IMPLEMENT RATING SERVICE CALL USING REST TEMPLATE
        return userRepository.findAll();
    }

    // get single user
    @Override
    public User getUser(String userId) {
        // get user from database with the help of user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Resource Not Found on the Server on given userId !!" + userId));
        // fetch rating of the above user from RATING SERVICE
        // http://localhost:8083/ratings/users/f192a41a-1558-4bb7-9a70-0438cea7f723

        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+ user.getUserId() , Rating[].class);
        logger.info("{}",ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {

            // API call to hotel to get the hotel
            //http://localhost:8082/hotels/ca1f5d68-1b94-4810-8c45-5eef19087d2b

            // Normal approach from 71-74
           // ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
           // Hotel hotel = forEntity.getBody();
           // logger.info("response status code: {}", forEntity.getStatusCode());

            // Feign client Approach
            Hotel hotel = hotelService.getHotel(rating.getHotelId());

            // set the hotel to rating
            rating.setHotel(hotel);
            //return the rating
            return rating;
        }).collect(Collectors.toList());
        user.setRatings(ratingList);
        return user;

    }

    @Override
    public User deleteUser(String userId) {
        userRepository.deleteById(userId);
        return null;
    }
}
