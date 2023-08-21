package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String roomName;

    public Room() {
        super();
    }

    public Room(String roomName) {
        super();
        this.roomName = roomName;
    }

    public String getRoomName() {
        return this.roomName;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
