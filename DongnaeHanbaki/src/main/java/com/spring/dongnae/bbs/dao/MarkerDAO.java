package com.spring.dongnae.bbs.dao;

import java.util.List;

import com.spring.dongnae.bbs.MarkerVO;

public interface MarkerDAO {
	// WHERE DEL_YN = 0  적용중
	//마커 제작자로 마커찾기/userEmail 또는 limitRole 필요, 있는 값으로 검색
	List<MarkerVO> getMakerList(MarkerVO vo);

	//makerIdx 필요
	MarkerVO getMaker(MarkerVO vo); 
	int insertMaker(MarkerVO vo);
	int updateMaker(MarkerVO vo);//type, image 변경가능
	int deleteMaker(MarkerVO vo);
}
