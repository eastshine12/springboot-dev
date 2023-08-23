package com.eastshine.springbootdev.repository;

import com.eastshine.springbootdev.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {

}
