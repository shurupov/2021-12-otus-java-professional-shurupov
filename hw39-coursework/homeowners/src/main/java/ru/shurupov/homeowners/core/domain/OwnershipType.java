package ru.shurupov.homeowners.core.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ownership_type")
@Data
public class OwnershipType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
