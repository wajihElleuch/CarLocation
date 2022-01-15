package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Client;
import services.ClientService;
import services.impl.ClientServiceImpl;
import utils.DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Rent Car");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);

    }
    /*public static void main(String args[]) throws SQLException {
        Connection c = DbConnection.getConnection();
        ClientServiceImpl clientService=new ClientServiceImpl();
        Client client=new Client("ftou7","gremda");
        client.setId(4);
        clientService.updateClient(client);
    }*/
}
