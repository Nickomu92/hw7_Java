package com.nikomu.Entities;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "street")
    private String street;

    @Column(name = "house")
    private String house;

    @Column(name = "apartment")
    private String apartment;

    @OneToOne
    @JoinColumn(name = "city_id")
    private City city;

    public Address() {
    }

    public Address(String street, String house, String apartment, City city) {
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    @Override
    public String toString() {
        return "Address {" +
                "id = " + id +
                ", city = " + city +
                ", street = '" + street + '\'' +
                ", house = '" + house + '\'' +
                ", apartment = '" + apartment + '\'' +
                '}';
    }
}
