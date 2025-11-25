package com.newsmoa.app.domain;

import java.util.ArrayList;
import java.util.List;

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
public class User {

    // PK: id
    @Id
    @Column(name = "id", length = 10, nullable = false)
    private String id; // varchar(10)

    @Column(name = "password", length = 10, nullable = false)
    private String password; // varchar(10)
    
 // YourArticle과의 1:N 관계 (User가 1, YourArticle이 N)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YourArticle> yourArticles = new ArrayList<>();

}