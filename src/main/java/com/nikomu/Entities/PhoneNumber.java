package com.nikomu.Entities;

import javax.persistence.*;

@Entity
@Table(name = "phone_numbers")
public class PhoneNumber {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "operator_code")
    private  String operator_code;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public PhoneNumber() {
    }

    public PhoneNumber(String phoneNumber, String operator_code) {
        this.phoneNumber = phoneNumber;
        this.operator_code = operator_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOperatorCode() {
        return operator_code;
    }

    public void setOperatorCode(String operator_code) {
        this.operator_code = operator_code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PhoneNumber {" +
                "id = " + id +
                ", phoneNumber = '" + phoneNumber + '\'' +
                ", operator_code = '" + operator_code + '\'' +
                ", user = " + user +
                '}';
    }
}
