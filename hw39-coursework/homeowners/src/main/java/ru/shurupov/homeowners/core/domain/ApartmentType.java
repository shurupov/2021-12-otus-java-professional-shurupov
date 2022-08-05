package ru.shurupov.homeowners.core.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "apartment_type")
@Data
public class ApartmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
