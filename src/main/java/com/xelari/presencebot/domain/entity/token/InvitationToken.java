package com.xelari.presencebot.domain.entity.token;

import com.xelari.presencebot.domain.entity.AbstractEntity;
import com.xelari.presencebot.domain.entity.team.Team;
import com.xelari.presencebot.domain.entity.team.TeamMember;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "invitation_token")
public class InvitationToken extends AbstractEntity {

    @Column(nullable = false, unique = true, name = "token")
    private String token;

    @ManyToOne()
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeamMember.Role assignedRole;

}


