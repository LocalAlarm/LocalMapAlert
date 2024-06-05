package com.spring.dongnae.bbs;

import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;

public class BbsServiceClient {

	public static void main(String[] args) {
		System.out.println("--- 스프링 컨테이너 구동전 ----");
		//1. 스프링 컨테이너 구동(XML 파일 읽어서)
		GenericXmlApplicationContext container
			= new GenericXmlApplicationContext("aaa.xml");
		
		System.out.println("--- 스프링 컨테이너 구동후 ----");
		BbsService bbsService = (BbsService)container.getBean("bbsService");
		BbsVO vo = new BbsVO();
		vo.setMapIdx("1");
		List<BbsVO> list = bbsService.getMarkingBbsList(vo);
		
		for(BbsVO bbs : list) {
			System.out.println(bbs.toString());
			
		}
		
		//3. 스프링 컨테이너 종료(close)
		container.close();
	}

}
