package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name = "Cab")
public class Cab{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cabId;
    private Integer perKmRate;
    private boolean available;

    @OneToOne
    @JoinColumn(name = "Driver")
    private Driver driver;


    public Cab() {
    }

    public Cab(Integer cabId, Integer perKmRate, boolean available, Driver driver) {
        this.cabId = cabId;
        this.perKmRate = perKmRate;
        this.available = available;
        this.driver = driver;
    }

    public Cab(Integer perKmRate, boolean available) {
        this.perKmRate = perKmRate;
        this.available = available;
    }

    public Integer getCabId() {
        return cabId;
    }

    public void setCabId(Integer cabId) {
        this.cabId = cabId;
    }

    public Integer getPerKmRate() {
        return perKmRate;
    }

    public void setPerKmRate(Integer perKmRate) {
        this.perKmRate = perKmRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
