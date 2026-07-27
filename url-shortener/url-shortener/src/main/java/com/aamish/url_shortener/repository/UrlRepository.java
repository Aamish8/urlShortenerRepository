package com.aamish.url_shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aamish.url_shortener.entity.Url;

public interface UrlRepository extends JpaRepository<Url,Long>{
		   Url findByShortCode(String shortCode);
		   boolean existsByShortCode(String shortCode);
		}
