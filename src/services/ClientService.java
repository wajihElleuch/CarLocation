package services;


import model.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientService {

    Client addClient(Client client) throws SQLException;
    boolean updateClient(Client client) throws SQLException;
    List<Client> getAll() throws SQLException;
    Client getById(long id) throws SQLException;
    boolean deleteClient(long id) throws SQLException;
}
