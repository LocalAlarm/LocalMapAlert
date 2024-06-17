package com.spring.dongnae.custom.ex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dongnae.custom.repo.CustomMapRepository;
import com.spring.dongnae.custom.scheme.MapData;

@RestController
public class CMC {
	
	private final CustomMapRepository customMapRepository;
	
    @Autowired
    public CMC(CustomMapRepository customMapRepository) {
        this.customMapRepository = customMapRepository;
    }
	
    @PostMapping("/submitMapData")
    @ResponseBody
    public String submitMapData(@RequestBody MapData mapData) {
        System.out.println(mapData.toString());
        customMapRepository.save(mapData);
        return "Data received successfully";
    }
}