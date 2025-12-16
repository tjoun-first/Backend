package com.newsmoa.app.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "mention_stats", 
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_mention_person_party",
                    columnNames = {"person_name", "party_name"}
            )
        }
)
public class MentionStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long no;

    @Column(name = "person_name", nullable = false)
    String personName;

    @Column(name = "party_name", nullable = false)
    String partyName;
    
    @Column(name = "mention_count")
    Long mentionCount = 0L;

    @PrePersist
    public void prePersist() {
        if (mentionCount == null) {
            mentionCount = 0L;
        }
    }
    
    public MentionStats(String personName, String partyName, Long mentionCount) {
        this.personName = personName;
        this.partyName = partyName;
        this.mentionCount = mentionCount;
    }
}
