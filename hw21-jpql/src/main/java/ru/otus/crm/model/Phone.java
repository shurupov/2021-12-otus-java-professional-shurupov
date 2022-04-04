package ru.otus.crm.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "phone")
@Getter
@Setter
public class Phone {

    public Phone() {}

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;
}
