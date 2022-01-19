package services.impl;

import model.Car;
import model.Client;
import services.CarService;
import utils.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarServiceImpl implements CarService {
    @Override
    public Car addCar(Car car) throws SQLException {
        Connection c= DbConnection.getConnection();
        var newInsertedId=0;
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            Statement stmt = c.createStatement();

            String sql= String.format("INSERT INTO CAR (mat,mark,color) "
                            + "VALUES ( '%s', '%s', '%s');",
                    car.getMat(),
                    car.getMark(),
                    car.getColor());



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
        car.setId(newInsertedId);
        return car;
    }

    @Override
    public boolean updateCar(Car car) throws SQLException {
        Connection c= DbConnection.getConnection();
        var newInsertedId=0;
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            Statement stmt = c.createStatement();

            String sql= String.format("UPDATE CAR SET mat='%s',mark='%s',color='%s'" +
                            "WHERE id = %d;",
                    car.getMat(),
                    car.getMark(),
                    car.getColor(),
                    car.getId());
            stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Car updated successfully");
        car.setId(newInsertedId);
        return true;
    }

    @Override
    public List<Car> getAll() throws SQLException {
        Connection c= DbConnection.getConnection();
        List<Car> carList= new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            String sql= String.format("SELECT * FROM CAR ORDER BY ID");
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()){
                Car car=new Car(result.getLong(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4));

                carList.add(car);
            }
            stmt.close();
            c.close();


        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return carList;
    }

    @Override
    public List<Car> getAvailable(String dateDeb,String dateFin) throws SQLException {
        Connection c= DbConnection.getConnection();
        List<Car> carList= new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            String sql= String.format("select * from car where id not in(select car_id from reservation where '%s' between date_debut AND date_fin OR '%s' between date_debut And date_fin OR '%s'< date_debut And '%s' >date_fin)",
                    dateDeb,dateFin,dateDeb,dateFin);
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()){
                Car car=new Car(result.getLong(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4));

                carList.add(car);
            }
            stmt.close();
            c.close();


        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return carList;
    }

    @Override
    public Car getById(long id) throws SQLException {
        return null;
    }

    @Override
    public boolean deleteCar(long id) throws SQLException {
        Connection c= DbConnection.getConnection();
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            Statement stmt = c.createStatement();

            String sql= String.format("DELETE FROM CAR WHERE ID = '%d';",
                    id);
            stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("car deleted successfully");
        return true;
    }
}
