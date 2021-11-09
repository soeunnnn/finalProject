package com.kh.spring.common;

import java.util.Date;
import java.util.Map;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.spring.member.model.dto.Member;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
public class MailSenderTest {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired //bean에 등록했던 객체 주입받기
	JavaMailSenderImpl mailSender;
	@Autowired //bean에 등록했던 객체 주입받기
	RestTemplate http;
	@Autowired
	ObjectMapper mapper;
	
	@Test
	public void sendEmail()throws Exception{
		MimeMessage msg = mailSender.createMimeMessage();
		msg.setFrom("thdms12333@gmail.com"); 
		msg.setRecipients(Message.RecipientType.TO, "thdms12333@gmail.com");  //메일 받는 사람
		msg.setSubject("메일테스트"); //메일 제목
		msg.setSentDate(new Date());
		msg.setText("<h1>Email Test</h1>", "UTF-8", "html"); //html 양식에 맞춰 메일이 갈 수 있도록 지정(api참고) 메일 내용
		mailSender.send(msg);
	}

	@Test
	public void restTemplateGetTest() throws JsonMappingException, JsonProcessingException, RestClientException {
		
		/*
		 * //MultiValueMap : 하나의 키값으로 여러개의 밸류값을 저장할 수 있도록 하는 객체 MultiValueMap<String,
		 * String> body = new LinkedMultiValueMap<>(); body.add("userId", "test");
		 * body.add("password", "1234");
		 * 
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		 * HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body,
		 * headers);
		 * 
		 * //RestTemplate에서 post메서드로 요청할때 사용하는 메서드 String login =
		 * http.postForObject("http://localhost:8989/member/login", entity,
		 * String.class);
		 * 
		 * logger.debug(login);
		 */
		
		//http통신을 위해 RequestEntity 사용  (카카오api활용해서 json테스트)
		RequestEntity<Void> request = RequestEntity
				.get("https://dapi.kakao.com/v3/search/book?query=java")
				.header("Authorization", "KakaoAK 4cf9c3f319106ea0a9ebb44fe6cdbf2f")
				.build();
		
		//제이슨으로 넘어온 정보들에서 제이슨 개체 필드 값들을 찾아서 꺼내옴(속성명으로 값에 바로 접근이 가능)
		JsonNode root = mapper.readTree(http.exchange(request, String.class).getBody());
		for (JsonNode jsonNode : root.findValues("url")) {
			logger.debug(jsonNode.asText());
		}
	}
	
	//네이버 번역 api활용
	@Test
	public void restTemplatePostTest() throws Exception{
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("source", "en");
		body.add("target", "ko");
		body.add("text", "Method for finding JSON Object fields with specified name, and returning found ones as a List."); //번역할 내용 넣음
		
		RequestEntity<MultiValueMap<String, String>> request = 
				RequestEntity.post("https://openapi.naver.com/v1/papago/n2mt")
				.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.header("X-Naver-Client-Id", "3rm_c4XH5kpUjbEYfjSb")
				.header("X-Naver-Client-Secret", "pW1mssGFy7")
				.body(body);
		
		JsonNode root = mapper.readTree(http.exchange(request, String.class).getBody());
		logger.debug(root.findValue("translatedText").asText());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
