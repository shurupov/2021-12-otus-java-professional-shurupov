package ru.shurupov.homeowners.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ownership")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Ownership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Apartment.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    @ManyToOne(targetEntity = ApartmentUser.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private ApartmentUser apartmentUser;

    @ManyToOne(targetEntity = OwnershipType.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "ownership_type_id")
    private OwnershipType ownershipType;

    private Float share;

    private Boolean decisionMaker;
}
