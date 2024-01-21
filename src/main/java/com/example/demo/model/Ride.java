package com.example.demo.model;

import com.example.demo.dbo.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ride {

    private Integer rideId ;
    private String name;
    private Vehicle vehicle;
    private int seats_available;
    private Journey jouney;
    private long start;
    private long end;
    private long duration;

}
