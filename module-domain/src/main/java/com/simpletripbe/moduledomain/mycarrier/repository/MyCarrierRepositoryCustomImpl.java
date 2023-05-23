package com.simpletripbe.moduledomain.mycarrier.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simpletripbe.moduledomain.batch.dto.MyBagSaveDTO;
import com.simpletripbe.moduledomain.batch.dto.MyBagTicketDTO;
import com.simpletripbe.moduledomain.batch.dto.QMyBagTicketDTO;
import com.simpletripbe.moduledomain.batch.dto.TicketListDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.StorageDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.querydsl.core.types.Projections.constructor;
import static com.simpletripbe.moduledomain.mycarrier.entity.QTicket.ticket;

@Repository
@Transactional(readOnly = true)
public class MyCarrierRepositoryCustomImpl extends QuerydslRepositorySupport implements MyCarrierRepositoryCustom {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public MyCarrierRepositoryCustomImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(MyCarrier.class);
        this.entityManager = entityManager;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<String> findAllByEmail(String email) {

        QMyCarrier q = QMyCarrier.myCarrier;
        QCarrierCountry c = QCarrierCountry.carrierCountry;

        List<Country> results = jpaQueryFactory
                .select(c.country).distinct()
                .from(c)
                .leftJoin(c.myCarrier, q)
                .where(q.deleteYn.eq("N").and(q.user.email.eq(email)))
                .fetch();

        return results.stream().map(Country::getName).collect(Collectors.toList());

    }

    @Override
    public List<MyBagTicketDTO> selectTicketList() {

        QTicket t = ticket;
        QMyCarrier q = QMyCarrier.myCarrier;

        final List<MyBagTicketDTO> results = jpaQueryFactory.select(
                        new QMyBagTicketDTO(
                                t.id,
                                t.type,
                                t.ticketUrl,
                                t.imageUrl,
                                t.title,
                                t.sequence,
                                q.endDate
                        )
                ).from(t)
                .leftJoin(t.myCarrier, q)
                .where(q.deleteYn.eq("N").and(q.type.eq(CarrierType.CARRIER)))
                .fetch();

        return results;

    }

    @Override
    public void updateToMyBag(MyBagSaveDTO dto) {

        QMyCarrier q = QMyCarrier.myCarrier;

         Ticket findTicket = jpaQueryFactory
                .select(QTicket.ticket)
                .from(QTicket.ticket)
                .where(QTicket.ticket.id.eq(dto.getTicketId()))
                .fetchOne();

        jpaQueryFactory.update(q)
                .set(q.type, dto.getType())
                .where(q.id.eq(findTicket.getMyCarrier().getId()))
                .execute();

    }

    @Override
    public List<TicketListDTO> selectCarrierList() {

        QMyCarrier q = QMyCarrier.myCarrier;

        List<TicketListDTO> results = jpaQueryFactory
                .select(constructor(TicketListDTO.class, q.startDate, q.endDate, q.name, q.user))
                .from(q)
                .fetch();

        return results;

    }

    @Override
    public List<Ticket> findTicketByEmail(String email) {

        QMyCarrier q = QMyCarrier.myCarrier;
        QTicket t = ticket;

        List<Ticket> results = jpaQueryFactory
                .selectFrom(t)
                .leftJoin(t.myCarrier, q)
                .distinct()
                .where(
                        q.deleteYn.eq("N").and(q.user.email.eq(email))
                )
                .fetch();

        return results;

    }

    @Override
    public List<StorageDTO> findStorageByEmail(String email) {

        QMyCarrier q = QMyCarrier.myCarrier;

        List<StorageDTO> results = jpaQueryFactory
                .select(constructor(StorageDTO.class, q.id, q.name))
                .from(q)
                .where(q.deleteYn.eq("N").and(q.user.email.eq(email)).and(q.type.eq(CarrierType.STORAGE)))
                .fetch();

        return results;

    }

}
