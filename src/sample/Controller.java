package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Car;
import model.Client;
import services.CarService;
import services.impl.CarServiceImpl;
import services.impl.ClientServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    // client attribute
    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField adress;

    @FXML
    private TableColumn<Client, String> clientColAdress;

    @FXML
    private TableColumn<Client, String> clientColFirstname;

    @FXML
    private TableColumn<Client, Long> clientColId;

    @FXML
    private TableColumn<Client, String> clientColLastname;

    @FXML
    private TableColumn<Client, Long> clientColPhoneNumber;

    @FXML
    private Tab clientTab;


    @FXML
    private Button createClientBtn;

    @FXML
    private Button deleteClientBtn;

    @FXML
    private Button updateClientBtn;
    @FXML
    private TableView<Client> clientTable;

    @FXML
    void carTabChange(ActionEvent event) {

    }

    @FXML
    void clientTabChange(ActionEvent event) {
    }

    Client selectedClient = new Client();

    // car attribute

    @FXML
    private Button carCreateBtn;

    @FXML
    private Button carDeleteBtn;

    @FXML
    private Tab carTab;

    @FXML
    private TableView<Car> carTable;


    @FXML
    private TextField color;

    @FXML
    private TableColumn<Car, String> colorColCar;

    @FXML
    private TableColumn<Car, Long> idColCar;

    @FXML
    private TextField mark;

    @FXML
    private TableColumn<Car, String> markColCar;

    @FXML
    private TextField mat;

    @FXML
    private TableColumn<Car, String> matColCar;

    @FXML
    private Button carUpdateBtn;

    Car selectedCar = new Car();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deleteClientBtn.setDisable(true);
        updateClientBtn.setDisable(true);
        clientTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                System.out.println(t1);
                if (t1 != null) {
                    Client client = (Client) t1;
                    firstname.setText(client.getFirstname());
                    lastname.setText(client.getLastname());
                    adress.setText(client.getAddress());
                    phoneNumber.setText(String.valueOf(client.getPhoneNumber()));
                    selectedClient = client;
                    deleteClientBtn.setDisable(false);
                    updateClientBtn.setDisable(false);
                }

            }
        });
        try {
            this.displayClient();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        carTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Car>() {
            @Override
            public void changed(ObservableValue observableValue, Car o, Car t1) {
                if (t1 != null) {
                    Car car = (Car) t1;
                    mat.setText(car.getMat());
                    mark.setText(car.getMark());
                    color.setText(car.getColor());
                    selectedCar = car;
                    carDeleteBtn.setDisable(false);
                    carUpdateBtn.setDisable(false);
                }
            }
        });
        carDeleteBtn.setDisable(true);
        carUpdateBtn.setDisable(true);
        try {
            this.displayCars();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void clearClientForm() {
        this.firstname.clear();
        this.lastname.clear();
        this.adress.clear();
        this.phoneNumber.clear();
    }

    @FXML
    void createClient(ActionEvent event) throws SQLException {
        Client client = new Client(firstname.getText(), lastname.getText(),
                adress.getText(), Long.parseLong(phoneNumber.getText()));
        ClientServiceImpl clientService = new ClientServiceImpl();
        clientService.addClient(client);
        this.clearClientForm();
        try {
            this.displayClient();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    void displayClient() throws SQLException {
        ClientServiceImpl clientService = new ClientServiceImpl();
        List<Client> clientList = clientService.getAll();
        ObservableList<Client> observableArrayList =
                FXCollections.observableArrayList(clientList);
        clientColId.setCellValueFactory(new PropertyValueFactory<Client, Long>("id"));
        clientColFirstname.setCellValueFactory(new PropertyValueFactory<Client, String>("firstname"));
        clientColLastname.setCellValueFactory(new PropertyValueFactory<Client, String>("lastname"));
        clientColAdress.setCellValueFactory(new PropertyValueFactory<Client, String>("address"));
        clientColPhoneNumber.setCellValueFactory(new PropertyValueFactory<Client, Long>("phoneNumber"));
        clientTable.setItems(observableArrayList);
    }

    @FXML
    void deleteClient(ActionEvent event) throws SQLException {
        ClientServiceImpl clientService = new ClientServiceImpl();
        clientService.deleteClient(selectedClient.getId());
        this.clearClientForm();
        deleteClientBtn.setDisable(true);
        updateClientBtn.setDisable(true);
        this.displayClient();
    }

    @FXML
    void updateClent(ActionEvent event) throws SQLException {
        ClientServiceImpl clientService = new ClientServiceImpl();
        Client client = new Client(firstname.getText(), lastname.getText(),
                adress.getText(), Long.parseLong(phoneNumber.getText()));
        client.setId(selectedClient.getId());
        clientService.updateClient(client);
        this.clearClientForm();
        deleteClientBtn.setDisable(true);
        updateClientBtn.setDisable(true);
        this.displayClient();
    }

    // car methods
    void displayCars() throws SQLException {
        CarServiceImpl carService = new CarServiceImpl();
        List<Car> carList = carService.getAll();
        ObservableList<Car> observableArrayList =
                FXCollections.observableArrayList(carList);
        idColCar.setCellValueFactory(new PropertyValueFactory<Car, Long>("id"));
        matColCar.setCellValueFactory(new PropertyValueFactory<Car, String>("mat"));
        markColCar.setCellValueFactory(new PropertyValueFactory<Car, String>("mark"));
        colorColCar.setCellValueFactory(new PropertyValueFactory<Car, String>("color"));
        carTable.setItems(observableArrayList);
    }

    @FXML
    void createCar(ActionEvent event) throws SQLException {

        Car car = new Car(mat.getText(), mark.getText(),
                color.getText());
        CarServiceImpl carService = new CarServiceImpl();
        carService.addCar(car);
        this.clearClientForm();
        try {
            this.displayCars();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @FXML
    void deleteCar(ActionEvent event) throws SQLException {
        CarServiceImpl carService= new CarServiceImpl();
        carService.deleteCar(selectedCar.getId());
        this.displayCars();
        this.carDeleteBtn.setDisable(true);
        this.carUpdateBtn.setDisable(true);
    }

    private void clearCarForm() {
        mat.clear();
        mark.clear();
        color.clear();
    }

    @FXML
    void updateCar(ActionEvent event) throws SQLException {
        CarServiceImpl carService = new CarServiceImpl();
        Car car = new Car(mat.getText(), mark.getText(), color.getText());
        car.setId(selectedCar.getId());
        carService.updateCar(car);
        this.clearCarForm();
        this.carUpdateBtn.setDisable(true);
        this.carDeleteBtn.setDisable(true);
        this.displayCars();

    }
}
