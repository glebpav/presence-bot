package com.xelari.presencebot.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true, exclude = {"team", "user"})
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "team_member",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "team_id"})
)
public class TeamMember extends AbstractEntity {

    public enum Role {MANAGER, MEMBER}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

}
