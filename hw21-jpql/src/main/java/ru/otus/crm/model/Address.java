package ru.otus.crm.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Getter
@Setter
public class Address {

    public Address() {
    }

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;
}
