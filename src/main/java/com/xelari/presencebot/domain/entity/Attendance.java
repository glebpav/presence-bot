package com.xelari.presencebot.domain.entity;

import com.xelari.presencebot.domain.entity.meeting.Meeting;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"meeting_id", "user_id"}),
        name = "attendance"
)
public class Attendance extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "attending")
    private Boolean attending;

}
