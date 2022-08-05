package ru.shurupov.homeowners.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "apartment_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(targetEntity = Ownership.class, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Ownership> ownerships = new ArrayList<>();

    private String fullName;
    private String shortName;
    private String phoneNumber;
    private String telegram;
    private String username;
    @JsonIgnore
    private String password;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Role> roles;

    @JsonIgnore
    private String regHash;
}
