package com.kh.billida.locker.image;

import lombok.Data;

@Data
public class Insert_DB_Image_Model {

	private String idx;
	private byte[] image;
 	
	public Insert_DB_Image_Model(String idx, byte[] image) {
		this.idx = idx;
		this.image = image;
	}
	
	
}
