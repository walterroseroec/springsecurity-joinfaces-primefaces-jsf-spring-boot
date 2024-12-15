package com.std.ec.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ruta")
public class Ruta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruta")
    private Long idRuta;
    @Column(name = "ruta_url")
    private String rutaUrl;

    /*
     Define una tabla de unión para mapear la relación muchos a muchos.
     La tabla "ruta_rol" se utiliza para relacionar las claves primarias de "Ruta" (id_ruta)
     con las claves primarias de "Rol" (id_rol).*/
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ruta_rol",
            joinColumns = @JoinColumn(name = "id_ruta"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private Set<Rol> roles;

}
