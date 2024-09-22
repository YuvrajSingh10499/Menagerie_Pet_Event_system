package com.petEvent.petEvent.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pet_id",nullable = false)
    @JsonIgnore
    private Pet pet;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(length = 15)
    private  String type;

    @Column(length = 255)
    private String remark;

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", date=" + date +
                ", type='" + type + '\'' +
                ", remark='" + remark + '\'' +
                ", petId=" + (pet != null ? pet.getId() : "null") + // Include just the ID of the pet
                '}';
    }


}
