package com.spring.dongnae.bbs.dao;

import java.util.List;
import java.util.Map;

import com.spring.dongnae.bbs.BbsVO;

public interface BbsDAO {
	//삭제된 게시글 :DEL_YN = 1, 기본으로 조회 안함
	//List 메서드 : MapIdx 로 게시글 조회
	//그 외 메서드 : BbsIdx 로 게시글 1개 선택
	
	//페이징 처리 위한 게시글목록/ 비긴,엔드,맵인덱스 필요
	List<BbsVO> getPagingBbsList(Map<String,String> beginEndMapIdx); 
	
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
