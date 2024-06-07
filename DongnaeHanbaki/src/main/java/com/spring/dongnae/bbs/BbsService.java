package com.spring.dongnae.bbs;

import java.util.List;

public interface BbsService {
	//페이징 처리 위한 게시글목록/ 비긴,엔드,맵인덱스 필요
		List<BbsVO> getPagingBbsList(String begin, String end, BbsVO vo); 
		
		//지도 마킹 위한 게시글 목록, 24시간내 작성글 가져오기/ 맵인덱스필요
		List<BbsVO> getMarkingBbsList(BbsVO vo); 
		
		//bbsIdx 필요
		BbsVO getBbs(BbsVO vo);
		int insertBbs(BbsVO vo); //게시글 인서트
		int updateBbs(BbsVO vo); //값이 있는 항목만 업데이트
		int deleteBbs(BbsVO vo); //DEL_YN = 0 -> 1
		
		//삭제된 게시글들 리턴 (필요한가?)
		List<BbsVO> getDelBbsList();
}
