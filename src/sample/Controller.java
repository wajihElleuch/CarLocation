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
import javafx.scene.input.InputMethodEvent;
import model.Car;
import model.Client;
import model.Reservation;
import services.CarService;
import services.impl.CarServiceImpl;
import services.impl.ClientServiceImpl;
import services.impl.ReservationServiceImpl;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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

    // reservation attribute
    @FXML
    private DatePicker dateDeb;

    @FXML
    private DatePicker dateFin;

    @FXML
    private TableColumn<Reservation, Long> reservationColCar;

    @FXML
    private TableColumn<Reservation, Long> reservationColClient;

    @FXML
    private TableColumn<Reservation, String> reservationColDateDeb;

    @FXML
    private TableColumn<Reservation, String> reservationColDateFin;

    @FXML
    private TableColumn<Reservation, Long> reservationColId;

    @FXML
    private Button reservationCreateBtn;

    @FXML
    private Tab reservationTab;

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private ChoiceBox<String> clientList;

    @FXML
    private ChoiceBox<String> carList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFin.setDisable(true);
        clientList.setDisable(true);
        carList.setDisable(true);


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
        try {
            this.displayReservation();
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
    // car methods
    void displayReservation() throws SQLException {
        ReservationServiceImpl reservationService = new ReservationServiceImpl();
        List<Reservation> reservationList = reservationService.getAll();
        ObservableList<Reservation> observableArrayList =
                FXCollections.observableArrayList(reservationList);
        reservationColId.setCellValueFactory(new PropertyValueFactory<Reservation, Long>("id"));
        reservationColDateDeb.setCellValueFactory(new PropertyValueFactory<Reservation, String>("date_deb"));
        reservationColDateFin.setCellValueFactory(new PropertyValueFactory<Reservation, String>("date_fin"));
        reservationColClient.setCellValueFactory(new PropertyValueFactory<Reservation, Long>("car_id"));
        reservationColCar.setCellValueFactory(new PropertyValueFactory<Reservation, Long>("client_id"));
        reservationTable.setItems(observableArrayList);
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
    @FXML
    void onChangeDateDeb(ActionEvent event) {
        dateFin.setDisable(false);

        System.out.println(dateDeb.getValue());
    }
    @FXML
    void onChangeDateFin(ActionEvent event) throws SQLException {
        clientList.setDisable(false);
        carList.setDisable(false);
        ClientServiceImpl clientService = new ClientServiceImpl();
        List<Client> cclientList = null;
        try {
            cclientList = clientService.getAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ObservableList<String> options = FXCollections.observableArrayList(
                cclientList.stream().map(client -> client.getId() +"_"+client.getFirstname()).collect(Collectors.toList()));
        clientList.setItems(options);
        System.out.println(dateFin.getValue());
        CarServiceImpl carService = new CarServiceImpl();
        List<Car> carList1= null;
        carList1 =carService.getAvailable(dateDeb.getValue().toString(),dateFin.getValue().toString());
        ObservableList<String> carOptions = FXCollections.observableArrayList(
                carList1.stream().map(car -> car.getId() +"_"+car.getMat()+"_"+car.getMark()).collect(Collectors.toList()));
        carList.setItems(carOptions);
    }
    @FXML
    void onCreateReservation(ActionEvent event) throws SQLException {
        ReservationServiceImpl reservationService= new ReservationServiceImpl();
        System.out.println(carList.getValue());
        Reservation reservation = new Reservation(
                dateDeb.getValue().toString(),
                dateFin.getValue().toString(),
                Long.parseLong(carList.getValue().split("_")[0]),
                Long.parseLong(clientList.getValue().split("_")[0])
                );
        System.out.println(reservation);
        reservationService.addReservation(reservation);
        this.displayReservation();
       // System.out.println(clientList.getValue());
        //System.out.println(clientList.getId());
    }
    @FXML
    void onSelectClient(InputMethodEvent event) {
        System.out.println(clientList.getValue());
    }
}
