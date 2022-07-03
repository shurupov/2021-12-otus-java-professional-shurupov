package ru.shurupov.homeowners.core.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "building")
@Data
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String address;
}
