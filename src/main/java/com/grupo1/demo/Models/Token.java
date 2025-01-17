package com.grupo1.demo.Models;


import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "token" , nullable = false , unique = true)
    private String token;

    @Column(name = "expiresAt" , nullable = false)
    private Date expiresAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    public Usuario user;
}

