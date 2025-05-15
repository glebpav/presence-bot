package com.xelari.presencebot.domain.entity.attendance;

import com.xelari.presencebot.domain.entity.AbstractEntity;
import com.xelari.presencebot.domain.entity.User;
import com.xelari.presencebot.domain.entity.meeting.Meeting;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    // TODO: rename attending -> isAttending
    @Column(name = "attending")
    private Boolean attending;

}
