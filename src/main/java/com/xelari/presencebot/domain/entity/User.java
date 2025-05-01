package com.xelari.presencebot.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity()
@Table(name = "user_table")
public class User {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "second_name")
    private String secondName;
    
    // This is id that uses in case if 
    // there is need to connect with user
    @Column(name = "back_connection")
    private Long backConnection;
    
}

