package model;

import java.time.LocalDate;

public class Reservation {
    long id ;
    String date_deb;
    String date_fin;
    long car_id;
    long client_id;

    public Reservation() {
    }

    public Reservation(String date_deb, String date_fin, long car_id, long client_id) {
        this.date_deb = date_deb;
        this.date_fin = date_fin;
        this.car_id = car_id;
        this.client_id = client_id;
    }

    public Reservation(long id, String date_deb, String date_fin, long car_id, long client_id) {
        this.id = id;
        this.date_deb = date_deb;
        this.date_fin = date_fin;
        this.car_id = car_id;
        this.client_id = client_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate_deb() {
        return date_deb;
    }

    public void setDate_deb(String date_deb) {
        this.date_deb = date_deb;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public long getCar_id() {
        return car_id;
    }

    public void setCar_id(long car_id) {
        this.car_id = car_id;
    }

    public long getClient_id() {
        return client_id;
    }

    public void setClient_id(long client_id) {
        this.client_id = client_id;
    }
}
