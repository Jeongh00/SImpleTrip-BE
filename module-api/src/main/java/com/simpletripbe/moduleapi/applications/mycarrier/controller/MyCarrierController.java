package com.simpletripbe.moduleapi.applications.mycarrier.controller;

import com.simpletripbe.moduleapi.applications.login.jwt.JwtFilter;
import com.simpletripbe.moduleapi.applications.login.jwt.JwtTokenProvider;
import com.simpletripbe.moduleapi.applications.mycarrier.service.MyCarrierService;
import com.simpletripbe.modulecommon.common.response.ApiResponse;
import com.simpletripbe.modulecommon.common.util.EmptyResponse;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketTypeDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketUrlDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "MyCarrierController", description = "나의 캐리어 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/myCarrier")
public class MyCarrierController {

    private final MyCarrierService myCarrierService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 캐리어 목록 조회
     */
    @Operation(summary = "컈리어 전체 목록 조회 api", description = "selectAll")
    @GetMapping("selectAll")
    public ApiResponse<List<String>> selectAll(HttpServletRequest request) {

        String refreshToken = request.getHeader(JwtFilter.AUTHORIZATION_HEADER).substring(7);
        String email = jwtTokenProvider.getUserEmail(refreshToken);

        final List<String> responses
                = myCarrierService.selectAll(email);

        return ApiResponse.success(responses);

    }

    /**
     * 캐리어 추가
     */
    @Operation(summary = "캐리어 추가 api", description = "addCarrier")
    @PostMapping("addCarrier")
    public ApiResponse<EmptyResponse> addCarrier(
            HttpServletRequest request,
            @RequestBody CarrierListDTO carrierListDTO
    ) {

        String refreshToken = request.getHeader(JwtFilter.AUTHORIZATION_HEADER).substring(7);
        String email = jwtTokenProvider.getUserEmail(refreshToken);

        carrierListDTO.setEmail(email);

        myCarrierService.saveOne(carrierListDTO);

        return ApiResponse.success(EmptyResponse.of());
    }

    /**
     * 캐리어 수정
     */
    @Operation(summary = "캐리어 수정 api", description = "editCarrier")
    @PutMapping("editCarrier")
    public ApiResponse<EmptyResponse> editCarrier(
            HttpServletRequest request,
            @RequestBody CarrierListDTO carrierListDTO
    ) {

        String refreshToken = request.getHeader(JwtFilter.AUTHORIZATION_HEADER).substring(7);
        String email = jwtTokenProvider.getUserEmail(refreshToken);

        carrierListDTO.setEmail(email);

        myCarrierService.editOne(carrierListDTO);

        return ApiResponse.success(EmptyResponse.of());
    }

    /**
     * 캐리어 삭제 - soft delete 로 dbsts 만 S 로 변경
     */
    @Operation(summary = "캐리어 삭제 api", description = "deleteCarrier")
    @PutMapping("deleteCarrier")
    public ApiResponse<EmptyResponse> deleteCarrier(
            HttpServletRequest request
    ) {

        String refreshToken = request.getHeader(JwtFilter.AUTHORIZATION_HEADER).substring(7);
        String email = jwtTokenProvider.getUserEmail(refreshToken);

        myCarrierService.deleteOne(email);

        return ApiResponse.success(EmptyResponse.of());
    }

    /**
     * 체크표시 선택 시 스탬프 생성
     */
    @Operation(summary = "스탬프 생성 api", description = "addStamp")
    @PostMapping("addStamp")
    public ApiResponse<EmptyResponse> addStamp(
            @RequestBody CarrierListDTO carrierListDTO
    ) {

        myCarrierService.saveStamp(carrierListDTO);

        return ApiResponse.success(EmptyResponse.of());
    }

    /**
     * 상세페이지 - 전체 티켓 조회
     */
    @Operation(summary = "전체 티켓 목록 조회 api", description = "selectTicketAll")
    @GetMapping("selectTicketAll")
    public ApiResponse<List<TicketTypeDTO>> selectTicketAll(
            HttpServletRequest request
    ) {

        String refreshToken = request.getHeader(JwtFilter.AUTHORIZATION_HEADER).substring(7);
        String email = jwtTokenProvider.getUserEmail(refreshToken);

        final List<TicketTypeDTO> responses
                = myCarrierService.selectTicketAll(email);

        return ApiResponse.success(responses);

    }

    /**
     * 상세 페이지 - 티켓 링크 저장
     */
    @Operation(summary = "티켓 url 추가 api", description = "addTicketInfo")
    @PostMapping("addTicketURL")
    public ApiResponse<TicketUrlDTO> addTicketInfo(
            @RequestBody TicketUrlDTO ticketUrlDTO
    ) {

        return ApiResponse.success(myCarrierService.saveUrl(ticketUrlDTO));

    }

    /**
     * 상세 페이지 - 티켓 이미지, 파일 저장
     */
    @Operation(summary = "티켓 파일 추가 api", description = "addTicketFile")
    @PostMapping("addTicketFile")
    public ApiResponse<TicketUrlDTO> addTicketFile(
            @RequestPart(value = "dto") TicketUrlDTO ticketUrlDTO,
            @RequestPart(value = "file")MultipartFile multipartFile
    ) throws FileUploadException {

        return ApiResponse.success(myCarrierService.saveFile(ticketUrlDTO, multipartFile));

    }
}
