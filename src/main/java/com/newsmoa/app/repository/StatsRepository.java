package com.newsmoa.app.repository;

import com.newsmoa.app.domain.MentionStats;
import com.newsmoa.app.dto.AssemblyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<MentionStats, Long> {
    List<MentionStats> findTop5ByOrderByMentionCountDesc();
}
