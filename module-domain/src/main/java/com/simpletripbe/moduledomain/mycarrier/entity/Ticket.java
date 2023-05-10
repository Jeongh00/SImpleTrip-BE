package com.simpletripbe.moduledomain.mycarrier.entity;

import com.simpletripbe.moduledomain.basetime.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Ticket extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrier_id")
    private MyCarrier myCarrier;

    private String ticketUrl;

    @Enumerated(value = EnumType.STRING)
    private TicketType type;

    private String title;
    private String imageUrl;
    private Integer sequence;

}
