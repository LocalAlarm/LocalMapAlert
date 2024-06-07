package com.spring.dongnae.bbs;

import java.util.List;
import java.util.Map;

public interface BbsDAO {
	List<BbsVO> getPagingBbsList(Map<String,String> beginEnd); //페이징 처리 위한 게시글목록
	List<BbsVO> getMarkingBbsList(); //지도 마킹 위한 게시글 목록, 24시간내 작성글 가져오기
	BbsVO getBbs(String BbsIdx);
	int insertBbs(String BbsIdx);
	int updateBbs(String BbsIdx); //값이 있는 항목만 업데이트
	int deleteBbs(String BbsIdx);
}
