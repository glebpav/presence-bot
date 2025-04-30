package com.xelari.presencebot.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity()
@Table(name = "user_table")
public class User extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "second_name")
    private String secondName;
    
    // This is id that uses in case if 
    // there is need to connect with user
    @Column(name = "back_connection")
    private Long backConnection;
    
}

