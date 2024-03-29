package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.List;

import static com.driver.model.TripStatus.*;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		customerRepository2.save(customer);
		//Save the customer in database
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		customerRepository2.deleteById(customerId);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		Customer customer = customerRepository2.findById(customerId).get();
		List<Driver> driverList = driverRepository2.findAll();
		int driverId = Integer.MAX_VALUE;
		for(Driver driver : driverList)
		{
			if(driver.getCab().isAvailable() && driver.getDriverId() < driverId)
			{
				driverId = driver.getDriverId();
			}
		}
		if(driverId == Integer.MAX_VALUE)
		{
			throw new Exception("No cab available!");
		}

		Driver driver = driverRepository2.findById(driverId).get();
		TripBooking tripBooking = new TripBooking(fromLocation,toLocation,distanceInKm,CONFIRMED,0,customer,driver);
		tripBooking.getDriver().getTripBookingList().add(tripBooking);
		tripBookingRepository2.save(tripBooking);
		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		tripBooking.getCustomer().getTripBookingList().remove(tripBooking);
		tripBooking.getDriver().getTripBookingList().remove(tripBooking);
		tripBooking.setStatus(CANCELED);
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		tripBooking.setStatus(COMPLETED);
	}
}
