package services.impl;

import model.Client;
import services.ClientService;
import utils.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientServiceImpl implements ClientService{
    public ClientServiceImpl() {
    }

    @Override
    public Client addClient(Client client) throws SQLException {
        Connection c= DbConnection.getConnection();
        var newInsertedId=0;
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            Statement stmt = c.createStatement();

            String sql= String.format("INSERT INTO CLIENT (firstname,lastname,address,phoneNumber) "
                    + "VALUES ( '%s', '%s', '%s', %d);",
                    client.getFirstname(),
                    client.getLastname(),
                    client.getAddress(),
                    client.getPhoneNumber());



            var a=stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            var rs = stmt.getGeneratedKeys();

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
        client.setId(newInsertedId);
        return client;
    }

    @Override
    public boolean updateClient(Client client) throws SQLException {
        Connection c= DbConnection.getConnection();
        var newInsertedId=0;
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            Statement stmt = c.createStatement();

            String sql= String.format("UPDATE CLIENT SET firstname='%s',lastname='%s',address='%s',phoneNumber=%d" +
                            "WHERE id = %d;",
                    client.getFirstname(),
                    client.getLastname(),
                    client.getAddress(),
                    client.getPhoneNumber(),
                    client.getId());
            stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Client updated successfully");
        client.setId(newInsertedId);
        return true;
    }

    @Override
    public List<Client> getAll() throws SQLException {
        Connection c= DbConnection.getConnection();
        List<Client> clientList= new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            String sql= String.format("SELECT * FROM CLIENT ORDER BY ID");
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()){
                Client client=new Client(result.getLong(1),
                result.getString(2),
                result.getString(3),
                result.getString(4),
                result.getLong(5));

                clientList.add(client);
            }
            stmt.close();
            c.close();


        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return clientList;
    }

    @Override
    public Client getById(long id) throws SQLException {
        Connection c= DbConnection.getConnection();
        Client client= new Client();
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            Statement stmt = c.createStatement();

            String sql= String.format("SELECT * FROM CLIENT WHERE id= %d",id);
            var result=stmt.executeQuery(sql);
            if (result.next()){
                client.setId(result.getLong(1));
                client.setFirstname(result.getString(2));
                client.setLastname(result.getString(3));
                client.setAddress(result.getString(4));
                client.setPhoneNumber(result.getLong(5));
            }else{
                throw new IllegalArgumentException("client with id " +id+ " not found");
            }
            stmt.close();
            c.commit();
            c.close();


        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("client deleted successfully");
        return client;
    }


    @Override
    public boolean deleteClient(long id) throws SQLException {
        Connection c= DbConnection.getConnection();
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            Statement stmt = c.createStatement();

            String sql= String.format("DELETE FROM CLIENT WHERE ID = '%d';",
                    id);
            stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("client deleted successfully");
        return true;
    }
}
