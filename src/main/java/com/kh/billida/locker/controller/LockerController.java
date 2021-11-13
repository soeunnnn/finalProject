package com.kh.billida.locker.controller;

import java.util.ArrayList;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.kh.billida.locker.model.dto.Locker;


import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/locker")
public class LockerController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*
	 * @Inject private LockerService lockerService;
	 */
	//업로드 폼 -> /WEB-INF/views/uploadLockerImage.jsp로 포워드
	@GetMapping("/uploadLockerImage")
	public void uploadImage() {
		logger.info("/upload.......");
	}
	
	//업로드 처리 파일 여러개를 전달 받으려면 배열 구조의 객체를 파라메터로 선언
	@PostMapping("/uploadLockerImagePost")
	public void uploadLockerImagePost(ArrayList<MultipartFile> files) {
		
		//파일 정보 확인
		// forEach(람다식) : files의 데이터를 한개 씩 꺼내서 file에 저장해서 실행
		// 출력만 하고 저장 안함, 저장하려면 저장하려넌 File 객체를 만들고 file.transterTo(file객체)
		files.forEach(file -> {
			logger.info("---------------------");
			logger.info("name : " + file.getOriginalFilename());
			logger.info("size : " + file.getSize());
			//file.transferTo();
		});
	}
	
	
	/*
	 * @RequestMapping(value="/create", method=RequestMethod.GET) public void
	 * createGET(Locker locker, Model model) throws Exception{
	 * System.out.println("/locker/create 입니다. GET방식"); }
	 * 
	 * 
	 * @GetMapping(value = "/create", method=RequestMethod.POST) public void
	 * createPost(Locker locker, Model model) throws Exception {
	 * System.out.println("/board/create Post방식 입니다.");
	 * System.out.println(locker.toString());
	 * 
	 * lockerService. }
	 */
	
}
