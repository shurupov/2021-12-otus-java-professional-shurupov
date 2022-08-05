package ru.shurupov.homeowners.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "apartment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Building.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne(targetEntity = ApartmentType.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "apartment_type_id")
    private ApartmentType apartmentType;

    private Integer number;

    private Integer floor;

    private Float square;
}
