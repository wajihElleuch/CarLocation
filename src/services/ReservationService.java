package services;

import model.Reservation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    void addReservation(Reservation reservation) throws SQLException;
    List<Reservation> getAll() throws SQLException;
}
