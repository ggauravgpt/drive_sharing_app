package com.example.demo.dbo;

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
public class Vehicle {

    private int vehicleId;
    private String ownerName;
    private String vehicleName;
    private String regNo;
    private int seats;
}
