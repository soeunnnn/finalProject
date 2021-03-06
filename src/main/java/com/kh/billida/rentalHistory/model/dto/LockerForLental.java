package com.kh.billida.rentalHistory.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class LockerForLental {
	
	private Long lockerId;
	private String userCode;
	private String lockerTitle;
	private String lockerContent;
	private String lockerImage;
	private String lockerSize;
	private String lockerPassword;
	private String location;
	private Date rentableDateStart;
	private Date rentableDateEnd;
	private Long rentStatus;
	private String imgToClob;
	private float latitude;
	private float longitude;
	
}



