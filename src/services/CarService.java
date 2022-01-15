package services;

import model.Car;
import model.Client;

import java.sql.SQLException;
import java.util.List;

public interface CarService {
    Car addCar(Car car) throws SQLException;
    boolean updateCar(Car car) throws SQLException;
    List<Car> getAll() throws SQLException;
    Car getById(long id) throws SQLException;
    boolean deleteCar(long id) throws SQLException;
}
