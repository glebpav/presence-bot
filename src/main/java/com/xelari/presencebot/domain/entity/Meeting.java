package com.xelari.presencebot.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.HashSet;
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
