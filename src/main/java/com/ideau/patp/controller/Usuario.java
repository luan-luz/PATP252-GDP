package com.ideau.patp.controller;


import jakarta.persistence.*;

@Entity
@Table(name ="test")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;


}
