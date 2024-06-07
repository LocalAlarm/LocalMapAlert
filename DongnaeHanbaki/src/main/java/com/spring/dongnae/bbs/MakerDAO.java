package com.spring.dongnae.bbs;

import java.util.List;

public interface MakerDAO {
	List<MakerVO> getMakerList();
	MakerVO getMaker(String makerIdx);
	int insertMaker(MakerVO vo);
	int updateMaker(MakerVO vo);
	int deleteMaker(String makerIdx);
}
