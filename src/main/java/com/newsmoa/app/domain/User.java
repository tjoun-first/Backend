package com.newsmoa.app.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user") // 테이블 이름이 'user'
public class User implements UserDetails {

    // PK: id
    @Id
    @Column(name = "id", length = 10, nullable = false)
    private String id; // varchar(10)

    // 암호화된 비밀번호를 저장하기 위해 길이를 255로 변경
    @Column(name = "password", length = 255, nullable = false)
    private String password; // varchar(255)
    
    // YourArticle과의 1:N 관계 (User가 1, YourArticle이 N)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YourArticle> yourArticles = new ArrayList<>();

    // UserDetails 구현
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 우선 모든 사용자에게 'ROLE_USER' 권한 부여
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.id; // username으로 id 사용
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 없음
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 없음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 없음
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화
    }
}