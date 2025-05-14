package com.xelari.presencebot.domain.entity.meeting;

import com.xelari.presencebot.domain.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meeting_alert")
public class MeetingAlert extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @Column(name = "was_send")
    private Boolean wasSend;

}
