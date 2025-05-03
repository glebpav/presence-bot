package com.xelari.presencebot.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"members", "meetings"})
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "team")
public class Team extends AbstractEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "team",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<TeamMember> members;

    @OneToMany(
            mappedBy = "team",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Meeting> meetings;

}