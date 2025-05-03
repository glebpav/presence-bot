package com.xelari.presencebot.domain.entity.meeting;

import com.xelari.presencebot.domain.entity.AbstractEntity;
import com.xelari.presencebot.domain.entity.Attendance;
import com.xelari.presencebot.domain.entity.User;
import com.xelari.presencebot.domain.entity.team.Team;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "meeting")
public class Meeting extends AbstractEntity {

    @Column(name = "shceduled_time")
    private LocalDateTime scheduledTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private Set<Attendance> attendances;

}
