package com.rail.railEase.repository;

import com.rail.railEase.dto.TicketDto;
import com.rail.railEase.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {

    @Query(value = "SELECT new com.rail.railEase.dto.TicketDto(t.id, t.user.id, t.route.id,t.route.departureStation.stationName, t.route.arrivalStation.stationName, t.createdAt)" +
            " FROM Ticket t WHERE t.user.id= :user_id")
    List<TicketDto> findAllTickets(@Param("user_id") Integer user_id);
}
