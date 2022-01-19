package services.impl;

import model.Car;
import model.Reservation;
import services.ReservationService;
import utils.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {
    @Override
    public void addReservation(Reservation reservation) throws SQLException {
        Connection c= DbConnection.getConnection();
        var newInsertedId=0;
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            Statement stmt = c.createStatement();

            String sql= String.format("INSERT INTO RESERVATION (date_debut,date_fin,client_id,car_id) "
                            + "VALUES ( '%s', '%s', %d, %d);",
                    reservation.getDate_deb(),
                    reservation.getDate_fin(),
                    reservation.getClient_id(),
                    reservation.getCar_id()
                    );



            stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();

            while (rs.next()){
                newInsertedId=rs.getInt(1);
            }
            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    @Override
    public List<Reservation> getAll() throws SQLException {
        Connection c= DbConnection.getConnection();
        List<Reservation> reservationList= new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            String sql= String.format("SELECT * FROM RESERVATION ORDER BY ID");
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()){
                Reservation reservation=new Reservation(result.getLong(1),
                        result.getString(2),
                        result.getString(3),
                        result.getLong(4),
                        result.getLong(5));

                reservationList.add(reservation);
            }
            stmt.close();
            c.close();


        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return reservationList;
    }
}
