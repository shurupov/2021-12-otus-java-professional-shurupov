package ru.otus.crm.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "client")
@Getter
@Setter
@Builder
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "client")
    //@JoinColumn(name = "client_id", referencedColumnName = "id")
    private List<Phone> phones = List.of();

    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    @Override
    public Client clone() {
        Client client = new Client(this.id, this.name);
        if (address != null) {
            client.setAddress(new Address(address.getId(), client, address.getStreet()));
        }
        if (phones != null) {
            client.setPhones(
                phones.stream()
                    .map(p -> new Phone(p.getId(), client, p.getNumber()))
                    .collect(Collectors.toList())
            );
        }
        return client;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(name, client.name) && Objects.equals(address, client.address) && Objects.equals(phones, client.phones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phones);
    }
}
