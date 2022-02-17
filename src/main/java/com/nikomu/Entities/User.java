package com.nikomu.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private List<PhoneNumber> phoneNumbers;

    public User() {
    }

    public User(String surname, String firstname, String lastname) {
        this.surname = surname;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        if(phoneNumbers == null) {
            phoneNumbers = new ArrayList<>();
        }

        phoneNumber.setUser(this);
        phoneNumbers.add(phoneNumber);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public String toString() {
        return "User {" +
                "id = " + id +
                ", surname = '" + surname + '\'' +
                ", firstname = '" + firstname + '\'' +
                ", lastname = '" + lastname + '\'' +
                ", address = " + address +
                ", phoneNumbers = " + phoneNumbers +
                '}';
    }
}
