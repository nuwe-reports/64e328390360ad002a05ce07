package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Patient extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Override
    public int getAge() {
        return super.getAge();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    public String getLastName() {
        return super.getLastName();
    }

    @Override
    public void setAge(int age) {
        super.setAge(age);
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        super.setLastName(lastName);
    }

    public Patient() {
        super();
    }

    public Patient(String firstName, String lastName, int age, String email) {
        super(firstName, lastName, age, email);
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
