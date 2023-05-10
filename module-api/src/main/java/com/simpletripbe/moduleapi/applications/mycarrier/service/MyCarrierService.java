package com.simpletripbe.moduleapi.applications.mycarrier.service;

import com.simpletripbe.moduledomain.mycarrier.api.MainCarrierService;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketTypeDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketUrlDTO;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyCarrierService {

    private final MainCarrierService mainCarrierService;

    @Transactional(readOnly = true)
    public List<String> selectAll(String email) {
        return mainCarrierService
                .selectAll(email);
    }

    @Transactional(readOnly = true)
    public List<TicketTypeDTO> selectTicketAll(String email) {
        return mainCarrierService
                .selectDetailAll(email);
    }

    @Transactional(readOnly = true)
    public void saveOne(CarrierListDTO carrierListDTO) {
        mainCarrierService.addCarrier(carrierListDTO);
    }

    @Transactional(readOnly = true)
    public void editOne(CarrierListDTO carrierListDTO) {
        mainCarrierService.editCarrier(carrierListDTO);
    }

    @Transactional(readOnly = true)
    public void deleteOne(String email) {
        mainCarrierService.deleteCarrier(email);
    }

    @Transactional(readOnly = true)
    public void saveStamp(CarrierListDTO carrierListDTO) {
        mainCarrierService.addStamp(carrierListDTO);
    }

    @Transactional
    public TicketUrlDTO saveUrl(TicketUrlDTO ticketUrlDTO) {
        return mainCarrierService.saveTicketUrl(ticketUrlDTO);
    }

    @Transactional
    public TicketUrlDTO saveFile(TicketUrlDTO ticketUrlDTO, MultipartFile multipartFile) throws FileUploadException {
        return mainCarrierService.saveTicketFile(ticketUrlDTO, multipartFile);
    }


}
