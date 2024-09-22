package com.petEvent.petEvent.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Pet {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,length = 20)
    private String name;

    @Column(length = 30)
    private String owner;

    @Column(length = 30)
    private String species;

    @Enumerated(EnumType.STRING)

    private Sex sex;

    private Date birth;

    private Date death;

    @OneToMany(mappedBy = "pet",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Event> events;

    public enum Sex{
        @JsonProperty("m") M,
        @JsonProperty("f") F;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", species='" + species + '\'' +
                ", sex=" + sex +
                ", birth=" + birth +
                ", death=" + death +
                ", eventCount=" + (events != null ? events.size() : 0) +
                '}';
    }

}
