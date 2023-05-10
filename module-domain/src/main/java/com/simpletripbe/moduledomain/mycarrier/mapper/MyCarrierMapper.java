package com.simpletripbe.moduledomain.mycarrier.mapper;

import com.simpletripbe.moduledomain.batch.dto.MyBagSaveDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketTypeDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketUrlDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.CarrierCountry;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import com.simpletripbe.moduledomain.mycarrier.entity.Ticket;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = MyCarrier.class
)
public interface MyCarrierMapper {

    @Mappings({
            @Mapping(source = "name", target = "name", ignore = true),
            @Mapping(source = "email", target = "email", ignore = true),
            @Mapping(source = "startDate", target = "startDate", ignore = true),
            @Mapping(source = "endDate", target = "endDate", ignore = true)
    })
    List<TicketTypeDTO> toTicketDto(List<Ticket> tickets);

    @Mappings({
            @Mapping(source = "name", target = "name", ignore = true),
            @Mapping(source = "email", target = "user.email", ignore = true),
            @Mapping(source = "startDate", target = "startDate", ignore = true),
            @Mapping(source = "endDate", target = "endDate", ignore = true),
            @Mapping(target = "deleteYn", expression = "java(\"N\")"),
//            @Mapping(target = "creDate", expression = "java(java.time.LocalDateTime.now())")
    })
    MyCarrier toCarrierEntity(CarrierListDTO carrierListDTO);

    @Mappings({
            @Mapping(source = "country", target = "country", ignore = true),
//            @Mapping(target = "creDate", expression = "java(java.time.LocalDateTime.now())")
    })
    CarrierCountry toCarrierCountryEntity(CarrierListDTO carrierListDTO);

    @Mappings({
            @Mapping(source = "type", target = "type"),
            @Mapping(source = "url", target = "ticketUrl"),
            @Mapping(source = "myCarrier", target = "myCarrier"),
            @Mapping(source = "title", target = "title")
    })
    Ticket toTicketEntity(TicketUrlDTO ticketUrlDTO);

    @Mappings({
            @Mapping(source = "type", target = "type", ignore = true),
//            @Mapping(source = "updDate", target = "updDate", ignore = true)
    })
    MyCarrier toMyBagEntity(MyBagSaveDTO myBagSaveDTO);

}
