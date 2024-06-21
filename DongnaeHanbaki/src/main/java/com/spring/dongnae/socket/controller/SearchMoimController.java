package com.spring.dongnae.socket.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dongnae.socket.dto.MoimDto;
import com.spring.dongnae.socket.dto.UserRoomsDto;
import com.spring.dongnae.socket.scheme.Moim;
import com.spring.dongnae.socket.service.MoimService;
import com.spring.dongnae.socket.service.UserRoomsService;
import com.spring.dongnae.utils.auth.GetAuthenticInfo;

@RestController
@RequestMapping("/moim-search")
public class SearchMoimController {
	@Autowired
	private MoimService moimService;
	@Autowired
	UserRoomsService userRoomsService;
	@Autowired
	GetAuthenticInfo getAuthenticInfo;
	@PostMapping(value = "/name", consumes = "text/plain", produces = "application/json")
    public Page<Moim> searchMoims(@RequestBody String searchData) {
        // 사용자 정보를 가져와서 userRoomsDto를 생성
        UserRoomsDto userRoomsDto = userRoomsService.getUserRoomsDtoById(getAuthenticInfo.GetToken());

        // 두 리스트를 가져와서 ID를 추출하고 합침
        List<String> masterMoims = userRoomsDto.getMasterMoims().stream()
                .map(MoimDto::getId)
                .collect(Collectors.toList());

        List<String> moims = userRoomsDto.getMoims().stream()
                .map(MoimDto::getId)
                .collect(Collectors.toList());

        List<String> excludeIds = Stream.concat(masterMoims.stream(), moims.stream())
                .collect(Collectors.toList());

        return moimService.searchMoims(searchData, excludeIds);
    }

}
