package com.example.demo.service;

import com.example.demo.dbo.User;
import com.example.demo.dbo.Vehicle;
import com.example.demo.model.Ride;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RideServices {

    User create_user(String name, String gender, Integer age);

    Vehicle create_vehicle(String name, String vehicleName, String regNo, int seats);

    Ride offer_ride(String name, String vehicleName, Integer seats_available, String origin, String destination, long start, long duration);

    List<Ride> select_ride(String name, String origin, String destination, int seats_requierd, String earliest_ending_ride);

    boolean confirm_ride(int rideId, int seats_requierd);

}
