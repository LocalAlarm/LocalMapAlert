package com.spring.dongnae.bbs.dao;

import java.util.List;

import com.spring.dongnae.bbs.MakerVO;

public interface MakerDAO {
	List<MakerVO> getMakerList();
	MakerVO getMaker(MakerVO vo); 
	int insertMaker(MakerVO vo);
	int updateMaker(MakerVO vo);
	int deleteMaker(MakerVO vo);
}
