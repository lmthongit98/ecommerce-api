package com.project.shopapp.models;

import com.project.shopapp.enums.HttpMethods;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends BaseEntity {

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "method")
    @Enumerated(EnumType.STRING)
    private HttpMethods method;

    @Column(name = "path")
    private String path;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();

}
