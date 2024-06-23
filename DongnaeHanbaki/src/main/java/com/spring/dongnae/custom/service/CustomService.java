package com.spring.dongnae.custom.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.spring.dongnae.custom.repo.CustomRepository;
import com.spring.dongnae.custom.scheme.CustomMarker;

@Service("customService")
public class CustomService {
	
	private final CustomRepository customRepository;
	
	@Autowired
	public CustomService(CustomRepository customRepository) {
		this.customRepository = customRepository;
		System.out.println(">> CustomService() 객체생성");
	}
	
	public CustomMarker saveMarker(CustomMarker customMarker) {
		return customRepository.save(customMarker);
	}
	
	public Optional<CustomMarker> selectMarker(int MapIdx) {
		return customRepository.findByMapIdx(MapIdx);
	}
}
