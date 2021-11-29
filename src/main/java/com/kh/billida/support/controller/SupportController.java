package com.kh.billida.support.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.kh.billida.common.paging.Criteria;
import com.kh.billida.common.paging.Paging;
import com.kh.billida.member.model.dto.Member;
import com.kh.billida.support.model.dto.Support;
import com.kh.billida.support.model.service.SupportService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("support")
public class SupportController {
	
	private final SupportService supportService;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// ����&�Ű� ������ ����
	@GetMapping("support-index")
	public void supportIndex() {}

	// �Ű� ������ ����
	@GetMapping("report-main")
	public void reportMain() {}
	
	// �Ű� ������ �ۼ� �� DB�� ����
	@PostMapping("report-main")
	public String reportMainPost(Model model 
								,@ModelAttribute Support support
								,HttpSession session) {
		LocalDate now = LocalDate.now();
		
		Map<String, Object> commandMap = new HashMap<String, Object>();
		commandMap.put("userId", support.getUserId());
		commandMap.put("reportTitle", support.getReportTitle());
		commandMap.put("reportContent", support.getReportContent());
		Object put = commandMap.put("reportDate", now);
		supportService.reportInsertPost(commandMap);
		
		System.out.println(now);
		System.out.println(commandMap);
		return "redirect:/support/support-index";
	}
	
	// �Ű� �Խ��� ����
		@GetMapping("report-board")
		public String reportBoardList(Model model
									, Criteria cri
									, HttpSession session
									) {

			//������ ���� �Է¹��� criMap����
			Map<String, Object> criMap = new HashMap<String, Object>();
			// pageNum�� cri.getPgeNum() �Է�
			criMap.put("pageNum", cri.getPageNum());
			// amount�� cri.getAmount()�Է�
			criMap.put("amount", cri.getAmount());
			//criMap�� ���� �߰�
			
			//������ ���� �Է�
			List<Map<String, Object>> getReportListPaging = supportService.getReportListPaging(criMap);
			
			//����Ʈ�� �Խñ� ���� total�� �޾ƿ�
			int total = supportService.getSupportTotal();
			
			//����¡ ȣ�� �� cri��, total�� �Է�
			Paging paging = new Paging(cri, total);
			
			Map<String, Object> map = new HashMap<String, Object>();
			//List<Map<String, Object>> selectPage = supportService.getReportListPaging(map);
			//list�� reportListMap���� �Է�
			map.put("list", getReportListPaging);
			//paging�� paging���� �Է�
			map.put("paging", paging);
			//map�� ���� �Է�
			model.addAllAttributes(map);

			logger.info("criMap : "+ criMap);
			logger.info("getReportListPaging : " + getReportListPaging);
			logger.info("paging : " + paging);
			
			return "support/report-board";
			
		}

		
	// �Ű� �� ������ ����
	@GetMapping("report-detail")
	public void reportDetail(Model model, String reportIdx) {
		Map<String, Object> commandMap = supportService.reportDetailPage(reportIdx);
		model.addAllAttributes(commandMap);
	}
	
	// �Ű� �� ���������� '�Ű�ó�����' Ŭ���� 0->1�� ����
	@PostMapping("report-addResult")
	public String reportAddResult () {
		supportService.reportAddResult();
		System.out.println("���ư��� �뱼�뱼");
		return "redirect:/support/report-board";
	}
	
	
	
//	@PostMapping("report-board")
//	public String reportBoardList(Model model
//								,@RequestParam(defaultValue="1") int currentPage
//								, String search
//								, String keyword
//								, HttpSession session
//								,Criteria cri) {
//		Member member = (Member) session.getAttribute("authentication");
//		String userCode = member.getUserCode();
//
//		Map<String, Object> commandMap = new HashMap<String, Object>();
//		commandMap.put("pageNum", cri.getPageNum());
//		commandMap.put("amount", cri.getAmount());
//		
//		
//		List<Map<String, Object>> list = supportService.getSupportListPaging(commandMap);
//		//�����ڵ忡 �ش��ϴ� �繰�Դ뿩����Ʈ ���� �޾ƿ���
//		int total = supportService.getSupportListPaging(commandMap);
//		Paging paging = new Paging(cri, total);
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("list", list);
//		map.put("paging", paging);
//		map.put("authentication", member);
//		model.addAllAttributes(map);
//		
//		return "review/rent-list";
//			}
	
	
	// �Ű� ���� �Է� �� ����
	//@PostMapping("report-main")
	//public String reportInsert(Support support) {
	//	supportService.reportInsert(support);
	//	return "report-main";
	//}
	
	
	
	
}
