package com.newsmoa.app.repository;

import com.newsmoa.app.domain.MentionStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<MentionStats, Long> {
    //사람 이름 랭킹
    @Query("select ms " +
            "from MentionStats ms " +
            "where not ms.personName = ms.partyName " +
            "and not ms.mentionCount = 0 " +
            "order by ms.mentionCount desc " +
            "limit 5")
    List<MentionStats> findTop5();
}
