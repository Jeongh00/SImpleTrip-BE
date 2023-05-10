package com.simpletripbe.moduledomain.mycarrier.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import com.simpletripbe.moduledomain.mycarrier.entity.TicketType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketUrlDTO {

    private Long id;
    private TicketType type;
    private String url;

    private MyCarrier myCarrier;
    private String title;

    public void setMapper(MyCarrier myCarrier, String title) {
        this.myCarrier = myCarrier;
        this.title = title;
    }

    public void setMapper(MyCarrier myCarrier, String title, String url) {
        this.myCarrier = myCarrier;
        this.title = title;
        this.url = url;
    }

    public TicketUrlDTO(String url, String title) {
        this.url = url;
        this.title = title;
    }
}
