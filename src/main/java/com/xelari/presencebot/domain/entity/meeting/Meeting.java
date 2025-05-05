package com.xelari.presencebot.domain.entity.meeting;

import com.xelari.presencebot.domain.entity.AbstractEntity;
import com.xelari.presencebot.domain.entity.Attendance;
import com.xelari.presencebot.domain.entity.User;
import com.xelari.presencebot.domain.entity.team.Team;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "meeting")
public class Meeting extends AbstractEntity {

    // TODO: add name and description

    @Column(name = "shceduled_time")
    private LocalDateTime scheduledTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private Set<Attendance> attendances;

    @Column(name = "duration_minutes")
    private int durationMinutes;

    @Override
    public String toString() {
        return "Time: %s; duration: %d".formatted(scheduledTime.toString(), durationMinutes);
    }

}
