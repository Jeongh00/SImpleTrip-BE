package com.simpletripbe.moduleapi.applications.mycarrier.service;

import com.simpletripbe.moduledomain.mycarrier.api.MainCarrierService;
import com.simpletripbe.moduledomain.mycarrier.dto.*;
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
    public List<TicketDTO> saveUrl(String email, TicketUrlDTO ticketUrlDTO) {
        return mainCarrierService.saveTicketUrl(email, ticketUrlDTO);
    }

    @Transactional
    public List<TicketDTO> saveFile(String email, TicketUrlDTO ticketUrlDTO, MultipartFile multipartFile) throws FileUploadException {
        return mainCarrierService.saveTicketFile(email, ticketUrlDTO, multipartFile);
    }

    public List<TicketDTO> selectTicketAll(String email, Long carrierId) {
        return mainCarrierService
                .selectTicketAll(email, carrierId);
    }

    @Transactional
    public void updateTicketOrder(String email, TicketEditListDTO ticketEditListDTO) {
        mainCarrierService.updateTicketOrder(email, ticketEditListDTO);
    }

    @Transactional
    public void updateTicketTitle(String email, TicketEditDTO ticketEditDTO) {
        mainCarrierService.updateTicketTitle(email, ticketEditDTO);
    }

    @Transactional
    public void deleteTicket(String email, Long carrierId, Long ticketId) {
        mainCarrierService.deleteTicket(email, carrierId, ticketId);
    }
}
