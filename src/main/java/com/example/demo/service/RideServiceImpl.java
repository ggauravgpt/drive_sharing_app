package com.example.demo.service;

import com.example.demo.dbo.User;
import com.example.demo.dbo.Vehicle;
import com.example.demo.model.Journey;
import com.example.demo.model.Ride;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RideServiceImpl implements RideServices {

    private static Map<String, User> userMap = new HashMap<>();
    private static Map<Integer, Vehicle> vehicalInfoMap = new HashMap<>();
    private static Integer vehiclId = 0;
    private static Integer rideId = 0;

    private static Map<Integer, Ride> rideInfoMap = new HashMap<>();
    private static Map<Journey, List<Integer>> rides_availableMap = new HashMap<>();


    @Override
    public User create_user(String name, String gender, Integer age) {

        User user = User.builder().name(name).gender(gender).age(age).build();
        userMap.put(name, user);

        return user;
    }

    @Override
    public Vehicle create_vehicle(String ownerName, String vehicleName, String regNo, int seats) throws RuntimeException {

        Vehicle vehicle ;
        if (userMap.containsKey(ownerName)) {
            User user = userMap.get(ownerName);
            vehiclId++;
            vehicle = Vehicle.builder().vehicleId(vehiclId).ownerName(ownerName)
                    .vehicleName(vehicleName).regNo(regNo).seats(seats).build();

            vehicalInfoMap.put(vehiclId, vehicle);
            user.setVehicleId(vehiclId);
            userMap.put(ownerName, user);

        } else {
            System.out.println("user not exist");
            throw new RuntimeException("user not exist");
        }

        return vehicle;
    }

    @Override
    public Ride offer_ride(String name, String vehicleName, Integer seats_available, String origin, String destination, long start, long duration) {
        Ride ride = null;

        if (userMap.containsKey(name) && userMap.get(name).getVehicleId() > 0) {
            Journey journey = Journey.builder().origin(origin).destination(destination).build();

            Vehicle vehicleinfo = vehicalInfoMap.get(userMap.get(name).getVehicleId());
            if (vehicleinfo.getVehicleName().equalsIgnoreCase(vehicleName)) {
                rideId++;
                ride = Ride.builder().name(name).rideId(rideId).vehicle(vehicleinfo).seats_available(seats_available)
                        .jouney(journey).start(start).duration(duration).build();

                List<Integer> rideList = new ArrayList<>();

                if (rides_availableMap.containsKey(journey)) {
                    rideList = rides_availableMap.get(journey);
                }
                rideList.add(rideId);
                rides_availableMap.put(journey, rideList);
                rideInfoMap.put(rideId,ride);


            }

        } else if (ride == null) {
            System.out.println("user or vehical not exist");
            throw new RuntimeException("user or vehical not exist");
        }

        return ride;

    }

    @Override
    public List<Ride> select_ride(String name, String origin, String destination, int seats_requierd, String pref) {

        Journey journey = Journey.builder().origin(origin).destination(destination).build();
        List<Integer> rideIds = rides_availableMap.get(journey);

        List<Ride> rides = new ArrayList<>();
        for (int rideId : rideIds) {
            if (rideInfoMap.containsKey(rideId)) {
                Ride ride = rideInfoMap.get(rideId);
                if (ride.getStart() > System.currentTimeMillis() && ride.getSeats_available() >= seats_requierd) {
                    rides.add(ride);
                }
            }
        }
        if (pref.equalsIgnoreCase("earliest_ending_ride")) {
            rides = rides.stream().sorted((a, b) -> (int) (b.getEnd() - a.getEnd())).collect(Collectors.toList());
        } else if (pref.equalsIgnoreCase("lowest_duration_ride")) {

            rides = rides.stream().sorted((a, b) -> (int) (b.getDuration() - a.getDuration())).collect(Collectors.toList());
        }

        if (confirm_ride(rideId, seats_requierd)) {
            return rides;
        }
        return null;
    }

    @Override
    public boolean confirm_ride(int rideId, int seats_requierd) {
        Ride ride = rideInfoMap.get(rideId);
        ride.setSeats_available(ride.getSeats_available() - seats_requierd);
        rideInfoMap.put(rideId, ride);
        return true;
    }
}
