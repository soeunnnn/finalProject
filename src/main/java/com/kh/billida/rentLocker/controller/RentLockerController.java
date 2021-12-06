package com.kh.billida.rentLocker.controller;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.billida.member.model.dto.Member;
import com.kh.billida.rentLocker.model.dto.Locker;
import com.kh.billida.rentLocker.model.service.RentLockerService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("rentLocker")
public class RentLockerController {

	private final RentLockerService rentLockerService;
	
	@GetMapping("rent-form")
	public void rental(Model model){
		
		
		LocalDate today = LocalDate.now();
		
		model.addAttribute("today", today);
		
		
	}
	
	@Scheduled(cron = "55 59 23 * * *")
	public void returnBatch() {
		
		LocalDate today = LocalDate.now();
		rentLockerService.returnBatch(today);

	}
	
	@Transactional
	@PostMapping("rent-form")
	public String rentalForm(HttpSession session, Member member, Locker locker){
		
		member = (Member)session.getAttribute("authentication");
		locker.setUserCode(member.getUserCode());
		rentLockerService.insertLocker(locker);
		
		Long insertedLockerId = rentLockerService.selectInsertedLocker();
		locker.setImgToClob(locker.getImgToClob());
		locker.setLockerId(insertedLockerId);
		
		rentLockerService.insertClob(locker);
		return "redirect:/review/myLocker-list";
	}	
	
}