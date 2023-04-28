package com.simpletripbe.moduleapi.applications.mypage.service;


import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import com.simpletripbe.moduledomain.mypage.api.MainMyPageService;
import com.simpletripbe.moduledomain.mypage.dto.MyPageDocumentListDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageProfileListDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageStampListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MainMyPageService mainMyPageService;

    public MyPageProfileListDTO selectMyProfile(String nickname) {

        return mainMyPageService.selectMyProfile(nickname);

    }

    public List<MyPageDocumentListDTO> selectMyDocumentList(String nickname) {

        return mainMyPageService.selectMyDocument(nickname);

    }

    public List<MyPageStampListDTO> selectMyStampList(String nickname) {

        return mainMyPageService.selectMyStamp(nickname);

    }

}
