package com.kh.billida.rentalHistory.model.service;

import com.kh.billida.locker.model.dto.Locker;
import com.kh.billida.rentalHistory.model.dto.Rental;

public interface RentalService {

	void insertRental(Rental rental);
 
	Locker selectLocker(Long param);

}
