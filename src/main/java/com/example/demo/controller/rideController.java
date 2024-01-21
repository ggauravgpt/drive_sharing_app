package com.example.demo.controller;

import ch.qos.logback.classic.Logger;
import com.example.demo.dbo.User;
import com.example.demo.dbo.Vehicle;
import com.example.demo.model.Journey;
import com.example.demo.model.Ride;
import com.example.demo.service.RideServices;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class rideController {

    private final RideServices rideServices;
    Logger log
            = (Logger) LoggerFactory.getLogger(rideController.class);



    @PostMapping("/create_user")
    @ResponseStatus(value = HttpStatus.OK)
    public User create_user(@RequestBody User userBody) {

        log.info("user created");
        return rideServices.create_user(userBody.getName(), userBody.getGender(), userBody.getAge());
    }

    @PostMapping("/create_vehicle")
    @ResponseStatus(value = HttpStatus.OK)
    public Vehicle create_vehicle(@RequestBody Vehicle vehicleBody) {

        return rideServices.create_vehicle(vehicleBody.getOwnerName(), vehicleBody.getVehicleName()
                , vehicleBody.getRegNo(), vehicleBody.getSeats());
    }

    @PostMapping("/offer_ride")
    @ResponseStatus(value = HttpStatus.OK)
    public Ride offer_ride(@RequestBody Ride rideBody) {

        return rideServices.offer_ride(rideBody.getName()
                , rideBody.getVehicle().getVehicleName()
                , rideBody.getSeats_available()
                , rideBody.getJouney().getOrigin()
                , rideBody.getJouney().getDestination()
                , rideBody.getStart()
                , rideBody.getDuration()
        );
    }
    @PostMapping("/select_ride")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Ride> select_ride(@RequestBody Ride rideBody) {

        return rideServices.select_ride(rideBody.getName()
                , rideBody.getJouney().getOrigin()
                , rideBody.getJouney().getDestination()
                ,rideBody.getSeats_available()
                ,"earliest_ending_ride"
        );
    }
}
